package game;

/**A representation of a cover square on the minesweeper board
 * with a flag.
 * 
 */
public class FlagCoverSquare implements GridSquare {

	//singleton instance
	public static final FlagCoverSquare INSTANCE;
	
	static {
		INSTANCE = new FlagCoverSquare();
	}

	//cannot be instantiated outside
	private FlagCoverSquare() {
		
	}

	/**Always returns true
	 *
	 * @return true
	 */
	@Override
	public boolean isFlagged() {
		return true;
	}
	
	
	
}
