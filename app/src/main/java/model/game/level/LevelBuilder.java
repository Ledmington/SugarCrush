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
package model.game.level;

import controller.Controller;
import model.game.level.stage.Stage;
import model.score.Status;

/**
 * An interface with builder pattern that allows an easier construction of a {@link Level} object. Each
 * {@link LevelBuilder} can be used to build only one {@link Level} object and then it becomes no more usable.
 *
 * @author Filippo Barbari
 */
public interface LevelBuilder {

	/**
	 * Allows to add a new {@link Stage} to this {@link Level}. Each new {@link Stage} is added at the end of the queue.
	 *
	 * @param newStage The {@link Stage} that is to be added.
	 * @return This instance of {@link LevelBuilder}.
	 */
	LevelBuilder addStage(final Stage newStage);

	/**
	 * Allows to set a {@link Controller} for the current {@link LevelBuilder}.
	 *
	 * @return This instance of {@link LevelBuilder}.
	 */
	LevelBuilder setController(final Controller controller);

	/**
	 * Returns an object implementing the {@link Level} interface with all the data inserted.
	 *
	 * @throws IllegalStateException If build is called twice on the same instance of {@link LevelBuilder} or if no
	 *     {@link Stage} has been added or if at least two {@link Stage}s use the same instance of {@link Status} or if
	 *     no {@link Controller} has been set.
	 */
	Level build();
}
