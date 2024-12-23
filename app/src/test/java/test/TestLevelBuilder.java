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

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import controller.Controller;
import controller.ControllerImpl;
import model.game.grid.candies.CandyColors;
import model.game.level.LevelBuilder;
import model.game.level.LevelBuilderImpl;
import model.game.level.stage.StageBuilderImpl;
import model.objectives.ObjectiveFactory;

/** @author Filippo Barbari */
final class TestLevelBuilder {

	private LevelBuilder lb;
	private Controller controller;

	@BeforeEach
	public void prepare() {
		lb = new LevelBuilderImpl();
		controller = new ControllerImpl();
	}

	@Test
	void nullStage() {
		assertThrows(NullPointerException.class, () -> lb.addStage(null));
	}

	@Test
	void cantBuildTwice() {
		lb.addStage(new StageBuilderImpl()
						.setDimensions(3, 3)
						.addAvailableColor(CandyColors.BLUE)
						.addAvailableColor(CandyColors.GREEN)
						.addAvailableColor(CandyColors.RED)
						.setObjective(ObjectiveFactory.explode())
						.setController(controller)
						.build())
				.setController(controller)
				.build();
		assertThrows(IllegalStateException.class, () -> lb.build());
	}

	@Test
	void emptyStages() {
		assertThrows(IllegalStateException.class, () -> lb.build());
	}

	@Test
	void cantCallMethodsAfterBuilding() {
		lb.addStage(new StageBuilderImpl()
						.setDimensions(3, 3)
						.addAvailableColor(CandyColors.BLUE)
						.addAvailableColor(CandyColors.GREEN)
						.addAvailableColor(CandyColors.RED)
						.setObjective(ObjectiveFactory.explode())
						.setController(controller)
						.build())
				.setController(controller)
				.build();

		assertThrows(
				IllegalStateException.class,
				() -> lb.addStage(new StageBuilderImpl()
						.setDimensions(3, 3)
						.addAvailableColor(CandyColors.BLUE)
						.addAvailableColor(CandyColors.GREEN)
						.addAvailableColor(CandyColors.RED)
						.setObjective(ObjectiveFactory.explode())
						.setController(controller)
						.build()));

		assertThrows(IllegalStateException.class, () -> lb.setController(controller));
	}
}
