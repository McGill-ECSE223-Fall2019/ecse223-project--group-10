package ca.mcgill.ecse223.quoridor.view;

import javax.swing.JLabel;

import ca.mcgill.ecse223.quoridor.QuoridorApplication;
import ca.mcgill.ecse223.quoridor.controller.Quoridor223Controller;

import javax.swing.JButton;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JFrame;
import javax.swing.GroupLayout;
import javax.swing.ImageIcon;
import java.awt.Image;
import java.awt.Color;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Font;

public class WelcomePage extends JFrame {
	
	public WelcomePage() {
		getContentPane().setBackground(new Color(153, 153, 153));
		initPage();
	}

	public void initPage() {
		
		// Page container
		this.setSize(1400, 720);
		this.setTitle("Welcome to Quoridor!");
		this.setBackground(Color.GRAY);
		
		// Welcome message
		JLabel lblWelcomeTo = new JLabel("W e l c o m e   t o");
		lblWelcomeTo.setFont(new Font("Heiti SC", Font.PLAIN, 18));
		
		// Image title
		ImageIcon imgQuoridor = new ImageIcon(getClass().getResource("logoicon.png"));
		Image image = imgQuoridor.getImage(); // transform it 
		Image newimg = image.getScaledInstance(590, 190,  java.awt.Image.SCALE_SMOOTH); // scale it the smooth way  
		imgQuoridor = new ImageIcon(newimg);  // transform it back
		JLabel lblQuoridor = new JLabel("", imgQuoridor, JLabel.CENTER); // render it
		
		// Button to start new game
		JButton btnStartANew = new JButton("START A NEW GAME");
		btnStartANew.setBackground(new Color(204, 153, 102));
		btnStartANew.setFont(new Font("Heiti SC", Font.BOLD, 16));
		btnStartANew.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// link to the set name page
				QuoridorApplication.setNamePage();
			}
		});


		// --------------------- Construct Page's Layout ----------------------------//
		GroupLayout layout = new GroupLayout(getContentPane());
		layout.setHorizontalGroup(
			layout.createParallelGroup(Alignment.LEADING)
				.addGroup(layout.createSequentialGroup()
					.addGroup(layout.createParallelGroup(Alignment.LEADING)
						.addGroup(layout.createSequentialGroup()
							.addGap(349)
							.addComponent(lblQuoridor, GroupLayout.PREFERRED_SIZE, 589, GroupLayout.PREFERRED_SIZE))
						.addGroup(layout.createSequentialGroup()
							.addGap(463)
							.addComponent(lblWelcomeTo))
						.addGroup(layout.createSequentialGroup()
							.addGap(544)
							.addComponent(btnStartANew, GroupLayout.PREFERRED_SIZE, 220, GroupLayout.PREFERRED_SIZE)))
					.addContainerGap(462, Short.MAX_VALUE))
		);
		layout.setVerticalGroup(
			layout.createParallelGroup(Alignment.LEADING)
				.addGroup(layout.createSequentialGroup()
					.addGap(195)
					.addComponent(lblWelcomeTo)
					.addGap(18)
					.addComponent(lblQuoridor)
					.addGap(86)
					.addComponent(btnStartANew, GroupLayout.PREFERRED_SIZE, 56, GroupLayout.PREFERRED_SIZE)
					.addContainerGap(133, Short.MAX_VALUE))
		);

		getContentPane().setLayout(layout);

		layout.setAutoCreateGaps(true);
		layout.setAutoCreateContainerGaps(true);
	}
}