package model.objectives;

/**
 * A class that creates an {@link Objective} (using the factory method pattern). If the {@link Challenge} is empty, the player only
 * has to reach the score in the def moves, otherwise he has to complete the {@link Challenge} too
 * 
 * @author Emanuele Lamagna
 */
public interface ObjectiveFactory {
	
	/**
	 * The player doesn't have any {@link Challenge}
	 * 
	 * @return a new Objective
	 */
	Objective normal();
	
	/**
	 * The player has to destroy 10 red candies, 10 yellow candies and 10 blue candies
	 * 
	 * @return a new {@link Objective}
	 */
	Objective primary();
	
	/**
	 * The player has to farm 4 striped candies
	 * 
	 * @return a new {@link Objective}
	 */
	Objective lineParty();
	
	/**
	 * The player has to farm 2 wrapped candies
	 * 
	 * @return a new {@link Objective}
	 */
	Objective explode();
	
	/**
	 * The player has to farm 1 freckles candies
	 * 
	 * @return a new {@link Objective}
	 */
	Objective multiBombs();

	/**
	 * The player has to destroy all jelly
	 * 
	 * @return a new {@link Objective}
	 */
    Objective jelly();
	
}
