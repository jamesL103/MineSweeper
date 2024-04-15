package game;

/**Class that represents the board of covers over the Minesweeper board.
 * Provides methods to manipulate the covers on the board.
 */
public class CoverBoard extends Board {
	
	/** Instantiates a new board of covers with the specified
	 * height and width.
	 * 
	 * @param height the height of the board
	 * @param width the width of the board.
	 */
	public CoverBoard(int height, int width) {
		super(height, width);
		for(int row = 0; row < HEIGHT; row ++) {
			for (int col = 0; col < WIDTH; col ++) {
				board[row][col] = CoverSquare.INSTANCE;
			}
		}
	}
	
	/**Removes a cover at a specified Square.
	 * If no cover exists on the board, this method is a no op.
	 * If the CoverSquare is flagged, the method will do nothing.
	 * 
	 * @param row the row of the cover to remove
	 * @param col the column of the cover to remove
	 * @return whether a cover was removed at the index
	 */
	public boolean removeCover(int row, int col) {
		if (board[row][col] == null) {
			return false;
		}
		if (!board[row][col].isFlagged()) {
			board[row][col] = null;
			return true;
		}
		return false;
	}
	
	/**Toggles a flag on the cover of a specified Square.
	 * If there is no cover on the board, this method is a no op.
	 * @param row the row of the cover to toggle the flag
	 * @param col the column of the cover to toggle the flag
	 */
	public void toggleFlag(int row, int col) {
		if (board[row][col] == null) {
			return;
		}
		if (!board[row][col].isFlagged()) {
			board[row][col] = FlagCoverSquare.INSTANCE;
		} else {
			board[row][col] = CoverSquare.INSTANCE;
		}
	}
	
	/**Determines if a cover at a specified index has a flag on it
	 * 
	 * @param row the row of the cover
	 * @param col the column of the cover
	 * @return whether the square has a flag
	 */
	public boolean hasFlag(int row, int col) {
		return board[row][col].isFlagged();
	}

	/**Returns whether there is a cover at the specified index of the board
	 *
	 * @param row
	 * @param col
	 * @return whether there is a cover
	 */
	public boolean hasCover(int row, int col) {
        return board[row][col] != null;
    }
	
}
