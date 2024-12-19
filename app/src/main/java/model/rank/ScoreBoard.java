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

import java.util.ArrayList;
import java.util.List;

import controller.files.FileTypes;
import controller.files.StatsTypes;
import model.players.*;
import utils.Pair;

/** @author Davide Degli Esposti */
public final class ScoreBoard {

	private final PlayerManagerImpl pl = new PlayerManagerImpl(); // variable for get player's properties
	private List<Pair<String, Integer>> rankPlayer; // list for represent certain rank of player

	/** @return list of players sorted by general score */
	public List<Pair<String, Integer>> rankByGeneralScore() {
		this.rankPlayer = new ArrayList<>();
		pl.getPlayers(FileTypes.STATS).stream()
				.sorted((a, b) -> Integer.valueOf((b.get(StatsTypes.totalScore.name())).toString())
						.compareTo(Integer.valueOf(
								a.get(StatsTypes.totalScore.name()).toString())))
				.forEach(p -> this.rankPlayer.add(new Pair<>(
						p.get(playerName).toString(),
						Integer.parseInt(p.get(StatsTypes.totalScore.name()).toString()))));

		return this.rankPlayer;
	}

	/**
	 * @param lvlNumber the level to show the rank
	 * @return list of players sorted by score in the given level
	 */
	public List<Pair<String, Integer>> rankByScoreInLevel(final int lvlNumber) {
		this.rankPlayer = new ArrayList<>();
		pl.getPlayers(FileTypes.STATS).stream()
				.sorted((a, b) -> Integer.valueOf((b.get("level" + lvlNumber + "Score")).toString())
						.compareTo(Integer.valueOf(
								a.get("level" + lvlNumber + "Score").toString())))
				.forEach(p -> this.rankPlayer.add(new Pair<>(
						p.get(playerName).toString(),
						Integer.parseInt(p.get("level" + lvlNumber + "Score").toString()))));

		return this.rankPlayer;
	}
}
