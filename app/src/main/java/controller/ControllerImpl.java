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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

import controller.files.*;
import model.Model;
import model.ModelImpl;
import model.game.GameResult;
import model.game.grid.candies.Candy;
import model.game.grid.candies.CandyBuilderImpl;
import model.game.grid.candies.CandyColors;
import model.game.grid.candies.CandyTypes;
import model.goals.Goal;
import model.objectives.Challenge;
import model.objectives.Objective;
import model.score.Status;
import model.shop.Boost;
import utils.Pair;
import utils.Point2D;
import utils.Triple;
import view.View;
import view.ViewImpl;
import view.gui.Login;
import view.sounds.*;

/**
 * @author Filippo Barbari
 * @author Emanuele Lamagna
 * @author Filippo Benvenuti
 * @author Davide Degli Esposti
 */
public final class ControllerImpl implements Controller {

	private static View view = null;
	private static Model model = null;
	private Optional<String> currentPlayer;

	/*
	 * If empty, there's no current level.
	 * If zero is contained, the current levelPedoniPP is the tutorial.
	 * If number contained is between 1 and 10 inclusive, the current level's number is contained.
	 * Otherwise, an internal error is raised.
	 */
	private Optional<Integer> currentLevel = Optional.empty();
	private final Sound sound = new SoundImpl();

	public static void main(final String[] args) {
		new ControllerImpl();
	}

	public ControllerImpl() {
		super();
		ControllerImpl.view = new ViewImpl();
		ControllerImpl.model = new ModelImpl(this);
		ControllerImpl.view.setCurrentGUI(new Login(this, ControllerImpl.view));
	}

	public final void setCurrentLevel(final int level) {
		if (level < 0 || level > ControllerImpl.model.getNumLevels()) {
			throw new IllegalArgumentException("Invalid level number.");
		}
		this.currentLevel = Optional.of(level);
	}

	public final Optional<Integer> getCurrentLevel() {
		if (this.currentLevel.isPresent()) {
			if (this.currentLevel.get() < 0 || this.currentLevel.get() > ControllerImpl.model.getNumLevels()) {
				throw new IllegalStateException("Current level is invalid number.");
			}
		}
		return this.currentLevel;
	}

	public final void setCurrentPlayer(final String player) {
		this.currentPlayer = Optional.of(Objects.requireNonNull(player));
		model.resetGoals();
	}

	public final String getCurrentPlayer() {
		return this.currentPlayer.get();
	}

	public final int getRemainingMoves() {
		return ControllerImpl.model.getRemainingMoves();
	}

	public final Status getCurrentScore() {
		return ControllerImpl.model.getCurrentScore();
	}

	public final boolean move(final Point2D first, final Point2D second) {

		final boolean ris = ControllerImpl.model.move(first, second);

		view.updateGrid();

		// Checks for endings.
		if (this.isStageEnded()) {
			// Consume remaining moves.
			model.consumeRemainingMoves();
			// Inform view that level is ended.
			this.stageEnd();
			// Stage is ended, we check if the level is done.
			if (this.isLevelEnded()) {
				for (Goal g : model.getAchievement()) {
					if (!g.isReached() && g.checkIfReached()) {
						view.achievementUnlocked("Achievement Unlocked!\n" + g.getTitle() + "\n" + g.getDescription());
					}
				}
				this.levelEnd();
			} else if (this.hasNextStage()) {
				this.nextStage();
			}
		}
		return ris;
	}

	public final int getNumLevels() {
		return ControllerImpl.model.getNumLevels();
	}

	public final int getLastLevelUnlocked() {
		for (int i = 1; i <= 10; i++) {
			if (Integer.parseInt(getCurrentPlayerMap(FileTypes.STATS)
							.get("level" + i + "Score")
							.toString())
					== 0) {
				return i;
			}
		}
		return 10;
	}

	public final Map<Point2D, Optional<Candy>> getGrid() {
		return ControllerImpl.model.getGrid();
	}

	public final void startTutorial() {
		ControllerImpl.model.startNewGame(Optional.empty());
		setCurrentLevel(0);
	}

	public final void startLevel(final int levelNumber) {
		ControllerImpl.model.startNewGame(Optional.of(levelNumber));
		setCurrentLevel(levelNumber);
	}

	public final Optional<String> getStartingMessage() {
		return ControllerImpl.model.getStartingMessage();
	}

	public final Optional<String> getEndingMessage() {
		return ControllerImpl.model.getEndingMessage();
	}

	public final boolean isStageEnded() {
		return ControllerImpl.model.getResult() != GameResult.STILL_PLAYING;
	}

	public final boolean isLevelEnded() {
		final boolean result = isStageEnded() && !ControllerImpl.model.hasNextStage();
		if (result) {
			setPlayerStats(
					getCurrentPlayer(), getCurrentScore(), getCurrentLevel().get());
		}
		return result;
	}

	public final boolean hasNextStage() {
		return ControllerImpl.model.hasNextStage();
	}

	public final void nextStage() {
		ControllerImpl.model.nextStage();
		view.nextStage();
	}

	public final String getResult() {
		final GameResult result = ControllerImpl.model.getResult();
		if (getCurrentLevel().get() != 0) {
			// If game won, it completes the level calling complete in Status
			if (result.equals(GameResult.MIN_SCORE_REACHED) || result.equals(GameResult.CHALLENGE_COMPLETED)) {
				sound.playSound("level_completed");
				ControllerImpl.model.getCurrentLevel().getCurrentScore().complete();
			} else {
				sound.playSound("level_failed1");
			}
		}
		return result.getDescription();
	}

	public final Map<String, Object> getCurrentPlayerMap(final FileTypes type) {
		Map<String, Object> player = null;
		for (Map<String, Object> map : ControllerImpl.model.getPlayers(type)) {
			if (map.get(playerName).toString().equals("\"" + this.currentPlayer.get() + "\"")) {
				player = map;
				break;
			}
		}
		if (player == null) {
			throw new NullPointerException("Current player not found.");
		}
		return player;
	}

	// returns the list of the maps of the players, according to the file type
	public final List<Map<String, Object>> getPlayers(final FileTypes type) {
		Objects.requireNonNull(type);
		for (FileTypes ft : FileTypes.values()) {
			if (ft.equals(type)) {
				return ControllerImpl.model.getPlayers(ft);
			}
		}
		throw new IllegalArgumentException("Invalid type");
	}

	// removes a player
	public final void removePlayer(final String name) {
		Objects.requireNonNull(name);
		ControllerImpl.model.removePlayer(name);
		this.currentPlayer = Optional.empty();
	}

	// adds a player
	public final void addPlayer(final String player) {
		Objects.requireNonNull(player);
		ControllerImpl.model.addPlayer(player);
	}

	// update the infos about the players
	public final void updatePlayer(final List<Map<String, Object>> list, final FileTypes type) {
		Objects.requireNonNull(list);
		Objects.requireNonNull(type);
		ControllerImpl.model.updatePlayer(list, type);
	}

	// sets the stats of a player
	public final void setPlayerStats(final String player, final Status status, final int level) {
		Objects.requireNonNull(player);
		Objects.requireNonNull(status);
		Objects.requireNonNull(level);
		// No need to update stats in tutorial
		if (level != 0) {
			final Map<String, Object> pl = this.getCurrentPlayerMap(FileTypes.STATS);
			final boolean check =
					Integer.parseInt(pl.get("level" + this.getCurrentLevel().get() + "Score")
									.toString())
							== 0;
			status.isFirstTime(check);
			ControllerImpl.model.setPlayerStats(player, status, level);
		}
	}

	// returns an objective
	public final Objective getObjective() {
		return ControllerImpl.model.getObjective();
	}

	public final Point2D getGridSize() {
		int minX = Integer.MAX_VALUE;
		int maxX = Integer.MIN_VALUE;
		int minY = Integer.MAX_VALUE;
		int maxY = Integer.MIN_VALUE;
		final Set<Point2D> grid = ControllerImpl.model.getGrid().keySet();

		for (Point2D p : grid) {
			int cx = p.getX();
			int cy = p.getY();
			if (cx < minX) minX = cx;
			if (cx > maxX) maxX = cx;
			if (cy < minY) minY = cy;
			if (cy > maxY) maxY = cy;
		}

		return new Point2D(maxX - minX + 1, maxY - minY + 1);
	}

	public final void updateGrid() {
		view.updateGrid();
	}

	public final Optional<Map<Point2D, Integer>> getJelly() {
		return model.getJelly();
	}

	public final List<Point2D> getHint() {
		return ControllerImpl.model.getHint();
	}

	public final void levelEnd() {
		view.levelEnd();
		model.end();
	}

	public final void stageEnd() {
		view.stageEnd();
	}

	public final List<Triple<String, String, Boolean>> getAchievements() {
		final List<Triple<String, String, Boolean>> dataAchievement = new ArrayList<>();
		for (Goal g : model.getAchievement()) {
			dataAchievement.add(new Triple<>(g.getTitle(), g.getDescription(), g.checkIfReached()));
		}
		return dataAchievement;
	}

	public final List<Pair<String, Integer>> getRankByGeneralScore() {
		return model.getGeneralScoreRank();
	}

	public final List<Pair<String, Integer>> getRankByLevelScore(final int lvlNumber) {
		return model.getLevelScoreRank(lvlNumber);
	}

	public final double getPercent() {
		if (model.getObjective().getChallenge().isEmpty()) {
			return 100;
		}

		final Challenge c = model.getObjective().getChallenge().get();
		final Status s = model.getCurrentScore();
		double done = 0;
		final double total = c.getBlueToDestroy()
				+ c.getRedToDestroy()
				+ c.getYellowToDestroy()
				+ c.getGreenToDestroy()
				+ c.getOrangeToDestroy()
				+ c.getPurpleToDestroy()
				+ c.getStripedToFarm()
				+ c.getFrecklesToFarm()
				+ c.getWrappedToFarm();

		if (s.getColors(CandyColors.BLUE) <= c.getBlueToDestroy()) {
			done = done + s.getColors(CandyColors.BLUE);
		} else {
			done = done + c.getBlueToDestroy();
		}
		if (s.getColors(CandyColors.RED) <= c.getRedToDestroy()) {
			done = done + s.getColors(CandyColors.RED);
		} else {
			done = done + c.getRedToDestroy();
		}
		if (s.getColors(CandyColors.YELLOW) <= c.getYellowToDestroy()) {
			done = done + s.getColors(CandyColors.YELLOW);
		} else {
			done = done + c.getYellowToDestroy();
		}
		if (s.getColors(CandyColors.GREEN) <= c.getGreenToDestroy()) {
			done = done + s.getColors(CandyColors.GREEN);
		} else {
			done = done + c.getGreenToDestroy();
		}
		if (s.getColors(CandyColors.ORANGE) <= c.getOrangeToDestroy()) {
			done = done + s.getColors(CandyColors.ORANGE);
		} else {
			done = done + c.getOrangeToDestroy();
		}
		if (s.getColors(CandyColors.PURPLE) <= c.getPurpleToDestroy()) {
			done = done + s.getColors(CandyColors.PURPLE);
		} else {
			done = done + c.getPurpleToDestroy();
		}

		if (s.getTypes(StatsTypes.STRIPED) <= c.getStripedToFarm()) {
			done = done + s.getTypes(StatsTypes.STRIPED);
		} else {
			done = done + c.getStripedToFarm();
		}

		if (s.getTypes(StatsTypes.FRECKLES) <= c.getFrecklesToFarm()) {
			done = done + s.getTypes(StatsTypes.FRECKLES);
		} else {
			done = done + c.getFrecklesToFarm();
		}

		if (s.getTypes(StatsTypes.WRAPPED) <= c.getWrappedToFarm()) {
			done = done + s.getTypes(StatsTypes.WRAPPED);
		} else {
			done = done + c.getWrappedToFarm();
		}

		return (done / total) * 100;
	}

	public final List<Boost> getBoostOnSale() {
		return model.getBoostsList();
	}

	public final Map<String, Integer> getObtatinedBoosts() {
		final Map<String, Integer> map = new HashMap<>();
		final Map<String, Object> m = new HashMap<>();
		m.putAll(getCurrentPlayerMap(FileTypes.BOOSTS));
		m.remove(playerName);

		m.keySet().stream()
				.filter(s -> Integer.parseInt(m.get(s).toString()) > 0)
				.forEach(s -> map.put(s, Integer.parseInt(m.get(s).toString())));
		return map;
	}

	public final void pay(final String playerName, final Boost bst) {
		model.makePayment(playerName, bst);
	}

	public final void useBoost(final String candyType, final Point2D position) {
		CandyTypes ct = null;
		for (CandyTypes c : CandyTypes.values()) {
			if (c.name().toLowerCase().equals(candyType.toLowerCase())) {
				ct = c;
				break;
			}
		}
		final CandyColors cc;
		if (ct == CandyTypes.FRECKLES) {
			cc = CandyColors.FRECKLES;
		} else {
			cc = model.getGrid().get(position).get().getColor();
		}
		final Candy candy = new CandyBuilderImpl().setColor(cc).setType(ct).build();
		model.mutateCandy(position, candy);

		final List<Map<String, Object>> l = getPlayers(FileTypes.BOOSTS);
		for (var map : l) {
			if (map.get(playerName).toString().equals("\"" + getCurrentPlayer() + "\"")) {
				map.put(ct.name(), Integer.parseInt(map.get(ct.name()).toString()) - 1);
			}
		}
		updatePlayer(l, FileTypes.BOOSTS);
	}

	public final void resetShop() {
		model.resetShop();
	}

	public final Sound getSound() {
		return this.sound;
	}

	public final int getCurrentMoney() {
		return Integer.parseInt(getCurrentPlayerMap(FileTypes.STATS)
				.get(StatsTypes.money.name())
				.toString());
	}
}
