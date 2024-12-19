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

import java.util.List;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;

import controller.files.*;
import model.players.PlayerManagerImpl;
import model.shop.BoostShop;

/** @author Davide Degli Esposti */
public final class TestBoostShop {

	private BoostShop bs;
	private PlayerManagerImpl pm;

	@Before
	public final void prepare() {
		bs = new BoostShop();
		pm = new PlayerManagerImpl();
		bs.generateShop();
	}

	@Test
	public final void enoughMoney() {
		pm.addPlayer("Player");

		Integer moneyGained = 2000000;
		List<Map<String, Object>> list = pm.getPlayers(FileTypes.STATS);
		for (Map<String, Object> map : list) {
			if (map.get(playerName).toString().equals("\"Player\"")) {
				map.put(
						StatsTypes.money.name(),
						Integer.parseInt(map.get(StatsTypes.money.name()).toString()) + moneyGained);
			}
		}
		pm.update(list, FileTypes.STATS);

		bs.payment("Player", bs.getBoosts().get(0));
		pm.removePlayer("Player");
	}

	@Test(expected = IllegalStateException.class)
	public final void notEnoughMoney() {
		pm.addPlayer("Player");
		List<Map<String, Object>> list = pm.getPlayers(FileTypes.STATS);
		for (Map<String, Object> map : list) {
			if (map.get(playerName).toString().equals("\"Player\"")) {
				map.put(StatsTypes.money.name(), 0);
				break;
			}
		}
		pm.update(list, FileTypes.STATS);

		bs.payment("Player", bs.getBoosts().get(1));
		pm.removePlayer("Player");
	}

	@Test(expected = IllegalStateException.class)
	public final void playerNotRegistered() {
		bs.payment("notRegisteredPlayer", bs.getBoosts().get(1));
	}
}
