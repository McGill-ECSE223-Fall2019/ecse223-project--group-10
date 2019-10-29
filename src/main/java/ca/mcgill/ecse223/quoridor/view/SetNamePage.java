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
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;


public class SetNamePage extends JFrame {
	// username and thinking time
	private JLabel userName1;
	private JLabel userName2;
			
	// thinking time
	private JFormattedTextField whiteTimePicker;
	private JFormattedTextField blackTimePicker;
	private MaskFormatter mask;
	
	// time error
	private JLabel setTimeError;
	private JButton btnLetsS;
	
	public SetNamePage() {
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
		
		// initialize error component
		setTimeError = new JLabel("<html><font color='black' >Please choose your player names</font></html>");
		setTimeError.setFont(new Font("Arial", Font.PLAIN, 25));
		
		JButton btnBack = new JButton("Back");
		btnBack.setBackground(new Color(112, 128, 144));
		
		btnLetsS = new JButton("Let's Start");
		btnLetsS.setBackground(new Color(204, 153, 102));
		btnLetsS.setFont(new Font("Lucida Grande", Font.PLAIN, 18));
		btnLetsS.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		
		
		//--------------------- Construct Page's Layout ----------------------------//
		GroupLayout layout = new GroupLayout(getContentPane());
		layout.setHorizontalGroup(
			layout.createParallelGroup(Alignment.LEADING)
				.addGroup(layout.createSequentialGroup()
					.addGroup(layout.createParallelGroup(Alignment.LEADING)
						.addGroup(layout.createSequentialGroup()
							.addGap(485)
							.addComponent(setTimeError))
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
						.addGroup(layout.createSequentialGroup()
							.addGap(43)
							.addComponent(btnBack)))
					.addContainerGap(294, Short.MAX_VALUE))
				.addGroup(Alignment.TRAILING, layout.createSequentialGroup()
					.addContainerGap(582, Short.MAX_VALUE)
					.addComponent(btnLetsS, GroupLayout.PREFERRED_SIZE, 256, GroupLayout.PREFERRED_SIZE)
					.addGap(562))
		);
		layout.setVerticalGroup(
			layout.createParallelGroup(Alignment.LEADING)
				.addGroup(layout.createSequentialGroup()
					.addGap(104)
					.addComponent(btnBack)
					.addGap(51)
					.addComponent(setTimeError)
					.addGap(75)
					.addGroup(layout.createParallelGroup(Alignment.LEADING)
						.addComponent(userName1)
						.addComponent(whiteTimePicker, 50, 50, 50))
					.addGap(70)
					.addGroup(layout.createParallelGroup(Alignment.LEADING)
						.addComponent(userName2)
						.addComponent(blackTimePicker, 50, 50, 50))
					.addGap(79)
					.addComponent(btnLetsS, GroupLayout.PREFERRED_SIZE, 48, GroupLayout.PREFERRED_SIZE)
					.addGap(112))
		);
				
		getContentPane().setLayout(layout);
				
		layout.setAutoCreateGaps(true);
		layout.setAutoCreateContainerGaps(true);
	}
}
