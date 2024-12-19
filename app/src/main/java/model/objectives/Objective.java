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

import java.util.Optional;

/**
 * An {@link Objective} always has the score to reach and the total moves, then it can have some challenges: some
 * candies you have to destroy, some special candies you have to farm and other
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

		private final int value;

		Values(final int value) {
			if (value < 1) {
				throw new IllegalArgumentException(String.format("%,d is an invalid value for a Candy.", value));
			}
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
