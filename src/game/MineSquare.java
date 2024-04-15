package game;

/**A representation of a mine tile in the game board.
 * 
 */
public class MineSquare implements GridSquare {
	
	//the singleton instance of MineSquare
	public static final MineSquare INSTANCE = new MineSquare();
	
	
	//private constructor, only one instance of Minesquare will exist
	private MineSquare() {
		
	}

	@Override
	public String toString() {
		return "m";
	}

	/**Always returns false
	 *
	 * @return false
	 */
	@Override
	public boolean isEmpty() {
		return false;
	}
	
}
