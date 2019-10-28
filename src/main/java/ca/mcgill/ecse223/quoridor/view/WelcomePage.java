package ca.mcgill.ecse223.quoridor.view;

import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JFrame;
import javax.swing.GroupLayout;

import java.awt.Color;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Font;
import javax.swing.LayoutStyle.ComponentPlacement;

public class WelcomePage extends JFrame {
	
	public WelcomePage() {
		getContentPane().setBackground(new Color(153, 153, 153));
		initPage();
	}

	public void initPage() {
		this.setSize(1400, 720);
		this.setTitle("Welcome to Quoridor!");
		this.setBackground(Color.GRAY);
		
		// Welcome message & title
		JLabel lblWelcomeTo = new JLabel("Welcome to");
		lblWelcomeTo.setFont(new Font("Heiti SC", Font.PLAIN, 18));
		
		
		// Button to start new game
		JButton btnStartANew = new JButton("START A NEW GAME");
		btnStartANew.setBackground(new Color(204, 153, 102));
		btnStartANew.setFont(new Font("Heiti SC", Font.BOLD, 15));
		btnStartANew.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});


		// --------------------- Construct Page's Layout ----------------------------//
		GroupLayout layout = new GroupLayout(getContentPane());
		layout.setHorizontalGroup(
			layout.createParallelGroup(Alignment.LEADING)
				.addGroup(layout.createSequentialGroup()
					.addGroup(layout.createParallelGroup(Alignment.LEADING)
						.addGroup(layout.createSequentialGroup()
							.addGap(528)
							.addComponent(btnStartANew, GroupLayout.PREFERRED_SIZE, 220, GroupLayout.PREFERRED_SIZE))
						.addGroup(layout.createSequentialGroup()
							.addGap(486)
							.addComponent(lblWelcomeTo)))
					.addContainerGap(652, Short.MAX_VALUE))
		);
		layout.setVerticalGroup(
			layout.createParallelGroup(Alignment.TRAILING)
				.addGroup(layout.createSequentialGroup()
					.addGap(158)
					.addComponent(lblWelcomeTo)
					.addPreferredGap(ComponentPlacement.RELATED, 251, Short.MAX_VALUE)
					.addComponent(btnStartANew, GroupLayout.PREFERRED_SIZE, 52, GroupLayout.PREFERRED_SIZE)
					.addGap(220))
		);

		getContentPane().setLayout(layout);

		layout.setAutoCreateGaps(true);
		layout.setAutoCreateContainerGaps(true);
	};
	
}