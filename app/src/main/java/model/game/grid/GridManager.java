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
package model.game.grid;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import model.game.grid.candies.Candy;
import model.game.grid.shapes.Shapes;
import model.score.Status;
import utils.Point2D;

/** @author Filippo Benvenuti */
public interface GridManager {
	/**
	 * Retrieves the grid filled with current {@link Candy}.
	 *
	 * @return The grid.
	 */
	Map<Point2D, Optional<Candy>> getGrid();

	/**
	 * Try to make a move.
	 *
	 * @param cndA The first {@link Candy}.
	 * @param cndB The second {@link Candy}.
	 * @return False if move couldn't be made (Not adjacent candies, non movable candies, out of range, no shapes
	 *     formed).
	 */
	boolean move(Point2D cndA, Point2D cndB);

	/**
	 * Force a move of candies.
	 *
	 * @param cndA The first {@link Candy}.
	 * @param cndB The second {@link Candy}.
	 * @return True even if no {@link Shapes} (false if it brings to bad state).
	 */
	boolean forceMove(Point2D cndA, Point2D cndB);

	/**
	 * Change a {@link Candy} with another {@link Candy} in determined coordinates.
	 *
	 * @param cord The coordinates of the {@link Candy} to be mutated.
	 * @param cnd The new {@link Candy} to be mutated into.
	 * @return False if mutation wasn't possible.
	 */
	boolean mutateCandy(Point2D cord, Candy cnd);

	/**
	 * Destroy the {@link Candy} at the coordinates passed.
	 *
	 * @param cord The coordinates of the {@link Candy}.
	 * @return False if coordinates are wrong.
	 */
	boolean destroyCandy(Point2D cord);

	/**
	 * Retrieves a list filled with coordinates of advised {@link Shapes}.
	 *
	 * @return The list of coordinates.
	 */
	List<Point2D> getHint();

	/**
	 * Retrieves all coordinates with relative jelly value.
	 *
	 * @return An optional of the map with coordinates and jelly values.
	 */
	Optional<Map<Point2D, Integer>> getJelly();

	/** For each remaining moves, a special {@link Candy} will be spawned and destroyed. */
	void consumeRemainingMoves();

	/**
	 * Retrieves current {@link Status}.
	 *
	 * @return The current {@link Status}.
	 */
	Status getCurrentScore();
}
