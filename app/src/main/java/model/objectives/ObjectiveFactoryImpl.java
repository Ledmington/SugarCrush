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

import static model.objectives.Objective.Values.*;

import java.util.Optional;

/**
 * Implementation of {@link ObjectiveFactory}
 *
 * @author Emanuele Lamagna
 */
public final class ObjectiveFactoryImpl implements ObjectiveFactory {

	public Objective normal() {
		return new ObjectiveBuilderImpl()
				.setMaxScore(DEF_SCORE.getValue())
				.setObjectiveText(
						"Reach a score of " + DEF_SCORE.getValue() + " within " + DEF_MOVES.getValue() + " moves!")
				.build();
	}

	public Objective primary() {
		return new ObjectiveBuilderImpl()
				.setChallenge(Optional.of(new ChallengeBuilderImpl()
						.setRed(DEF_RED.getValue())
						.setBlue(DEF_BLUE.getValue())
						.setYellow(DEF_YELLOW.getValue())
						.build()))
				.setObjectiveText("Reach a score of " + SIMPLE_SCORE.getValue() + " within " + DEF_MOVES.getValue()
						+ " moves and destroy "
						+ DEF_RED.getValue() + " red candies, " + DEF_YELLOW.getValue() + " yellow candies and "
						+ DEF_BLUE.getValue() + " blue candies!")
				.build();
	}

	@Override
	public Objective lineParty() {
		return new ObjectiveBuilderImpl()
				.setChallenge(Optional.of(new ChallengeBuilderImpl()
						.setStriped(DEF_STRIPED.getValue())
						.build()))
				.setObjectiveText("Reach a score of " + SIMPLE_SCORE.getValue() + " within " + DEF_MOVES.getValue()
						+ " moves and farm " + DEF_STRIPED.getValue() + " striped candies!")
				.build();
	}

	@Override
	public Objective explode() {
		return new ObjectiveBuilderImpl()
				.setChallenge(Optional.of(new ChallengeBuilderImpl()
						.setWrapped(DEF_WRAPPED.getValue())
						.build()))
				.setObjectiveText("Reach a score of " + SIMPLE_SCORE.getValue() + " within " + DEF_MOVES.getValue()
						+ " moves and farm " + DEF_WRAPPED.getValue() + " wrapped candies!")
				.build();
	}

	@Override
	public Objective multiBombs() {
		return new ObjectiveBuilderImpl()
				.setChallenge(Optional.of(new ChallengeBuilderImpl()
						.setFreckles(DEF_FRECKLES.getValue())
						.build()))
				.setObjectiveText("Reach a score of " + SIMPLE_SCORE.getValue() + " within " + DEF_MOVES.getValue()
						+ " moves and farm " + DEF_FRECKLES.getValue() + " freckles candies!")
				.build();
	}

	@Override
	public Objective jelly() {
		return new ObjectiveBuilderImpl()
				.setChallenge(Optional.of(
						new ChallengeBuilderImpl().setDestroyJelly(true).build()))
				.setObjectiveText("Reach a score of " + SIMPLE_SCORE.getValue() + " within " + DEF_MOVES.getValue()
						+ " moves and destroy all jelly!")
				.build();
	}
}
