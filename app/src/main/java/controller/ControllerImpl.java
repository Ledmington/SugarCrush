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
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

import controller.files.*;
import model.Model;
import model.ModelImpl;
import model.achievement.Achievement;
import model.game.GameResult;
import model.game.grid.candies.Candy;
import model.game.grid.candies.CandyBuilderImpl;
import model.game.grid.candies.CandyColors;
import model.game.grid.candies.CandyTypes;
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

	private View view = null;
	private Model model = null;
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
		this.view = new ViewImpl();
		this.model = new ModelImpl(this);
		this.view.setCurrentGUI(new Login(this, this.view));
	}

	public void setCurrentLevel(final int level) {
		if (level < 0 || level > this.model.getNumLevels()) {
			throw new IllegalArgumentException("Invalid level number.");
		}
		this.currentLevel = Optional.of(level);
	}

	public Optional<Integer> getCurrentLevel() {
		if (this.currentLevel.isPresent()) {
			if (this.currentLevel.orElseThrow() < 0 || this.currentLevel.orElseThrow() > this.model.getNumLevels()) {
				throw new IllegalStateException("Current level is invalid number.");
			}
		}
		return this.currentLevel;
	}

	public void setCurrentPlayer(final String player) {
		this.currentPlayer = Optional.of(Objects.requireNonNull(player));
		model.resetGoals();
	}

	public String getCurrentPlayerName() {
		return this.currentPlayer.orElseThrow();
	}

	public int getRemainingMoves() {
		return this.model.getRemainingMoves();
	}

	public Status getCurrentScore() {
		return this.model.getCurrentScore();
	}

	public boolean move(final Point2D first, final Point2D second) {

		final boolean ris = this.model.move(first, second);

		view.updateGrid();

		// Checks for endings.
		if (this.isStageEnded()) {
			// Consume remaining moves.
			model.consumeRemainingMoves();
			// Inform view that level is ended.
			this.stageEnd();
			// Stage is ended, we check if the level is done.
			if (this.isLevelEnded()) {
				for (final Achievement g : model.getAchievement()) {
					if (!g.isReached() && g.checkIfReached(this.model.getPlayerManager())) {
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

	public int getNumLevels() {
		return this.model.getNumLevels();
	}

	public int getLastLevelUnlocked() {
		for (int i = 1; i <= 10; i++) {
			if (Integer.parseInt(getCurrentPlayer(FileTypes.STATS)
							.get("LEVEL_" + i + "_SCORE")
							.toString())
					== 0) {
				return i;
			}
		}
		return 10;
	}

	public Map<Point2D, Optional<Candy>> getGrid() {
		return this.model.getGrid();
	}

	public void startTutorial() {
		this.model.startNewGame(Optional.empty());
		setCurrentLevel(0);
	}

	public void startLevel(final int levelNumber) {
		this.model.startNewGame(Optional.of(levelNumber));
		setCurrentLevel(levelNumber);
	}

	public Optional<String> getStartingMessage() {
		return this.model.getStartingMessage();
	}

	public Optional<String> getEndingMessage() {
		return this.model.getEndingMessage();
	}

	public boolean isStageEnded() {
		return this.model.getResult() != GameResult.STILL_PLAYING;
	}

	public boolean isLevelEnded() {
		final boolean result = isStageEnded() && !this.model.hasNextStage();
		if (result) {
			setPlayerStats(
					getCurrentPlayerName(), getCurrentScore(), getCurrentLevel().orElseThrow());
		}
		return result;
	}

	public boolean hasNextStage() {
		return this.model.hasNextStage();
	}

	public void nextStage() {
		this.model.nextStage();
		view.nextStage();
	}

	public String getResult() {
		final GameResult result = this.model.getResult();
		if (getCurrentLevel().orElseThrow() != 0) {
			// If game won, it completes the level calling complete in Status
			if (result.equals(GameResult.MIN_SCORE_REACHED) || result.equals(GameResult.CHALLENGE_COMPLETED)) {
				sound.playSound("level_completed");
				this.model.getCurrentLevel().getCurrentScore().complete();
			} else {
				sound.playSound("level_failed1");
			}
		}
		return result.getDescription();
	}

	public Map<String, Object> getCurrentPlayer(final FileTypes type) {
		Map<String, Object> player = null;
		for (final Map<String, Object> map : this.model.getPlayers(type)) {
			if (map.get(playerName).toString().equals("\"" + this.currentPlayer.orElseThrow() + "\"")) {
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
	public List<Map<String, Object>> getPlayers(final FileTypes type) {
		Objects.requireNonNull(type);
		for (final FileTypes ft : FileTypes.values()) {
			if (ft.equals(type)) {
				return this.model.getPlayers(ft);
			}
		}
		throw new IllegalArgumentException("Invalid type");
	}

	// removes a player
	public void removePlayer(final String name) {
		Objects.requireNonNull(name);
		this.model.removePlayer(name);
		this.currentPlayer = Optional.empty();
	}

	// adds a player
	public void addPlayer(final String player) {
		Objects.requireNonNull(player);
		this.model.addPlayer(player);
	}

	// update the infos about the players
	public void updatePlayer(final List<Map<String, Object>> list, final FileTypes type) {
		Objects.requireNonNull(list);
		Objects.requireNonNull(type);
		this.model.updatePlayer(list, type);
	}

	// sets the stats of a player
	public void setPlayerStats(final String player, final Status status, final int level) {
		Objects.requireNonNull(player);
		Objects.requireNonNull(status);
		// No need to update stats in tutorial
		if (level != 0) {
			final Map<String, Object> pl = this.getCurrentPlayer(FileTypes.STATS);
			final boolean check =
					Integer.parseInt(pl.get("LEVEL_" + this.getCurrentLevel().orElseThrow() + "_SCORE")
									.toString())
							== 0;
			status.isFirstTime(check);
			this.model.setPlayerStats(player, status, level);
		}
	}

	// returns an objective
	public Objective getObjective() {
		return this.model.getObjective();
	}

	public Point2D getGridSize() {
		int minX = Integer.MAX_VALUE;
		int maxX = Integer.MIN_VALUE;
		int minY = Integer.MAX_VALUE;
		int maxY = Integer.MIN_VALUE;
		final Set<Point2D> grid = this.model.getGrid().keySet();

		for (Point2D p : grid) {
			int cx = p.x();
			int cy = p.y();
			if (cx < minX) minX = cx;
			if (cx > maxX) maxX = cx;
			if (cy < minY) minY = cy;
			if (cy > maxY) maxY = cy;
		}

		return new Point2D(maxX - minX + 1, maxY - minY + 1);
	}

	public void updateGrid() {
		view.updateGrid();
	}

	public Optional<Map<Point2D, Integer>> getJelly() {
		return model.getJelly();
	}

	public List<Point2D> getHint() {
		return this.model.getHint();
	}

	public void levelEnd() {
		view.levelEnd();
		model.end();
	}

	public void stageEnd() {
		view.stageEnd();
	}

	public List<Triple<String, String, Boolean>> getAchievements() {
		final List<Triple<String, String, Boolean>> dataAchievement = new ArrayList<>();
		for (final Achievement g : model.getAchievement()) {
			dataAchievement.add(
					new Triple<>(g.getTitle(), g.getDescription(), g.checkIfReached(this.model.getPlayerManager())));
		}
		return dataAchievement;
	}

	public List<Pair<String, Integer>> getRankByGeneralScore() {
		return model.getGeneralScoreRank();
	}

	public List<Pair<String, Integer>> getRankByLevelScore(final int lvlNumber) {
		return model.getLevelScoreRank(lvlNumber);
	}

	public double getPercent() {
		if (model.getObjective().getChallenge().isEmpty()) {
			return 100;
		}

		final Challenge c = model.getObjective().getChallenge().orElseThrow();
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

	public List<Boost> getBoostOnSale() {
		return model.getBoostsList();
	}

	public Map<String, Integer> getObtainedBoosts() {
		final Map<String, Integer> map = new HashMap<>();
		final Map<String, Object> m = new HashMap<>(getCurrentPlayer(FileTypes.BOOSTS));
		m.remove(playerName);

		m.keySet().stream()
				.filter(s -> Integer.parseInt(m.get(s).toString()) > 0)
				.forEach(s -> map.put(s, Integer.parseInt(m.get(s).toString())));
		return map;
	}

	public void pay(final String playerName, final Boost bst) {
		model.makePayment(playerName, bst);
	}

	public void useBoost(final String candyType, final Point2D position) {
		final CandyTypes ct = Arrays.stream(CandyTypes.values())
				.filter(c -> c.name().equalsIgnoreCase(candyType))
				.findFirst()
				.orElseThrow();

		final CandyColors cc;
		if (ct == CandyTypes.FRECKLES) {
			cc = CandyColors.FRECKLES;
		} else {
			cc = model.getGrid().get(position).orElseThrow().getColor();
		}
		final Candy candy = new CandyBuilderImpl().setColor(cc).setType(ct).build();
		model.mutateCandy(position, candy);

		final List<Map<String, Object>> l = getPlayers(FileTypes.BOOSTS);
		for (final Map<String, Object> map : l) {
			if (map.get(playerName).toString().equals("\"" + getCurrentPlayerName() + "\"")) {
				map.put(ct.name(), Integer.parseInt(map.get(ct.name()).toString()) - 1);
			}
		}
		updatePlayer(l, FileTypes.BOOSTS);
	}

	public void resetShop() {
		model.resetShop();
	}

	public Sound getSound() {
		return this.sound;
	}

	public int getCurrentMoney() {
		return Integer.parseInt(
				getCurrentPlayer(FileTypes.STATS).get(StatsTypes.MONEY.name()).toString());
	}
}
