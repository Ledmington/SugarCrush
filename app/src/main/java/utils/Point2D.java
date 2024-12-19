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
package utils;

/**
 * A more pragmatic way to use Pair.
 *
 * @author Filippo Barbari
 */
public final class Point2D extends Pair<Integer, Integer> {

	public Point2D(final Integer newX, final Integer newY) {
		super(newX, newY);
	}

	/**
	 * Creates a new instance of Point2D which the result of vectorial sum between this and otherPoint.
	 *
	 * @return A new Point2D.
	 */
	public final Point2D add(final Point2D otherPoint) {
		return new Point2D(this.x + otherPoint.getX(), this.y + otherPoint.getY());
	}

	@Override
	public final String toString() {
		return "Point2D [x=" + x + ", y=" + y + "]";
	}
}
