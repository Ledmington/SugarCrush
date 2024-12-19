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

import java.util.List;
import java.util.Map;
import java.util.function.Predicate;

import controller.*;
import controller.files.FileTypes;
import model.players.PlayerManagerImpl;

/** @author Davide Degli Esposti */
public final class Goal {

	private final String title; // is the main title of the goal
	private final String descr; // is the short description of the goal
	private boolean reached; // is the flag that is true if the agoal is reached
	private final Predicate<Map<String, Object>> method; // the method for check if a goal is reached
	private final PlayerManagerImpl pm = new PlayerManagerImpl(); // variable to get the list of the players
	private final Controller cntrlImpl;

	/**
	 * constructor for initializing the achievement
	 *
	 * @param title the title of the goal
	 * @param descr the description of the goal
	 * @param method the method for check if a goal is reached
	 */
	public Goal(
			final Controller controller,
			final String title,
			final String descr,
			final Predicate<Map<String, Object>> method) {
		this.cntrlImpl = controller;
		this.title = title;
		this.descr = descr;
		this.reached = false;
		this.method = method;
	}

	/** @return the title of the goal */
	public String getTitle() {
		return this.title;
	}

	/** @return the description of the goal */
	public String getDescription() {
		return this.descr;
	}

	/** @return true if the goal is reached */
	public boolean isReached() {
		return this.reached;
	}

	/**
	 * check through "method" if the goal is reached
	 *
	 * @return true if is reached
	 */
	public boolean checkIfReached() {
		final List<Map<String, Object>> list = pm.getPlayers(FileTypes.STATS);
		for (var map : list) {
			if (map.get(playerName).toString().equals("\"" + cntrlImpl.getCurrentPlayer() + "\"")) {
				this.reached = this.method.test(map);
				break;
			}
		}
		return this.reached;
	}
}
