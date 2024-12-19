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

/**
 * An interface to manage all available {@link Level}s.
 *
 * @author Filippo Barbari
 */
public interface LevelsManager {

	/**
	 * @param levelNumber The number of level to be returned.
	 * @return The level corresponding to the given number.
	 * @throws IllegalStateException If a level with the given number doesn't exist
	 */
	Level getLevel(final int levelNumber);

	/** @return The {@link Level} representing the tutorial. */
	Level getTutorial();

	/** @return The number of available levels excluding tutorial. */
	int getNumLevels();
}
