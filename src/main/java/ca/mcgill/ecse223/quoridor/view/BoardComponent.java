package ca.mcgill.ecse223.quoridor.view;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
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

import ca.mcgill.ecse223.quoridor.controller.Quoridor223Controller;
import ca.mcgill.ecse223.quoridor.controller.TOPlayer;
import ca.mcgill.ecse223.quoridor.controller.TOWall;

public class BoardComponent extends JPanel {
	private int size;
	private float width;
	private float margin;
	private BufferedImage hWall, vWall, hHighlightedWall, vHighlightedWall;

	enum playerColor {
		black, white
	}

	private ArrayList<Rectangle2D> rects = new ArrayList<>();
	private ArrayList<Line2D> lines = new ArrayList<>();
	private float[][] whiteWallInStock = new float[10][2];
	private float[][] blackWallInStock = new float[10][2];
	private ArrayList<TOWall> whiteWallOnBoard;
	private ArrayList<TOWall> blackWallOnBoard;
	private ArrayList<TOWall> wallOnBoard;
	private ArrayList<TOPlayer> players;
	private TOWall wallInHand;
	private final BasicStroke thinStroke = new BasicStroke(2);
	private final BasicStroke thickStroke = new BasicStroke(8);

	public BoardComponent(int size) {
		// TODO Auto-generated constructor stub
		super();
		this.size = size;
		width = size / 13;
		margin = (size - width * 9) / 2;
		try {
			hWall = ImageIO.read(getFileFromResources("Wall.png"));
			hWall = resize(hWall, 15, (int) width * 2 - 5);
			vWall = ImageIO.read(getFileFromResources("WallV.png"));
			vWall = resize(vWall, (int) width * 2 - 5, 15);
			hHighlightedWall = ImageIO.read(getFileFromResources("HighlightedWall.png"));
			hHighlightedWall = resize(hHighlightedWall, 15, (int) width * 2 - 5);
			vHighlightedWall = ImageIO.read(getFileFromResources("HighlightedWallV.png"));
			vHighlightedWall = resize(vHighlightedWall, (int) width * 2 - 5, 15);
		} catch (IOException e) {
			System.out.println(e.getMessage());
		}
		init();
	}

	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		loadWall();
		loadPlayers();
		doDrawing(g);
	}

	private void init() {
		setupGrid();
		loadWall();
		loadPlayers();
		initStock();
	}

	private void doDrawing(Graphics g) {
		Graphics2D g2d = (Graphics2D) g.create();
		drawGrid(g2d);
		int wn = Quoridor223Controller.getWhiteWallInStock();
		int bn = Quoridor223Controller.getBlackWallInStock();
		for (int i = 0; i < bn; i++)
			g2d.drawImage(hWall, (int) blackWallInStock[i][0], (int) blackWallInStock[i][1], this);
		for (int i = 0; i < wn; i++)
			g2d.drawImage(hWall, (int) whiteWallInStock[wn - 1 - i][0], (int) whiteWallInStock[wn - i - 1][1], this);
		for (TOWall wall : wallOnBoard)
			drawWall(wall, g2d, false);
		if (wallInHand != null)
			drawWall(wallInHand, g2d, true);
		drawPlayers(g2d, players.get(0), false);
		drawPlayers(g2d, players.get(1), false);
	}

	private void setupGrid() {
		for (int i = 0; i < 9; i++) {
			for (int j = 0; j < 9; j++) {
				rects.add(new Rectangle2D.Float(margin + i * width, margin + j * width, width, width));
			}
		}
		float y = margin;
		float l = 2 * width;
		for (int i = 0; i < 10; i++) {
			lines.add(new Line2D.Float(new Point2D.Float(0, y), new Point2D.Float(l, y)));
			lines.add(new Line2D.Float(new Point2D.Float(size, y), new Point2D.Float(size - l, y)));
			y += width;
		}
	}

	private void loadWall() {
		wallInHand = null;
		whiteWallOnBoard = new ArrayList<>();
		blackWallOnBoard = new ArrayList<>();
		wallInHand = Quoridor223Controller.getWallInHand();
		whiteWallOnBoard = Quoridor223Controller.getWhiteWallOnBoard();
		blackWallOnBoard = Quoridor223Controller.getBlackWallOnBoard();
		wallOnBoard = new ArrayList<>(whiteWallOnBoard);
		wallOnBoard.addAll(blackWallOnBoard);
		Collections.sort(wallOnBoard, (a, b) -> {
			return a.getCol() + a.getRow() - b.getCol() - b.getRow();
		});
	}

	private void loadPlayers() {
		players = Quoridor223Controller.getPlayers();
	}

	private void initStock() {
		float lx = 0, rx = size - 2 * width;
		float y = margin - 5;
		for (int i = 0; i < 10; i++) {
			whiteWallInStock[i] = new float[] { lx, y };
			blackWallInStock[i] = new float[] { rx, y };
			y += width;
		}
	}

	private void drawWall(TOWall wall, Graphics2D g2d, boolean isHighlighted) {
		int[] cord = getWallCord(wall);
		BufferedImage img;
		if (isHighlighted)
			img = wall.getDir() == TOWall.Direction.Horizontal ? hHighlightedWall : vHighlightedWall;
		else
			img = wall.getDir() == TOWall.Direction.Horizontal ? hWall : vWall;
		g2d.drawImage(img, cord[0], cord[1], this);
	}

	private void drawPlayers(Graphics2D g2d, TOPlayer player, boolean isHighlighted) {
		float x = getTileCord(player.getCol());
		float y = getTileCord(player.getRow());
		g2d.setStroke(thinStroke);
		Color c;
		if (player.getColor() == TOPlayer.Color.Black)
			c = Color.black;
		else
			c = Color.white;
		g2d.setColor(c);
		Ellipse2D pawn =new Ellipse2D.Float(x, y, width*3/5, width*3/5);
//		g2d.fill(pawn);
		g2d.draw(pawn);
	}

	private void drawGrid(Graphics2D g2d) {
		g2d.setColor(new Color(172, 128, 81));
		g2d.setStroke(thickStroke);
		for (Rectangle2D rect : rects)
			g2d.draw(rect);
		for (Line2D line : lines)
			g2d.draw(line);
	}

	private int[] getWallCord(TOWall wall) {
		int[] cord = new int[2];
		boolean isHorizontal = wall.getDir() == TOWall.Direction.Horizontal;
		int vadj = isHorizontal ? 7 : -4;
		int hadj = isHorizontal ? -2 : 9;
		int horizontalOffset = isHorizontal ? 1 : 0;
		int verticalOffset = (!isHorizontal) ? 1 : 0;
		cord[1] = (int) (margin - vadj + (wall.getRow() - verticalOffset) * width);
		cord[0] = (int) (margin - hadj + (wall.getCol() - horizontalOffset) * width);
		return cord;
	}

	private float getTileCord(int index) {
		return (float) (index - 5.0 / 6) * width + margin;
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

}
