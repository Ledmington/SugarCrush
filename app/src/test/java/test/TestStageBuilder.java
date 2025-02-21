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

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import controller.Controller;
import controller.ControllerImpl;
import model.game.grid.candies.Candy;
import model.game.grid.candies.CandyColors;
import model.game.grid.candies.CandyFactory;
import model.game.level.stage.StageBuilder;
import model.game.level.stage.StageBuilderImpl;
import model.objectives.ObjectiveFactory;
import utils.Point2D;

/** @author Filippo Barbari */
final class TestStageBuilder {

	private StageBuilder sb;
	private Controller controller;

	public TestStageBuilder() {}

	@BeforeEach
	public void prepare() {
		sb = new StageBuilderImpl();
		controller = new ControllerImpl();
	}

	@Test
	void zeroOrNegativeDimensions() {
		assertThrows(IllegalArgumentException.class, () -> sb.setDimensions(5, 0));
		assertThrows(IllegalArgumentException.class, () -> sb.setDimensions(0, 2));
		assertThrows(IllegalArgumentException.class, () -> sb.setDimensions(-1, -1));
	}

	@Test
	void nullEmptyCells() {
		assertThrows(NullPointerException.class, () -> sb.setEmptyCells(null));
	}

	@Test
	void emptyCellsWithoutGrid() {
		final Set<Point2D> s = new HashSet<>();
		s.add(new Point2D(1, 1));
		assertThrows(IllegalStateException.class, () -> sb.setEmptyCells(s));
	}

	@Test
	void nullObjective() {
		assertThrows(NullPointerException.class, () -> sb.setObjective(null));
	}

	@Test
	void nullChocolate() {
		assertThrows(NullPointerException.class, () -> sb.addChocolatePosition(null));
	}

	@Test
	void chocolateNotInGrid() {
		sb.setDimensions(3, 3);
		assertThrows(IllegalArgumentException.class, () -> sb.addChocolatePosition(new Point2D(4, 4)));
	}

	@Test
	void nullColor() {
		assertThrows(NullPointerException.class, () -> sb.addAvailableColor(null));
	}

	@Test
	void nullCandies() {
		assertThrows(NullPointerException.class, () -> sb.setCandies(null));
	}

	@Test
	void candiesNotInGrid() {
		final Map<Point2D, Candy> m = new HashMap<>();
		m.put(new Point2D(3, 3), CandyFactory.getFreckles());

		// Setting candies without map
		assertThrows(IllegalArgumentException.class, () -> sb.setCandies(m));

		// Setting candies with map
		assertThrows(
				IllegalArgumentException.class, () -> sb.setDimensions(2, 2).setCandies(m));
	}

	@Test
	void twoCandiesInSamePosition() {
		final Map<Point2D, Candy> m = new HashMap<>();
		m.put(new Point2D(3, 3), CandyFactory.getFreckles());

		sb.setDimensions(5, 5).setCandies(m);
		assertThrows(IllegalArgumentException.class, () -> sb.setCandies(m));
	}

	@Test
	void emptyCandies() {
		final Map<Point2D, Candy> m = new HashMap<>();
		m.put(new Point2D(1, 1), null);
		sb.setDimensions(5, 5);
		assertThrows(NullPointerException.class, () -> sb.setCandies(m));
	}

	@Test
	void nullStartingMessage() {
		assertThrows(NullPointerException.class, () -> sb.setStartingMessage(null));
	}

	@Test
	void nullEndingMessage() {
		assertThrows(NullPointerException.class, () -> sb.setEndingMessage(null));
	}

	@Test
	void cantBuildTwice() {
		sb.setDimensions(5, 5)
				.addAvailableColor(CandyColors.BLUE)
				.addAvailableColor(CandyColors.GREEN)
				.addAvailableColor(CandyColors.ORANGE)
				.addAvailableColor(CandyColors.YELLOW)
				.addAvailableColor(CandyColors.RED)
				.setObjective(ObjectiveFactory.explode())
				.setController(controller)
				.build();
		assertThrows(IllegalStateException.class, () -> sb.build());
	}

	@Test
	void cantBuildWithoutGrid() {
		assertThrows(IllegalStateException.class, () -> sb.build());
	}

	@Test
	void cantBuildWithoutColors() {
		sb.setDimensions(5, 5);
		assertThrows(IllegalStateException.class, () -> sb.build());
	}

	@Test
	void gridOnlyChocolate() {
		sb.setDimensions(1, 1).addAvailableColor(CandyColors.BLUE).addChocolatePosition(new Point2D(0, 0));
		assertThrows(IllegalStateException.class, () -> sb.build());
	}

	@Test
	void chocolateAndJelly() {
		sb.setDimensions(5, 5)
				.addAvailableColor(CandyColors.BLUE)
				.addChocolatePosition(new Point2D(2, 2))
				.addJelly();

		assertThrows(IllegalStateException.class, () -> sb.build());
	}

	@Test
	void chocolateAndCandyInSamePosition() {
		final Map<Point2D, Candy> m = new HashMap<>();
		m.put(new Point2D(2, 2), CandyFactory.getVerticalStripedCandy(CandyColors.BLUE));
		sb.setDimensions(5, 5).setCandies(m).addChocolatePosition(new Point2D(2, 2));
		assertThrows(IllegalStateException.class, () -> sb.build());
	}

	@Test
	void buildWithoutObjective() {
		sb.setDimensions(5, 5).addAvailableColor(CandyColors.BLUE);
		assertThrows(IllegalStateException.class, () -> sb.build());
	}

	@Test
	void cantDoAnythingAfterBuilding() {
		sb.setDimensions(5, 5)
				.addAvailableColor(CandyColors.BLUE)
				.addAvailableColor(CandyColors.GREEN)
				.addAvailableColor(CandyColors.ORANGE)
				.addAvailableColor(CandyColors.YELLOW)
				.addAvailableColor(CandyColors.RED)
				.setObjective(ObjectiveFactory.explode())
				.setController(controller)
				.build();

		assertThrows(IllegalStateException.class, () -> sb.setDimensions(3, 3));
		assertThrows(IllegalStateException.class, () -> sb.addAvailableColor(CandyColors.BLUE));
		assertThrows(IllegalStateException.class, () -> sb.addChocolatePosition(new Point2D(2, 2)));
		assertThrows(IllegalStateException.class, () -> sb.addJelly());
		assertThrows(IllegalStateException.class, () -> sb.setCandies(new HashMap<>()));
		assertThrows(IllegalStateException.class, () -> sb.setEmptyCells(new HashSet<>()));
		assertThrows(IllegalStateException.class, () -> sb.setEndingMessage("Bye"));
		assertThrows(IllegalStateException.class, () -> sb.setStartingMessage("Hello"));
		assertThrows(IllegalStateException.class, () -> sb.setObjective(ObjectiveFactory.lineParty()));
	}
}
