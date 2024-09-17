package game;

import java.awt.Point;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Set;

import gui.GameComponent;

public class MineSweeperGame {
	//board that stores the mines and empty tiles on the board
	private MineBoard board;
	
	//board that stores the covers and flags on the board
	private CoverBoard covers;
	
	private GameComponent.GuiObserver observer;
	
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
		board = new MineBoard(rows, cols, density);
		covers = new CoverBoard(rows,cols);
	}
	
	/**Sets the game's observer to the specified observer.
	 * 
	 * @param observer the observer to be used by the game.
	 */
	public void setObserver(GameComponent.GuiObserver observer) {
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
			board.generateBoard(row, col);
			generated = true;
			System.out.println(board);
		}
		//if the selected square can't be removed/no square exists
		//returns without doing anything
		if (!covers.removeCover(row, col)) {
			return;
		}

		if (!board.getBoard()[row][col].isEmpty()) { //check if the selected tile has a mine
			covers.removeCover(row, col);
			observer.notifyRepaint();
			gameOver();//ends the game
			return;
		}

		Point topLeft = new Point(col, row), bottomRight = new Point(col + 1, row + 1); //points to set the boundary of the repaint area
		if (row < 0 || row >= board.getHeight() || col < 0 || col >= board.getWidth()) { //checks if boundaries are out of bounds
			throw new IllegalArgumentException("Coordinates out of bounds");
		}
		Queue<Point> toVisit = new LinkedList<>(); //list of squares to visit
 		Set<Point> visited = new HashSet<>(); //set of squares already checked/removed
 		toVisit.add(new Point(col, row));
 		while (!toVisit.isEmpty()) {
 			Point currentIndex = toVisit.remove();
 			if (!visited.contains(currentIndex)) {
	 			GridSquare currentSquare = board.getBoard()[currentIndex.y][currentIndex.x];
	 			if (currentSquare.isEmpty()) {
	 				visited.add(currentIndex);
	 				covers.removeCover(currentIndex.y, currentIndex.x);
	 				if (currentIndex.x < topLeft.x) {
	 					topLeft.setLocation(currentIndex.x, topLeft.y);
	 				} else if (currentIndex.x > bottomRight.x) {
	 					bottomRight.setLocation(currentIndex.x, bottomRight.y);
	 				}
	 				if (currentIndex.y < topLeft.y) {
	 					topLeft.setLocation(topLeft.x, currentIndex.y);
	 				} else if (currentIndex.y > bottomRight.y) {
	 					bottomRight.setLocation(bottomRight.x, currentIndex.y);
	 				}
	 				if (((EmptySquare)currentSquare).getMines() == 0) {
	 					toVisit.addAll(getBorderingEmptySquares(currentIndex, visited));
	 				}
	 			}
 			}
 		}
 		observer.notifyRepaint();
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
		GridSquare[][] gameBoard = board.getBoard();
		for (int rowOffset = (row == 0 ? 0: -1); rowOffset <= 1 && row + rowOffset < board.HEIGHT; rowOffset ++) {
			for (int colOffset = (col == 0 ? 0: -1); colOffset <= 1 && col + colOffset < board.WIDTH; colOffset ++) {
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
		covers.toggleFlag(row, col);
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
		return covers.hasFlag(row, col);
	}

	/**Returns whether there is a cover at the specified board index
	 *
	 * @param row the row index
	 * @param col the column index
	 * @return whether there is a cover
	 */
	public boolean hasCover(int row, int col) {
		return covers.hasCover(row, col);
	}

	/**Returns the number of mines bordering a specified tile.
	 * Returns -1 if there is a mine on the tile.
	 *
	 * @param row the row index
	 * @param col the column index
	 * @return the number of bordering mines
	 */
	public int getMineCount(int row, int col) {
		return board.getBorderingMineCount(row, col);
	}

	/**This method is called when the player hits a mine.
	 * 
	 */
	public void gameOver() {
		System.out.println("fuck");
	}
	
	/**Returns the height, in squares, of the game board.
	 * 
	 * @return the height of the game board
	 */
	public int getHeight() {
		return board.getHeight();
	}
	
	/**Returns the width, in squares, of the game board.
	 * 
	 * @return the width of the game board.
	 */
	public int getWidth() {
		return board.getWidth();
	}
}
