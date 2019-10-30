package ca.mcgill.ecse223.quoridor.view;

import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JFrame;
import javax.swing.GroupLayout;
import javax.swing.ImageIcon;
import javax.swing.text.MaskFormatter;
import java.awt.Font;
import java.awt.Image;
import java.awt.Color;
import java.text.ParseException;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JComboBox;
import javax.swing.LayoutStyle.ComponentPlacement;


public class SetNamePage extends JFrame {
	// username and thinking time
	private JLabel userName1;
	private JLabel userName2;
	
	// time error
	private JButton btnLetsS;
	
	public SetNamePage() {
		initPage();
	}
	
	public void initPage(){
		this.setSize(1400, 720);
		this.setTitle("Set Time Page");
		this.getContentPane().setBackground(Color.LIGHT_GRAY);
	
		
		// header: back button brings user back to welcome page
		JButton btnBack = new JButton("Back");
		btnBack.setBackground(new Color(112, 128, 144));
		btnBack.setFont(new Font("Avenir Next", Font.PLAIN, 13));
		btnBack.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		
		// header: game title (image)
		ImageIcon imgQuoridor = new ImageIcon(getClass().getResource("logoicon.png"));
		Image image = imgQuoridor.getImage(); // transform it
		Image newimg = image.getScaledInstance(200, 60, java.awt.Image.SCALE_SMOOTH); // scale it the smooth way
		imgQuoridor = new ImageIcon(newimg); // transform it back
		JLabel lblQuoridor = new JLabel("", imgQuoridor, JLabel.CENTER); // render it
		
		// white and black player labels
		userName1 = new JLabel("<html><font color='white' >WHITE PLAYER</font></html>");
		userName1.setFont(new Font("Avenir Next", Font.PLAIN, 14));
		userName2 = new JLabel("<html><font color='black' >BLACK PLAYER</font></html>");
		userName2.setFont(new Font("Avenir Next", Font.PLAIN, 14));
		
		// dropdown menus with usernames
		String usernames[]={"", "Van","Bob","Laura","Jerry","Nathalie"};
		JComboBox comboBox = new JComboBox(usernames);
		JComboBox comboBox_1 = new JComboBox(usernames);
		
		// button starts a new game
				btnLetsS = new JButton("Let's Start");
				btnLetsS.setBackground(new Color(204, 153, 102));
				btnLetsS.setFont(new Font("Lucida Grande", Font.PLAIN, 18));
				btnLetsS.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent f) {
					}
				});
		
		JLabel label = new JLabel("Please choose your player names");
		label.setFont(new Font("Heiti SC", Font.PLAIN, 26));
		
		
		//--------------------- Construct Page's Layout ----------------------------//
		GroupLayout layout = new GroupLayout(getContentPane());
		layout.setHorizontalGroup(
			layout.createParallelGroup(Alignment.LEADING)
				.addGroup(Alignment.TRAILING, layout.createSequentialGroup()
					.addGap(49)
					.addComponent(btnBack)
					.addPreferredGap(ComponentPlacement.RELATED, 893, Short.MAX_VALUE)
					.addComponent(lblQuoridor, GroupLayout.PREFERRED_SIZE, 211, GroupLayout.PREFERRED_SIZE)
					.addGap(172))
				.addGroup(layout.createSequentialGroup()
					.addGap(469)
					.addGroup(layout.createParallelGroup(Alignment.LEADING)
						.addComponent(userName2, 200, 200, 200)
						.addComponent(userName1, 200, 200, 200))
					.addContainerGap(731, Short.MAX_VALUE))
				.addGroup(layout.createSequentialGroup()
					.addGap(455)
					.addGroup(layout.createParallelGroup(Alignment.TRAILING, false)
						.addComponent(comboBox, Alignment.LEADING, 0, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
						.addComponent(comboBox_1, Alignment.LEADING, 0, 302, Short.MAX_VALUE))
					.addContainerGap())
				.addGroup(layout.createSequentialGroup()
					.addGap(392)
					.addComponent(label, GroupLayout.PREFERRED_SIZE, 448, GroupLayout.PREFERRED_SIZE)
					.addContainerGap(560, Short.MAX_VALUE))
				.addGroup(layout.createSequentialGroup()
					.addGap(495)
					.addComponent(btnLetsS, GroupLayout.DEFAULT_SIZE, 196, Short.MAX_VALUE)
					.addGap(709))
		);
		layout.setVerticalGroup(
			layout.createParallelGroup(Alignment.LEADING)
				.addGroup(layout.createSequentialGroup()
					.addGap(35)
					.addComponent(lblQuoridor, GroupLayout.PREFERRED_SIZE, 69, GroupLayout.PREFERRED_SIZE)
					.addGap(78)
					.addComponent(label)
					.addGap(58)
					.addComponent(userName1)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(comboBox, GroupLayout.PREFERRED_SIZE, 47, GroupLayout.PREFERRED_SIZE)
					.addGap(33)
					.addComponent(userName2)
					.addGap(18)
					.addComponent(comboBox_1, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
					.addGap(104)
					.addComponent(btnLetsS, GroupLayout.PREFERRED_SIZE, 48, GroupLayout.PREFERRED_SIZE)
					.addContainerGap(107, Short.MAX_VALUE))
				.addGroup(layout.createSequentialGroup()
					.addGap(49)
					.addComponent(btnBack, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
					.addGap(605))
		);
				
		getContentPane().setLayout(layout);
				
		layout.setAutoCreateGaps(true);
		layout.setAutoCreateContainerGaps(true);
	}
}
