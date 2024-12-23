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
package controller;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import controller.files.FileTypes;
import model.game.grid.candies.Candy;
import model.game.level.Level;
import model.game.level.stage.Stage;
import model.objectives.Objective;
import model.score.Status;
import model.shop.Boost;
import utils.Pair;
import utils.Point2D;
import utils.Triple;
import view.sounds.Sound;

/**
 * The interface used by View to communicate
 *
 * @author Filippo Barbari
 * @author Filippo Benvenuti
 * @author Emanuele Lamagna
 * @author Davide Degli Esposti
 */
public interface Controller {

	String playerName = "playerName";

	/**
	 * Sets the name of the current player
	 *
	 * @param player the name of the player to put as current
	 */
	void setCurrentPlayer(final String player);

	/**
	 * Getter of the name of the current player
	 *
	 * @return the name of the current player
	 */
	String getCurrentPlayer();

	/**
	 * Getter of the map of the current player
	 *
	 * @return the map of the current player (for stats or boosts, depending by "type" parameter)
	 */
	Map<String, Object> getCurrentPlayerMap(final FileTypes type);

	/**
	 * Allows to set the {@link Level} corresponding to the given number as the current {@link Level}.
	 *
	 * @param level If 0, the tutorial is set; otherwise, the corresponding normal level is set.
	 */
	void setCurrentLevel(final int level);

	/**
	 * @return An Optional containing the number of the current {@link Level}. If 0, the current level is the tutorial;
	 *     if empty, there is no current level; otherwise the number represents the current {@link Level}.
	 */
	Optional<Integer> getCurrentLevel();

	/**
	 * Reads the right file to get all the players stats, achievements or boost (it depends by "type" parameter)
	 *
	 * @param type the type of file to get (boosts or stats)
	 * @return a list of all the players (as maps)
	 */
	List<Map<String, Object>> getPlayers(final FileTypes type);

	/**
	 * Removes a player from all the files
	 *
	 * @param name the name of the player
	 */
	void removePlayer(final String name);

	/**
	 * Adds a new player in the list of the players.
	 *
	 * @param playerName the name of the player
	 */
	void addPlayer(final String playerName);

	/**
	 * Updates the information of the player, with the received list of maps
	 *
	 * @param list the list of the players (as maps)
	 * @param type the type of file to update (boosts or stats)
	 */
	void updatePlayer(final List<Map<String, Object>> list, final FileTypes type);

	/**
	 * Checks the values in Status and refreshes the information of that player
	 *
	 * @param playerName the name of the player
	 * @param status the status of the current player
	 * @param level the number of the actual level
	 */
	void setPlayerStats(final String playerName, final Status status, final int level);

	/** @return The number of currently available {@link Level}s. */
	int getNumLevels();

	/** @return The number corresponding to the last level unlocked. */
	int getLastLevelUnlocked();

	/**
	 * @return true if the current {@link Stage} of the current {@link Level} is not the last {@link Stage}. false
	 *     otherwise.
	 */
	boolean hasNextStage();

	/** If the current {@link Stage} isn't the last one, the next {@link Stage} is set to be the current one. */
	void nextStage();

	/**
	 * Getter of the remaining moves of the level
	 *
	 * @return the remaining moves of the level
	 */
	int getRemainingMoves();

	/**
	 * Getter of the current score
	 *
	 * @return the current score
	 */
	Status getCurrentScore();

	/**
	 * Try to swap two near candies, if it is possible grid is updated.
	 *
	 * @param first The first {@link Candy} selected.
	 * @param second The second {@link Candy} selected to be swapped with first.
	 * @return True if move was possible, false otherwise.
	 */
	boolean move(Point2D first, Point2D second);

	/**
	 * Retrieves the current state of the grid, {@link Candy} is an optional cause of if it is broken it is empty.
	 *
	 * @return A Map from {@link Point2D} to an Optional of {@link Candy}.
	 */
	Map<Point2D, Optional<Candy>> getGrid();

	/**
	 * Retrieves the dimensions of the grid.
	 *
	 * @return A {@link Point2D} ordered filled with width and height of the grid.
	 */
	Point2D getGridSize();

	/** Sets the tutorial as the current {@link Level}. */
	void startTutorial();

	/** Sets the {@link Level} corresponding to the given number as the current {@link Level}. */
	void startLevel(final int levelNumber);

	/**
	 * Retrieves the tip for a move in game.
	 *
	 * @return A list containing hint coordinates.
	 */
	List<Point2D> getHint();

	/**
	 * Retrieves a Map from {@link Point2D} to {@link Integer} representing kelly liveness.
	 *
	 * @return The Map of jelly liveness.
	 */
	Optional<Map<Point2D, Integer>> getJelly();

	/**
	 * @return The message that needs to be printed at the beginning of the current {@link Stage}. If Optional.empty()
	 *     is returned, no message is to be printed.
	 */
	Optional<String> getStartingMessage();

	/**
	 * @return The message that needs to be printed at the end of the current {@link Stage}. If Optional.empty() is
	 *     returned, no message is to be printed.
	 */
	Optional<String> getEndingMessage();

	/** @return True if current {@link Level} has ended false otherwise. */
	boolean isLevelEnded();

	/** Forces the current {@link Level} to end. Updates current player's stats. */
	void levelEnd();

	/** @return True if current {@link Stage} has ended false otherwise. */
	boolean isStageEnded();

	/** Forces the current {@link Stage} to end and */
	void stageEnd();

	/**
	 * Updates current player's money, if game won.
	 *
	 * @return The description of the result of the current level.
	 */
	String getResult();

	/**
	 * Retrieves the objective of the current level
	 *
	 * @return Objective of current level.
	 */
	Objective getObjective();
	// -----------------------

	// About view
	/** Tells the view to update the grid. */
	void updateGrid();
	// -----------------

	/** @return a list of triple containing all the info about achievements */
	List<Triple<String, String, Boolean>> getAchievements();

	/** @return the list of players sort by general score */
	List<Pair<String, Integer>> getRankByGeneralScore();

	/** @return the list of players sort by the score of the lvlNumber */
	List<Pair<String, Integer>> getRankByLevelScore(int lvlNumber);

	/**
	 * A method that calculates the right import to write in the percentage of completion in the game gui
	 *
	 * @return a double value, that will be reduced to an integer
	 */
	double getPercent();

	/** @return the list of item on sale */
	List<Boost> getBoostOnSale();

	/**
	 * @return A list containing the name and the number of each currently obtained boost (no zero values contained).
	 */
	Map<String, Integer> getObtatinedBoosts();

	/** make the payement for the selected item in the shop */
	void pay(final String playerName, final Boost bst);

	/**
	 * Tells the model to use the given boost in the given position.
	 *
	 * @param candyType The type of the boost
	 * @param position The position where to place the boost.
	 */
	void useBoost(final String candyType, final Point2D position);

	/** refresh the items in the shop */
	void resetShop();

	/**
	 * Getter of the sound object uset to play sounds or set the on/off system
	 *
	 * @return the sound object, to play sounds or set the on/off system
	 */
	Sound getSound();

	/** @return The amount of money of the current player. */
	int getCurrentMoney();
}
