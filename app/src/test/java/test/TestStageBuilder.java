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

import static org.junit.Assert.fail;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;

import controller.Controller;
import controller.ControllerImpl;
import model.game.grid.candies.Candy;
import model.game.grid.candies.CandyColors;
import model.game.grid.candies.CandyFactory;
import model.game.grid.candies.CandyFactoryImpl;
import model.game.level.stage.StageBuilder;
import model.game.level.stage.StageBuilderImpl;
import model.objectives.ObjectiveFactory;
import model.objectives.ObjectiveFactoryImpl;
import utils.Point2D;

/** @author Filippo Barbari */
public final class TestStageBuilder {

	private StageBuilder sb;
	private CandyFactory cf;
	private ObjectiveFactory of;
	private Controller controller;

	public TestStageBuilder() {}

	@Before
	public final void prepare() {
		sb = new StageBuilderImpl();
		cf = new CandyFactoryImpl();
		of = new ObjectiveFactoryImpl();
		controller = new ControllerImpl();
	}

	@Test
	public final void zeroOrNegativeDimensions() {
		final String msg = "Zero or negative values shouldn't be passed as grid dimensions.";
		try {
			sb.setDimensions(5, 0);
			fail(msg);
		} catch (IllegalArgumentException e) {
		}

		try {
			sb.setDimensions(0, 2);
			fail(msg);
		} catch (IllegalArgumentException e) {
		}

		try {
			sb.setDimensions(-1, -1);
			fail(msg);
		} catch (IllegalArgumentException e) {
		}
	}

	@Test(expected = NullPointerException.class)
	public final void nullEmptyCells() {
		sb.setEmptyCells(null);
		fail("Null pointer shouldn't be passed.");
	}

	@Test(expected = IllegalStateException.class)
	public final void emptyCellsWithoutGrid() {
		final Set<Point2D> s = new HashSet<>();
		s.add(new Point2D(1, 1));
		sb.setEmptyCells(s);
		fail("Setting empty cells shouldn't be allowed before creating grid.");
	}

	@Test(expected = NullPointerException.class)
	public final void nullObjective() {
		sb.setObjective(null);
		fail("Null pointer shouldn't be passed.");
	}

	@Test(expected = NullPointerException.class)
	public final void nullChocolate() {
		sb.addChocolatePosition(null);
		fail("Null pointer shouldn't be passed.");
	}

	@Test(expected = IllegalArgumentException.class)
	public final void chocolateNotInGrid() {
		sb.setDimensions(3, 3).addChocolatePosition(new Point2D(4, 4));
		fail("Setting chocolate outside grid shouldn't be allowed.");
	}

	@Test(expected = NullPointerException.class)
	public final void nullColor() {
		sb.addAvailableColor(null);
		fail("Null pointer shouldn't be passed.");
	}

	@Test(expected = NullPointerException.class)
	public final void nullCandies() {
		sb.setCandies(null);
		fail("Null pointer shouldn't be passed.");
	}

	@Test
	public final void candiesNotInGrid() {
		final String msg = "Setting candies in unexisting positions shouldn't be allowed.";
		final Map<Point2D, Candy> m = new HashMap<>();
		m.put(new Point2D(3, 3), cf.getFreckles());

		// Setting candies without map
		try {
			sb.setCandies(m);
			fail(msg);
		} catch (IllegalArgumentException e) {
		}

		// Setting candies with map
		try {
			sb.setDimensions(2, 2).setCandies(m);
			fail(msg);
		} catch (IllegalArgumentException e) {
		}
	}

	@Test(expected = IllegalArgumentException.class)
	public final void twoCandiesInSamePosition() {
		final Map<Point2D, Candy> m = new HashMap<>();
		m.put(new Point2D(3, 3), cf.getFreckles());

		sb.setDimensions(5, 5).setCandies(m).setCandies(m);

		fail("Setting twice the same position shouldn't be allowed.");
	}

	@Test(expected = NullPointerException.class)
	public final void emptyCandies() {
		final Map<Point2D, Candy> m = new HashMap<>();
		m.put(new Point2D(1, 1), null);

		sb.setDimensions(5, 5).setCandies(m);
		fail("Passed map of candies shouldn't contain null values.");
	}

	@Test(expected = NullPointerException.class)
	public final void nullStartingMessage() {
		sb.setStartingMessage(null);
		fail("Passing null pointers shouldn't be allowed.");
	}

	@Test(expected = NullPointerException.class)
	public final void nullEndingMessage() {
		sb.setEndingMessage(null);
		fail("Passing null pointers shouldn't be allowed.");
	}

	@Test(expected = IllegalStateException.class)
	public final void cantBuildTwice() {
		sb.setDimensions(5, 5)
				.addAvailableColor(CandyColors.BLUE)
				.addAvailableColor(CandyColors.GREEN)
				.addAvailableColor(CandyColors.ORANGE)
				.addAvailableColor(CandyColors.YELLOW)
				.addAvailableColor(CandyColors.RED)
				.setObjective(of.explode())
				.setController(controller)
				.build();
		sb.build();
		fail("Building same stage twice shouldn't be allowed.");
	}

	@Test(expected = IllegalStateException.class)
	public final void cantBuildWithoutGrid() {
		sb.build();
		fail("Building the stage without grid shouldn't be allowed.");
	}

	@Test(expected = IllegalStateException.class)
	public final void cantBuildWithoutColors() {
		sb.setDimensions(5, 5).build();
		fail("Building the stage without colors shouldn't be allowed.");
	}

	@Test(expected = IllegalStateException.class)
	public final void gridOnlyChocolate() {
		sb.setDimensions(1, 1)
				.addAvailableColor(CandyColors.BLUE)
				.addChocolatePosition(new Point2D(0, 0))
				.build();
		fail("Setting the whole grid to be chocolate shouldn't be allowed.");
	}

	@Test(expected = IllegalStateException.class)
	public final void chocolateAndJelly() {
		sb.setDimensions(5, 5)
				.addAvailableColor(CandyColors.BLUE)
				.addChocolatePosition(new Point2D(2, 2))
				.addJelly()
				.build();
		fail("Having chocolate and jelly in the same stage shouldn't be allowed.");
	}

	@Test(expected = IllegalStateException.class)
	public final void chocolateAndCandyInSamePosition() {
		final Map<Point2D, Candy> m = new HashMap<>();
		m.put(new Point2D(2, 2), cf.getVerticalStripedCandy(CandyColors.BLUE));

		sb.setDimensions(5, 5)
				.setCandies(m)
				.addChocolatePosition(new Point2D(2, 2))
				.build();
		fail("Having a candy and chocolate in the same position shouldn't be allowed.");
	}

	@Test(expected = IllegalStateException.class)
	public final void buildWithoutObjective() {
		sb.setDimensions(5, 5).addAvailableColor(CandyColors.BLUE).build();
		fail("Building without objective shouldn't be allowed.");
	}

	@Test
	public final void cantDoAnythingAfterBuilding() {
		sb.setDimensions(5, 5)
				.addAvailableColor(CandyColors.BLUE)
				.addAvailableColor(CandyColors.GREEN)
				.addAvailableColor(CandyColors.ORANGE)
				.addAvailableColor(CandyColors.YELLOW)
				.addAvailableColor(CandyColors.RED)
				.setObjective(of.explode())
				.setController(controller)
				.build();

		try {
			sb.setDimensions(3, 3);
			fail("Calling methods after building shouldn't be allowed.");
		} catch (IllegalStateException e) {
		}

		try {
			sb.addAvailableColor(CandyColors.BLUE);
			fail("Calling methods after building shouldn't be allowed.");
		} catch (IllegalStateException e) {
		}

		try {
			sb.addChocolatePosition(new Point2D(2, 2));
			fail("Calling methods after building shouldn't be allowed.");
		} catch (IllegalStateException e) {
		}

		try {
			sb.addJelly();
			fail("Calling methods after building shouldn't be allowed.");
		} catch (IllegalStateException e) {
		}

		try {
			sb.setCandies(new HashMap<Point2D, Candy>());
			fail("Calling methods after building shouldn't be allowed.");
		} catch (IllegalStateException e) {
		}

		try {
			sb.setEmptyCells(new HashSet<Point2D>());
			fail("Calling methods after building shouldn't be allowed.");
		} catch (IllegalStateException e) {
		}

		try {
			sb.setEndingMessage("Bye");
			fail("Calling methods after building shouldn't be allowed.");
		} catch (IllegalStateException e) {
		}

		try {
			sb.setStartingMessage("Hello");
			fail("Calling methods after building shouldn't be allowed.");
		} catch (IllegalStateException e) {
		}

		try {
			sb.setObjective(of.lineParty());
			fail("Calling methods after building shouldn't be allowed.");
		} catch (IllegalStateException e) {
		}
	}
}
