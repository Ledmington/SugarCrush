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
package model.game;

/**
 * Contains the possible results of a game.
 *
 * @author Filippo Barbari
 */
public enum GameResult {

	/** The "normal" victory. */
	MIN_SCORE_REACHED("Congrats! You reached the minimum score!"),

	/** Obtained if (and only if) the level's minimum score is reached and the relative challenge is fulfilled. */
	CHALLENGE_COMPLETED("You're a champion! You completed the challenge!"),

	/** Happens when the number of moves has reached zero before reaching the minimum score. */
	OUT_OF_MOVES("Oh no! You just run out of moves without completing the objective."),

	/**
	 * Happens if (and only if) a game becomes unplayable because of an enemy/obstacle. (example: the chocolate has
	 * filled the entire map)
	 */
	ENEMY_WIN("What a disaster! Chocolate has filled the entire grid =( ."),

	/** Happens when the level is not ended */
	STILL_PLAYING(null);

	private String description;

	private GameResult(final String desc) {
		this.description = desc;
	}

	public final String getDescription() {
		return this.description;
	}
}
