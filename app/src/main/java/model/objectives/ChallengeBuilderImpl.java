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
	public ChallengeBuilder setRed(final int num) {
		check(!this.built);
		assertNotNegative(num);
		this.red = num;
		return this;
	}

	@Override
	public ChallengeBuilder setYellow(final int num) {
		check(!this.built);
		assertNotNegative(num);
		this.yellow = num;
		return this;
	}

	@Override
	public ChallengeBuilder setBlue(final int num) {
		check(!this.built);
		assertNotNegative(num);
		this.blue = num;
		return this;
	}

	@Override
	public ChallengeBuilder setGreen(final int num) {
		check(!this.built);
		assertNotNegative(num);
		this.green = num;
		return this;
	}

	@Override
	public ChallengeBuilder setPurple(final int num) {
		check(!this.built);
		assertNotNegative(num);
		this.purple = num;
		return this;
	}

	@Override
	public ChallengeBuilder setOrange(final int num) {
		check(!this.built);
		assertNotNegative(num);
		this.orange = num;
		return this;
	}

	@Override
	public ChallengeBuilder setFreckles(final int num) {
		check(!this.built);
		assertNotNegative(num);
		this.freckles = num;
		return this;
	}

	@Override
	public ChallengeBuilder setStriped(int num) {
		check(!this.built);
		assertNotNegative(num);
		this.striped = num;
		return this;
	}

	@Override
	public ChallengeBuilder setWrapped(final int num) {
		check(!this.built);
		assertNotNegative(num);
		this.wrapped = num;
		return this;
	}

	@Override
	public ChallengeBuilder setDestroyJelly(final boolean bool) {
		check(!this.built);
		this.jelly = bool;
		return this;
	}

	@Override
	public Challenge build() {
		check(!this.built);
		built = true;
		return new Challenge() {

			@Override
			public int getRedToDestroy() {
				return red;
			}

			@Override
			public int getYellowToDestroy() {
				return yellow;
			}

			@Override
			public int getBlueToDestroy() {
				return blue;
			}

			@Override
			public int getGreenToDestroy() {
				return green;
			}

			@Override
			public int getPurpleToDestroy() {
				return purple;
			}

			@Override
			public int getOrangeToDestroy() {
				return orange;
			}

			@Override
			public int getFrecklesToFarm() {
				return freckles;
			}

			@Override
			public int getStripedToFarm() {
				return striped;
			}

			@Override
			public int getWrappedToFarm() {
				return wrapped;
			}

			@Override
			public boolean isJellyToDestroy() {
				return jelly;
			}
		};
	}

	// If is already built, throws an exception
	private void check(final boolean built) {
		if (!built) {
			throw new IllegalStateException("Can't build twice");
		}
	}

	// If the number passed to set is negative, throws an exception
	private void assertNotNegative(final int num) {
		if (num < 0) {
			throw new IllegalArgumentException("The number must be positive");
		}
	}
}
