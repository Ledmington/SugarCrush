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
package model.game.grid.shapes;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import utils.Point2D;

/** @author Filippo Benvenuti */
public final class ShapeCoordinates {

	private final List<Point2D> coords;

	/**
	 * Constructor by array of ints.
	 *
	 * @param cord this array will be split into couples (x, y) of coordinates.
	 */
	public ShapeCoordinates(final int... cord) {
		if (cord.length % 2 == 1 || cord.length < 2) {
			throw new IllegalStateException();
		}

		// Converting sequence of integers into list p.
		coords = new ArrayList<>();
		for (int i = 0; i < cord.length; i += 2) {
			coords.add(new Point2D(cord[i], cord[i + 1]));
		}
	}

	/**
	 * Constructor by List of {@link Point2D}.
	 *
	 * @param cord The list of coordinates.
	 */
	public ShapeCoordinates(List<Point2D> cord) {
		if (cord == null) throw new NullPointerException();
		for (Point2D x : cord) {
			if (x == null) {
				throw new NullPointerException();
			}
		}
		coords = cord;
	}

	/**
	 * Retrieves the list of coordinates.
	 *
	 * @return List<Point2D> of coordinates.
	 */
	public List<Point2D> getRelativeCoordinates() {
		return new ArrayList<>(this.coords);
	}

	/**
	 * Create another {@link ShapeCoordinates} and fill it with coordinates rotated of 90°.
	 *
	 * @return One new {@link ShapeCoordinates} with rotated coordinates.
	 */
	public ShapeCoordinates getNextRotatedCandyCoordinates() {
		final List<Point2D> rotated = new ArrayList<>();

		// Every coordinate must be treated different based on his position.
		this.coords.forEach((point) -> {
			final int x = point.x();
			final int y = point.y();

			if (x < 0) { // If at top, goes right.
				rotated.add(new Point2D(y, -x));
			} else if (y > 0) { // If at the right, goes down.
				rotated.add(new Point2D(y, x));
			} else if (x > 0) { // If at bottom, goes left.
				rotated.add(new Point2D(y, -x));
			} else if (y < 0) { // If at left, goes up.
				rotated.add(new Point2D(y, x));
			}
		});

		return new ShapeCoordinates(rotated);
	}

	/**
	 * Retrieves a list filled with coordinates of all possible shape 1 move away from the {@link ShapeCoordinates}
	 * passed, if it is possible to obtain shpC moving 1 candy.
	 *
	 * @return A list of {@link ShapeCoordinates}.
	 */
	public List<ShapeCoordinates> getNearCoordinateShapes() {
		final List<ShapeCoordinates> shpLst = new ArrayList<>();
		// Four direction neighbor ( up, down, right, left).
		final List<Point2D> rNeighList =
				Arrays.asList(new Point2D(-1, 0), new Point2D(1, 0), new Point2D(0, 1), new Point2D(0, -1));

		// Adding implicit coordinate 0, 0.
		final List<Point2D> coordsWithImplicit = new ArrayList<>(this.coords);
		coordsWithImplicit.add(new Point2D(0, 0));

		// For every coordinate check if it has a non already present neighbor.
		coordsWithImplicit.forEach((crd) -> {
			// Every neighbor.
			rNeighList.forEach((direction) -> {
				final Point2D neirCrd = new Point2D(crd.x() + direction.x(), crd.y() + direction.y());
				// If non already in shpC.
				if (!coordsWithImplicit.contains(neirCrd)) {
					// Creating this possible shape (removing considered coordinate, adding the new one).
					var tmpList = new ArrayList<>(coordsWithImplicit);
					tmpList.remove(crd);
					tmpList.add(neirCrd);
					// Add this list to shpLst.
					shpLst.add(new ShapeCoordinates(tmpList));
				}
			});
		});
		return shpLst;
	}
}
