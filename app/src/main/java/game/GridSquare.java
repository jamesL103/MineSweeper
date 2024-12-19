package game;

/**An interface that represents one square on the game board.
 * Provides the framework for a square type to be created
 * 
 */
public interface GridSquare {
	
	/**Returns a string representation of the square.
	 * 
	 * @return a string that represents the square;
	 */
	@Override
	public String toString();
	
	
	/**Returns whether the square is empty.
	 * A GridSquare is Empty if it does not contain a mine.
	 * By default, this method throws an exception.
	 * @return whether the square is empty.
	 */
	public default boolean isEmpty() {
		throw new UnsupportedOperationException("Square cannot support an Empty/Full state");
	}
	
	/**Returns whether the square has a flag on it.
	 * By default, this method throws an exception.
	 * @return whether the square has a flag.
	 */
	public default boolean isFlagged() {
		throw new UnsupportedOperationException("Square cannot support an Empty/Full state");
	}
	
	
	
}
