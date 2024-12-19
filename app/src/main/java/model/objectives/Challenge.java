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
 * Contains the methods of a general {@link Challenge}
 *
 * @author Emanuele Lamagna
 */
public interface Challenge {

	/**
	 * Getter of the red candies to destroy
	 *
	 * @return the red candies to destroy
	 */
	int getRedToDestroy();

	/**
	 * Getter of the yellow candies to destroy
	 *
	 * @return the yellow candies to destroy
	 */
	int getYellowToDestroy();

	/**
	 * Getter of the blue candies to destroy
	 *
	 * @return the blue candies to destroy
	 */
	int getBlueToDestroy();

	/**
	 * Getter of the green candies to destroy
	 *
	 * @return the green candies to destroy
	 */
	int getGreenToDestroy();

	/**
	 * Getter of the purple candies to destroy
	 *
	 * @return the purple candies to destroy
	 */
	int getPurpleToDestroy();

	/**
	 * Getter of the orange candies to destroy
	 *
	 * @return the orange candies to destroy
	 */
	int getOrangeToDestroy();

	/**
	 * Getter of the freckles candies to farm
	 *
	 * @return the freckles candies to farm
	 */
	int getFrecklesToFarm();

	/**
	 * Getter of the striped candies to farm
	 *
	 * @return the striped candies to farm
	 */
	int getStripedToFarm();

	/**
	 * Getter of the wrapped candies to farm
	 *
	 * @return the wrapped candies to farm
	 */
	int getWrappedToFarm();

	/**
	 * Getter of the jelly condition (if is to be destroyed or not)
	 *
	 * @return if the jelly should be all destroyed
	 */
	boolean isJellyToDestroy();
}
