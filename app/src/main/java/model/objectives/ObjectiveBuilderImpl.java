/*
 * Sugar Crush
 * Copyright (C) 2020 Filippo Benvenuti, Filippo Barbari, Lamagna Emanuele, Degli Esposti Davide
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package model.objectives;

import java.util.Objects;
import java.util.Optional;

/**
 * Implementation of {@link ObjectiveBuilder}.
 *
 * @author Emanuele Lamagna
 */
public final class ObjectiveBuilderImpl implements ObjectiveBuilder {

	private int maxScore = Objective.Values.SIMPLE_SCORE.getValue();
	private int maxMoves = Objective.Values.DEF_MOVES.getValue();
	private Optional<Challenge> challenge = Optional.empty();
	private String text = "";
	private boolean built = false;

	@Override
	public ObjectiveBuilder setMaxScore(final int num) {
		check(!this.built);
		assertNotNegative(num);
		this.maxScore = num;
		return this;
	}

	@Override
	public ObjectiveBuilder setMaxMoves(final int num) {
		check(!this.built);
		assertNotNegative(num);
		this.maxMoves = num;
		return this;
	}

	@Override
	public ObjectiveBuilder setChallenge(final Optional<Challenge> challenge) {
		check(!this.built);
		this.challenge = Objects.requireNonNull(challenge);
		return this;
	}

	@Override
	public ObjectiveBuilder setObjectiveText(final String text) {
		check(!this.built);
		assertNotEmptyString(text);
		this.text = Objects.requireNonNull(text);
		return this;
	}

	@Override
	public Objective build() {
		check(!this.built);
		built = true;
		return new Objective() {

			@Override
			public int getMaxScore() {
				return maxScore;
			}

			@Override
			public int getMaxMoves() {
				return maxMoves;
			}

			@Override
			public Optional<Challenge> getChallenge() {
				return challenge;
			}

			@Override
			public String objectiveText() {
				return text;
			}
		};
	}

	// If is already built, throws an exception
	private void check(final boolean built) {
		if (!built) {
			throw new IllegalStateException("Can't build twice or set after build");
		}
	}

	// If the number passed to set is negative, throws an exception
	private void assertNotNegative(final int num) {
		if (num < 0) {
			throw new IllegalArgumentException("The number must be positive");
		}
	}

	// If the string passed to set is empty, throws an exception
	private void assertNotEmptyString(final String text) {
		if (text.isEmpty()) {
			throw new IllegalArgumentException("The text can't be empty");
		}
	}
}
