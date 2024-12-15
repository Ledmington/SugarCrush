package model.objectives;

import java.util.Objects;
import java.util.Optional;
import static model.objectives.Objective.Values.*;

/**
 * Implementation of {@link ObjectiveBuilder}
 * 
 * @author  Emanuele Lamagna
 */
public final class ObjectiveBuilderImpl implements ObjectiveBuilder{
	
	private int maxScore = SIMPLE_SCORE.getValue();
	private int maxMoves = DEF_MOVES.getValue();
	private Optional<Challenge> challenge = Optional.empty();
	private String text = "";
	private boolean built = false;
	
	@Override
	public final ObjectiveBuilder setMaxScore(final int num) {
		check(!this.built);
		assertNotNegative(num);
		this.maxScore = Objects.requireNonNull(num);
		return this;
	}

	@Override
	public final ObjectiveBuilder setMaxMoves(final int num) {
		check(!this.built);
		assertNotNegative(num);
		this.maxMoves = Objects.requireNonNull(num);
		return this;
	}

	@Override
	public final ObjectiveBuilder setChallenge(final Optional<Challenge> challenge) {
		check(!this.built);
		this.challenge = Objects.requireNonNull(challenge);
		return this;
	}

	@Override
	public final ObjectiveBuilder setObjectiveText(final String text) {
		check(!this.built);
		assertNotEmptyString(text);
		this.text = Objects.requireNonNull(text);
		return this;
	}

	@Override
	public final Objective build() {
		check(!this.built);
		built = true;
		return new Objective() {

			@Override
			public final int getMaxScore() {
				return maxScore;
			}

			@Override
			public final int getMaxMoves() {
				return maxMoves;
			}

			@Override
			public final Optional<Challenge> getChallenge() {
				return challenge;
			}

			@Override
			public final String objectiveText() {
				return text;
			}
			
		};
	}

	//If is already built, throws an exception
	private final void check(final boolean built) {
		if(!built) {
			throw new IllegalStateException("Can't build twice or set after build");
		}
	}
	
	//If the number passed to set is negative, throws an exception
	private final void assertNotNegative(final int num) {
		if(num<0) {
			throw new IllegalArgumentException("The number must be positive");
		}
	}
	
	//If the string passed to set is empty, throws an exception
	private final void assertNotEmptyString(final String text) {
		if(text.isEmpty()) {
			throw new IllegalArgumentException("The text can't be empty");
		}
	}
	
}
