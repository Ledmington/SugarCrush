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
package test;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Arrays;

import org.junit.Test;

import model.game.grid.shapes.ShapeCoordinates;
import model.game.grid.shapes.Shapes;
import utils.Point2D;

/** @author Filippo Benvenuti */
public final class TestShapeCoordinates {

	private ShapeCoordinates cc;

	@Test(expected = IllegalStateException.class)
	public final void cantBeOdd() {
		cc = new ShapeCoordinates(1, 2, 3, 4, 5, 6, 7);
	}

	@Test(expected = NullPointerException.class)
	public final void cantBeNull() {
		cc = new ShapeCoordinates(Arrays.asList(new Point2D(1, 2), null));
	}

	@Test
	public final void correctRotation() {
		cc = new ShapeCoordinates(0, 1, 1, 0);
		assertEquals(
				Arrays.asList(new Point2D(1, 0), new Point2D(0, -1)),
				cc.getNextRotatedCandyCoordinates().getRelativeCoordinates());
	}

	@Test
	public final void stripedVertical() {
		cc = Shapes.LINE_FOUR_VERTICAL.getCoordinates();
		assertEquals(Shapes.LINE_FOUR_VERTICAL.getCoordinates().getRelativeCoordinates(), cc.getRelativeCoordinates());
	}
}
