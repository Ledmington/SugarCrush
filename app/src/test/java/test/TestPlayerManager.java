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

import static controller.Controller.playerName;
import static controller.files.FileTypes.*;
import static org.junit.Assert.assertEquals;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.junit.Before;
import org.junit.Test;

import controller.Controller;
import controller.ControllerImpl;
import controller.files.*;
import controller.files.FileTypes;
import model.game.grid.candies.CandyColors;
import model.game.grid.candies.CandyFactoryImpl;
import model.game.grid.shapes.Shapes;
import model.players.*;
import model.score.*;
import model.score.Status.Ratios;

/**
 * Tests {@link PlayerManager}, {@link Score} and {@link Status}
 *
 * @author Emanuele Lamagna
 */
public final class TestPlayerManager {

	private final Controller controller = new ControllerImpl();
	private PlayerManager pm;
	Optional<Map<String, Object>> mapP;
	Optional<Map<String, Object>> mapB;

	/** Prepare the tests */
	@Before
	public final void prepare() {
		pm = new PlayerManagerImpl();
		mapP = Optional.empty();
		mapB = Optional.empty();
		controller.startLevel(3);
	}

	/** Creates a new player, modifies some informations and then remove it */
	@Test
	public final void testStats() {
		// create player
		pm.addPlayer("tmpPlayer");
		mapP = check(STATS);
		mapB = check(BOOSTS);

		assertEquals(mapP.get().get(playerName).toString(), ("\"tmpPlayer\""));
		assertEquals(mapB.get().get(playerName).toString(), ("\"tmpPlayer\""));

		// modifiy all stats with score and status
		Status status = new StatusImpl(controller);
		status.update(new CandyFactoryImpl().getNormalCandy(CandyColors.PURPLE));
		status.update(new CandyFactoryImpl().getWrapped(CandyColors.BLUE));
		status.update(Shapes.LINE_FIVE);
		status.complete();
		pm.setStat("tmpPlayer", status, 3);
		pm.getPlayers(STATS).stream()
				.filter(map -> map.get(playerName).toString().equals("\"tmpPlayer\""))
				.forEach(map -> {
					assertEquals(map.get(StatsTypes.PURPLE.name()).toString(), "1");
					assertEquals(map.get(StatsTypes.BLUE.name()).toString(), "1");
					assertEquals(map.get(StatsTypes.FRECKLES.name()).toString(), "1");
					assertEquals(
							map.get(StatsTypes.level3Score.name()).toString(),
							Integer.toString((Ratios.WRAPPED.get() + Ratios.DEF.get() * 5)));
					assertEquals(
							map.get(StatsTypes.level3Score.name()).toString(),
							map.get(StatsTypes.totalScore.name()).toString());
				});
		// modify a stat with update
		int moneyGained = 20;
		int moneyHold = 0;
		List<Map<String, Object>> list = pm.getPlayers(STATS);
		for (var map : list) {
			if (map.get(playerName).toString().equals("\"tmpPlayer\"")) {
				moneyHold = Integer.parseInt(map.get(StatsTypes.money.name()).toString());
				map.put(
						StatsTypes.money.name(),
						Integer.parseInt(map.get(StatsTypes.money.name()).toString()) + moneyGained);
			}
		}
		pm.update(list, STATS);
		for (var map : pm.getPlayers(STATS)) {
			if (map.get(playerName).toString().equals("\"tmpPlayer\"")) {
				assertEquals(map.get(StatsTypes.money.name()).toString(), Integer.toString(moneyHold + moneyGained));
			}
		}
		// remove the player
		pm.removePlayer("tmpPlayer");
		mapP = check(STATS);
		mapB = check(BOOSTS);

		assertEquals(mapP, Optional.empty());
		assertEquals(mapB, Optional.empty());
	}

	private final Optional<Map<String, Object>> check(final FileTypes type) {
		for (var map : pm.getPlayers(type)) {
			if (map.get(playerName).toString().equals(("\"tmpPlayer\""))) {
				return Optional.of(map);
			}
		}
		return Optional.empty();
	}
}
