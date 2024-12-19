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
public final class CandyFactoryImpl implements CandyFactory {

	public Candy getNormalCandy(final CandyColors cndColor) {
		if (cndColor == CandyColors.CHOCOLATE || cndColor == CandyColors.FRECKLES) {
			throw new IllegalStateException();
		}

		return new CandyBuilderImpl()
				.setColor(cndColor)
				.setType(CandyTypes.NORMAL)
				.build();
	}

	public final Candy getVerticalStripedCandy(final CandyColors cndColor) {
		if (cndColor == CandyColors.CHOCOLATE || cndColor == CandyColors.FRECKLES) {
			throw new IllegalStateException();
		}

		return new CandyBuilderImpl()
				.setColor(cndColor)
				.setType(CandyTypes.STRIPED_VERTICAL)
				.build();
	}

	public final Candy getHorizontalStriped(final CandyColors cndColor) {
		if (cndColor == CandyColors.CHOCOLATE || cndColor == CandyColors.FRECKLES) {
			throw new IllegalStateException();
		}

		return new CandyBuilderImpl()
				.setColor(cndColor)
				.setType(CandyTypes.STRIPED_HORIZONTAL)
				.build();
	}

	public final Candy getWrapped(final CandyColors cndColor) {
		if (cndColor == CandyColors.CHOCOLATE || cndColor == CandyColors.FRECKLES) {
			throw new IllegalStateException();
		}

		return new CandyBuilderImpl()
				.setColor(cndColor)
				.setType(CandyTypes.WRAPPED)
				.build();
	}

	public final Candy getFreckles() {
		return new CandyBuilderImpl()
				.setColor(CandyColors.FRECKLES)
				.setType(CandyTypes.FRECKLES)
				.build();
	}

	public final Candy getChocolate() {
		return new CandyBuilderImpl()
				.setColor(CandyColors.CHOCOLATE)
				.setType(CandyTypes.CHOCOLATE)
				.build();
	}
}
