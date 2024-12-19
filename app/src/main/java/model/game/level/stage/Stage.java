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
package model.game.level.stage;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import model.game.grid.candies.Candy;
import model.game.level.Level;
import model.objectives.Objective;
import model.score.Status;
import utils.Point2D;

/** @author Filippo Barbari */
public interface Stage {

	/**
	 * @return The message that needs to be printed at the beginning of the {@link Stage}. If Optional.empty() is
	 *     returned, no message is to be printed.
	 */
	Optional<String> getStartingMessage();

	/**
	 * @return The grid of the {@link Level} in a certain moment in the form of a Map object containing all
	 *     {@link Point2D} positions and {@link Candy} in the grid.
	 */
	Map<Point2D, Optional<Candy>> getGrid();

	/**
	 * @return The message that needs to be printed after the end of the {@link Stage}. If Optional.empty() is returned,
	 *     no message is to be printed.
	 */
	Optional<String> getEndingMessage();

	/** @return An {@link Objective} interface. */
	Objective getObjective();

	/**
	 * Retrieves a list of coordinates describing a possible shape that can be formed.
	 *
	 * @return A list filled with coordinates of shape.
	 */
	List<Point2D> getHint();

	/**
	 * Tells the {@link Level} to swap a specific pair of adjacent candies.
	 *
	 * @param first The first candy.
	 * @param second The second candy.
	 * @return true if swapping the given candies has resulted in a good move, false otherwise.
	 */
	boolean move(final Point2D first, final Point2D second);

	/** @return the score of the stage */
	Status getCurrentScore();

	/** Consume all remaining moves, spawning random special candies and blowing them up. */
	void consumeRemainingMoves();

	/**
	 * Retrieves a map with state of jelly.
	 *
	 * @return A map with jelly lives.
	 */
	Optional<Map<Point2D, Integer>> getJelly();

	/**
	 * Change a candy with another candy in determined coordinates.
	 *
	 * @param cord The coordinates of the candy to be mutated.
	 * @param cnd The new candy to be mutated into.
	 * @return False if mutation wasn't possible.
	 */
	boolean mutateCandy(Point2D cord, Candy cnd);
}
