package ca.mcgill.ecse223.quoridor.view;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.sql.Date;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Properties;

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
	private BoardComponent boardComponent;
	private JButton upButton;
	private JButton downButton;
	public GamePage(){
		initComponent();
	}
	private void initComponent(){
		initFrame();
		//initialize the board
				boardComponent = new BoardComponent();
				boardComponent.setSize(new Dimension(500,500));
				boardComponent.setBackground(new Color(50, 50, 50));
		
		JButton btnNewButton = new JButton("New button");
		
		JButton btnNewButton_1 = new JButton("New button");
		
		JButton btnNewButton_2 = new JButton("New button");
		GroupLayout layout = new GroupLayout(getContentPane());
		layout.setHorizontalGroup(
			layout.createParallelGroup(Alignment.TRAILING)
				.addGroup(layout.createSequentialGroup()
					.addContainerGap(162, Short.MAX_VALUE)
					.addComponent(boardComponent,500, 500, 500)
					.addGap(18)
					.addGroup(layout.createParallelGroup(Alignment.LEADING)
						.addComponent(btnNewButton_1)
						.addComponent(btnNewButton_2)
						.addComponent(btnNewButton))
					.addGap(95))
		);
		layout.setVerticalGroup(
			layout.createParallelGroup(Alignment.LEADING)
				.addGroup(layout.createSequentialGroup()
					.addGap(92)
					.addComponent(btnNewButton)
					.addGap(26)
					.addComponent(btnNewButton_2)
					.addGap(18)
					.addGroup(layout.createParallelGroup(Alignment.LEADING)
						.addComponent(boardComponent, 500, 500, 500)
						.addComponent(btnNewButton_1))
					.addContainerGap(224, Short.MAX_VALUE))
		);
		getContentPane().setLayout(layout);
		layout.setAutoCreateGaps(true);
		layout.setAutoCreateContainerGaps(true);
	}
	private void initFrame() {
		this.setSize(800,800);
		setTitle("Quoridor Game");
		this.setBackground(Color.LIGHT_GRAY);
	}
}
