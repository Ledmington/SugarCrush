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
package test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.Optional;

import org.junit.jupiter.api.Test;

import model.objectives.Challenge;
import model.objectives.ChallengeBuilder;
import model.objectives.ChallengeBuilderImpl;
import model.objectives.Objective;
import model.objectives.ObjectiveBuilder;
import model.objectives.ObjectiveFactory;

/**
 * A class that tests the right operation of the {@link Objective} creation.
 *
 * @author Emanuele Lamagna
 */
final class TestObjective {

	private ObjectiveBuilder obb;
	private ChallengeBuilder ch;

	/** Creates normal {@link Objective}. */
	@Test
	void testNormalObjective() {
		final Objective ob = ObjectiveFactory.normal();
		assertEquals(ob.getChallenge(), Optional.empty());
	}

	/** Creates an explode {@link Objective}. */
	@Test
	void testExplodeObjective() {
		final Objective ob = ObjectiveFactory.explode();
		assertNotEquals(ob.getChallenge(), Optional.empty());
		assertEquals(ob.getChallenge().orElseThrow().getWrappedToFarm(), Objective.Values.DEF_WRAPPED.getValue());
		assertEquals(0, ob.getChallenge().orElseThrow().getFrecklesToFarm());
	}

	/** Test if an {@link Objective} can be built twice. */
	@Test
	void testDoubleBuildObjective() {
		obb = Objective.builder();
		obb.setMaxScore(10000).setMaxMoves(20).build();
		assertThrows(IllegalStateException.class, () -> obb.build());
	}

	/** Test if an {@link Objective} can have an empty string. */
	@Test
	void testStringNotEmpty() {
		obb = Objective.builder();
		obb.setMaxScore(10000).setMaxMoves(20);
		assertThrows(IllegalArgumentException.class, () -> obb.setObjectiveText(""));
	}

	/** Test if an {@link Objective} can have a negative parameter. */
	@Test
	void testNotNegativeObjective() {
		obb = Objective.builder();
		assertThrows(IllegalArgumentException.class, () -> obb.setMaxScore(-1));
	}

	/** Test if a {@link Challenge} can be built twice. */
	@Test
	void testDoubleBuildChallenge() {
		ch = new ChallengeBuilderImpl();
		ch.setBlue(10).setRed(10).setYellow(10).build();
		assertThrows(IllegalStateException.class, () -> ch.build());
	}

	/** Test if an {@link Challenge} can have a negative parameter */
	@Test
	void testNotNegativeChallenge() {
		ch = new ChallengeBuilderImpl();
		ch.setBlue(10).setRed(10);
		assertThrows(IllegalArgumentException.class, () -> ch.setYellow(-4));
	}
}
