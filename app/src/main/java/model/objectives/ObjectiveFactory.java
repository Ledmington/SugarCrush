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
 * A class that creates an {@link Objective} (using the factory method pattern). If the {@link Challenge} is empty, the
 * player only has to reach the score in the def moves; otherwise he has to complete the {@link Challenge} too.
 *
 * @author Emanuele Lamagna
 */
public final class ObjectiveFactory {

	private ObjectiveFactory() {}

	/**
	 * The player doesn't have any {@link Challenge}
	 *
	 * @return a new Objective
	 */
	public static Objective normal() {
		return Objective.builder()
				.setMaxScore(Objective.Values.DEF_SCORE.getValue())
				.setObjectiveText("Reach a score of " + Objective.Values.DEF_SCORE.getValue() + " within "
						+ Objective.Values.DEF_MOVES.getValue() + " moves!")
				.build();
	}

	/**
	 * The player has to destroy 10 red candies, 10 yellow candies and 10 blue candies.
	 *
	 * @return a new {@link Objective}
	 */
	public static Objective primary() {
		return Objective.builder()
				.setChallenge(Optional.of(new ChallengeBuilderImpl()
						.setRed(Objective.Values.DEF_RED.getValue())
						.setBlue(Objective.Values.DEF_BLUE.getValue())
						.setYellow(Objective.Values.DEF_YELLOW.getValue())
						.build()))
				.setObjectiveText("Reach a score of " + Objective.Values.SIMPLE_SCORE.getValue() + " within "
						+ Objective.Values.DEF_MOVES.getValue()
						+ " moves and destroy "
						+ Objective.Values.DEF_RED.getValue() + " red candies, "
						+ Objective.Values.DEF_YELLOW.getValue() + " yellow candies and "
						+ Objective.Values.DEF_BLUE.getValue() + " blue candies!")
				.build();
	}

	/**
	 * The player has to farm four striped candies.
	 *
	 * @return a new {@link Objective}
	 */
	public static Objective lineParty() {
		return Objective.builder()
				.setChallenge(Optional.of(new ChallengeBuilderImpl()
						.setStriped(Objective.Values.DEF_STRIPED.getValue())
						.build()))
				.setObjectiveText("Reach a score of " + Objective.Values.SIMPLE_SCORE.getValue() + " within "
						+ Objective.Values.DEF_MOVES.getValue() + " moves and farm "
						+ Objective.Values.DEF_STRIPED.getValue() + " striped candies!")
				.build();
	}

	/**
	 * The player has to farm two wrapped candies.
	 *
	 * @return a new {@link Objective}
	 */
	public static Objective explode() {
		return Objective.builder()
				.setChallenge(Optional.of(new ChallengeBuilderImpl()
						.setWrapped(Objective.Values.DEF_WRAPPED.getValue())
						.build()))
				.setObjectiveText("Reach a score of " + Objective.Values.SIMPLE_SCORE.getValue() + " within "
						+ Objective.Values.DEF_MOVES.getValue() + " moves and farm "
						+ Objective.Values.DEF_WRAPPED.getValue() + " wrapped candies!")
				.build();
	}

	/**
	 * The player has to farm one freckles candies.
	 *
	 * @return a new {@link Objective}
	 */
	public static Objective multiBombs() {
		return Objective.builder()
				.setChallenge(Optional.of(new ChallengeBuilderImpl()
						.setFreckles(Objective.Values.DEF_FRECKLES.getValue())
						.build()))
				.setObjectiveText("Reach a score of " + Objective.Values.SIMPLE_SCORE.getValue() + " within "
						+ Objective.Values.DEF_MOVES.getValue() + " moves and farm "
						+ Objective.Values.DEF_FRECKLES.getValue() + " freckles candies!")
				.build();
	}

	/**
	 * The player has to destroy all jelly.
	 *
	 * @return a new {@link Objective}
	 */
	public static Objective jelly() {
		return Objective.builder()
				.setChallenge(Optional.of(
						new ChallengeBuilderImpl().setDestroyJelly(true).build()))
				.setObjectiveText("Reach a score of " + Objective.Values.SIMPLE_SCORE.getValue() + " within "
						+ Objective.Values.DEF_MOVES.getValue() + " moves and destroy all jelly!")
				.build();
	}
}
