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

public interface CandyBuilder {

	/**
	 * Sets the {@link CandyColors} of the {@link Candy}.
	 *
	 * @param cndCol The {@link CandyColors}.
	 * @return This instance of {@link CandyBuilder}.
	 */
	CandyBuilder setColor(CandyColors cndCol);

	/**
	 * Sets the type of the {@link Candy}.
	 *
	 * @param cndTyp The {@link CandyTypes}.
	 * @return This instance of {@link CandyBuilder}.
	 */
	CandyBuilder setType(CandyTypes cndTyp);

	/**
	 * Build the {@link Candy} with settings set.
	 *
	 * @return The {@link Candy} if settings were set.
	 */
	Candy build();
}
