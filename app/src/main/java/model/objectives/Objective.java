package model.objectives;

import java.util.Optional;

/**
 * An {@link Objective} always has the score to reach and the total moves, then it can have some challenges: 
 * some candies you have to destroy, some special candies you have to farm and other
 * 
 * @author Emanuele Lamagna
 */
public interface Objective {
	
	/**
	 * Enum of the used scores
	 * 
	 * @author Emanuele Lamagna
	 */
	enum Values {
		
		DEF_MOVES(25),
		DEF_SCORE(20000),
		SIMPLE_SCORE(15000),
		DEF_RED(10),
		DEF_YELLOW(10),
		DEF_BLUE(10),
		DEF_STRIPED(4),
		DEF_WRAPPED(2),
		DEF_FRECKLES(1);
		
		private int value;
	    
	    private Values(final int value) {
	        this.value = value;
	    }
	    
	    /**
	     * Getter of the description of the element
	     * 
	     * @return the description of an element
	     */
	    public final int getValue() {
	        return this.value;
	    }
	    
	}
	
	/**
	 * Getter of the score to reach in the level
	 * 
	 * @return the score the player has to reach
	 */
	int getMaxScore();
	
	/**
	 * Getter of the moves that the player can do in the level
	 * 
	 * @return the maximum number of moves the player can do
	 */
	int getMaxMoves();
	
	/**
	 * Getter of the optional {@link Challenge} that the level could have
	 * 
	 * @return the optional {@link Challenge} of the level
	 */
	Optional<Challenge> getChallenge();
	
	/**
	 * Getter of the text that explains what the the player has to do in the level
	 * 
	 * @return the text that explain the {@link Objective}
	 */
	String objectiveText();
	
}
