package game;

/**Class that represents a board for the minesweeper game.
 * Meant to be extended to specialized boards holding specific GridSquare types.
 * Stores and tracks GridSquares on the board, provides methods to
 * view the dimensions of the board.
 * Once instantiated, the height and width of the board cannot be
 * modified.
 */
public class Board {
	
	//Stores the dimensions of the board
	protected final int HEIGHT, WIDTH;
	
	//Array representation of the board that stores all squares
	protected GridSquare[][] board;
	
	/**This constructor is not meant to be used;
	 * 
	 */
	private Board() {
		throw new UnsupportedOperationException("Do not use the default constructor");
	}
	
	/**Instantiates a new board with the specified height and width
	 * 
	 * @param h the height of the board
	 * @param w the width of the board
	 */
	public Board(int h, int w) {
		HEIGHT = h;
		WIDTH = w;
		board = new GridSquare[h][w];
	}
	
	/**Returns the height of the board.
	 * 
	 * @return height
	 */
	public int getHeight() {
		return HEIGHT;
	}
	
	/**Returns the width of the board
	 * 
	 * @return width
	 */
	public int getWidth() {
		return WIDTH;
	}
	
	/**Returns the array representation of the board, without
	 * making a copy.
	 * 
	 * @return the board
	 */
	public GridSquare[][] getBoard() {
		return board;
	}
	
	
}
