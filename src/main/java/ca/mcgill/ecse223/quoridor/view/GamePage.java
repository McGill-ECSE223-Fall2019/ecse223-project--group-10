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
import javax.swing.JTextArea;

public class GamePage extends JFrame{
	private BoardComponent boardComponent;
	
	private JButton upBtn;
	private JButton downBtn;
	private JButton leftBtn;
	private JButton rightBtn;
	
	private JButton grabBtn;
	private JButton dropBtn;
	private JButton rotateBtn;
	private JButton forfeitBtn;
	
	private JButton newGameBtn;
	private JButton replayGameBtn;
	private JButton saveGameBtn;
	private JButton loadGameBtn;
	
	private JTextArea notificationText;
	private JTextArea whitePlayerName;
	private JTextArea whitePlayerTime;
	private JTextArea blackPlayerName;
	private JTextArea blackPlayerTime;
	
	private JTextArea quoridor;
	private JTextArea white;
	private JTextArea black;
	
	public GamePage(){
		initComponent();
	}
	
	private void initComponent(){
		initFrame();
		//initialize the board
				boardComponent = new BoardComponent();
				boardComponent.setSize(new Dimension(500,500));
				boardComponent.setBackground(new Color(50, 50, 50));
		
		upBtn = new JButton("^");
		downBtn = new JButton("v");
		leftBtn = new JButton("<");
		rightBtn = new JButton(">");
		
		grabBtn = new JButton("Grab Wall");
		dropBtn = new JButton("Drop Wall");
		rotateBtn = new JButton("Rotate Wall");
		forfeitBtn = new JButton("Forfeit Game");
		
		newGameBtn = new JButton("New Game");
		replayGameBtn = new JButton("Replay Game");
		saveGameBtn = new JButton("Save Game");
		loadGameBtn = new JButton("Load Game");
		
		notificationText = new JTextArea("Notification Center");
		whitePlayerName = new JTextArea("Player Name");
		whitePlayerTime = new JTextArea("00:00");
		blackPlayerName = new JTextArea("Player Name");
		blackPlayerTime = new JTextArea("00:00");
		
		quoridor = new JTextArea("Quoridor");
		white = new JTextArea("White:");
		black = new JTextArea("Black:");
		
		JButton btnNewButton = new JButton("New button");
		
		JButton btnNewButton_1 = new JButton("New button");
		
		JButton btnNewButton_2 = new JButton("New button");
		
		GroupLayout layout = new GroupLayout(getContentPane());
		layout.setHorizontalGroup(
			layout.createParallelGroup(Alignment.TRAILING)
				.addGroup(layout.createSequentialGroup()
					.addContainerGap(70, Short.MAX_VALUE)
					.addGroup(layout.createParallelGroup(Alignment.LEADING, false)
						.addComponent(notificationText)
						.addComponent(boardComponent, 500, 500, Short.MAX_VALUE))
					.addGap(18)
					.addGroup(layout.createParallelGroup(Alignment.LEADING)
						.addComponent(black)
						.addComponent(white))
					.addGap(18)
					.addGroup(layout.createParallelGroup(Alignment.LEADING)
						.addComponent(whitePlayerName)
						.addComponent(blackPlayerName))
					.addGap(18)
					.addGroup(layout.createParallelGroup(Alignment.LEADING)
						.addComponent(whitePlayerTime)
						.addComponent(blackPlayerTime))
					.addGap(95))
		);
		layout.setVerticalGroup(
			layout.createParallelGroup(Alignment.LEADING)
				.addGroup(layout.createSequentialGroup()
					.addGap(92)
					.addGroup(layout.createParallelGroup(Alignment.BASELINE)
						.addComponent(notificationText, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(white, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(whitePlayerName, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(whitePlayerTime, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						)
					.addGap(26)
					.addGroup(layout.createParallelGroup(Alignment.LEADING)
							.addComponent(black, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
							.addComponent(blackPlayerName, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
							.addComponent(blackPlayerTime, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addGap(18)
					.addGroup(layout.createParallelGroup(Alignment.LEADING)
						.addComponent(boardComponent, 500, 500, 500))
					.addContainerGap(29, Short.MAX_VALUE))
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
