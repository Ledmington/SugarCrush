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
package model;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

import controller.Controller;
import controller.files.FileTypes;
import model.game.GameResult;
import model.game.grid.candies.Candy;
import model.game.level.Level;
import model.game.level.LevelsManager;
import model.game.level.LevelsManagerImpl;
import model.goals.Goal;
import model.goals.GoalManager;
import model.objectives.Objective;
import model.players.PlayerManager;
import model.players.PlayerManagerImpl;
import model.rank.ScoreBoard;
import model.score.Status;
import model.shop.Boost;
import model.shop.BoostShop;
import utils.Pair;
import utils.Point2D;

/**
 * @author Filippo Barbari
 * @author Filippo Benvenuti
 * @author Emanuele Lamagna
 * @author Davide Degli Esposti
 */
public final class ModelImpl implements Model {

	private final Controller controller;
	private Optional<Level> currentLevel;
	private final PlayerManager pm;
	private final LevelsManager lm;
	private GoalManager gm;
	private final ScoreBoard sb;
	private final BoostShop bs;

	public ModelImpl(final Controller controller) {
		super();
		this.controller = controller;
		this.currentLevel = Optional.empty();
		this.pm = new PlayerManagerImpl();
		this.lm = new LevelsManagerImpl(controller);
		this.gm = new GoalManager(controller);
		this.sb = new ScoreBoard();
		this.bs = new BoostShop();
	}

	public void startNewGame(final Optional<Integer> levelIndex) {
		if (this.currentLevel.isPresent()) {
			throw new IllegalStateException("Game already running! End the running game first.");
		}

		if (levelIndex.isEmpty()) {
			this.currentLevel = Optional.of(this.lm.getTutorial());
		} else {
			this.currentLevel = Optional.of(this.lm.getLevel(levelIndex.orElseThrow()));
		}
	}

	public Optional<String> getStartingMessage() {
		assertGameRunning();
		return this.currentLevel.orElseThrow().getStartingMessage();
	}

	public Optional<String> getEndingMessage() {
		assertGameRunning();
		return this.currentLevel.orElseThrow().getEndingMessage();
	}

	public boolean hasNextStage() {
		assertGameRunning();
		return this.currentLevel.orElseThrow().hasNextStage();
	}

	public void nextStage() {
		assertGameRunning();
		this.currentLevel.orElseThrow().nextStage();
	}

	public Objective getObjective() {
		this.assertGameRunning();
		return this.currentLevel.orElseThrow().getObjective();
	}

	public Map<Point2D, Optional<Candy>> getGrid() {
		this.assertGameRunning();
		return this.currentLevel.orElseThrow().getGrid();
	}

	public boolean move(final Point2D first, final Point2D second) {
		this.assertGameRunning();
		return this.currentLevel.orElseThrow().move(first, second);
	}

	public Status getCurrentScore() {
		this.assertGameRunning();
		return this.currentLevel.orElseThrow().getCurrentScore();
	}

	public Integer getRemainingMoves() {
		this.assertGameRunning();
		return this.currentLevel.orElseThrow().getObjective().getMaxMoves()
				- this.currentLevel.orElseThrow().getCurrentScore().getMoves();
	}

	public GameResult getResult() {
		if (this.currentLevel.isEmpty()) {
			return GameResult.CHALLENGE_COMPLETED;
		}
		this.assertGameRunning();
		final GameResult result = currentLevel.orElseThrow().getResult();
		if (result == GameResult.MIN_SCORE_REACHED || result == GameResult.CHALLENGE_COMPLETED) {
			currentLevel.orElseThrow().getCurrentScore().complete();
		}
		return result;
	}

	public void end() {
		this.currentLevel = Optional.empty();
	}

	private void assertGameRunning() {
		if (this.currentLevel.isEmpty()) {
			throw new IllegalStateException("Game must be running to do this.");
		}
	}

	public void addPlayer(final String name) {
		Objects.requireNonNull(name);
		pm.addPlayer(name);
	}

	public void setPlayerStats(final String name, final Status status, final int level) {
		Objects.requireNonNull(name);
		Objects.requireNonNull(status);
		pm.setStat(name, status, level);
	}

	public void updatePlayer(final List<Map<String, Object>> list, final FileTypes type) {
		Objects.requireNonNull(list);
		Objects.requireNonNull(type);
		pm.update(list, type);
	}

	public List<Map<String, Object>> getPlayers(final FileTypes type) {
		Objects.requireNonNull(type);
		return pm.getPlayers(type);
	}

	public void removePlayer(final String name) {
		Objects.requireNonNull(name);
		pm.removePlayer(name);
	}

	public int getNumLevels() {
		return lm.getNumLevels();
	}

	public Level getCurrentLevel() {
		this.assertGameRunning();
		return currentLevel.orElseThrow();
	}

	public List<Point2D> getHint() {
		this.assertGameRunning();
		return currentLevel.orElseThrow().getHint();
	}

	public void consumeRemainingMoves() {
		this.assertGameRunning();
		this.currentLevel.orElseThrow().consumeRemainingMoves();
	}

	public Optional<Map<Point2D, Integer>> getJelly() {
		this.assertGameRunning();
		return this.currentLevel.orElseThrow().getJelly();
	}

	public void resetGoals() {
		this.gm = new GoalManager(controller);
	}

	public List<Goal> getAchievement() {
		this.gm.resetPlayerMap();
		return this.gm.getAchievement();
	}

	public List<Pair<String, Integer>> getGeneralScoreRank() {
		return this.sb.rankByGeneralScore();
	}

	public List<Pair<String, Integer>> getLevelScoreRank(int lvlNumber) {
		return this.sb.rankByScoreInLevel(lvlNumber);
	}

	public List<Boost> getBoostsList() {
		return this.bs.getBoosts();
	}

	public void makePayment(final String name, final Boost bst) {
		this.bs.payment(name, bst);
	}

	public boolean mutateCandy(final Point2D cord, final Candy cnd) {
		assertGameRunning();
		return currentLevel.orElseThrow().mutateCandy(cord, cnd);
	}

	public void resetShop() {
		this.bs.generateShop();
	}
}
