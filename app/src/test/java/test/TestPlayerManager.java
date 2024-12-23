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

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import controller.Controller;
import controller.ControllerImpl;
import controller.files.FileTypes;
import controller.files.StatsTypes;
import model.game.grid.candies.CandyColors;
import model.game.grid.candies.CandyFactoryImpl;
import model.game.grid.shapes.Shapes;
import model.players.PlayerManager;
import model.players.PlayerManagerImpl;
import model.score.Status;
import model.score.Status.Ratios;
import model.score.StatusImpl;

/** @author Emanuele Lamagna */
public final class TestPlayerManager {

	private final Controller controller = new ControllerImpl();
	private PlayerManager pm;
	private Optional<Map<String, Object>> mapP;
	private Optional<Map<String, Object>> mapB;

	/** Prepare the tests */
	@BeforeEach
	public void prepare() {
		pm = new PlayerManagerImpl();
		mapP = Optional.empty();
		mapB = Optional.empty();
		controller.startLevel(3);
	}

	/** Creates a new player, modifies some information and then remove it. */
	@Test
	public void testStats() {
		// create player
		pm.addPlayer("tmpPlayer");
		mapP = check(FileTypes.STATS);
		mapB = check(FileTypes.BOOSTS);

		assertEquals(
				"\"tmpPlayer\"", mapP.orElseThrow().get(Controller.playerName).toString());
		assertEquals(
				"\"tmpPlayer\"", mapB.orElseThrow().get(Controller.playerName).toString());

		// modify all stats with score and status
		final Status status = new StatusImpl(controller);
		status.update(new CandyFactoryImpl().getNormalCandy(CandyColors.PURPLE));
		status.update(new CandyFactoryImpl().getWrapped(CandyColors.BLUE));
		status.update(Shapes.LINE_FIVE);
		status.complete();
		pm.setStat("tmpPlayer", status, 3);
		pm.getPlayers(FileTypes.STATS).stream()
				.filter(map -> map.get(Controller.playerName).toString().equals("\"tmpPlayer\""))
				.forEach(map -> {
					assertEquals("1", map.get(StatsTypes.PURPLE.name()).toString());
					assertEquals("1", map.get(StatsTypes.BLUE.name()).toString());
					assertEquals("1", map.get(StatsTypes.FRECKLES.name()).toString());
					assertEquals(
							map.get(StatsTypes.LEVEL_3_SCORE.name()).toString(),
							Integer.toString((Ratios.WRAPPED.get() + Ratios.DEF.get() * 5)));
					assertEquals(
							map.get(StatsTypes.LEVEL_3_SCORE.name()).toString(),
							map.get(StatsTypes.TOTAL_SCORE.name()).toString());
				});

		// modify a stat with update
		final int moneyGained = 20;
		int moneyHold = 0;
		final List<Map<String, Object>> list = pm.getPlayers(FileTypes.STATS);
		for (final Map<String, Object> map : list) {
			if (map.get(Controller.playerName).toString().equals("\"tmpPlayer\"")) {
				moneyHold = Integer.parseInt(map.get(StatsTypes.MONEY.name()).toString());
				map.put(
						StatsTypes.MONEY.name(),
						Integer.parseInt(map.get(StatsTypes.MONEY.name()).toString()) + moneyGained);
			}
		}
		pm.update(list, FileTypes.STATS);
		for (final Map<String, Object> map : pm.getPlayers(FileTypes.STATS)) {
			if (map.get(Controller.playerName).toString().equals("\"tmpPlayer\"")) {
				assertEquals(map.get(StatsTypes.MONEY.name()).toString(), Integer.toString(moneyHold + moneyGained));
			}
		}

		// remove the player
		pm.removePlayer("tmpPlayer");
		mapP = check(FileTypes.STATS);
		mapB = check(FileTypes.BOOSTS);

		assertEquals(mapP, Optional.empty());
		assertEquals(mapB, Optional.empty());
	}

	private Optional<Map<String, Object>> check(final FileTypes type) {
		for (final Map<String, Object> map : pm.getPlayers(type)) {
			if (map.get(Controller.playerName).toString().equals(("\"tmpPlayer\""))) {
				return Optional.of(map);
			}
		}
		return Optional.empty();
	}
}
