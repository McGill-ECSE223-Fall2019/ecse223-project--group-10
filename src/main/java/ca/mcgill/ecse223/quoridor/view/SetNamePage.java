package ca.mcgill.ecse223.quoridor.view;

import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JFrame;
import javax.swing.GroupLayout;
import javax.swing.ImageIcon;
import javax.swing.text.MaskFormatter;

import ca.mcgill.ecse223.quoridor.QuoridorApplication;
import ca.mcgill.ecse223.quoridor.controller.Quoridor223Controller;

import java.awt.Font;
import java.awt.Image;
import java.awt.Color;
import java.text.ParseException;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JComboBox;
import javax.swing.LayoutStyle.ComponentPlacement;
import java.util.ArrayList;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.File;

public class SetNamePage extends JFrame {
	// username and thinking time
	private JLabel userName1;
	private JLabel userName2;

	// name field
	private JTextField whiteNamePicker;
	private JTextField blackNamePicker;

	private static ArrayList<String> usernames;

	// time error
	private JButton btnLetsS;
	
	JLabel error = new JLabel("");
	boolean hasError = false;
	boolean confirm = false;
	boolean startAnyways = false;

	public SetNamePage() {
		initPage();
	}

	public void initPage(){
		this.setSize(1400, 720);
		this.setTitle("Set Time Page");
		this.getContentPane().setBackground(Color.LIGHT_GRAY);
		
		// header: back button brings user back to welcome page
		JButton btnBack = new JButton("Back");
		btnBack.setBackground(new Color(204, 153, 102));
		btnBack.setFont(new Font("Arial", Font.PLAIN, 13));
		btnBack.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				QuoridorApplication.setWelcomePage();
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
		userName1.setFont(new Font("Arial", Font.PLAIN, 14));
		userName2 = new JLabel("<html><font color='black' >BLACK PLAYER</font></html>");
		userName2.setFont(new Font("Arial", Font.PLAIN, 14));
		
		// dropdown menus with usernames
		usernames = new ArrayList<String>();
		try {
			File f = new File("names.txt");
            FileReader reader = new FileReader(f.getAbsolutePath());
            BufferedReader bufferedReader = new BufferedReader(reader);
 
            String line;
 
            while ((line = bufferedReader.readLine()) != null) {
                usernames.add(line);
            }
            reader.close();
 
        } catch (IOException e) {
            e.printStackTrace();
        }
		
		JComboBox comboBox = new JComboBox(usernames.toArray());
		JComboBox comboBox_1 = new JComboBox(usernames.toArray());
		comboBox.setEditable(true);
		comboBox_1.setEditable(true);
		
		// previous name implementation with text fields
		//whiteNamePicker = new JTextField(20);
		//blackNamePicker = new JTextField(20);

		// button starts a new game
		btnLetsS = new JButton("Let's Start");
		btnLetsS.setBackground(new Color(204, 153, 102));
		btnLetsS.setFont(new Font("Arial", Font.PLAIN, 17));
		
		//render NO button when usernames chosen exist
		JButton btnNO = new JButton("NO");
		btnNO.setVisible(false);
		btnNO.setBackground(new Color(204, 153, 102));
		btnNO.setFont(new Font("Arial", Font.PLAIN, 16));
		btnNO.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				btnNO.setVisible(false);
				error.setText("");
			}
		});
		
		
		
		btnLetsS.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				String name1 = comboBox.getSelectedItem().toString();
				String name2 = comboBox_1.getSelectedItem().toString();
				error.setText("");
				
				if(name1.equals("") || name2.equals("") || name1.equals(" ") || name2.equals(" ")) {
					error.setText("Names must not be empty");
					hasError = true;
				}else if (name1.equals(name2)) {
					error.setText("Names must be unique.");
					hasError = true;
				}
				
				try {
					error.setText("");
					
					hasError = false;
					if (name1.equals(name2) && !startAnyways) {
						error.setText("Names must be unique.");
						hasError = true;
					} else if ((name1.equals("") || name2.equals("") || name1.equals(" ") || name2.equals(" ")) && !startAnyways) {
						error.setText("Names cannot be empty.");
						hasError = true;
					} else if ((usernames.contains(name1) || usernames.contains(name2)) && !startAnyways) {
						if (usernames.contains(name1)) {
							error.setText("White player's name already exists.");
							confirm = true;
						} else {
							File f = new File("names.txt");
							FileWriter writer = new FileWriter(f.getAbsolutePath(), true);
							writer.write("\n" + name1);
							writer.close();
						}
						if (usernames.contains(name2)) {
							error.setText(error.getText() + "\n" + "Black player's name already exists.");
							confirm = true;
						} else {
							File f = new File("names.txt");
							FileWriter writer = new FileWriter(f.getAbsolutePath(), true);
							writer.write("\n" + name1);
							writer.close();
						}

					} else {
						Quoridor223Controller.createGame();
						Quoridor223Controller.setUser(name1, "white");
						Quoridor223Controller.setUser(name2, "black");
						QuoridorApplication.setTimePage();
					}
					
					if (confirm && !hasError) {
						error.setText(error.getText() + "\n" + "Do you want to continue with these names?");
						btnNO.setVisible(true);
						startAnyways = true;
					}

				} catch (Exception g) {
					
				}

				

			}
		});
	

		JLabel label = new JLabel("Please choose your player names");
		label.setFont(new Font("Arial", Font.PLAIN, 26));
		
		
		
		//--------------------- Construct Page's Layout ----------------------------//
		GroupLayout layout = new GroupLayout(getContentPane());
		layout.setHorizontalGroup(
			layout.createParallelGroup(Alignment.LEADING)
				.addGroup(layout.createSequentialGroup()
					.addGap(49)
					.addComponent(btnBack)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(layout.createParallelGroup(Alignment.LEADING)
						.addGroup(layout.createSequentialGroup()
							.addPreferredGap(ComponentPlacement.RELATED, 598, Short.MAX_VALUE)
							.addComponent(lblQuoridor, GroupLayout.PREFERRED_SIZE, 211, GroupLayout.PREFERRED_SIZE)
							.addGap(172))
						.addGroup(layout.createSequentialGroup()
							.addGap(295)
							.addComponent(userName1, 200, 200, 200)
							.addContainerGap())))
				.addGroup(layout.createSequentialGroup()
					.addGap(502)
					.addComponent(btnNO)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(btnLetsS, GroupLayout.PREFERRED_SIZE, 188, GroupLayout.PREFERRED_SIZE)
					.addContainerGap(629, Short.MAX_VALUE))
				.addGroup(layout.createSequentialGroup()
					.addGap(421)
					.addComponent(userName2, 200, 200, 200)
					.addContainerGap(779, Short.MAX_VALUE))
				.addGroup(layout.createSequentialGroup()
					.addGap(411)
					.addGroup(layout.createParallelGroup(Alignment.TRAILING)
						.addComponent(comboBox_1, Alignment.LEADING, 0, 377, Short.MAX_VALUE)
						.addComponent(comboBox, Alignment.LEADING, GroupLayout.PREFERRED_SIZE, 377, GroupLayout.PREFERRED_SIZE))
					.addGap(612))
				.addGroup(layout.createSequentialGroup()
					.addGap(392)
					.addComponent(label, GroupLayout.PREFERRED_SIZE, 448, GroupLayout.PREFERRED_SIZE)
					.addContainerGap(560, Short.MAX_VALUE))
				.addGroup(layout.createSequentialGroup()
					.addGap(443)
					.addComponent(error)
					.addContainerGap(797, Short.MAX_VALUE))
		);
		layout.setVerticalGroup(
			layout.createParallelGroup(Alignment.LEADING)
				.addGroup(layout.createSequentialGroup()
					.addGap(35)
					.addComponent(lblQuoridor, GroupLayout.PREFERRED_SIZE, 69, GroupLayout.PREFERRED_SIZE)
					.addGap(47)
					.addComponent(label)
					.addGap(76)
					.addComponent(userName1)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(comboBox, GroupLayout.PREFERRED_SIZE, 43, GroupLayout.PREFERRED_SIZE)
					.addGap(46)
					.addComponent(userName2)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(comboBox_1, GroupLayout.PREFERRED_SIZE, 42, GroupLayout.PREFERRED_SIZE)
					.addGap(45)
					.addComponent(error)
					.addGap(60)
					.addGroup(layout.createParallelGroup(Alignment.LEADING)
						.addComponent(btnNO, GroupLayout.DEFAULT_SIZE, 49, Short.MAX_VALUE)
						.addComponent(btnLetsS, GroupLayout.DEFAULT_SIZE, 49, Short.MAX_VALUE))
					.addGap(78))
				.addGroup(layout.createSequentialGroup()
					.addGap(49)
					.addComponent(btnBack, GroupLayout.PREFERRED_SIZE, 46, GroupLayout.PREFERRED_SIZE)
					.addContainerGap(603, Short.MAX_VALUE))
		);
				
		getContentPane().setLayout(layout);
				
		layout.setAutoCreateGaps(true);
		layout.setAutoCreateContainerGaps(true);
	}
}
