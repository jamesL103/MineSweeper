package gui.screens;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;
import javax.swing.*;
import javax.imageio.ImageIO;

import game.*;
import gui.MineSweeper;

/**Class that is a JComponent containing the minesweeper game instance.
 * Used as a component to be added into a container gui.
 *
 */
public class GameScreen extends JPanel implements Screen{

	//path to image for red flag
	private static final String PATH_RED_FLAG = "\\assets\\red_triangle.png";

	//size, in pixels, of each square on the board
	private final static int SQUARE_DIMENSION= 30;
	//the MineSweeperGame being run by the GUI
	private MineSweeperGame game;

	//observer used to watch game for updates
	private final GuiObserver OBSERVER;

	//image for the red flag image
	private final static Image RED_FLAG;

	//Default colors of the game
	private static final Color COVER_COLOR = Color.GREEN;
	private static final Color EMPTY_COLOR = new Color(215, 184, 153);
	private static final Color TEXT_COLOR = Color.BLACK;
	
	//Initializes the images used in the game
	static {
		String curr_dir = System.getProperty("user.dir");
		System.out.println(curr_dir);
		Image temp = null;
		try {
			 temp = ImageIO.read(new File(curr_dir + PATH_RED_FLAG));
		} catch (IOException e) {
			System.out.println("Error: image " + curr_dir + PATH_RED_FLAG + " cannot be found");
			System.exit(1);
		}
		//scale the image to the right height
		double scale = ((double)SQUARE_DIMENSION - 2)/ temp.getHeight(null);
		RED_FLAG = temp.getScaledInstance((int)(temp.getWidth(null) * scale), (int)(temp.getHeight(null) * scale),0);
	}
	
	/**Observer that receives notifications from operations done on
	 * the minesweeper board. 
	 * The observer notifies this component to repaint when required by the game.
	 * 
	 */
	 public class GuiObserver {
		/**Notifies the observer that a repaint is necessary.
		 * The method's parameter is a Rectangle representing the region that needs to be
		 * repainted.
		 * When invoked, the method will cause the GUI to repaint
		 *
		 */
		public void notifyRepaint() {
			repaint();
		}
		
	}

	/**Instantiates a new Minesweeper gameGui component.
	 * The game will have the specified height, width, and density.
	 *
	 * @param height the height of the game's board
	 * @param width the width of the game's board
	 * @param density the density of the mines
	 */
	public GameScreen(int height, int width, double density) {
		OBSERVER = new GuiObserver();
		Dimension size = new Dimension(SQUARE_DIMENSION * width, SQUARE_DIMENSION * height);
		setPreferredSize(size);
		setMaximumSize(size);
		setBorder(BorderFactory.createLineBorder(Color.WHITE));
		addMouseListener(new MouseHandler());
		newGame(height, width, density);
	}

	public void newGame(int height, int width, double density) {
		game = new MineSweeperGame(height,width, density);
		game.setObserver(OBSERVER);
	}
	
	/**Mouse Listener to handle user interaction with the mouse
	 * 
	 */
	private class MouseHandler extends MouseAdapter {

		/** Method to handle the user clicking on the board. The method
		 *  gets the location of the user's click and translates it to
		 *  an index on the game's board. It then calls the appropriate game
		 *  interaction based on if the user right clicks or left clicks.
		 *
		 * @param e the event to be processed
		 */
		public void mouseClicked(MouseEvent e) {
			Point point = e.getPoint();
			int rowIndex = point.y / SQUARE_DIMENSION;
			int colIndex = point.x / SQUARE_DIMENSION;
			if (rowIndex < game.getHeight() && colIndex < game.getWidth()) {
				if (e.getButton() == MouseEvent.BUTTON3) {
					game.toggleFlag(rowIndex, colIndex);
				} else if (e.getButton() == MouseEvent.BUTTON1) {
					game.removeCover(rowIndex, colIndex);
				}
				repaint();
			}
		}
	}
	
	
	
	//paints the game board
	protected void paintComponent(Graphics g) {
		Rectangle bound = g.getClipBounds();
		//sets the bounds of the area in the game board to be drawn
		int colStart = (int)((bound.getMinX())/SQUARE_DIMENSION);
		int rowStart = (int)((bound.getMinY())/SQUARE_DIMENSION);
		int colEnd = (int)((bound.getMaxX())/SQUARE_DIMENSION);
		int rowEnd = (int)((bound.getMaxY())/SQUARE_DIMENSION);

		//check if either corner of the repaint region is out of bounds
		if (colEnd > game.getWidth()) {
			colEnd = game.getWidth();
			if (colStart >= colEnd) {
				colStart = colEnd - 2;
			}
		}
		if (rowEnd > game.getHeight() ) {
			rowEnd = game.getHeight();
			if (rowStart >= rowEnd) {
				rowStart = rowEnd - 2;
			}
		}

		Rectangle gridBounds = new Rectangle(colStart, rowStart, colEnd - colStart, rowEnd - rowStart);
		
		/* Setting background */
		g.setColor(MineSweeper.BACKGROUND_COLOR);
		g.fillRect(0, 0, getWidth(), getHeight());

		/* Drawing the grid */
		paintGrid(g, gridBounds);
	}

	/**Paints the grid of the game containing all the squares on the board.
	 *
	 * @param g the graphics to draw the game
	 * @param gridRegion a Rectangle representing the region of the grid to paint
	 */
	private void paintGrid(Graphics g, Rectangle gridRegion) {
		int colStart = gridRegion.x;
		int rowStart = gridRegion.y;
		int colEnd = gridRegion.x + (int)gridRegion.getWidth();
		int rowEnd = gridRegion.y + (int)gridRegion.getHeight();

		for (int row = rowStart; row < rowEnd; row++) { //iterate over each board row
			for (int col = colStart; col < colEnd; col++) { //iterate over each board column
				if (game.hasCover(row, col)) {
					paintCover(g, row, col); //paint a cover square
				} else {
					int mineCount = game.getMineCount(row, col);
					if (mineCount != -1) { //square has mine
						paintEmptySquare(g, row, col, mineCount); //paint an empty square, along with mine count if applicable
					} else {
						paintMineSquare(g, row, col); //paint a mine square
					}
				}
			}
		}
	}
	
	//Paints a green cover square over the given square.
	private void paintCover(Graphics g, int row, int col) {
		g.setColor(COVER_COLOR);
		g.fill3DRect(col * SQUARE_DIMENSION, row * SQUARE_DIMENSION,
		SQUARE_DIMENSION, SQUARE_DIMENSION, true);
		if (game.hasFlag(row, col)) {
			paintFlag(g, row, col);
		}
	}

	//Paint a flag image over the specified tile, only if there is a cover at the index.
	private void paintFlag(Graphics g, int row, int col) {
		g.drawImage(RED_FLAG, col * SQUARE_DIMENSION, row * SQUARE_DIMENSION, null);
	}
	
	/**Paints an empty exposed square and the number
	 * of mines bordering it.
	 * Number will not be painted if there are no bordering mines.
	 * 
	 * @param g graphics object
	 * @param row y of square
	 * @param col x of square
	 * @param mineCount the number of mines bordering the specified square
	 */
	private void paintEmptySquare(Graphics g, int row, int col, int mineCount) {
		g.setColor(EMPTY_COLOR);
		g.fill3DRect(col * SQUARE_DIMENSION, row * SQUARE_DIMENSION,
		SQUARE_DIMENSION, SQUARE_DIMENSION, true);
		if (mineCount != 0) {
			g.setColor(TEXT_COLOR);
			String squareNum = String.valueOf(mineCount);
			g.drawString(squareNum, (int)((col + 0.5)* SQUARE_DIMENSION),(int)( (row + 0.5) * SQUARE_DIMENSION));
		}
	}
	
	//Paints a mine in the square
	private void paintMineSquare(Graphics g, int row, int col) {
		g.setColor(EMPTY_COLOR);
		g.fill3DRect(col * SQUARE_DIMENSION, row * SQUARE_DIMENSION,
		SQUARE_DIMENSION, SQUARE_DIMENSION, true);
		g.setColor(TEXT_COLOR);
		g.drawString("x", (int)((col + 0.5)* SQUARE_DIMENSION),(int)( (row + 0.5) * SQUARE_DIMENSION));
	}

	
}
