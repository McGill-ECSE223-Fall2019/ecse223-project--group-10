package ca.mcgill.ecse223.quoridor.view;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.util.ArrayList;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

public class BoardComponent extends JPanel {
	private int size;
	private float width;
	private float margin;
	private BufferedImage hWall;
	enum playerColor{black,white}
	private ArrayList<Rectangle2D> rects = new ArrayList<>();
	private ArrayList<Line2D> lines = new ArrayList<>();
	private ArrayList<float[]> whiteWallInStock;
	private ArrayList<float[]> blackWallInStock;
	private ArrayList<float[]> whiteWallOnBoard;
	private ArrayList<float[]> blackWallOnBoard;
	private float[] whiteWallInHand;
	private float[] blackWallInHand;
	private HashMap<playerColor,Ellipse2D> players = new HashMap<>();
	
	public BoardComponent(int size) {
		// TODO Auto-generated constructor stub
		super();
		this.size=size;
		width = size/13;
		margin = (size- width*9)/2;
		try {
			hWall = ImageIO.read(getFileFromResources("Wall.png"));
			System.out.println(hWall.getHeight() +" "+ hWall.getWidth());
			hWall = resize(hWall, 15, (int)width*2);
		}
		catch(IOException e) {
			System.out.println(e.getMessage());
		}
		init();
	}
	
	private void init() {
		setupGrid();
		loadWall();
	}
	
	private void doDrawing(Graphics g) {
		Graphics2D g2d = (Graphics2D) g.create();
		BasicStroke thickStroke = new BasicStroke(8);
		g2d.setColor(new Color(172,128,81));
		g2d.setStroke(thickStroke);
		for(Rectangle2D rect: rects)g2d.draw(rect);
		for(Line2D line: lines)g2d.draw(line);
		g2d.setColor(Color.black);
		
		BasicStroke thinStroke = new BasicStroke(2);
		g2d.setStroke(thinStroke);
		g2d.setPaint(Color.black);
		g2d.fill(players.get(playerColor.black));
		g2d.draw(players.get(playerColor.black));
		
		g2d.setPaint(Color.white);		
		g2d.fill(players.get(playerColor.white));
		g2d.setColor(Color.black);
		g2d.draw(players.get(playerColor.white));
		for(float[] wallCord: blackWallInStock)g2d.drawImage(hWall, (int)wallCord[0], (int)wallCord[1], this);
		for(float[] wallCord: whiteWallInStock)g2d.drawImage(hWall, (int)wallCord[0], (int)wallCord[1], this);
	}
	
	private void setupGrid() {
		for(int i = 0; i < 9; i++) {
			for(int j = 0; j < 9 ;j++) {
				rects.add(new Rectangle2D.Float(margin+i*width,margin+j*width,width,width));
			}
		}
		float y = margin;
		float l = 2*width;
		for(int i = 0 ; i < 10; i++) {
			lines.add(new Line2D.Float(new Point2D.Float(0,y), new Point2D.Float(l,y)));
			lines.add(new Line2D.Float(new Point2D.Float(size,y), new Point2D.Float(size-l,y)));
			y+=width;
		}
		players.put(playerColor.black,new Ellipse2D.Float(getTileCord(1),getTileCord(5),width*2/3,width*2/3));
		players.put(playerColor.white,new Ellipse2D.Float(getTileCord(9),getTileCord(5),width*2/3,width*2/3));
	}
	
	private void loadWall() {
		whiteWallInHand=null;
		whiteWallInStock = new ArrayList<>();
		blackWallInStock = new ArrayList<>();
		whiteWallOnBoard = new ArrayList<>();
		blackWallOnBoard = new ArrayList<>();
		Controller.getWallInHand()
		Controller.getWhiteWallInStock();
		Controller.getBlackWallInStock();
		Controller.getWhiteWallOnBoard();
		Controller.getBlackWallOnBoard();
		float lx =0, rx=size-2*width;
		float y = margin-7;
		for(int i = 0;i<10;i++) {
			blackWallInStock.add(new float[] {lx,y});
			whiteWallInStock.add(new float[] {rx,y});
			y+=width;
		}
	}
	
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		doDrawing(g);
	}
	
	private float getTileCord(int index) {
		return (float)(index-5.0/6)*width+margin;
	}
	
	private File getFileFromResources(String fileName) {
        ClassLoader classLoader = getClass().getClassLoader();
        URL resource = classLoader.getResource(fileName);
        if (resource == null) {
            throw new IllegalArgumentException("file is not found!");
        } else {
            return new File(resource.getFile());
        }
    	}
	
	private static BufferedImage resize(BufferedImage img, int height, int width) {
        Image tmp = img.getScaledInstance(width, height, Image.SCALE_SMOOTH);
        BufferedImage resized = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = resized.createGraphics();
        g2d.drawImage(tmp, 0, 0, null);
        g2d.dispose();
        return resized;
    	}
	private void getWallCord(){
		
	}
}
