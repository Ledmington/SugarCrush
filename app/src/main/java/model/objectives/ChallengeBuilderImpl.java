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

import java.util.Objects;

/**
 * Implementation of {@link ChallengeBuilder}
 *
 * @author Emanuele Lamagna
 */
public final class ChallengeBuilderImpl implements ChallengeBuilder {

	private int red = 0;
	private int yellow = 0;
	private int blue = 0;
	private int green = 0;
	private int purple = 0;
	private int orange = 0;
	private int freckles = 0;
	private int striped = 0;
	private int wrapped = 0;
	private boolean jelly = false;
	private boolean built = false;

	@Override
	public final ChallengeBuilder setRed(final int num) {
		check(!this.built);
		assertNotNegative(num);
		this.red = Objects.requireNonNull(num);
		return this;
	}

	@Override
	public final ChallengeBuilder setYellow(final int num) {
		check(!this.built);
		assertNotNegative(num);
		this.yellow = Objects.requireNonNull(num);
		return this;
	}

	@Override
	public final ChallengeBuilder setBlue(final int num) {
		check(!this.built);
		assertNotNegative(num);
		this.blue = Objects.requireNonNull(num);
		return this;
	}

	@Override
	public final ChallengeBuilder setGreen(final int num) {
		check(!this.built);
		assertNotNegative(num);
		this.green = Objects.requireNonNull(num);
		return this;
	}

	@Override
	public final ChallengeBuilder setPurple(final int num) {
		check(!this.built);
		assertNotNegative(num);
		this.purple = Objects.requireNonNull(num);
		return this;
	}

	@Override
	public final ChallengeBuilder setOrange(final int num) {
		check(!this.built);
		assertNotNegative(num);
		this.orange = Objects.requireNonNull(num);
		return this;
	}

	@Override
	public final ChallengeBuilder setFreckles(final int num) {
		check(!this.built);
		assertNotNegative(num);
		this.freckles = Objects.requireNonNull(num);
		return this;
	}

	@Override
	public final ChallengeBuilder setStriped(int num) {
		check(!this.built);
		assertNotNegative(num);
		this.striped = Objects.requireNonNull(num);
		return this;
	}

	@Override
	public final ChallengeBuilder setWrapped(final int num) {
		check(!this.built);
		assertNotNegative(num);
		this.wrapped = Objects.requireNonNull(num);
		return this;
	}

	@Override
	public final ChallengeBuilder setDestroyJelly(final boolean bool) {
		check(!this.built);
		this.jelly = Objects.requireNonNull(bool);
		return this;
	}

	@Override
	public final Challenge build() {
		check(!this.built);
		built = true;
		return new Challenge() {

			@Override
			public final int getRedToDestroy() {
				return red;
			}

			@Override
			public final int getYellowToDestroy() {
				return yellow;
			}

			@Override
			public final int getBlueToDestroy() {
				return blue;
			}

			@Override
			public final int getGreenToDestroy() {
				return green;
			}

			@Override
			public final int getPurpleToDestroy() {
				return purple;
			}

			@Override
			public final int getOrangeToDestroy() {
				return orange;
			}

			@Override
			public final int getFrecklesToFarm() {
				return freckles;
			}

			@Override
			public final int getStripedToFarm() {
				return striped;
			}

			@Override
			public final int getWrappedToFarm() {
				return wrapped;
			}

			@Override
			public final boolean isJellyToDestroy() {
				return jelly;
			}
		};
	}

	// If is already built, throws an exception
	private final void check(final boolean built) {
		if (!built) {
			throw new IllegalStateException("Can't build twice");
		}
	}

	// If the number passed to set is negative, throws an exception
	private final void assertNotNegative(final int num) {
		if (num < 0) {
			throw new IllegalArgumentException("The number must be positive");
		}
	}
}
