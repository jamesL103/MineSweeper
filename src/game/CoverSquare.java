package game;

/**Representation of a cover square on the minesweeper board
 * without a flag.
 * 
 */
public class CoverSquare implements GridSquare {
	
	public static final CoverSquare INSTANCE; //singleton instance
	
	static {
		INSTANCE = new CoverSquare();
	}

	//Can't be instantiated
	private CoverSquare() {
		
	}
	
	/**Returns whether a flag is on the tile.
	 * This method will always return false.
	 * 
	 * @return false
	 */
	@Override
	public boolean isFlagged() {
		return false;
	}
	
}
