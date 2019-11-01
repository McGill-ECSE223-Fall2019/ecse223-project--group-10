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
	private Time whiteRemainingTime;
	private Time blackRemainingTime;
	private JLabel whiteTime;
	private JLabel blackTime;

	// grab, drop, rotate wall button
	private JButton grabWall;
	private JButton dropWall;
	private JButton btnRotateWall;

	// forfeit game
	private JButton forfeit;

	// load, save, new, replay
	private JButton loadGame;
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

		// initialize username
		TOGame players = Quoridor223Controller.getListOfPlayers();
		userName1 = new JLabel(players.getPlayerOne(), SwingConstants.CENTER);
		name1 = players.getPlayerOne();
		userName1.setFont(new Font("Tahoma", Font.PLAIN, 14));
		userName1.setBounds(876, 94, 50, 32);
		userName2 = new JLabel(players.getPlayerTwo(), SwingConstants.CENTER);
		name2 = players.getPlayerTwo();
		userName2.setFont(new Font("Tahoma", Font.PLAIN, 14));
		userName2.setBounds(620, 94, 46, 33);
		userToMove = players.getPlayerToMove();

		// initialize time (for now default to 10, later will get from model through
		// controller)
		whiteRemainingTime = players.getPlayerOneTime();
		String wTime[] = whiteRemainingTime.toString().split(":");
		blackRemainingTime = players.getPlayerTwoTime();
		String bTime[] = blackRemainingTime.toString().split(":");
		whiteTime = new JLabel(wTime[1] + ":" + wTime[2], SwingConstants.CENTER);
		whiteTime.setFont(new Font("Tahoma", Font.PLAIN, 14));
		whiteTime.setBounds(936, 94, 64, 33);
		blackTime = new JLabel(bTime[1] + ":" + bTime[2], SwingConstants.CENTER);
		blackTime.setFont(new Font("Tahoma", Font.PLAIN, 14));
		blackTime.setBounds(676, 94, 64, 33);

		// initialize grab, drop, rotate wall
		grabWall = new JButton("Grab Wall");
		grabWall.setBackground(new Color(184, 134, 11));
		grabWall.setFont(new Font("Tahoma", Font.PLAIN, 13));
		grabWall.setBounds(620, 145, 120, 40);
		dropWall = new JButton("Drop Wall");
		dropWall.setFont(new Font("Tahoma", Font.PLAIN, 13));
		dropWall.setBounds(750, 145, 120, 40);
		btnRotateWall = new JButton("Rotate Wall");
		btnRotateWall.setFont(new Font("Tahoma", Font.PLAIN, 13));
		btnRotateWall.setBounds(880, 145, 120, 40);
		// initialize forfeit, confirm
		forfeit = new JButton("Forfeit Game");
		forfeit.setFont(new Font("Tahoma", Font.PLAIN, 13));
		forfeit.setBounds(750, 498, 120, 40);

		// initialize save, load, replay, new game
		saveGame = new JButton("Save Game");
		saveGame.setFont(new Font("Tahoma", Font.PLAIN, 13));
		saveGame.setBounds(220, 606, 110, 40);
		loadGame = new JButton("Load Game");
		loadGame.setFont(new Font("Tahoma", Font.PLAIN, 13));
		loadGame.setBounds(350, 606, 110, 40);
		replayGame = new JButton("Replay Game");
		replayGame.setFont(new Font("Tahoma", Font.PLAIN, 13));
		replayGame.setBounds(480, 606, 110, 40);
		newGame = new JButton("New Game");
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

		btnRotateWall = new JButton("Rotate Wall");
		btnRotateWall.setFont(new Font("Tahoma", Font.PLAIN, 13));
		btnRotateWall.setBounds(880, 145, 120, 40);

		// move buttons
		btnLeft = new JButton("LEFT");
		btnLeft.setFont(new Font("Tahoma", Font.PLAIN, 13));
		btnLeft.setBounds(680, 336, 80, 80);
		btnRight = new JButton("RIGHT");
		btnRight.setFont(new Font("Tahoma", Font.PLAIN, 13));
		btnRight.setBounds(860, 336, 80, 80);
		btnDown = new JButton("DOWN");
		btnDown.setFont(new Font("Tahoma", Font.PLAIN, 13));
		btnDown.setBounds(770, 336, 80, 80);
		btnUp = new JButton("UP");
		btnUp.setFont(new Font("Tahoma", Font.PLAIN, 13));
		btnUp.setBounds(770, 246, 80, 80);

		// ------------------------- Add to Panel ----------------------------//
		getContentPane().setLayout(null);
		getContentPane().add(gameMessage);
		getContentPane().add(newGame);
		getContentPane().add(saveGame);
		getContentPane().add(loadGame);
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
					gameMessage.setText("It is "+Quoridor223Controller.getCurrentPlayer()+"'s Turn !!");
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

		forfeit.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {

			}
		});

		newGame.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {

			}
		});

		saveGame.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (e.getButton() == MouseEvent.BUTTON1) {
					userClicksToSaveGame();
				}
			}
		});

		loadGame.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (e.getButton() == MouseEvent.BUTTON1) {
					userClicksToLoadGame();
				}
			}
		});

		replayGame.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {

			}
		});
	}

	private void refreshTime() {
		if (userToMove.equals(name1)) {
			Quoridor223Controller.setThinkingTime(new Time(whiteRemainingTime.getTime() - 1000), name1);
		} else {
			Quoridor223Controller.setThinkingTime(new Time(blackRemainingTime.getTime() - 1000), name2);
		}

		TOGame players = Quoridor223Controller.getListOfPlayers();
		userToMove = players.getPlayerToMove();

		if (players.getPlayerToMove().equals(players.getPlayerOne())) {
			whiteRemainingTime = new Time(players.getPlayerOneTime().getTime());
			String wTime[] = whiteRemainingTime.toString().split(":");
			whiteTime.setText(wTime[1] + ":" + wTime[2]);
		} else {
			blackRemainingTime = new Time(players.getPlayerTwoTime().getTime());
			String bTime[] = blackRemainingTime.toString().split(":");
			blackTime.setText(bTime[1] + ":" + bTime[2]);
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

		filename = (String) saveGameInputDialog("Enter the file path below:", "Save Game As", null);
		try {
			saveSuccessful = Quoridor223Controller.savePosition(filename);
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			// e1.printStackTrace();
		}

		// keep trying to save until the user cancels save attempt, or the save is
		// successful
		while (filename == null || saveSuccessful == false) {

			filename = (String) saveGameInputDialog("Enter a valid file path here:", "Save Game As", "");

			if (!filename.equals(null)) {
				try {
					saveSuccessful = Quoridor223Controller.savePosition(filename);
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		}
	}

	private Object saveGameInputDialog(String message, String title, String initialValue) {
		Object userInput;

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

	private void userClicksToLoadGame() {
		String filename = null;
		Boolean saveSuccessful = false;

		filename = (String) loadGameInputDialog("Enter the file path below:", "Load Game From File", null);
		saveSuccessful = Quoridor223Controller.loadPosition(filename);

		// keep trying to save until the user cancels save attempt, or the save is
		// successful
		while (filename == null || saveSuccessful == false) {

			filename = (String) loadGameInputDialog("Enter a valid file path here:", "Load Game From File", "");

			if (!filename.equals(null)) {
				saveSuccessful = Quoridor223Controller.loadPosition(filename);
			}
		}
	}

	private Object loadGameInputDialog(String message, String title, String initialValue) {
		Object userInput;

		userInput = (String) JOptionPane.showInputDialog((Component) boardComponent, (Object) message, title,
				JOptionPane.INFORMATION_MESSAGE, (Icon) null, (Object[]) null, (Object) initialValue);

		return userInput;
	}

	public static void errorPrompt(String error) {
		JOptionPane.showConfirmDialog((Component) boardComponent,
				(Object) "The Following Error Has Occurred:\n\"" + error + "\"", "Operation Error",
				JOptionPane.OK_OPTION, JOptionPane.ERROR_MESSAGE, (Icon) null);
	}
	public TOWall getWallInHand() {
		return boardComponent.getWallInHand();
	}
	public String getGameMessage() {
		return gameMessage.getText();
	}
}
