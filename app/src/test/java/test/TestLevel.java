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

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import controller.Controller;
import controller.ControllerImpl;
import model.game.GameResult;
import model.game.grid.candies.CandyColors;
import model.game.level.Level;
import model.game.level.LevelBuilderImpl;
import model.game.level.stage.Stage;
import model.game.level.stage.StageBuilderImpl;
import model.objectives.ObjectiveFactoryImpl;

/** @author Filippo Barbari */
public final class TestLevel {

	private Level l;

	@BeforeEach
	public void prepare() {
		final Controller controller = new ControllerImpl();
		final Stage s1 = new StageBuilderImpl()
				.setDimensions(3, 3)
				.addAvailableColor(CandyColors.BLUE)
				.addAvailableColor(CandyColors.GREEN)
				.addAvailableColor(CandyColors.ORANGE)
				.addAvailableColor(CandyColors.PURPLE)
				.setObjective(new ObjectiveFactoryImpl().explode())
				.setController(controller)
				.build();
		final Stage s2 = new StageBuilderImpl()
				.setDimensions(3, 3)
				.addAvailableColor(CandyColors.BLUE)
				.addAvailableColor(CandyColors.GREEN)
				.addAvailableColor(CandyColors.ORANGE)
				.addAvailableColor(CandyColors.PURPLE)
				.setObjective(new ObjectiveFactoryImpl().explode())
				.setController(controller)
				.build();
		l = new LevelBuilderImpl()
				.addStage(s1)
				.addStage(s2)
				.setController(controller)
				.build();
	}

	@Test
	public void checkResult() {
		assertSame(GameResult.STILL_PLAYING, l.getResult());
	}

	@Test
	public void stageIterationCorrect() {
		assertTrue(l.hasNextStage());
		l.nextStage();
		assertFalse(l.hasNextStage());
	}
}
