package ca.mcgill.ecse223.quoridor.view;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JButton;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.GroupLayout;
import javax.swing.Icon;
import javax.swing.text.MaskFormatter;

import ca.mcgill.ecse223.quoridor.controller.Quoridor223Controller;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.sql.Time;
import java.awt.Color;
import java.awt.Component;
import java.text.ParseException;
import java.sql.Time;

import ca.mcgill.ecse223.quoridor.QuoridorApplication;
import ca.mcgill.ecse223.quoridor.controller.Quoridor223Controller;
import javax.swing.LayoutStyle.ComponentPlacement;

public class SetThinkingTimePage extends JFrame {
	//username and thinking time
	private JLabel userName1;
	private JLabel userName2;;
	
	//thinking time
	private JFormattedTextField whiteTimePicker;
	private JFormattedTextField blackTimePicker;
	
	//set time, start game button, load game button
	private JButton startGame;
	private JButton loadGame;
	private MaskFormatter mask;
	
	// page title
	private JLabel title;
	
	// time error
	private JLabel setTimeError;
	
	public SetThinkingTimePage() {
		initPage();
	}
	
	private void initPage(){
		this.setSize(1400, 720);
		this.setTitle("Set Time Page");
		this.getContentPane().setBackground(Color.LIGHT_GRAY);
	
		// initialize username
		// @sacha: commented JLABELS to get player names from controller
		//userName1 = new JLabel(String.format("<html><font color='white' >%s</font></html>", Quoridor223Controller.getWhitePlayerName()));
		userName1 = new JLabel("<html><font color='black' >WHITE</font></html>");
		userName1.setFont(new Font("Arial", Font.PLAIN, 25));
		//userName2 = new JLabel(String.format("<html><font color='black' >%s</font></html>", Quoridor223Controller.getBlackPlayerName()))
		userName2 = new JLabel("<html><font color='black' >BLACK</font></html>");
		userName2.setFont(new Font("Arial", Font.PLAIN, 25));
				
		// initialize start-game button
		startGame = new JButton("<html><font color='white' >START GAME</font></html>");
		startGame.setBackground(new Color(206, 159, 111));
		startGame.setFont(new Font("Arial", Font.PLAIN, 30));
		
		// initialize the load game button
		loadGame = new JButton("<html><font color='white' >LOAD GAME</font></html>");
		loadGame.setBackground(new Color(206, 159, 111));
		loadGame.setFont(new Font("Arial", Font.PLAIN, 30));
	
		// initialize time picker
		try {
            mask = new MaskFormatter("##:##");
            mask.setPlaceholderCharacter('#');
        } catch (ParseException e) {
            e.printStackTrace();
        }
		mask.setPlaceholderCharacter('#');
		whiteTimePicker = new JFormattedTextField(mask);
		whiteTimePicker.setFont(new Font("Arial", Font.PLAIN, 25));
		blackTimePicker = new JFormattedTextField(mask);
		blackTimePicker.setFont(new Font("Arial", Font.PLAIN, 25));
		
		// initialize title 
		title = new JLabel("<html><font color='black' >SET THINKING TIME</font></html>");
		title.setFont(new Font("Arial", Font.PLAIN, 50));
		
		// initialize error component
		setTimeError = new JLabel("");
		setTimeError.setFont(new Font("Arial", Font.PLAIN, 25));
		

		//--------------------- Add Event Listener ---------------------------------//
		startGame.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				try {
					String whiteTime = "00:" + whiteTimePicker.getText();
					String blackTime = "00:" + blackTimePicker.getText();
					
					// set thinking time for two players
					Quoridor223Controller.setThinkingTime(Time.valueOf(whiteTime), Quoridor223Controller.getWhitePlayerName());
					Quoridor223Controller.setThinkingTime(Time.valueOf(blackTime), Quoridor223Controller.getBlackPlayerName());
					
					// set game ready to play
					Quoridor223Controller.setGameToReady();
					
					// initialize a board and open a game page
					Quoridor223Controller.initializeBoard();
					QuoridorApplication.setMainPage();
					
				}catch (NumberFormatException e) {
					setTimeError.setText("<html><font color='red' >INPUT TIME IS NOT VALID</font></html>");
				}
				
			}});
		
		loadGame.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				try {
					String whiteTime = "00:" + whiteTimePicker.getText();
					String blackTime = "00:" + blackTimePicker.getText();
					
					// set thinking time for two players
					Quoridor223Controller.setThinkingTime(Time.valueOf(whiteTime), Quoridor223Controller.getWhitePlayerName());
					Quoridor223Controller.setThinkingTime(Time.valueOf(blackTime), Quoridor223Controller.getBlackPlayerName());
					
					// set game ready to play
					Quoridor223Controller.setGameToReady();
					
					// initialize a board
					Quoridor223Controller.initializeBoard();
					
				}catch (NumberFormatException e) {
					setTimeError.setText("<html><font color='red' >INPUT TIME IS NOT VALID</font></html>");
				}
				
				try {
					userClicksToLoadGame();
					QuoridorApplication.setMainPage();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		
		//--------------------- Construct Page's Layout ----------------------------//
		GroupLayout layout = new GroupLayout(getContentPane());
		layout.setHorizontalGroup(
			layout.createParallelGroup(Alignment.TRAILING)
				.addGroup(layout.createSequentialGroup()
					.addGap(485)
					.addComponent(setTimeError)
					.addGap(174))
				.addGroup(Alignment.LEADING, layout.createSequentialGroup()
					.addGap(342)
					.addComponent(startGame, 300, 300, 300)
					.addGap(100)
					.addComponent(loadGame, 300, 300, 300)
					.addContainerGap(342, Short.MAX_VALUE))
				.addGroup(Alignment.LEADING, layout.createSequentialGroup()
					.addGap(447)
					.addComponent(title, GroupLayout.PREFERRED_SIZE, 491, GroupLayout.PREFERRED_SIZE)
					.addContainerGap(446, Short.MAX_VALUE))
				.addGroup(Alignment.LEADING, layout.createSequentialGroup()
					.addGap(337)
					.addGroup(layout.createParallelGroup(Alignment.LEADING)
						.addGroup(layout.createSequentialGroup()
							.addComponent(userName1, 200, 200, 200)
							.addGap(310)
							.addComponent(whiteTimePicker, 200, 200, 200))
						.addGroup(layout.createSequentialGroup()
							.addComponent(userName2, 200, 200, 200)
							.addGap(310)
							.addComponent(blackTimePicker, 200, 200, 200)))
					.addContainerGap(337, Short.MAX_VALUE))
		);
		layout.setVerticalGroup(
			layout.createParallelGroup(Alignment.LEADING)
				.addGroup(layout.createSequentialGroup()
					.addGap(50)
					.addComponent(title)
					.addGap(75)
					.addComponent(setTimeError)
					.addGap(75)
					.addGroup(layout.createParallelGroup(Alignment.LEADING)
						.addComponent(userName1)
						.addComponent(whiteTimePicker, 50, 50, 50))
					.addGap(70)
					.addGroup(layout.createParallelGroup(Alignment.LEADING)
						.addComponent(userName2)
						.addComponent(blackTimePicker, 50, 50, 50))
					.addGap(73)
					.addGroup(layout.createParallelGroup(Alignment.BASELINE)
						.addComponent(loadGame)
						.addComponent(startGame))
					.addContainerGap(136, Short.MAX_VALUE))
		);
				
		getContentPane().setLayout(layout);
				
		layout.setAutoCreateGaps(true);
		layout.setAutoCreateContainerGaps(true);
	}
	
	private void userClicksToLoadGame() throws IOException {
		String filename = null;
		Boolean saveSuccessful = false;

		filename = loadGameInputDialog("Enter the file path below:", "Load Game From File", null);
		
		if (filename != null) {
			try {
				saveSuccessful = Quoridor223Controller.loadPosition(filename);
			} catch (IOException e1) {
				e1.printStackTrace();
			}

			// keep trying to load until the user cancels load attempt, or the load is successful
			while (saveSuccessful == false) {
	
				filename = loadGameInputDialog("Enter a valid file path here:", "Load Game From File", null);
				
				// if the user cancels the save, return
				if (filename == null) {
					return;
				}
				
				// otherwise, keep trying to save
				try {
					saveSuccessful = Quoridor223Controller.loadPosition(filename);
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		}
		return;
	}

	private String loadGameInputDialog(String message, String title, String initialValue) {
		String userInput;

		userInput = (String) JOptionPane.showInputDialog(null, (Object) message, title,
				JOptionPane.INFORMATION_MESSAGE, (Icon) null, (Object[]) null, (Object) initialValue);

		return userInput;
	}
}
