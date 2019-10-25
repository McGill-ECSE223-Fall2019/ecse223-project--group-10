package ca.mcgill.ecse223.quoridor.view;

import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.JSpinner;
import javax.swing.GroupLayout.Alignment;
import javax.swing.text.DateFormatter;
import javax.swing.JFrame;
import javax.swing.GroupLayout;

import java.sql.Time;
import java.awt.Color;

public class SetThinkingTimePage extends JFrame {
	// TODO: implement the UI for this page
	//username and thinking time
	private JLabel userName1;
	private JLabel userName2;
			
	//thinking time
	private Time whiteThinkingTime;
	private Time blackThinkingTime;
	private JSpinner whiteTimePicker;
	private JSpinner blackTimePicker;
	
	//set time, start game button
	private JButton setTime;
	private JButton startGame;
	
	// page title
	private JLabel title;
	
	
	public SetThinkingTimePage() {
		initPage();
	}
	
	public void initPage(){
		this.setSize(1400, 720);
		this.setTitle("Set Thinking Time");
		this.setBackground(Color.LIGHT_GRAY);
		
		userName1 = new JLabel("Player 1");
		userName2 = new JLabel("Player 2");
		
		whiteThinkingTime = new Time(10);
		blackThinkingTime = new Time(10);
		
		setTime = new JButton("Set Time");
		startGame = new JButton("Start Game");
		
		whiteTimePicker = new JSpinner();
		blackTimePicker = new JSpinner();
		
		title = new JLabel("Set Thinking Time");
		
//		configSpinner(whiteTimePicker);
//		configSpinner(blackTimePicker);
		
		//--------------------- Construct Page's Layout ----------------------------//
		GroupLayout layout = new GroupLayout(getContentPane());
				
		getContentPane().setLayout(layout);
				
		layout.setAutoCreateGaps(true);
		layout.setAutoCreateContainerGaps(true);
		
		layout.setHorizontalGroup(
			layout.createParallelGroup(Alignment.TRAILING)
			.addGroup(layout.createParallelGroup()
				.addGroup(layout.createSequentialGroup()
					.addGap(600)
					.addComponent(title))
				.addGroup(layout.createSequentialGroup()
					.addGap(250)
					.addGroup(layout.createParallelGroup()
						.addComponent(userName1)
						.addComponent(whiteTimePicker, 150, 150, 150)
						.addComponent(setTime))
					.addGap(500)
					.addGroup(layout.createParallelGroup()
						.addComponent(userName2)
						.addComponent(blackTimePicker, 150, 150, 150)
						.addComponent(startGame)))
				)
		);
		
		layout.setVerticalGroup(
			layout.createParallelGroup(Alignment.LEADING)
			.addGroup(layout.createSequentialGroup()
				.addGap(50)
				.addComponent(title)
				.addGroup(layout.createParallelGroup()
					.addGroup(layout.createSequentialGroup()
						.addGap(100)
						.addComponent(userName1)
						.addGap(50)
						.addComponent(whiteTimePicker, 50, 50, 50)
						.addGap(200)
						.addComponent(setTime))
					.addGroup(layout.createSequentialGroup()
						.addGap(100)
						.addComponent(userName2)
						.addGap(50)
						.addComponent(blackTimePicker, 50, 50, 50)
						.addGap(200)
						.addComponent(startGame)))
				)
		);
	};
	
	private static void configSpinner(JSpinner spinner) {
		JSpinner.DateEditor editor = new JSpinner.DateEditor(spinner, "HH:mm:ss");
		DateFormatter formatter = (DateFormatter)editor.getTextField().getFormatter();
		formatter.setAllowsInvalid(false);
		formatter.setOverwriteMode(true);
	}
}
