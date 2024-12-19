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
 * A class used to build a new {@link Challenge} (with builder pattern)
 *
 * @author Emanuele Lamagna
 */
public interface ChallengeBuilder {

	/**
	 * Setter of the red candies to destroy
	 *
	 * @param num the number of the red candies to destroy
	 * @return this {@link ChallengeBuilder}, with red modified
	 */
	ChallengeBuilder setRed(int num);

	/**
	 * Setter of the yellow candies to destroy
	 *
	 * @param num the number of the yellow candies to destroy
	 * @return this {@link ChallengeBuilder}, with yellow modified
	 */
	ChallengeBuilder setYellow(int num);

	/**
	 * Setter of the blue candies to destroy
	 *
	 * @param num the number of the blue candies to destroy
	 * @return this {@link ChallengeBuilder}, with blue modified
	 */
	ChallengeBuilder setBlue(int num);

	/**
	 * Setter of the green candies to destroy
	 *
	 * @param num the number of the greem candies to destroy
	 * @return this {@link ChallengeBuilder}, with green modified
	 */
	ChallengeBuilder setGreen(int num);

	/**
	 * Setter of the purple candies to destroy
	 *
	 * @param num the number of the purple candies to destroy
	 * @return this {@link ChallengeBuilder}, with purple modified
	 */
	ChallengeBuilder setPurple(int num);

	/**
	 * Setter of the orange candies to destroy
	 *
	 * @param num the number of the orange candies to destroy
	 * @return this {@link ChallengeBuilder}, with orange modified
	 */
	ChallengeBuilder setOrange(int num);

	/**
	 * Setter of the freckles candies to farm
	 *
	 * @param num the number of the freckles candies to farm
	 * @return this {@link ChallengeBuilder}, with freckles modified
	 */
	ChallengeBuilder setFreckles(int num);

	/**
	 * Setter of the striped candies to farm
	 *
	 * @param num the number of the striped candies to farm
	 * @return this {@link ChallengeBuilder}, with striped modified
	 */
	ChallengeBuilder setStriped(int num);

	/**
	 * Setter of the wrapped candies to farm
	 *
	 * @param num the number of the wrapped candies to farm
	 * @return this {@link ChallengeBuilder}, with wrapped modified
	 */
	ChallengeBuilder setWrapped(int num);

	/**
	 * Setter of the jelly condition (to be destroyed or not)
	 *
	 * @param bool the boolean that tells if the jelly is to destroy or not
	 * @return this {@link ChallengeBuilder}, with jelly modified
	 */
	ChallengeBuilder setDestroyJelly(boolean bool);

	/**
	 * The method that builds the {@link Challenge} using all the getters
	 *
	 * @return a new {@link Challenge}, built from the fields
	 */
	Challenge build();
}
