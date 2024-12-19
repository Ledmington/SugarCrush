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
package model.game.grid.candies;

/**
 * The list of every type of {@link Candy}.
 *
 * @author Filippo Benvenuti
 */
public enum CandyTypes {
	NORMAL, // Normal behaviour.
	STRIPED_VERTICAL, // Destroys vertical line where it get destroyed.
	STRIPED_HORIZONTAL, // Destroys horizontal line where it get destroyed.
	WRAPPED, // Destroys 8 candyes near-by where it get destroyed.
	FRECKLES, // Destroys all candies with the same color of the one switched with that. (and itself)
	CHOCOLATE // Can't be moved, get destroyed by near destroyed candies.
}
