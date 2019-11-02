package ca.mcgill.ecse223.quoridor.view;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.sql.Date;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Properties;
import java.util.Timer;
import java.util.TimerTask;
import java.sql.Time;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingConstants;
import javax.swing.WindowConstants;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;

import ca.mcgill.ecse223.quoridor.controller.GameNotRunningException;
import ca.mcgill.ecse223.quoridor.controller.InvalidOperationException;
import ca.mcgill.ecse223.quoridor.controller.Quoridor223Controller;
import ca.mcgill.ecse223.quoridor.controller.TOGame;
import ca.mcgill.ecse223.quoridor.controller.TOWall;

import javax.swing.JSplitPane;
import java.awt.BorderLayout;
import java.awt.Panel;
import java.awt.GridLayout;
import java.awt.Button;
import javax.swing.BoxLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.Icon;
import javax.swing.ImageIcon;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.LayoutStyle.ComponentPlacement;
import java.awt.Font;
import javax.swing.JPanel;
import java.awt.CardLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import javax.swing.JLayeredPane;
import javax.swing.JOptionPane;
import javax.swing.JTextPane;
import java.awt.SystemColor;
import javax.swing.JTextArea;

public class GamePage extends JFrame {
	// timer
	private static Timer timer;

	// board
	private static BoardComponent boardComponent;

	// username
	private JLabel userName1;
	private JLabel userName2;
	private String name1;
	private String name2;
	private String userToMove;

	// remaining time
	private JLabel whiteTime;
	private JLabel blackTime;

	// grab, drop, rotate wall button
	private JButton grabWall;
	private JButton dropWall;
	private JButton btnRotateWall;

	// forfeit game
	private JButton forfeit;
	private JButton saveGame;
	private JButton newGame;
	private JButton replayGame;

	// player's turn
	private JLabel gameMessage;

	// Move buttons
	private JButton btnUp;
	private JButton btnDown;
	private JButton btnRight;
	private JButton btnLeft;

	public GamePage() {
		initComponent();
	}

	private void initComponent() {
		initFrame();

		// initialize timer
		timer = new Timer();

		// initialize the board
		boardComponent = new BoardComponent(500);
		boardComponent.setLocation(90, 95);
		boardComponent.setSize(new Dimension(500, 500));
		boardComponent.setBackground(new Color(206, 159, 111));

		// initialize username 1
		TOGame players = Quoridor223Controller.getListOfPlayers();
		name1 = players.getPlayerOne();
		userName1 = new JLabel(name1, SwingConstants.CENTER);
		userName1.setFont(new Font("Tahoma", Font.PLAIN, 14));
		userName1.setBounds(620, 94, 46, 33);
		
		
		// initialize username 2
		name2 = players.getPlayerTwo();
		userName2 = new JLabel(name2, SwingConstants.CENTER);
		userName2.setFont(new Font("Tahoma", Font.PLAIN, 14));
		userName2.setBounds(876, 94, 50, 32);
		
		userToMove = players.getPlayerToMove();

		// initialize time 
		whiteTime = new JLabel(players.getPlayerOneTime().toString());
		whiteTime.setFont(new Font("Tahoma", Font.PLAIN, 14));
		whiteTime.setBounds(676, 94, 64, 33);
		
		blackTime = new JLabel(players.getPlayerTwoTime().toString());
		blackTime.setFont(new Font("Tahoma", Font.PLAIN, 14));
		blackTime.setBounds(936, 94, 64, 33);

		// initialize grab, drop, rotate wall
		grabWall = new JButton("Grab Wall");
		grabWall.setBackground(new Color(204, 153, 102));
		grabWall.setFont(new Font("Tahoma", Font.PLAIN, 13));
		grabWall.setBounds(620, 145, 120, 40);
		dropWall = new JButton("Drop Wall");
		dropWall.setBackground(new Color(204, 153, 102));
		dropWall.setFont(new Font("Tahoma", Font.PLAIN, 13));
		dropWall.setBounds(750, 145, 120, 40);
		btnRotateWall = new JButton("Rotate Wall");
		btnRotateWall.setBackground(new Color(204, 153, 102));
		btnRotateWall.setFont(new Font("Tahoma", Font.PLAIN, 13));
		btnRotateWall.setBounds(880, 145, 120, 40);
		
		// initialize forfeit, confirm
		forfeit = new JButton("Forfeit Game");
		forfeit.setBackground(new Color(204, 153, 102));
		forfeit.setFont(new Font("Tahoma", Font.PLAIN, 13));
		forfeit.setBounds(750, 507, 120, 40);

		// initialize save, replay, new game
		saveGame = new JButton("Save Game");
		saveGame.setBackground(new Color(204, 153, 102));
		saveGame.setFont(new Font("Tahoma", Font.PLAIN, 13));
		saveGame.setBounds(285, 606, 110, 40);
		replayGame = new JButton("Replay Game");
		replayGame.setBackground(new Color(204, 153, 102));
		replayGame.setFont(new Font("Tahoma", Font.PLAIN, 13));
		replayGame.setBounds(480, 606, 110, 40);
		newGame = new JButton("New Game");
		newGame.setBackground(new Color(204, 153, 102));
		newGame.setFont(new Font("Tahoma", Font.PLAIN, 13));
		newGame.setBounds(90, 606, 110, 40);

		// button size
		forfeit.setPreferredSize(new Dimension(40, 40));

		// button color
		// forfeit.setBackground(Color.BLUE);

		// player turn
		gameMessage = new JLabel("Quoridor Game Notification Center", SwingConstants.CENTER);
		gameMessage.setFont(new Font("Tahoma", Font.PLAIN, 16));
		gameMessage.setBounds(90, 28, 500, 46);

		// move buttons
		btnLeft = new JButton("LEFT");
		btnLeft.setBackground(new Color(204, 153, 102));
		btnLeft.setFont(new Font("Tahoma", Font.PLAIN, 13));
		btnLeft.setBounds(678, 356, 80, 80);
		btnRight = new JButton("RIGHT");
		btnRight.setBackground(new Color(204, 153, 102));
		btnRight.setFont(new Font("Tahoma", Font.PLAIN, 13));
		btnRight.setBounds(860, 356, 80, 80);
		btnDown = new JButton("DOWN");
		btnDown.setBackground(new Color(204, 153, 102));
		btnDown.setFont(new Font("Tahoma", Font.PLAIN, 13));
		btnDown.setBounds(770, 356, 80, 80);
		btnUp = new JButton("UP");
		btnUp.setBackground(new Color(204, 153, 102));
		btnUp.setFont(new Font("Tahoma", Font.PLAIN, 13));
		btnUp.setBounds(770, 266, 80, 80);

		// ------------------------- Add to Panel ----------------------------//
		getContentPane().setLayout(null);
		getContentPane().add(gameMessage);
		getContentPane().add(newGame);
		getContentPane().add(saveGame);
		getContentPane().add(replayGame);
		getContentPane().add(boardComponent);
		getContentPane().add(userName1);
		getContentPane().add(whiteTime);
		getContentPane().add(userName2);
		getContentPane().add(blackTime);
		getContentPane().add(grabWall);
		getContentPane().add(forfeit);
		getContentPane().add(dropWall);
		getContentPane().add(btnRotateWall);
		getContentPane().add(btnUp);
		getContentPane().add(btnDown);
		getContentPane().add(btnRight);
		getContentPane().add(btnLeft);

		// ------------------------- Add Event Listener ----------------------------//
		// set game to run
		boardComponent.repaint();
		Quoridor223Controller.setGameToRun();
		
		timer.scheduleAtFixedRate(new TimerTask() {
			@Override
			public void run() {
				refreshTime();
			}
		}, 1000, 1000);

		grabWall.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				try {
					Quoridor223Controller.grabWall();
				} catch (InvalidOperationException eGrab) {
					gameMessage.setText(eGrab.getLocalizedMessage());
				} catch (GameNotRunningException eGrab) {
					gameMessage.setText(eGrab.getLocalizedMessage());
				}
				boardComponent.repaint();
			}
		});
		btnUp.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					Quoridor223Controller.moveWall(TOWall.Side.Up);
				} catch (GameNotRunningException ex) {
					gameMessage.setText(ex.getLocalizedMessage());
					// set the notification panel to message
				} catch (InvalidOperationException ex) {
					gameMessage.setText(ex.getLocalizedMessage());
					// TODO: handle exception
				}
				boardComponent.repaint();
			}

		});

		btnDown.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					Quoridor223Controller.moveWall(TOWall.Side.Down);
				}catch (GameNotRunningException ex) {
					gameMessage.setText(ex.getLocalizedMessage());
					// set the notification panel to message
				} catch (InvalidOperationException ex) {
					gameMessage.setText(ex.getLocalizedMessage());
					// TODO: handle exception
				}
				boardComponent.repaint();
			}
		});

		btnLeft.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					Quoridor223Controller.moveWall(TOWall.Side.Left);
				} catch (GameNotRunningException ex) {
					gameMessage.setText(ex.getLocalizedMessage());
					// set the notification panel to message
				} catch (InvalidOperationException ex) {
					gameMessage.setText(ex.getLocalizedMessage());
					// TODO: handle exception
				}
				boardComponent.repaint();
			}
		});

		btnRight.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					Quoridor223Controller.moveWall(TOWall.Side.Right);
				} catch (GameNotRunningException ex) {
					gameMessage.setText(ex.getLocalizedMessage());
					// set the notification panel to message
				} catch (InvalidOperationException ex) {
					gameMessage.setText(ex.getLocalizedMessage());
					// TODO: handle exception
				}
				boardComponent.repaint();
			}

		});
		btnRotateWall.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					Quoridor223Controller.rotateWall();
				} catch (GameNotRunningException ex) {
					gameMessage.setText(ex.getLocalizedMessage());
					// set the notification panel to message
				} catch (InvalidOperationException ex) {
					gameMessage.setText(ex.getLocalizedMessage());
					// TODO: handle exception
				}
				boardComponent.repaint();
			}
		});

		dropWall.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				try {
					Quoridor223Controller.dropWall();
					gameMessage.setText("It is "+Quoridor223Controller.getCurrentPlayerName()+"'s Turn !!");
				} catch (GameNotRunningException ex) {
					gameMessage.setText(ex.getMessage());
					// set the notification panel to message
				} catch (InvalidOperationException ex) {
					gameMessage.setText(ex.getMessage());
					// TODO: handle exception
				}
				boardComponent.repaint();
			}

		});

		forfeit.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {

			}
		});

		newGame.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {

			}
		});

		saveGame.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				userClicksToSaveGame();
			}
		});

		replayGame.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {

			}
		});
	}

	private void refreshTime() {
		if(whiteTime.getText().equals("00:00:00") || blackTime.getText().equals("00:00:00")) {
			timer.cancel();
		}else {
			if (userToMove.equals(name1)) {
				Quoridor223Controller.setThinkingTime(new Time(Time.valueOf(whiteTime.getText()).getTime() - 1000), name1);
			} else {
				Quoridor223Controller.setThinkingTime(new Time(Time.valueOf(blackTime.getText()).getTime() - 1000), name2);
			}

			TOGame players = Quoridor223Controller.getListOfPlayers();
			userToMove = players.getPlayerToMove();

			if (players.getPlayerToMove().equals(players.getPlayerOne())) {
				whiteTime.setText(players.getPlayerOneTime().toString());
			} else {
				blackTime.setText(players.getPlayerTwoTime().toString());
			}
		}
	}

	private void initFrame() {
		this.setSize(1035, 720);
		setTitle("Quoridor Game");
		this.setBackground(Color.LIGHT_GRAY);
	}

	private void userClicksToSaveGame() {
		String filename = null;
		Boolean saveSuccessful = false;

		filename = saveGameInputDialog("Enter the file path below:", "Save Game As", null);
		
		if (filename != null) {
			try {
				saveSuccessful = Quoridor223Controller.savePosition(filename);
			} catch (IOException e1) {
				e1.printStackTrace();
			}

			// keep trying to save until the user cancels save attempt, or the save is successful
			while (saveSuccessful == false) {
	
				filename = saveGameInputDialog("Enter a valid file path here:", "Save Game As", null);
				
				// if the user cancels the save, return
				if (filename == null) {
					return;
				}
				
				// otherwise, keep trying to save
				try {
					saveSuccessful = Quoridor223Controller.savePosition(filename);
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		}
		return;
	}

	private String saveGameInputDialog(String message, String title, String initialValue) {
		String userInput;

		userInput = (String) JOptionPane.showInputDialog((Component) boardComponent, (Object) message, title,
				JOptionPane.INFORMATION_MESSAGE, (Icon) null, (Object[]) null, (Object) initialValue);

		return userInput;
	}

	public static int userOverwritePrompt(String filename) {

		int overWriteAllowed = JOptionPane.showConfirmDialog((Component) boardComponent,
				(Object) "Permisssion to Overwrite file:\n\"" + filename + "\"", "Overwrite File",
				JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, (Icon) null);

		return overWriteAllowed;
	}

	public static void errorPrompt(String error) {
		JOptionPane.showConfirmDialog((Component) boardComponent,
				(Object) "The Following Error Has Occurred:\n\"" + error + "\"", "Operation Error",
				JOptionPane.OK_OPTION, JOptionPane.ERROR_MESSAGE, (Icon) null);
	}

	public boolean hasWallInHand() {
		return boardComponent.hasWallInHand();
	}
	
	public String getGameMessage() {
		return gameMessage.getText();
	}
	public void clickMoveWall(String dir) {
		if(dir.equalsIgnoreCase("UP"))btnUp.doClick();
		if(dir.equalsIgnoreCase("DOWN"))btnDown.doClick();
		if(dir.equalsIgnoreCase("LEFT"))btnLeft.doClick();
		if(dir.equalsIgnoreCase("RIGHT"))btnRight.doClick();

	}
	public void clickDropWall() {
		dropWall.doClick();
	}
	public void refresh() {
		boardComponent.repaint();
	}
	public void delete() {
		boardComponent=null;
	}
}
