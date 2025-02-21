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

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import controller.Controller;
import controller.ControllerImpl;
import model.game.grid.candies.Candy;
import model.game.grid.candies.CandyColors;
import model.game.grid.candies.CandyFactory;
import model.game.level.stage.Stage;
import model.game.level.stage.StageBuilderImpl;
import model.objectives.ObjectiveFactory;
import utils.Point2D;

/** @author Filippo Barbari */
final class TestStage {

	private Stage s;
	private final Map<Point2D, Candy> m = new HashMap<>();

	public TestStage() {}

	@BeforeEach
	public void prepare() {
		final Controller controller = new ControllerImpl();

		m.put(new Point2D(0, 0), CandyFactory.getNormalCandy(CandyColors.BLUE));
		m.put(new Point2D(0, 1), CandyFactory.getNormalCandy(CandyColors.RED));
		m.put(new Point2D(0, 2), CandyFactory.getNormalCandy(CandyColors.YELLOW));
		m.put(new Point2D(1, 0), CandyFactory.getNormalCandy(CandyColors.PURPLE));
		m.put(new Point2D(1, 1), CandyFactory.getNormalCandy(CandyColors.YELLOW));
		m.put(new Point2D(1, 2), CandyFactory.getNormalCandy(CandyColors.BLUE));
		m.put(new Point2D(2, 0), CandyFactory.getNormalCandy(CandyColors.RED));
		m.put(new Point2D(2, 1), CandyFactory.getNormalCandy(CandyColors.ORANGE));
		m.put(new Point2D(2, 2), CandyFactory.getNormalCandy(CandyColors.ORANGE));

		s = new StageBuilderImpl()
				.setDimensions(3, 3)
				.setCandies(m)
				.setObjective(ObjectiveFactory.explode())
				.setController(controller)
				.build();
	}

	@Test
	void gridConsistency() {
		final Map<Point2D, Candy> m2 = new HashMap<>();
		final Map<Point2D, Optional<Candy>> grid = s.getGrid();
		grid.keySet().forEach(p -> m2.put(p, grid.get(p).orElseThrow()));
		assertEquals(m2, m);
	}
}
