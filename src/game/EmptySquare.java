package game;
/**Representation of an empty square in the game board.
 * Stores a numerical representation of how many mines the tile
 * borders
 * 
 */
public class EmptySquare implements GridSquare {
	
	
	//all possible types of empty squares
	//Squares with 0-8 neighboring mines
	public final static EmptySquare[] INSTANCES;
	
	static
	{
		//initializes array containing all instances of empty squares
		EmptySquare[] temp = new EmptySquare[9];
		for (int i = 0; i <= 8; i ++) {
			temp[i] = new EmptySquare(i);
		}
		INSTANCES = temp;
	}
	
	//number of mines bordering the empty square
	private int mineCount;
	
	//cannot instantiate outside
	private EmptySquare() {
		
	}
	
	/**Constructs a new EmptySquare with the number of mines bordering
	 * stored.
	 * 
	 * @param mines the number of mines bordering the EmptySquare
	 */
	private EmptySquare(int mines) {
		mineCount = mines;
	}
	
	/**Returns an EmptySquare with a mine count one higher than that of 
	 * the EmptySquare that invokes the method.
	 * If this method is called on a square with a mine count greater than
	 * or equal to 8, this method will throw an Exception.
	 * @return an incremented EmptySquare
	 */
	public EmptySquare increment() {
		if (mineCount >= 8) {
			throw new IllegalArgumentException("The mine count of an EmptySquare cannot"
			+ " exceed 8");
		}
		return INSTANCES[mineCount + 1];
	}
	
	
	/**Returns the number of mines bordering the square.
	 * 
	 * @return the number of bordering mines.
	 */
	public int getMines() {
		return mineCount;
	}
	
	
	@Override
	public String toString() {
		return String.valueOf(mineCount);
	}

	/**Always returns true.
	 *
	 * @return true
	 */
	@Override
	public boolean isEmpty() {
		return true;
	}
	
}
