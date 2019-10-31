package ca.mcgill.ecse223.quoridor.view;

import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.GroupLayout;
import javax.swing.text.MaskFormatter;

import ca.mcgill.ecse223.quoridor.controller.Quoridor223Controller;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Time;
import java.awt.Color;
import java.text.ParseException;
import java.sql.Time;

import ca.mcgill.ecse223.quoridor.QuoridorApplication;
import ca.mcgill.ecse223.quoridor.controller.Quoridor223Controller;

public class SetThinkingTimePage extends JFrame {
	//username and thinking time
	private JLabel userName1;
	private JLabel userName2;;
			
	//set time status
	private boolean status = false;
	
	//thinking time
	private JFormattedTextField whiteTimePicker;
	private JFormattedTextField blackTimePicker;
	
	//set time, start game button
	private JButton startGame;
	private MaskFormatter mask;
	
	// page title
	private JLabel title;
	
	// time error
	private JLabel setTimeError;
	
	public SetThinkingTimePage() {
		initPage();
	}

  // @sacha method for old timer
  // to delete
	private Time getWhiteTime() {
		return Time.valueOf("00:"+whiteTimePicker.getText());
	}
	
  // @sacha method for old timer
  // to delete
	private Time getBlackTime() {
		return Time.valueOf("00:"+blackTimePicker.getText());
	}
	
  // @sacha log users when time format is incorrect
	private void failToReadTime() {
		setTimeError.setText("<html><font color='red' >INPUT TIME IS NOT VALID</font></html>");
	}
	
  // @sacha method to create main page
	private void createMainPage() {
		// if this is clicked then now display the setThinkingTime page
		GamePage mainPage = new GamePage();
		mainPage.setVisible(true);
	}
	
	private void initPage(){
		this.setSize(1400, 720);
		this.setTitle("Set Time Page");
		this.getContentPane().setBackground(Color.LIGHT_GRAY);
	
		// initialize username
		userName1 = new JLabel(String.format("<html><font color='white' >%s</font></html>", Quoridor223Controller.getWhitePlayerName()));
		userName1.setFont(new Font("Arial", Font.PLAIN, 25));
		userName2 = new JLabel(String.format("<html><font color='black' >%s</font></html>", Quoridor223Controller.getBlackPlayerName()));
		userName2.setFont(new Font("Arial", Font.PLAIN, 25));
				
		// intialize start-game button
		startGame = new JButton("<html><font color='white' >START GAME</font></html>");
		startGame.setBackground(Color.BLUE);
		startGame.setFont(new Font("Arial", Font.PLAIN, 30));
	
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
		title = new JLabel("<html><font color='blue' >SET THINKING TIME</font></html>");
		title.setFont(new Font("Arial", Font.PLAIN, 50));
		
		// initialize error component
		setTimeError = new JLabel("");
		setTimeError.setFont(new Font("Arial", Font.PLAIN, 25));
		

		//--------------------- Add Event Listener ---------------------------------//
		startGame.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				try {
					String whiteTime[] = whiteTimePicker.getText().split(":");
					String blackTime[] = blackTimePicker.getText().split(":");
					
					long wTime = Integer.parseInt(whiteTime[0])*60*1000 + Integer.parseInt(whiteTime[1])*1000;
					long bTime = Integer.parseInt(blackTime[0])*60*1000 + Integer.parseInt(blackTime[1])*1000;
					
					Quoridor223Controller.createUser("White");
					Quoridor223Controller.createUser("Black");
					Quoridor223Controller.createGame();
					Quoridor223Controller.setThinkingTime(new Time(wTime), "White");
					Quoridor223Controller.setThinkingTime(new Time(bTime), "Black");
					status = true;
					Quoridor223Controller.initializeBoard();
					QuoridorApplication.setMainPage();
				}catch (NumberFormatException e) {
					setTimeError.setText("<html><font color='red' >INPUT TIME IS NOT VALID</font></html>");
				}
				
			}});
		
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
					.addComponent(title)
				)
				.addGroup(layout.createSequentialGroup()
					.addGap(485)
					.addComponent(setTimeError))
				.addGroup(layout.createParallelGroup()
					.addGroup(layout.createSequentialGroup()
						.addGap(300)
						.addComponent(userName1, 200, 200, 200)
						.addGap(310)
						.addComponent(whiteTimePicker, 200, 200, 200))
					.addGroup(layout.createSequentialGroup()
						.addGap(300)
						.addComponent(userName2, 200, 200, 200)
						.addGap(310)
						.addComponent(blackTimePicker, 200, 200, 200))
				)
				.addGroup(layout.createSequentialGroup()
					.addGap(500)
					.addComponent(startGame, 300, 300, 300))
			)
		);
		
		layout.setVerticalGroup(
			layout.createParallelGroup(Alignment.LEADING)
			.addGroup(layout.createSequentialGroup()
				.addGap(50)
				.addComponent(title)
				.addGap(75)
				.addComponent(setTimeError)
				.addGap(75)
				.addGroup(layout.createSequentialGroup()
					.addGroup(layout.createParallelGroup()
						.addComponent(userName1)
						.addComponent(whiteTimePicker, 50, 50, 50))
					.addGap(70)
					.addGroup(layout.createParallelGroup()
						.addComponent(userName2)
						.addComponent(blackTimePicker, 50, 50, 50)))
				.addGap(70)
				.addComponent(startGame)
			)
		);
	}
	
	private void refreshData() {
		
	}
	
	public boolean getPageStatus() {
		return this.status;
	}
}
