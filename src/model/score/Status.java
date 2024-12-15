package model.score;

import model.game.grid.candies.Candy;
import model.game.grid.candies.CandyColors;
import model.game.grid.shapes.Shapes;
import controller.files.*;

/**
 * The {@link Status} of the current level, with score, money, and all the necessary stats
 * 
 * @author Emanuele Lamagna
 *
 */
public interface Status {
	
	/**
	 * Enum of all the used {@link Ratios} for the score and the stats
	 * 
	 * @author Emanuele Lamagna
	 *
	 */
	enum Ratios {
		
		DEF(100),
		WRAPPED(500),
		FRECKLES(2000),
		STRIPED_HORIZONTAL(700),
		STRIPED_VERTICAL(700),
		MONEY(100),
		REDUCE_MONEY(5);
		
		private int ratio;
		
		private Ratios(final int ratio) {
			this.ratio = ratio;
		}
		
		 /**
	     * Getter of the description of the element
	     * 
	     * @return the description of an element
	     */
		public final int get() {
			return this.ratio;
		}
		
	}
	
	/**
	 * Gets the shape and increases the score of the value of that shape, than updates the map
	 * 
	 * @param shape  the shape passed by parameter
	 */
	void update(final Shapes shape);
	
	/**
	 * Gets a single candy and increases the score of the value of that candy, than updates the map
	 * 
	 * @param candy  the candy passed by parameter
	 */
	void update(final Candy candy);
	
	/**
	 * Getter of the number of the destroyed candies of the specified color
	 * 
	 * @param color  the color passed by parameter
	 * @return the number of the destroyed candies of the specified color
	 */
	int getColors(final CandyColors color);
	
	/**
	 * Getter of the number of the destroyed candies of the specified type
	 * 
	 * @param type  the type passed by parameter
	 * @return the number of the destroyed candies of the specified type
	 */
	int getTypes(final StatsTypes type);
	
	/**
	 * Adds a move to the moves count 
	 */
	void updateMoves();
	
	/**
	 * Getter of the moves done so far
	 * 
	 * @return the moves done so far
	 */
	int getMoves();
	
	/**
	 * Getter of the money gained so far
	 * 
	 * @return the money gained so far
	 */
	int getMoney();
	
	/**
	 * Tells if the level is completed or not
	 * 
	 * @return if the level is completed or not
	 */
	boolean isCompleted();
	
	/**
	 * Tells if the jelly is destroyed
	 * 
	 * @return if the jelly is destroyed
	 */
	boolean isJellyDestroyed();
	
	/**
	 * If there isn't jelly in the grid anymore, sets jellyDestroyed to true
	 */
	void setJelly();
	
	/**
	 * Completes the level
	 */
	void complete();
	
	/**
	 * Checks if it's the first time or not. If it's the first time, the gained money is only 1/5
	 * 
	 * @param firstTime  boolean that tells if it's the first time playing the level
	 */
	void isFirstTime(final boolean firstTime);
	
	/**
	 * Getter of the score of the level
	 * 
	 * @return the score of the level
	 */
	int getScore();
	
	
	
}
