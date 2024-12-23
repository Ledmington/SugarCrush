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
package model.achievement;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import controller.Controller;
import controller.files.FileTypes;
import controller.files.StatsTypes;
import model.players.PlayerManagerImpl;

/** @author Davide Degli Esposti */
public final class AchievementManager {

	private final List<Achievement> achievements = new ArrayList<>(); // list of all achievement of the player
	private final Controller controller; // variable to get the current player
	private Map<String, Object> mapCurrentPlayer; // contains the map with the stats of the current player
	private final PlayerManagerImpl pm = new PlayerManagerImpl(); // variable to get the players

	/** the constructor of the class */
	public AchievementManager(final Controller controller) {
		this.controller = Objects.requireNonNull(controller);

		// first achievement
		this.achievements.add(Achievement.builder()
				.title("Gotta eat 'em all")
				.description("Finish the first level")
				.check(e -> {
					resetPlayerMap();
					return Integer.parseInt(mapCurrentPlayer
									.get(StatsTypes.LEVEL_1_SCORE.name())
									.toString())
							> 0;
				})
				.setController(this.controller)
				.build());

		// second achievement
		this.achievements.add(Achievement.builder()
				.title("Nyan Ca...ndy!")
				.description("Farm 5 freckles")
				.check(e -> {
					resetPlayerMap();
					return Integer.parseInt(mapCurrentPlayer
									.get(StatsTypes.FRECKLES.name())
									.toString())
							>= 5;
				})
				.setController(this.controller)
				.build());

		// third achievement
		this.achievements.add(Achievement.builder()
				.title("Be like Greta Thunberg ")
				.description("Destroy 250 green candy")
				.check(e -> {
					resetPlayerMap();
					return Integer.parseInt(mapCurrentPlayer
									.get(StatsTypes.GREEN.name())
									.toString())
							>= 250;
				})
				.setController(this.controller)
				.build());

		// 4th achievement
		this.achievements.add(Achievement.builder()
				.title("Im Blue...")
				.description("Destroy 250 blue candy")
				.check(e -> {
					resetPlayerMap();
					return Integer.parseInt(
									mapCurrentPlayer.get(StatsTypes.BLUE.name()).toString())
							>= 250;
				})
				.setController(this.controller)
				.build());

		// 5th achievement
		this.achievements.add(Achievement.builder()
				.title("...Da Ba Dee Da Ba Daa")
				.description("Destroy 500 blue candy")
				.check(e -> {
					resetPlayerMap();
					return Integer.parseInt(
									mapCurrentPlayer.get(StatsTypes.BLUE.name()).toString())
							>= 500;
				})
				.setController(this.controller)
				.build());

		// 6th achievement
		this.achievements.add(Achievement.builder()
				.title("A Red Bull for you")
				.description("Destroy 250 red candy")
				.check(e -> {
					resetPlayerMap();
					return Integer.parseInt(
									mapCurrentPlayer.get(StatsTypes.RED.name()).toString())
							>= 250;
				})
				.setController(this.controller)
				.build());

		// 7th achievement
		this.achievements.add(Achievement.builder()
				.title("Praise the Sun!!!")
				.description("Destroy 250 yellow candy")
				.check(e -> {
					resetPlayerMap();
					return Integer.parseInt(mapCurrentPlayer
									.get(StatsTypes.YELLOW.name())
									.toString())
							>= 250;
				})
				.setController(this.controller)
				.build());

		// 8th achievement
		this.achievements.add(Achievement.builder()
				.title("I am inevitable.")
				.description("Destroy 250 purple candy")
				.check(e -> {
					resetPlayerMap();
					return Integer.parseInt(mapCurrentPlayer
									.get(StatsTypes.PURPLE.name())
									.toString())
							>= 250;
				})
				.setController(this.controller)
				.build());

		// 9th achievement
		this.achievements.add(Achievement.builder()
				.title("Immune to Scurvy")
				.description("Destroy 250 orange candy")
				.check(e -> {
					resetPlayerMap();
					return Integer.parseInt(mapCurrentPlayer
									.get(StatsTypes.ORANGE.name())
									.toString())
							>= 250;
				})
				.setController(this.controller)
				.build());

		// 10th achievement
		this.achievements.add(Achievement.builder()
				.title("Lactose intolerance")
				.description("Destroy 50 pieces of chocolate")
				.check(e -> {
					resetPlayerMap();
					return Integer.parseInt(mapCurrentPlayer
									.get(StatsTypes.CHOCOLATE.name())
									.toString())
							>= 50;
				})
				.setController(this.controller)
				.build());

		// 11th achievement
		this.achievements.add(Achievement.builder()
				.title("THIS...IS...SUGAR CRUSH!!!")
				.description("Destroy 300 candy")
				.check(e -> {
					resetPlayerMap();
					return (Integer.parseInt(mapCurrentPlayer
											.get(StatsTypes.BLUE.name())
											.toString())
									+ Integer.parseInt(mapCurrentPlayer
											.get(StatsTypes.GREEN.name())
											.toString())
									+ Integer.parseInt(mapCurrentPlayer
											.get(StatsTypes.ORANGE.name())
											.toString())
									+ Integer.parseInt(mapCurrentPlayer
											.get(StatsTypes.PURPLE.name())
											.toString())
									+ Integer.parseInt(mapCurrentPlayer
											.get(StatsTypes.RED.name())
											.toString())
									+ Integer.parseInt(mapCurrentPlayer
											.get(StatsTypes.YELLOW.name())
											.toString()))
							>= 300;
				})
				.setController(this.controller)
				.build());

		// 12th achievement
		this.achievements.add(Achievement.builder()
				.title("Finish!!")
				.description("Finish the game")
				.check(e -> {
					resetPlayerMap();
					return Integer.parseInt(mapCurrentPlayer
									.get(StatsTypes.LEVEL_10_SCORE.name())
									.toString())
							> 0;
				})
				.setController(this.controller)
				.build());
	}

	/** Tells the {@link AchievementManager} to reset the internal data about current player. */
	public void resetPlayerMap() {
		for (final Map<String, Object> map : pm.getPlayers(FileTypes.STATS)) {
			if (map.get(Controller.playerName).toString().equals("\"" + controller.getCurrentPlayerName() + "\"")) {
				mapCurrentPlayer = map;
				break;
			}
		}
	}

	/** @return the list of achievements of the player. */
	public List<Achievement> getAchievements() {
		return this.achievements;
	}
}
