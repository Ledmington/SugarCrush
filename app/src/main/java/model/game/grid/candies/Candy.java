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

/** @author Filippo Benvenuti */
public interface Candy {
	/**
	 * Retrieves the {@link CandyColors} of the {@link Candy}.
	 *
	 * @return the {@link CandyColors} of the {@link Candy}.
	 */
	CandyColors getColor();

	/**
	 * Retrieves the {@link CandyTypes} of the {@link Candy}.
	 *
	 * @return the {@link CandyTypes} of the {@link Candy}.
	 */
	CandyTypes getType();

	int hashCode();

	boolean equals(Object obj);

	String toString();
}
