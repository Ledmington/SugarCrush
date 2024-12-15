package model.objectives;

import java.util.Optional;
import static model.objectives.Objective.Values.*;

/**
 * Implementation of {@link ObjectiveFactory}
 * 
 * @author Emanuele Lamagna
 *
 */
public final class ObjectiveFactoryImpl implements ObjectiveFactory {
	
	public final Objective normal() {
		return new ObjectiveBuilderImpl()
				.setMaxScore(DEF_SCORE.getValue())
				.setObjectiveText("Reach a score of " + DEF_SCORE.getValue() + " within " + DEF_MOVES.getValue() + " moves!")
				.build();
	}
	
	public final Objective primary() {
		return new ObjectiveBuilderImpl()
				.setChallenge(Optional.of(new ChallengeBuilderImpl()
											.setRed(DEF_RED.getValue())
											.setBlue(DEF_BLUE.getValue())
											.setYellow(DEF_YELLOW.getValue())
											.build()))
				.setObjectiveText("Reach a score of " + SIMPLE_SCORE.getValue() + " within " + DEF_MOVES.getValue() + 
											" moves and destroy " + DEF_RED.getValue() + " red candies, " + DEF_YELLOW.getValue() + 
											" yellow candies and " + DEF_BLUE.getValue() + " blue candies!")
				.build();
	}

	@Override
	public final Objective lineParty() {
		return new ObjectiveBuilderImpl()
				.setChallenge(Optional.of(new ChallengeBuilderImpl()
											.setStriped(DEF_STRIPED.getValue())
											.build()))
				.setObjectiveText("Reach a score of " + SIMPLE_SCORE.getValue() + " within " + DEF_MOVES.getValue() + 
											" moves and farm " + DEF_STRIPED.getValue() + " striped candies!")
				.build();
	}

	@Override
	public final Objective explode() {
		return new ObjectiveBuilderImpl()
				.setChallenge(Optional.of(new ChallengeBuilderImpl()
											.setWrapped(DEF_WRAPPED.getValue())
											.build()))
				.setObjectiveText("Reach a score of " + SIMPLE_SCORE.getValue() + " within " + DEF_MOVES.getValue() + 
											" moves and farm " + DEF_WRAPPED.getValue() + " wrapped candies!")
				.build();
	}

	@Override
	public final Objective multiBombs() {
		return new ObjectiveBuilderImpl()
				.setChallenge(Optional.of(new ChallengeBuilderImpl()
											.setFreckles(DEF_FRECKLES.getValue())
											.build()))
				.setObjectiveText("Reach a score of " + SIMPLE_SCORE.getValue() + " within " + DEF_MOVES.getValue() + 
											" moves and farm " + DEF_FRECKLES.getValue() + " freckles candies!")
				.build();
	}
	
    @Override
    public final Objective jelly() {
    	return new ObjectiveBuilderImpl()
				.setChallenge(Optional.of(new ChallengeBuilderImpl()
											.setDestroyJelly(true)
											.build()))
				.setObjectiveText("Reach a score of " + SIMPLE_SCORE.getValue() + " within " + DEF_MOVES.getValue() + 
											" moves and destroy all jelly!")
				.build();
    }
	
}
