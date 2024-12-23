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
package model.rank;

import static controller.Controller.playerName;

import java.util.List;

import controller.files.FileTypes;
import controller.files.StatsTypes;
import model.players.*;
import utils.Pair;

/** @author Davide Degli Esposti */
public final class ScoreBoard {

	private static final PlayerManagerImpl PLAYERS = new PlayerManagerImpl();

	/** @return list of players sorted by general score */
	public List<Pair<String, Integer>> rankByGeneralScore() {
		return PLAYERS.getPlayers(FileTypes.STATS).stream()
				.map(p -> new Pair<>(
						p.get(playerName).toString(),
						Integer.parseInt(p.get(StatsTypes.TOTAL_SCORE.name()).toString())))
				.sorted((a, b) -> Integer.compare(b.second(), a.second()))
				.toList();
	}

	/**
	 * @param lvlNumber the level to show the rank
	 * @return list of players sorted by score in the given level
	 */
	public List<Pair<String, Integer>> rankByScoreInLevel(final int lvlNumber) {
		return PLAYERS.getPlayers(FileTypes.STATS).stream()
				.map(p -> new Pair<>(
						p.get(playerName).toString(),
						Integer.parseInt(p.get("LEVEL_" + lvlNumber + "_SCORE").toString())))
				.sorted((a, b) -> Integer.compare(b.second(), a.second()))
				.toList();
	}
}
