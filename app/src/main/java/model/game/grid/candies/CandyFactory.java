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
public final class CandyFactory {

	private CandyFactory() {}

	/**
	 * Return normal {@link Candy} with specified {@link CandyColors}.
	 *
	 * @param cndColor The {@link CandyColors} of the {@link Candy}.
	 * @return The {@link Candy} obtained.
	 */
	public static Candy getNormalCandy(final CandyColors cndColor) {
		if (cndColor == CandyColors.CHOCOLATE || cndColor == CandyColors.FRECKLES) {
			throw new IllegalArgumentException("Chocolate and freckles are not normal candies.");
		}

		return new CandyBuilderImpl()
				.setColor(cndColor)
				.setType(CandyTypes.NORMAL)
				.build();
	}

	/**
	 * Return vertical striped {@link Candy} with specified {@link CandyColors}.
	 *
	 * @param cndColor The {@link CandyColors} of the {@link Candy}.
	 * @return The {@link Candy} obtained.
	 */
	public static Candy getVerticalStripedCandy(final CandyColors cndColor) {
		if (cndColor == CandyColors.CHOCOLATE || cndColor == CandyColors.FRECKLES) {
			throw new IllegalArgumentException("Chocolate and freckles are not vertical striped candies.");
		}

		return new CandyBuilderImpl()
				.setColor(cndColor)
				.setType(CandyTypes.STRIPED_VERTICAL)
				.build();
	}

	/**
	 * Return horizontal striped {@link Candy} with specified {@link CandyColors}.
	 *
	 * @param cndColor The {@link CandyColors} of the {@link Candy}.
	 * @return The {@link Candy} obtained.
	 */
	public static Candy getHorizontalStriped(final CandyColors cndColor) {
		if (cndColor == CandyColors.CHOCOLATE || cndColor == CandyColors.FRECKLES) {
			throw new IllegalArgumentException("Chocolate and freckles are not horizontal striped candies.");
		}

		return new CandyBuilderImpl()
				.setColor(cndColor)
				.setType(CandyTypes.STRIPED_HORIZONTAL)
				.build();
	}

	/**
	 * Return wrapped {@link Candy} with specified {@link CandyColors}.
	 *
	 * @param cndColor The {@link CandyColors} of the {@link Candy}.
	 * @return The {@link Candy} obtained.
	 */
	public static Candy getWrapped(final CandyColors cndColor) {
		if (cndColor == CandyColors.CHOCOLATE || cndColor == CandyColors.FRECKLES) {
			throw new IllegalArgumentException("Chocolate and freckles are not wrapper candies.");
		}

		return new CandyBuilderImpl()
				.setColor(cndColor)
				.setType(CandyTypes.WRAPPED)
				.build();
	}

	/**
	 * Return freckles {@link Candy}.
	 *
	 * @return The {@link Candy} obtained.
	 */
	public static Candy getFreckles() {
		return new CandyBuilderImpl()
				.setColor(CandyColors.FRECKLES)
				.setType(CandyTypes.FRECKLES)
				.build();
	}

	/**
	 * Return chocolate {@link Candy}.
	 *
	 * @return The {@link Candy} obtained.
	 */
	public static Candy getChocolate() {
		return new CandyBuilderImpl()
				.setColor(CandyColors.CHOCOLATE)
				.setType(CandyTypes.CHOCOLATE)
				.build();
	}
}
