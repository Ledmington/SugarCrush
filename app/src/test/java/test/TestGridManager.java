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
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.Serial;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import controller.Controller;
import controller.ControllerImpl;
import model.game.grid.GridManager;
import model.game.grid.GridManagerImpl;
import model.game.grid.candies.Candy;
import model.game.grid.candies.CandyColors;
import model.game.grid.candies.CandyFactory;
import model.game.grid.candies.CandyFactoryImpl;
import model.game.grid.candies.CandyTypes;
import model.score.Status;
import model.score.StatusImpl;
import utils.Point2D;

/** @author Filippo Benvenuti */
public final class TestGridManager {

	private GridManager grdMng;
	private Map<Point2D, Optional<Candy>> map;
	private Map<Point2D, Optional<Candy>> map2;
	private Map<Point2D, Optional<Candy>> map3;
	private final CandyFactory cndFac = new CandyFactoryImpl();
	private List<CandyColors> colors;
	private Status score;
	private final Controller c = new ControllerImpl();

	@BeforeEach
	public void prepare() {
		this.score = new StatusImpl(c);
		colors = new ArrayList<>();
		colors.add(CandyColors.BLUE);
		colors.add(CandyColors.GREEN);
		colors.add(CandyColors.ORANGE);
		colors.add(CandyColors.YELLOW);
		colors.add(CandyColors.PURPLE);
		colors.add(CandyColors.RED);
		map = new HashMap<>() {
			@Serial
			private static final long serialVersionUID = 1L;

			{
				put(new Point2D(1, 0), Optional.of(cndFac.getNormalCandy(CandyColors.BLUE)));
				put(new Point2D(2, 0), Optional.of(cndFac.getNormalCandy(CandyColors.BLUE)));
				put(new Point2D(3, 0), Optional.of(cndFac.getNormalCandy(CandyColors.BLUE)));
				put(new Point2D(4, 0), Optional.of(cndFac.getNormalCandy(CandyColors.BLUE)));
				put(new Point2D(5, 0), Optional.of(cndFac.getNormalCandy(CandyColors.BLUE)));
			}
		};
		map.put(new Point2D(6, 0), Optional.empty());
		map2 = new HashMap<>() {
			@Serial
			private static final long serialVersionUID = 1L;

			{
				put(new Point2D(1, 0), Optional.of(cndFac.getNormalCandy(CandyColors.BLUE)));
				put(new Point2D(2, 0), Optional.of(cndFac.getNormalCandy(CandyColors.GREEN)));
				put(new Point2D(4, 0), Optional.of(cndFac.getNormalCandy(CandyColors.BLUE)));
			}
		};
		map3 = new HashMap<>() {
			@Serial
			private static final long serialVersionUID = 1L;

			{
				put(new Point2D(1, 0), Optional.of(cndFac.getNormalCandy(CandyColors.BLUE)));
				put(new Point2D(2, 0), Optional.of(cndFac.getNormalCandy(CandyColors.PURPLE)));
				put(new Point2D(3, 0), Optional.of(cndFac.getNormalCandy(CandyColors.GREEN)));
				put(new Point2D(4, 0), Optional.of(cndFac.getChocolate()));
			}
		};
	}

	@Test
	public void gridCantBeNull() {
		assertThrows(NullPointerException.class, () -> new GridManagerImpl(c, null, null, null, false));
	}

	@Test
	public void noEmptyInsideGridAfterDropCandy() {
		grdMng = new GridManagerImpl(c, map, this.score, colors, false);
		assertFalse(grdMng.getGrid().containsValue(Optional.empty()));
	}

	@Test
	public void frecklesPresent() {
		grdMng = new GridManagerImpl(c, map, this.score, colors, false);
		boolean found = false;
		for (final Map.Entry<Point2D, Optional<Candy>> crd : grdMng.getGrid().entrySet()) {
			found = found || crd.getValue().orElseThrow().getType() == CandyTypes.FRECKLES;
		}
		assertTrue(found);
	}

	@Test
	public void genericChecks() {
		grdMng = new GridManagerImpl(c, map2, this.score, colors, false);
		assertFalse(grdMng.move(new Point2D(1, 0), new Point2D(2, 0)));
		assertFalse(grdMng.move(new Point2D(1, 0), new Point2D(4, 0)));
		assertTrue(grdMng.forceMove(new Point2D(1, 0), new Point2D(2, 0)));
		grdMng.mutateCandy(new Point2D(1, 0), cndFac.getVerticalStripedCandy(CandyColors.RED));
		grdMng.destroyCandy(new Point2D(1, 0));
		assertFalse(grdMng.getGrid().containsValue(Optional.of(cndFac.getNormalCandy(CandyColors.BLUE))));
	}

	@Test
	public void insaneAndCurious() {
		final Map<Point2D, Optional<Candy>> nsnMap = new HashMap<>();
		for (int i = 0; i < 10; i++) {
			for (int j = 0; j < 10; j++) {
				nsnMap.put(new Point2D(i, j), Optional.of(cndFac.getNormalCandy(CandyColors.BLUE)));
			}
		}
		grdMng = new GridManagerImpl(c, nsnMap, this.score, colors, false);
	}

	@Test
	public void chocolateLiveness() {
		grdMng = new GridManagerImpl(c, map3, this.score, colors, false);

		// Checking ascending chocolate and if it is possible to destroy it.
		assertTrue(grdMng.forceMove(new Point2D(1, 0), new Point2D(2, 0)));
		assertEquals(grdMng.getGrid().get(new Point2D(4, 0)).orElseThrow(), cndFac.getChocolate());
		assertTrue(grdMng.forceMove(new Point2D(1, 0), new Point2D(2, 0)));
		assertEquals(grdMng.getGrid().get(new Point2D(4, 0)).orElseThrow(), cndFac.getChocolate());
		assertEquals(grdMng.getGrid().get(new Point2D(3, 0)).orElseThrow(), cndFac.getChocolate());
		assertTrue(grdMng.destroyCandy(new Point2D(2, 0)));
		assertTrue(grdMng.getGrid().get(new Point2D(3, 0)).isEmpty());
	}
}
