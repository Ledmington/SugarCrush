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

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import controller.Controller;
import controller.files.FileTypes;
import controller.files.StatsTypes;
import model.players.PlayerManagerImpl;
import model.rank.ScoreBoard;
import utils.Pair;

/** @author Davide Degli Esposti */
public final class TestScoreBoard {

	private static PlayerManagerImpl pm;
	private static ScoreBoard sb;
	private static List<Pair<String, Integer>> testList;

	@BeforeAll
	public static void prepare() {
		sb = new ScoreBoard();
		pm = new PlayerManagerImpl();
		testList = new ArrayList<>();
		testList.add(new Pair<>("\"davide\"", 138));
		testList.add(new Pair<>("\"filippo\"", 90));
		testList.add(new Pair<>("\"samuele\"", 0));
	}

	public void addPlayerAndScore(final String name, final Integer score) {
		pm.addPlayer(name);
		final List<Map<String, Object>> list = pm.getPlayers(FileTypes.STATS);
		for (final Map<String, Object> map : list) {
			if (map.get(Controller.playerName).toString().equals("\"" + name + "\"")) {
				map.put(
						StatsTypes.totalScore.name(),
						Integer.parseInt(map.get(StatsTypes.totalScore.name()).toString()) + score);
			}
		}
		pm.update(list, FileTypes.STATS);
	}

	public void addPlayerAndLvlScore(final String name, final Integer score) {
		pm.addPlayer(name);
		final List<Map<String, Object>> list = pm.getPlayers(FileTypes.STATS);
		for (final Map<String, Object> map : list) {
			if (map.get(Controller.playerName).toString().equals("\"" + name + "\"")) {
				map.put(
						StatsTypes.level3Score.name(),
						Integer.parseInt(map.get(StatsTypes.level3Score.name()).toString()) + score);
			}
		}
		pm.update(list, FileTypes.STATS);
	}

	@Test
	public void testGeneralScore() {
		addPlayerAndScore("filippo", 90);
		addPlayerAndScore("samuele", 0);
		addPlayerAndScore("davide", 138);
		assertEquals(testList, sb.rankByGeneralScore());
		pm.removePlayer("davide");
		pm.removePlayer("filippo");
		pm.removePlayer("samuele");
	}

	@Test
	public void testScoreByLevel() {
		addPlayerAndLvlScore("filippo", 90);
		addPlayerAndLvlScore("samuele", 0);
		addPlayerAndLvlScore("davide", 138);
		assertEquals(testList, sb.rankByScoreInLevel(3));
		pm.removePlayer("davide");
		pm.removePlayer("filippo");
		pm.removePlayer("samuele");
	}
}
