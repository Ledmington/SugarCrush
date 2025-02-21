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

import java.util.List;
import java.util.Map;
import java.util.function.Predicate;

import controller.Controller;
import controller.files.FileTypes;
import model.players.PlayerManager;

/** @author Davide Degli Esposti */
public final class Achievement {

	public static AchievementBuilder builder() {
		return new AchievementBuilderImpl();
	}

	private final String title; // The main title of the goal
	private final String description; // The short description of the goal
	private boolean reached;
	private final Predicate<Map<String, Object>> method; // the method for check if a goal is reached
	private final Controller controller;

	/**
	 * constructor for initializing the achievement
	 *
	 * @param title the title of the goal
	 * @param description the description of the goal
	 * @param method the method for check if a goal is reached
	 */
	public Achievement(
			final Controller controller,
			final String title,
			final String description,
			final Predicate<Map<String, Object>> method) {
		this.controller = controller;
		this.title = title;
		this.description = description;
		this.reached = false;
		this.method = method;
	}

	/** @return the title of the goal */
	public String getTitle() {
		return this.title;
	}

	/** @return the description of the goal */
	public String getDescription() {
		return this.description;
	}

	/** @return true if the goal is reached */
	public boolean isReached() {
		return this.reached;
	}

	/**
	 * check through "method" if the goal is reached.
	 *
	 * @return true if is reached
	 */
	public boolean checkIfReached(final PlayerManager pm) {
		final List<Map<String, Object>> playersList = pm.getPlayers(FileTypes.STATS);
		for (final Map<String, Object> map : playersList) {
			if (map.get(Controller.playerName).toString().equals("\"" + controller.getCurrentPlayerName() + "\"")) {
				this.reached = this.method.test(map);
				break;
			}
		}
		return this.reached;
	}
}
