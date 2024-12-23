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
package model.goals;

import static controller.Controller.playerName;
import static controller.files.StatsTypes.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import controller.*;
import controller.files.FileTypes;
import model.players.PlayerManagerImpl;

/** @author Davide Degli Esposti */
public final class GoalManager {

	private final List<Goal> achievement = new ArrayList<>(); // list of all achievement of the player
	private final Controller cntrlImpl; // variable to get the current player
	private Map<String, Object> mapCurrentPlayer; // contains the map with the stats of the current player
	private final PlayerManagerImpl pm = new PlayerManagerImpl(); // variable to get the players

	/** the constructor of the class */
	public GoalManager(final Controller controller) {
		this.cntrlImpl = controller;
		// first achievement
		// builder for the achievement
		GoalBuilderImpl gb = new GoalBuilderImpl();
		gb.setTitle("Gotta eat 'em all");
		gb.setDescr("Finish the first level");
		gb.setMethod(e -> {
			resetPlayerMap();
			return Integer.parseInt(mapCurrentPlayer.get(level1Score.name()).toString()) > 0;
		});
		gb.setController(cntrlImpl);
		this.achievement.add(gb.build());

		// second achievement
		gb = new GoalBuilderImpl();
		gb.setTitle("Nayan Ca...ndy!");
		gb.setDescr("Farm 5 freckles");
		gb.setMethod(e -> {
			resetPlayerMap();
			return Integer.parseInt(mapCurrentPlayer.get(FRECKLES.name()).toString()) >= 5;
		});
		gb.setController(cntrlImpl);
		this.achievement.add(gb.build());

		// third achievement
		gb = new GoalBuilderImpl();
		gb.setTitle("Be like Greta Thunberg ");
		gb.setDescr("Destroy 250 green candy");
		gb.setMethod(e -> {
			resetPlayerMap();
			return Integer.parseInt(mapCurrentPlayer.get(GREEN.name()).toString()) >= 250;
		});
		gb.setController(cntrlImpl);
		this.achievement.add(gb.build());

		// 4th achievement
		gb = new GoalBuilderImpl();
		gb.setTitle("Im Blue...");
		gb.setDescr("Destroy 250 blue candy");
		gb.setMethod(e -> {
			resetPlayerMap();
			return Integer.parseInt(mapCurrentPlayer.get(BLUE.name()).toString()) >= 250;
		});
		gb.setController(cntrlImpl);
		this.achievement.add(gb.build());

		// 5th achievement
		gb = new GoalBuilderImpl();
		gb.setTitle("...Da Ba Dee Da Ba Daa");
		gb.setDescr("Destroy 500 blue candy");
		gb.setMethod(e -> {
			resetPlayerMap();
			return Integer.parseInt(mapCurrentPlayer.get(BLUE.name()).toString()) >= 500;
		});
		gb.setController(cntrlImpl);
		this.achievement.add(gb.build());

		// 6th achievement
		gb = new GoalBuilderImpl();
		gb.setTitle("A Red Bull for you");
		gb.setDescr("Destroy 250 red candy");
		gb.setMethod(e -> {
			resetPlayerMap();
			return Integer.parseInt(mapCurrentPlayer.get(RED.name()).toString()) >= 250;
		});
		gb.setController(cntrlImpl);
		this.achievement.add(gb.build());

		// 7th achievement
		gb = new GoalBuilderImpl();
		gb.setTitle("Praise the Sun!!!");
		gb.setDescr("Destroy 250 yellow candy");
		gb.setMethod(e -> {
			resetPlayerMap();
			return Integer.parseInt(mapCurrentPlayer.get(YELLOW.name()).toString()) >= 250;
		});
		gb.setController(cntrlImpl);
		this.achievement.add(gb.build());

		// 8th achievement
		gb = new GoalBuilderImpl();
		gb.setTitle("I am inevitable.");
		gb.setDescr("Destroy 250 purple candy");
		gb.setMethod(e -> {
			resetPlayerMap();
			return Integer.parseInt(mapCurrentPlayer.get(PURPLE.name()).toString()) >= 250;
		});
		gb.setController(cntrlImpl);
		this.achievement.add(gb.build());

		// 9th achievement
		gb = new GoalBuilderImpl();
		gb.setTitle("Immune to Scurvy");
		gb.setDescr("Destroy 250 orange candy");
		gb.setMethod(e -> {
			resetPlayerMap();
			return Integer.parseInt(mapCurrentPlayer.get(ORANGE.name()).toString()) >= 250;
		});
		gb.setController(cntrlImpl);
		this.achievement.add(gb.build());

		// 10th achievement
		gb = new GoalBuilderImpl();
		gb.setTitle("Lactose intolerance");
		gb.setDescr("Destroy 50 pieces of chocolate");
		gb.setMethod(e -> {
			resetPlayerMap();
			return Integer.parseInt(mapCurrentPlayer.get(CHOCOLATE.name()).toString()) >= 50;
		});
		gb.setController(cntrlImpl);
		this.achievement.add(gb.build());

		// 11th achievement
		gb = new GoalBuilderImpl();
		gb.setTitle("THIS...IS...SUGAR CRUSH!!!");
		gb.setDescr("Destroy 300 candy");
		gb.setMethod(e -> {
			resetPlayerMap();
			return (Integer.parseInt(mapCurrentPlayer.get(BLUE.name()).toString())
							+ Integer.parseInt(
									mapCurrentPlayer.get(GREEN.name()).toString())
							+ Integer.parseInt(
									mapCurrentPlayer.get(ORANGE.name()).toString())
							+ Integer.parseInt(
									mapCurrentPlayer.get(PURPLE.name()).toString())
							+ Integer.parseInt(mapCurrentPlayer.get(RED.name()).toString())
							+ Integer.parseInt(
									mapCurrentPlayer.get(YELLOW.name()).toString()))
					>= 300;
		});
		gb.setController(cntrlImpl);
		this.achievement.add(gb.build());

		// 12th achievement
		gb = new GoalBuilderImpl();
		gb.setTitle("Finish!!");
		gb.setDescr("Finish the game");
		gb.setMethod(e -> {
			resetPlayerMap();
			return Integer.parseInt(mapCurrentPlayer.get(level10Score.name()).toString()) > 0;
		});
		gb.setController(cntrlImpl);
		this.achievement.add(gb.build());
	}

	/** Tells the {@link GoalManager} to reset the internal data about current player. */
	public void resetPlayerMap() {
		for (final Map<String, Object> map : pm.getPlayers(FileTypes.STATS)) {
			if (map.get(playerName).toString().equals("\"" + cntrlImpl.getCurrentPlayer() + "\"")) {
				mapCurrentPlayer = map;
				break;
			}
		}
	}

	/** @return the list of achievements of the player */
	public List<Goal> getAchievement() {
		return this.achievement;
	}
}
