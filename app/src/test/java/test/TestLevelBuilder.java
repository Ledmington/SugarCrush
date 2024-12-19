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

import org.junit.Before;
import org.junit.Test;

import controller.Controller;
import controller.ControllerImpl;
import model.game.grid.candies.CandyColors;
import model.game.level.LevelBuilder;
import model.game.level.LevelBuilderImpl;
import model.game.level.stage.StageBuilderImpl;
import model.objectives.ObjectiveFactoryImpl;

/** @author Filippo Barbari */
public final class TestLevelBuilder {

	private LevelBuilder lb;
	private Controller controller;

	public TestLevelBuilder() {}

	@Before
	public final void prepare() {
		lb = new LevelBuilderImpl();
		controller = new ControllerImpl();
	}

	@Test(expected = NullPointerException.class)
	public final void nullStage() {
		lb.addStage(null);
		fail("Passing null pointers shoudln't be allowed.");
	}

	@Test(expected = IllegalStateException.class)
	public final void cantBuildTwice() {
		lb.addStage(new StageBuilderImpl()
						.setDimensions(3, 3)
						.addAvailableColor(CandyColors.BLUE)
						.addAvailableColor(CandyColors.GREEN)
						.addAvailableColor(CandyColors.RED)
						.setObjective(new ObjectiveFactoryImpl().explode())
						.build())
				.build();
		lb.build();
		fail("Building the same Level twice shouldn't be allowed.");
	}

	@Test(expected = IllegalStateException.class)
	public final void emptyStages() {
		lb.build();
		fail("Building a Level with no Stages inside shouldn't be allowed.");
	}

	@Test
	public final void cantCallMethodsAfterBuilding() {
		lb.addStage(new StageBuilderImpl()
						.setDimensions(3, 3)
						.addAvailableColor(CandyColors.BLUE)
						.addAvailableColor(CandyColors.GREEN)
						.addAvailableColor(CandyColors.RED)
						.setObjective(new ObjectiveFactoryImpl().explode())
						.setController(controller)
						.build())
				.setController(controller)
				.build();
		final String msg = "Calling methods after building shouldn't be allowed.";

		try {
			lb.addStage(new StageBuilderImpl()
					.setDimensions(3, 3)
					.addAvailableColor(CandyColors.BLUE)
					.addAvailableColor(CandyColors.GREEN)
					.addAvailableColor(CandyColors.RED)
					.setObjective(new ObjectiveFactoryImpl().explode())
					.setController(controller)
					.build());
			fail(msg);
		} catch (IllegalStateException e) {
		}

		try {
			lb.setController(controller);
			fail(msg);
		} catch (IllegalStateException e) {
		}
	}
}
