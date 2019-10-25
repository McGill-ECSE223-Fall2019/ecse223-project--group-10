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
import javax.swing.JSplitPane;
import java.awt.BorderLayout;
import java.awt.Panel;
import java.awt.GridLayout;
import java.awt.Button;
import javax.swing.BoxLayout;
import javax.swing.GroupLayout.Alignment;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.LayoutStyle.ComponentPlacement;
import java.awt.Font;
import javax.swing.JPanel;
import java.awt.CardLayout;

public class GamePage extends JFrame{
	
	// board
	private BoardComponent boardComponent;

	// username
	private JLabel userName1;
	private JLabel userName2;
	
	// remaining time
	private Time whiteRemainingTime;
	private Time blackRemainingTime;
	private JLabel whiteTime;
	private JLabel blackTime;
	
	// grab, drop, rotate wall button
	private JButton whiteGrabWall;
	private JButton whiteDropWall;
	private JButton whiteRotateWall;
	
	private JButton blackGrabWall;
	private JButton blackDropWall;
	private JButton blackRotateWall;
	
	// forfeit game
	private JButton whiteForfeit;
	private JButton blackForfeit;
	
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
		whiteGrabWall = new JButton("Grab Wall");
		whiteDropWall = new JButton("Drop Wall");
		whiteRotateWall = new JButton("Rotate Wall");
		blackGrabWall = new JButton("Grab Wall");
		blackDropWall = new JButton("Drop Wall");
		blackRotateWall = new JButton("Rotate Wall");
		
		//initialize forfeit 
		whiteForfeit = new JButton("Forfeit");
		blackForfeit = new JButton("Forfeit");
		
		//initialize save, load, replay, new game
		saveGame = new JButton("Save Game");
		loadGame = new JButton("Load Game");
		replayGame = new JButton("Replay Game");
		newGame = new JButton("New Game");
		
		//player turn
		playerTurn = new JLabel("White Player: Your turn", SwingConstants.CENTER);
		
		
		//------------------------- Add Event Listener ----------------------------//
		whiteGrabWall.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				
			}
		});
		
		whiteDropWall.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				
			}
		});
		
		whiteRotateWall.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				
			}
		});
		
		blackGrabWall.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				
			}
		});
		
		blackDropWall.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				
			}
		});
		
		blackRotateWall.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				
			}
		});
		
		whiteForfeit.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				
			}
		});
		
		blackForfeit.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				
			}
		});
		
		newGame.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				
			}
		});
		
		saveGame.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				
			}
		});
		
		loadGame.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				
			}
		});
		
		replayGame.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				
			}
		});
		
		
		//--------------------- Construct Page's Layout ----------------------------//
		GroupLayout layout = new GroupLayout(getContentPane());
		
		getContentPane().setLayout(layout);
		
		layout.setAutoCreateGaps(true);
		layout.setAutoCreateContainerGaps(true);
		
		layout.setHorizontalGroup(
			layout.createParallelGroup(Alignment.TRAILING)
				.addGroup(layout.createParallelGroup()
					.addGroup(layout.createSequentialGroup()
						.addGap(400)
						.addComponent(playerTurn, 500, 500, 500))
					.addGroup(layout.createSequentialGroup()
							.addGroup(layout.createParallelGroup()
								.addComponent(userName1, 400, 400, 400)
								.addComponent(whiteTime, 400, 400, 400)
								.addGroup(layout.createSequentialGroup()
									.addGap(45)
									.addComponent(whiteGrabWall)
									.addComponent(whiteDropWall)
									.addComponent(whiteRotateWall))
							)
							.addGroup(layout.createParallelGroup()
								.addComponent(boardComponent,500, 500, 500)	
								.addGroup(layout.createSequentialGroup()
									.addGap(35)
									.addComponent(newGame)
									.addComponent(saveGame)
									.addComponent(loadGame)
									.addComponent(replayGame)))
							.addGroup(layout.createParallelGroup()
								.addComponent(userName2, 400, 400, 400)
								.addComponent(blackTime, 400, 400, 400)
								.addGroup(layout.createSequentialGroup()
									.addGap(45)
									.addComponent(blackGrabWall)
									.addComponent(blackDropWall)
									.addComponent(blackRotateWall))
								)
					)
			)
		);
		
		layout.setVerticalGroup(
			layout.createParallelGroup(Alignment.LEADING)
				.addGroup(layout.createSequentialGroup()
					.addGap(30)
					.addComponent(playerTurn)
					.addGap(30)
					.addGroup(layout.createParallelGroup()
						.addGroup(layout.createSequentialGroup()
							.addComponent(userName1)
							.addGap(50)
							.addComponent(whiteTime)
							.addGap(50)
							.addGroup(layout.createParallelGroup()
								.addComponent(whiteGrabWall)
								.addComponent(whiteDropWall)
								.addComponent(whiteRotateWall)))
						.addGroup(layout.createSequentialGroup()
							.addComponent(boardComponent,500, 500, 500)
							.addGap(40)
							.addGroup(layout.createParallelGroup()
								.addComponent(newGame)
								.addComponent(saveGame)
								.addComponent(loadGame)
								.addComponent(replayGame)))
						.addGroup(layout.createSequentialGroup()
							.addComponent(userName2)
							.addGap(50)
							.addComponent(blackTime)
							.addGap(50)
							.addGroup(layout.createParallelGroup()
								.addComponent(blackGrabWall)
								.addComponent(blackDropWall)
								.addComponent(blackRotateWall)))
					)
			)
		);
	}
	
	
	private void refreshData() {
		// TODO: call transfer objects' method to query data and update the game's states
	}
	
	private void initFrame() {
		this.setSize(1400, 720);
		setTitle("Quoridor Game");
		this.setBackground(Color.LIGHT_GRAY);
	}
}
