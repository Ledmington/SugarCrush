package model.objectives;

import java.util.Optional;

/**
 * A class used to build a new {@link Objective} (with builder pattern)
 * 
 * @author  Emanuele Lamagna
 *
 */
public interface ObjectiveBuilder {

	/**
	 * Sets the max score of the {@link Objective}
	 * 
	 * @param num  the number that specifies the max score to reach
	 * @return this {@link ObjectiveBuilder}, with the maxScore modified
	 */
	ObjectiveBuilder setMaxScore(int num);
	
	/**
	 * Sets the max moves of the {@link Objective}
	 * 
	 * @param num  the number that specifies the max moves to do
	 * @return this {@link ObjectiveBuilder}, with the maxMoves modified
	 */
	ObjectiveBuilder setMaxMoves(int num);
	
	/**
	 * Sets the challenge of the {@link Objective}, that could be empty
	 * 
	 * @param num  the number that specifies the eventual {@link Challenge} to finish
	 * @return this {@link Objective} builder, with the {@link Challenge} modified
	 */
	ObjectiveBuilder setChallenge(Optional<Challenge> challenge);
	
	/**
	 * Sets the text of the {@link Objective}
	 * 
	 * @param String  the string that specifies the the text that explain the {@link Objective}
	 * @return this {@link ObjectiveBuilder}, with the objectiveText modified
	 */
	ObjectiveBuilder setObjectiveText(String text);
	
	/**
	 * Built the new {@link Objective}
	 * 
	 * @return a new {@link Objective}, built from the fields
	 */
	Objective build();
	
}