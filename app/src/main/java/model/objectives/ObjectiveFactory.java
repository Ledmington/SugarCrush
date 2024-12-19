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
package model.objectives;

/**
 * A class that creates an {@link Objective} (using the factory method pattern). If the {@link Challenge} is empty, the
 * player only has to reach the score in the def moves, otherwise he has to complete the {@link Challenge} too
 *
 * @author Emanuele Lamagna
 */
public interface ObjectiveFactory {

	/**
	 * The player doesn't have any {@link Challenge}
	 *
	 * @return a new Objective
	 */
	Objective normal();

	/**
	 * The player has to destroy 10 red candies, 10 yellow candies and 10 blue candies
	 *
	 * @return a new {@link Objective}
	 */
	Objective primary();

	/**
	 * The player has to farm 4 striped candies
	 *
	 * @return a new {@link Objective}
	 */
	Objective lineParty();

	/**
	 * The player has to farm 2 wrapped candies
	 *
	 * @return a new {@link Objective}
	 */
	Objective explode();

	/**
	 * The player has to farm 1 freckles candies
	 *
	 * @return a new {@link Objective}
	 */
	Objective multiBombs();

	/**
	 * The player has to destroy all jelly
	 *
	 * @return a new {@link Objective}
	 */
	Objective jelly();
}
