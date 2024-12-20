package game;
import java.awt.*;
import java.util.Collection;
import java.util.LinkedList;
import java.util.Random;

/**A representation of the game board. Stores a two-dimensional field
 * of empty squares and squares with mines.
 * Once the mines are generated, the MineBoard cannot have the position of the mines
 * modified.
 */
public class MineBoard extends Board {
	
	//the ratio of mines to empty tiles
	private final double DENSITY;

	private int mineCount;
	
	
	//list of all squares containing mines
	private final Collection<Point> MINES;

	
	/**Creates a new MineBoard with specified dimension and density.
	 * 
	 * @param rows the height of the board
	 * @param cols the width of the board
	 * @param density the ratio of mines to empty tiles
	 */
	public MineBoard(int rows, int cols, double density) {
		super(rows,cols);
		DENSITY = density;
		MINES = new LinkedList<>();
		mineCount = 0;
	}

	
	/** Generates a new board with an exclusion area around the
	 * starting point where no mines will spawn.
	 * 
	 * @param selectedRow the row index of the center of the exclusion zone
	 * @param selectedCol the column index of the center of the exclusion zone
	 */
	//todo: this is fucking bad
	public void generateBoard(int selectedRow, int selectedCol) {
		//generates an Area that sets the exclusion area
		Rectangle exclusionBound = generateExclusionBound(selectedRow, selectedCol);
		for (int row = 0; row < HEIGHT; row ++) {
			for (int col = 0; col < WIDTH; col ++) {
				board[row][col] = EmptySquare.INSTANCES[0];
			}
		}
		Random gen = new Random();
		//populate board with mines
		for (int i = 1; i <= DENSITY * (HEIGHT*WIDTH); i ++) {
			int x = gen.nextInt(WIDTH);
			int y = gen.nextInt(HEIGHT);
			while (!board[y][x].isEmpty() || exclusionBound.contains(new Point(x, y))) {
				x = gen.nextInt(WIDTH);
				y = gen.nextInt(HEIGHT);
			}
			board[y][x] = MineSquare.INSTANCE;
			mineCount++;
			MINES.add(new Point(x, y));
		}
		numberEmptyTiles();
	}
	
	
	/**Generates a rectangle that surrounds a given point.
	 * 
	 * @param row the row index of the center of the rectangle
	 * @param col the column index of the center of the rectangle
	 * @return a rectangle surrounding the specified point.
	 */
	private Rectangle generateExclusionBound(int row, int col) {
		final int RECT_WIDTH = WIDTH / 5;
		final int RECT_HEIGHT = HEIGHT / 5;
		int startCol= (col - RECT_WIDTH / 2);
		int startRow = (row - RECT_HEIGHT / 2);
		if (startCol < 0) {
			startCol = 0;
		} else if (startCol + RECT_WIDTH > board[0].length - 1) {
			startCol -= (startCol + RECT_WIDTH) - board[0].length - 1;
		}
		if (startRow < 0) {
			startRow = 0;
		} else if (startRow + RECT_HEIGHT > board.length - 1) {
			startRow -= (startRow + RECT_HEIGHT) - board.length - 1;
		}
		Point upperLeft = new Point(startCol, startRow);
		return new Rectangle(upperLeft, new Dimension(RECT_WIDTH, RECT_HEIGHT));
	}

	
	/**Numbers all empty tiles that border a mine in the board
	 * 
	 */
	private void numberEmptyTiles() {
		for (Point mine_point: MINES) {
			int x = mine_point.x;
			int y = mine_point.y;
			if (x > 0) {
				numberLeftTiles(x, y);
			}
			if (x < WIDTH - 1) {
				numberRightTiles(x, y);
			}
			if (y > 0) {
				numberTopTile(x, y);
			}
			if (y < HEIGHT - 1) {
				numberBottomTile(x, y);
			}
			
		}
	}
	
	/**Increments any empty tiles to the left of a mine tile during
	 * board generation.
	 * 
	 * @param x the x coordinate of the mine
	 * @param y the y coordinate of the mine
	 */
	private void numberLeftTiles(int x, int y) {
		GridSquare left = board[y][x-1];
		if (left.isEmpty()) {
			board[y][x - 1] = ((EmptySquare)left).increment();
		}
		if (y > 0) {
			GridSquare upLeft = board[y - 1][x - 1];
			if (upLeft.isEmpty()) {
				board[y - 1][x - 1] = ((EmptySquare)upLeft).increment();
			}
		}
		if (y < HEIGHT - 1) {
			GridSquare downLeft = board[y + 1][x - 1];
			if (downLeft.isEmpty()) {
				board[y + 1][x - 1] = ((EmptySquare)downLeft).increment();
			}
		}
	}

	/**Increments any empty tiles to the right of a mine tile
	 * 
	 * @param x the x coordinate of the mine
	 * @param y the y coordinate of the mine
	 */
	private void numberRightTiles(int x, int y) {
		GridSquare right = board[y][x + 1];
		if (right.isEmpty()) {
			board[y][x + 1] = ((EmptySquare)right).increment();
		}
		if (y > 0) {
			GridSquare upRight = board[y - 1][x + 1];
			if (upRight.isEmpty()) {
				board[y - 1][x + 1] = ((EmptySquare)upRight).increment();
			}
		}
		if (y < HEIGHT - 1) {
			GridSquare downRight = board[y + 1][x + 1];
			if (downRight.isEmpty()) {
				board[y + 1][x + 1] = ((EmptySquare)downRight).increment();
			}
		}
	}
	
	/**Increments the tile above a mine tile
	 */
	private void numberTopTile(int x, int y) {
		GridSquare top = board[y - 1][x];
		if (top.isEmpty()) {
			board[y - 1][x] = ((EmptySquare)top).increment();
		}
	} 
	
	/**Increments the tile below a mine tile
	 * 
	 * @param x x coordinate of the tile
	 * @param y y coordinate of the tile
	 */
	private void numberBottomTile(int x, int y) {
		GridSquare bot = board[y + 1][x];
		if (bot.isEmpty()) {
			board[y + 1][x] = ((EmptySquare)bot).increment();
		}
	}

	/**Returns the number of bordering mines of a specified tile.
	 * If the tile is a mine tile, returns -1.
	 *
	 * @param row row of specified tile
	 * @param col column of specified tile
	 * @return the number of bordering mines
	 */
	public int getBorderingMineCount(int row, int col) {
		if (board[row][col].isEmpty()) {
			return ((EmptySquare)board[row][col]).getMines();
		}
		return -1;
	}


	/**Returns the number of mines on the board.
	 *
	 * @return the number of mines
	 */
	public int getMineCount() {
		return mineCount;
	}

	@Override
	public String toString() {
		String toReturn = "";
		for (GridSquare[] row: board) {
			for (GridSquare square: row) {
				toReturn = toReturn.concat(square + ", ");
			}
			toReturn = toReturn.concat("\n");
		}
		return toReturn;
	}
}
