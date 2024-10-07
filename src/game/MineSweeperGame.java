package game;

import java.awt.Point;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Set;

import gui.screens.GameScreen;
import gui.MineSweeper;

public class MineSweeperGame {
	//board that stores the mines and empty tiles on the board
	private final MineBoard BOARD;
	
	//board that stores the covers and flags on the board
	private final CoverBoard COVERS;
	
	private GameScreen.GuiObserver observer;

	//observer to notify gui of game state changes
	private final MineSweeper.GameStateObserver STATE_OBSERVER = new MineSweeper.GameStateObserver();
	
	//Stores whether the mines have been placed on the game board
	private boolean generated = false;
	
	/**Constructs a new Minesweeper game with the board having 
	 * the specified rows, columns, and density of mines.
	 * 
	 * @param rows the amount of rows in the board
	 * @param cols the amount of columns in the board
	 * @param density the density of the mines on the board
	 */
	public MineSweeperGame(int rows, int cols, double density) {
		BOARD = new MineBoard(rows, cols, density);
		COVERS = new CoverBoard(rows,cols);
	}
	
	/**Sets the game's observer to the specified observer.
	 * 
	 * @param observer the observer to be used by the game.
	 */
	public void setObserver(GameScreen.GuiObserver observer) {
		this.observer = observer;
	}
	
	/**Removes the cover at a specified index, along with all connected covers that 
	 * are covering an empty square.
	 * \nIf the cover at the specified index is already removed, this method
	 * will do nothing.
	 * \n The method will throw an IllegalArgumentException if the arguments are out of
	 * bounds of the board.
	 * 
	 * @param row the row index of the cover
	 * @param col the col index of the cover
	 */
	public void removeCover(int row, int col) {
		if (!generated) { //only runs on game start, when first cover is removed
			BOARD.generateBoard(row, col);
			generated = true;
			System.out.println(BOARD);
		}
		//if the selected square can't be removed/no square exists
		//returns without doing anything
		if (!COVERS.removeCover(row, col)) {
			return;
		}

		if (!BOARD.getBoard()[row][col].isEmpty()) { //check if the selected tile has a mine
			COVERS.removeCover(row, col);
			observer.notifyRepaint();
			gameOver();//ends the game
			return;
		}

		removeNeighboringCovers(new Point(col, row));
 		observer.notifyRepaint();

		 //checks if all non-mine covers have been removed, winning the game
		if (COVERS.getCoverCount() - BOARD.getMineCount() <= 0) {
			gameWin();
		}
	}

	//helper method to remove any neighboring covers from the start that are empty
	private void removeNeighboringCovers(Point start) {
		Queue<Point> toVisit = new LinkedList<>(); //list of squares to visit
		Set<Point> visited = new HashSet<>(); //set of squares already checked/removed
		toVisit.add(start);
		while (!toVisit.isEmpty()) {
			Point currentIndex = toVisit.remove();
			if (!visited.contains(currentIndex)) {
				GridSquare currentSquare = BOARD.getBoard()[currentIndex.y][currentIndex.x];
				if (currentSquare.isEmpty()) {
					visited.add(currentIndex);
					COVERS.removeCover(currentIndex.y, currentIndex.x);
					if (((EmptySquare)currentSquare).getMines() == 0) {
						toVisit.addAll(getBorderingEmptySquares(currentIndex, visited));
					}
				}
			}
		}
	}
	
	/**Returns a Queue containing all adjacent, empty, unvisited Squares
	 * adjacent to the specified point.
	 * 
	 * @param p the 2D index to search for adjacencies.
	 * @param visited the points already visited
	 * @return a Queue containing adjacent Squares
	 */
	private Queue<Point> getBorderingEmptySquares(Point p, Set<Point> visited) {
		Queue<Point> toReturn = new LinkedList<>();
		int row = p.y;
		int col = p.x;
		GridSquare[][] gameBoard = BOARD.getBoard();
		for (int rowOffset = (row == 0 ? 0: -1); rowOffset <= 1 && row + rowOffset < BOARD.HEIGHT; rowOffset ++) {
			for (int colOffset = (col == 0 ? 0: -1); colOffset <= 1 && col + colOffset < BOARD.WIDTH; colOffset ++) {
				Point curr = new Point(col + colOffset, row + rowOffset);
				if (gameBoard[curr.y][curr.x].isEmpty() && !visited.contains(curr)) {
					toReturn.add(curr);
				}
			}
			
		}
		return toReturn;
	}
	
	/** Toggles a flag at the specified index on the board if there
	 * is a cover at the index.
	 * If the specified index is out of bounds, an
	 * Exception will be thrown.
	 * 
	 * @param row the row to add a flag at
	 * @param col the column to add a flag at
	 */
	public void toggleFlag(int row, int col) {
		COVERS.toggleFlag(row, col);
	}
	
	/**Determines whether there is a flag at a specified index
	 * on the board.
	 * 
	 * @param row the row on the board
	 * @param col the column on the board
	 * @return whether there is a flag
	 */
	public boolean hasFlag(int row, int col) {
		if (row > getHeight() || col > getWidth()) {
			throw new IllegalArgumentException("Index out of bounds.");
		}
		return COVERS.hasFlag(row, col);
	}

	/**Returns whether there is a cover at the specified board index
	 *
	 * @param row the row index
	 * @param col the column index
	 * @return whether there is a cover
	 */
	public boolean hasCover(int row, int col) {
		return COVERS.hasCover(row, col);
	}

	/**Returns the number of mines bordering a specified tile.
	 * Returns -1 if there is a mine on the tile.
	 *
	 * @param row the row index
	 * @param col the column index
	 * @return the number of bordering mines
	 */
	public int getMineCount(int row, int col) {
		return BOARD.getBorderingMineCount(row, col);
	}

	/**This method is called when the player hits a mine.
	 * 
	 */
	private void gameOver() {
		System.out.println("fuck");
		STATE_OBSERVER.updateState(1);
	}

	private void gameWin() {
		STATE_OBSERVER.updateState(2);
	}
	
	/**Returns the height, in squares, of the game board.
	 * 
	 * @return the height of the game board
	 */
	public int getHeight() {
		return BOARD.getHeight();
	}
	
	/**Returns the width, in squares, of the game board.
	 * 
	 * @return the width of the game board.
	 */
	public int getWidth() {
		return BOARD.getWidth();
	}
}
