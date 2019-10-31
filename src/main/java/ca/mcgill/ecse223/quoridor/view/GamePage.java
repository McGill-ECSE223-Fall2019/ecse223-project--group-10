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
import ca.mcgill.ecse223.quoridor.controller.Quoridor223Controller;
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

public class GamePage extends JFrame{
	
	// board
	private static BoardComponent boardComponent;
	
	// username
	private JLabel userName1;
	private JLabel userName2;
	
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
	private JLabel playerTurn;
	
	//Move buttons
	private JButton btnUp;
	private JButton btnDown;
	private JButton btnRight;
	private JButton btnLeft;
	
	public GamePage(){
		initComponent();
	}
	
	private void initComponent(){
		initFrame();
		
		//initialize the board
		boardComponent = new BoardComponent(500);
		boardComponent.setLocation(90, 95);
		boardComponent.setSize(new Dimension(500, 500));
		boardComponent.setBackground(new Color(206,159,111));
		
		//initialize username
		//initialize username
		userName1 = new JLabel(Quoridor223Controller.getWhitePlayerName(), SwingConstants.CENTER);
		userName1.setFont(new Font("Tahoma", Font.PLAIN, 14));
		userName1.setBounds(876, 94, 50, 32);
		userName2 = new JLabel(Quoridor223Controller.getBlackPlayerName(), SwingConstants.CENTER);
		userName2.setFont(new Font("Tahoma", Font.PLAIN, 14));
		userName2.setBounds(620, 94, 46, 33);
		
		//initialize time (for now default to 10, later will get from model through controller)
		whiteRemainingTime = new Time(10);
		blackRemainingTime = new Time(10);
		whiteTime = new JLabel(whiteRemainingTime.toString(), SwingConstants.CENTER);
		whiteTime.setFont(new Font("Tahoma", Font.PLAIN, 14));
		whiteTime.setBounds(936, 94, 64, 33);
		blackTime = new JLabel(blackRemainingTime.toString(), SwingConstants.CENTER);
		blackTime.setFont(new Font("Tahoma", Font.PLAIN, 14));
		blackTime.setBounds(676, 94, 64, 33);
		
		//initialize grab, drop, rotate wall
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
		//initialize forfeit, confirm
		forfeit = new JButton("Forfeit Game");
		forfeit.setFont(new Font("Tahoma", Font.PLAIN, 13));
		forfeit.setBounds(750, 498, 120, 40);
		
		//initialize save, load, replay, new game
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
		
		//button size
		forfeit.setPreferredSize(new Dimension(40, 40));
		
		//button color
		//forfeit.setBackground(Color.BLUE);
		
		//player turn
		playerTurn = new JLabel("Quoridor Game Notification Center", SwingConstants.CENTER);
		playerTurn.setFont(new Font("Tahoma", Font.PLAIN, 16));
		playerTurn.setBounds(90, 28, 500, 46);
		
		//move button
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
		
		//------------------------- Add Event Listener ----------------------------//
		grabWall.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				
			}
		});
		
		dropWall.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				try {
					Quoridor223Controller.dropWall();
				}catch(Exception e) {}

				if (!Quoridor223Controller.hasWallMoveCandidate()) failToValidatePosition();
				else logSwitchPlayer();
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
				if(e.getButton() == MouseEvent.BUTTON1){
					userClicksToSaveGame();
				}
			}
		});
		
		loadGame.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if(e.getButton() == MouseEvent.BUTTON1){
					userClicksToLoadGame();
				}
			}
		});
				
		replayGame.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				
			}
		});
		
		btnUp.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					Quoridor223Controller.moveWall(TOWall.Side.Up);
				}
				catch(Exception ex) {
					//set the notification panel to message
				}
				boardComponent.repaint();
			}
			
		});
		
		btnDown.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					Quoridor223Controller.moveWall(TOWall.Side.Down);
				}
				catch(Exception ex) {
					//set the notification panel to message
				}
				boardComponent.repaint();
			}
		});
		
		btnLeft.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					Quoridor223Controller.moveWall(TOWall.Side.Left);
				}
				catch(Exception ex) {
					//set the notification panel to message
				}
				boardComponent.repaint();
			}
		});
		
		btnRight.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					Quoridor223Controller.moveWall(TOWall.Side.Right);
				}
				catch(Exception ex) {
					//set the notification panel to message
				}
				boardComponent.repaint();
			}
			
		});
		
		getContentPane().setLayout(null);
		getContentPane().add(playerTurn);
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

		refreshData();
	}
	
	
	// @sacha: would the refreshData method be the real switch player element for the UI ?
	private void refreshData() {
		// TODO: call transfer objects' method to query data and update the game's states
		Timer timer = new Timer();
		TimerTask refreshTask = new TimerTask() {
		    @Override
		    public void run() {
		    	String moving_playerName = Quoridor223Controller.getPlayerMovingName();
				Quoridor223Controller.updateTime();
		    	refreshTimePanels();
		    };  		
		};
		// update all elements every seconds
		timer.scheduleAtFixedRate(refreshTask,0,1000);    
	}

	/**
	 * @author Sacha Lévy
	 * panel to refresh the data and then delete it 
	 * */
	private void failToValidatePosition() {
		// interact with the playerTurn element
		String name = Quoridor223Controller.getPlayerMovingName();
		playerTurn.setText(String.format("%s: this move is invalid! Please try another.", name));
	}
	
	private void logSwitchPlayer() {
		// interact witht the playerTurn element
		try {
			Quoridor223Controller.SwitchPlayer();
		} catch (UnsupportedOperationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (GameNotRunningException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String name = Quoridor223Controller.getPlayerMovingName();
		playerTurn.setText(String.format("%s: it is now your turn to move!", name));
	}
	
	private void refreshTimePanels() {
		whiteTime.setText(Quoridor223Controller.getWhiteRemainingTime().toString());
		blackTime.setText(Quoridor223Controller.getBlackRemainingTime().toString());
	}
	
	private void initFrame() {
		this.setSize(1035, 720);
		setTitle("Quoridor Game");
		this.setBackground(Color.LIGHT_GRAY);
	}
	
	private void userClicksToSaveGame() {
		String filename = null;
		Boolean saveSuccessful = false;
		
		filename = (String)saveGameInputDialog("Enter the file path below:", "Save Game As", null);
		try {
			saveSuccessful = Quoridor223Controller.savePosition(filename);
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			//e1.printStackTrace();
		}
		
		// keep trying to save until the user cancels save attempt, or the save is successful
		while( filename == null || saveSuccessful == false) {
			
			filename = (String)saveGameInputDialog("Enter a valid file path here:", "Save Game As", "");
			
			if(!filename.equals(null)) {
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
		
		userInput = (String) JOptionPane.showInputDialog((Component)boardComponent,
					(Object)message, title, JOptionPane.INFORMATION_MESSAGE, 
					(Icon)null, (Object[])null, (Object)initialValue);
		
		return userInput;
	}
	
	public static int userOverwritePrompt(String filename) {
				
		int overWriteAllowed = JOptionPane.showConfirmDialog((Component)boardComponent,
				(Object)"Permisssion to Overwrite file:\n\"" + filename + "\"", "Overwrite File",
				JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, (Icon)null);
		
		return overWriteAllowed;
	}
	
	private void userClicksToLoadGame() {
		String filename = null;
		Boolean saveSuccessful = false;
		
		filename = (String)loadGameInputDialog("Enter the file path below:", "Load Game From File", null);
		saveSuccessful = Quoridor223Controller.loadPosition(filename);
		
		// keep trying to save until the user cancels save attempt, or the save is successful
		while( filename == null || saveSuccessful == false) {
			
			filename = (String)loadGameInputDialog("Enter a valid file path here:", "Load Game From File", "");
			
			if(!filename.equals(null)) {
				saveSuccessful = Quoridor223Controller.loadPosition(filename);
			}
		}
	}
	
	private Object loadGameInputDialog(String message, String title, String initialValue) {
		Object userInput;
		
		userInput = (String) JOptionPane.showInputDialog((Component)boardComponent,
					(Object)message, title, JOptionPane.INFORMATION_MESSAGE, 
					(Icon)null, (Object[])null, (Object)initialValue);
		
		return userInput;
	}
	
	public static void errorPrompt(String error) {
		JOptionPane.showConfirmDialog((Component)boardComponent,
				(Object)"The Following Error Has Occurred:\n\"" + error + "\"", "Operation Error",
				JOptionPane.OK_OPTION, JOptionPane.ERROR_MESSAGE, (Icon)null);
	}
}
