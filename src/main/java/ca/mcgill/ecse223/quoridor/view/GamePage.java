package ca.mcgill.ecse223.quoridor.view;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.sql.Date;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Properties;
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

import ca.mcgill.ecse223.quoridor.controller.Quoridor223Controller;

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
	private JButton rotateWall;
	
	// forfeit game
	private JButton forfeit;
	private JButton confirm;
	
	// load, save, new, replay
	private JButton loadGame;
	private JButton saveGame;
	private JButton newGame;
	private JButton replayGame;
	
	// player's turn
	private JLabel playerTurn;
	
	public GamePage(){
		initComponent();
	}
	
	private void initComponent(){
		initFrame();
		
		//initialize the board
		boardComponent = new BoardComponent(500);
		boardComponent.setSize(new Dimension(500,500));
		boardComponent.setBackground(new Color(206,159,111));
		
		//initialize username
		userName1 = new JLabel("White", SwingConstants.CENTER);
		userName2 = new JLabel("Black", SwingConstants.CENTER);
		
		//initialize time (for now default to 10, later will get from model through controller)
		whiteRemainingTime = new Time(10);
		blackRemainingTime = new Time(10);
		whiteTime = new JLabel(whiteRemainingTime.toString(), SwingConstants.CENTER);
		blackTime = new JLabel(blackRemainingTime.toString(), SwingConstants.CENTER);
		
		//initialize grab, drop, rotate wall
		grabWall = new JButton("Grab Wall");
		dropWall = new JButton("Drop Wall");
		rotateWall = new JButton("Rotate Wall");
		
		//initialize forfeit 
		forfeit = new JButton("Forfeit");
		confirm = new JButton("Confirm");
		
		//initialize save, load, replay, new game
		saveGame = new JButton("Save Game");
		loadGame = new JButton("Load Game");
		replayGame = new JButton("Replay Game");
		newGame = new JButton("New Game");
				
		//player turn
		playerTurn = new JLabel("White Player: Your turn", SwingConstants.CENTER);
		
		
		//------------------------- Add Event Listener ----------------------------//
		grabWall.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				
			}
		});
		
		dropWall.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				
			}
		});
		
		rotateWall.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				
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
		
		
		//--------------------- Construct Page's Layout ----------------------------//
		GroupLayout layout = new GroupLayout(getContentPane());
		layout.setHorizontalGroup(
			layout.createParallelGroup(Alignment.TRAILING)
				.addGroup(layout.createSequentialGroup()
					.addContainerGap(457, Short.MAX_VALUE)
					.addGroup(layout.createParallelGroup(Alignment.LEADING)
						.addComponent(playerTurn, 500, 500, 500)
						.addGroup(layout.createSequentialGroup()
							.addGap(100)
							.addGroup(layout.createParallelGroup(Alignment.LEADING)
								.addComponent(boardComponent, 500, 500, 500)
								.addGroup(layout.createSequentialGroup()
									.addGap(30)
									.addComponent(newGame)
									.addComponent(saveGame)
									.addComponent(loadGame)
									.addComponent(replayGame)))
							.addGroup(layout.createParallelGroup(Alignment.LEADING)
								.addGroup(layout.createSequentialGroup()
									.addComponent(userName1)
									.addComponent(whiteTime))
								.addGroup(layout.createSequentialGroup()
									.addComponent(userName2)
									.addComponent(blackTime))
								.addGroup(layout.createSequentialGroup()
									.addComponent(grabWall)
									.addComponent(dropWall)
									.addComponent(rotateWall))))))
		);
		layout.setVerticalGroup(
			layout.createParallelGroup(Alignment.LEADING)
				.addGroup(layout.createSequentialGroup()
					.addGap(30)
					.addComponent(playerTurn)
					.addGap(30)
					.addGroup(layout.createParallelGroup(Alignment.LEADING)
						.addGroup(layout.createSequentialGroup()
							.addComponent(boardComponent, 500, 500, 500)
							.addGap(30)
							.addGroup(layout.createParallelGroup(Alignment.LEADING)
								.addComponent(newGame)
								.addComponent(saveGame)
								.addComponent(loadGame)
								.addComponent(replayGame)))
						.addGroup(layout.createSequentialGroup()
							.addGroup(layout.createParallelGroup(Alignment.LEADING)
								.addComponent(userName1)
								.addComponent(whiteTime))
							.addGroup(layout.createParallelGroup(Alignment.LEADING)
								.addComponent(userName2)
								.addComponent(blackTime))
							.addGroup(layout.createParallelGroup(Alignment.LEADING)
								.addComponent(grabWall)
								.addComponent(dropWall)
								.addComponent(rotateWall))))
					.addContainerGap(49, Short.MAX_VALUE))
		);
		
		getContentPane().setLayout(layout);
		
		layout.setAutoCreateGaps(true);
		layout.setAutoCreateContainerGaps(true);
	}
	
	
	private void refreshData() {
		// TODO: call transfer objects' method to query data and update the game's states
	}
	
	private void initFrame() {
		this.setSize(1400, 720);
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
