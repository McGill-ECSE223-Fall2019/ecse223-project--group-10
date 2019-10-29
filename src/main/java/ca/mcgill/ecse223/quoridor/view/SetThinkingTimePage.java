package ca.mcgill.ecse223.quoridor.view;

import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.GroupLayout;
import javax.swing.text.MaskFormatter;
import java.awt.Font;

import java.awt.Color;
import java.text.ParseException;

public class SetThinkingTimePage extends JFrame {
	//username and thinking time
	private JLabel userName1;
	private JLabel userName2;
			
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
	
	public void initPage(){
		this.setSize(1400, 720);
		this.setTitle("Set Time Page");
		this.getContentPane().setBackground(Color.LIGHT_GRAY);
	
		// initialize username
		userName1 = new JLabel("<html><font color='white' >WHITE PLAYER</font></html>");
		userName1.setFont(new Font("Arial", Font.PLAIN, 25));
		userName2 = new JLabel("<html><font color='black' >BLACK PLAYER</font></html>");
		userName2.setFont(new Font("Arial", Font.PLAIN, 25));
				
		// intialize start-game button
		startGame = new JButton("<html><font color='white' >START GAME</font></html>");
		startGame.setBackground(Color.BLUE);
		startGame.setFont(new Font("Arial", Font.PLAIN, 30));
		
		// initialize time picker
		try {
            mask = new MaskFormatter("##min ##sec");
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
		setTimeError = new JLabel("<html><font color='red' >INPUT TIME IS NOT VALID</font></html>");
		setTimeError.setFont(new Font("Arial", Font.PLAIN, 25));
		
		
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
	};
}
