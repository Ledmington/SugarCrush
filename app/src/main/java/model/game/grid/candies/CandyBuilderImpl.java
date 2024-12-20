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

import java.util.Optional;

public final class CandyBuilderImpl implements CandyBuilder {

	private Optional<CandyColors> cndCol = Optional.empty();
	private Optional<CandyTypes> cndTyp = Optional.empty();

	private boolean built = false;

	@Override
	public CandyBuilder setColor(final CandyColors cndCol) {
		if (this.built) {
			throw new IllegalStateException("Candy already built");
		}
		this.cndCol = Optional.of(cndCol);
		return this;
	}

	@Override
	public CandyBuilder setType(final CandyTypes cndTyp) {
		if (this.built) {
			throw new IllegalStateException("Candy already built");
		}
		this.cndTyp = Optional.of(cndTyp);
		return this;
	}

	@Override
	public Candy build() {
		if (this.built) {
			throw new IllegalStateException("Candy can't be built twice");
		}
		this.built = true;
		if (this.cndCol.isEmpty() || this.cndTyp.isEmpty()) {
			throw new IllegalStateException("Color and type must be set first");
		}
		return new Candy() {

			@Override
			public CandyTypes getType() {
				return cndTyp.orElseThrow();
			}

			@Override
			public CandyColors getColor() {
				return cndCol.orElseThrow();
			}

			@Override
			public int hashCode() {
				final int prime = 31;
				int result = 1;
				result = prime * result + cndCol.hashCode();
				result = prime * result + cndTyp.hashCode();
				return result;
			}

			@Override
			public boolean equals(final Object obj) {
				if (obj == null) {
					return false;
				}
				if (this.getClass() != obj.getClass()) {
					return false;
				}
				return cndCol.orElseThrow() == ((Candy) obj).getColor()
						&& cndTyp.orElseThrow() == ((Candy) obj).getType();
			}

			@Override
			public String toString() {
				return cndTyp.orElseThrow().name() + "_" + cndCol.orElseThrow().name();
			}
		};
	}
}
