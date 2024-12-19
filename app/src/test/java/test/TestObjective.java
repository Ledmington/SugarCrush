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

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Optional;

import org.junit.Test;

import model.objectives.*;

/**
 * A class that tests the right operation of the {@link Objective} creation
 *
 * @author Emanuele Lamagna
 */
public final class TestObjective {

	private Objective ob;
	private ObjectiveBuilder obb;
	private ChallengeBuilder ch;

	/** Creates a normal {@link Objective} */
	@Test
	public final void testNormalObjective() {
		ob = new ObjectiveFactoryImpl().normal();
		assertTrue(ob.getChallenge().equals(Optional.empty()));
	}

	/** Creates an explode {@link Objective} */
	@Test
	public final void testExplodeObjective() {
		ob = new ObjectiveFactoryImpl().explode();
		assertTrue(!ob.getChallenge().equals(Optional.empty()));
		assertTrue(ob.getChallenge().get().getWrappedToFarm() == Objective.Values.DEF_WRAPPED.getValue());
		assertTrue(ob.getChallenge().get().getFrecklesToFarm() == 0);
	}

	/** Test if an {@link Objective} can be built twice */
	@Test(expected = IllegalStateException.class)
	public final void testDoubleBuildObjective() {
		obb = new ObjectiveBuilderImpl();
		obb.setMaxScore(10000).setMaxMoves(20).build();
		obb.build();
	}

	/** Test if an {@link Objective} can have an empty string */
	@Test(expected = IllegalArgumentException.class)
	public final void testStringNotEmpty() {
		obb = new ObjectiveBuilderImpl();
		obb.setMaxScore(10000).setMaxMoves(20).setObjectiveText("").build();
	}

	/** Test if an {@link Objective} can have a negative parameter */
	@Test(expected = IllegalArgumentException.class)
	public final void testNotNegativeObjective() {
		obb = new ObjectiveBuilderImpl();
		obb.setMaxScore(-1).setMaxMoves(20).build();
	}

	/** Test if a {@link Challenge} can be built twice */
	@Test(expected = IllegalStateException.class)
	public final void testDoubleBuildChallenge() {
		ch = new ChallengeBuilderImpl();
		ch.setBlue(10).setRed(10).setYellow(10).build();
		ch.build();
	}

	/** Test if an {@link Challenge} can have a negative parameter */
	@Test(expected = IllegalArgumentException.class)
	public final void testNotNegativeChallenge() {
		ch = new ChallengeBuilderImpl();
		ch.setBlue(10).setRed(10).setYellow(-4).build();
	}
}
