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
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import controller.files.FileTypes;
import controller.files.StatsTypes;
import model.players.PlayerManagerImpl;
import model.shop.BoostShop;

/** @author Davide Degli Esposti */
final class TestBoostShop {

	private BoostShop bs;
	private PlayerManagerImpl pm;

	@BeforeEach
	public void prepare() {
		bs = new BoostShop();
		pm = new PlayerManagerImpl();
		bs.generateShop();
	}

	@Test
	void enoughMoney() {
		pm.addPlayer("Player");

		final int moneyGained = 2000000;
		final List<Map<String, Object>> list = pm.getPlayers(FileTypes.STATS);
		for (final Map<String, Object> map : list) {
			if ("\"Player\"".equals(map.get(playerName).toString())) {
				map.put(
						StatsTypes.MONEY.name(),
						Integer.parseInt(map.get(StatsTypes.MONEY.name()).toString()) + moneyGained);
			}
		}
		pm.update(list, FileTypes.STATS);

		bs.payment("Player", bs.getBoosts().getFirst());
		pm.removePlayer("Player");
	}

	@Test
	void notEnoughMoney() {
		pm.addPlayer("Player");
		final List<Map<String, Object>> list = pm.getPlayers(FileTypes.STATS);
		for (final Map<String, Object> map : list) {
			if ("\"Player\"".equals(map.get(playerName).toString())) {
				map.put(StatsTypes.MONEY.name(), 0);
				break;
			}
		}
		pm.update(list, FileTypes.STATS);

		assertThrows(
				IllegalStateException.class,
				() -> bs.payment("Player", bs.getBoosts().get(1)));
		pm.removePlayer("Player");
	}

	@Test
	void playerNotRegistered() {
		assertThrows(
				IllegalStateException.class,
				() -> bs.payment("notRegisteredPlayer", bs.getBoosts().get(1)));
	}
}
