package model;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

import controller.Controller;
import controller.files.FileTypes;
import model.score.Status;
import model.shop.Boost;
import model.shop.BoostShop;
import model.game.grid.candies.Candy;
import model.game.level.Level;
import model.game.GameResult;
import model.game.level.LevelsManager;
import model.game.level.LevelsManagerImpl;
import model.goals.Goal;
import model.goals.GoalManager;
import model.objectives.Objective;
import model.players.PlayerManager;
import model.players.PlayerManagerImpl;
import model.rank.ScoreBoard;
import utils.Pair;
import utils.Point2D;

/**
 * 
 * @author Filippo Barbari
 * @author Filippo Benvenuti
 * @author Emanuele Lamagna
 * @author Davide Degli Esposti
 *
 */
public final class ModelImpl implements Model {
	
    private final Controller controller;
    private Optional<Level> currentLevel;
    private PlayerManager pm;
    private LevelsManager lm;
    private GoalManager gm;
    private ScoreBoard sb;
    private BoostShop bs;
    
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

    public final void startNewGame(final Optional<Integer> levelIndex) {
        if(this.currentLevel.isPresent()) {
            throw new IllegalStateException("Game already running! End the running game first.");
        }

        if(levelIndex.isEmpty()) {
            this.currentLevel = Optional.of(this.lm.getTutorial());
        }
        else {
            this.currentLevel = Optional.of(this.lm.getLevel(levelIndex.get()));
        }
    }
    
    public final Optional<String> getStartingMessage(){
        assertGameRunning();
        return this.currentLevel.get().getStartingMessage();
    }
    
    public final Optional<String> getEndingMessage(){
        assertGameRunning();
        return this.currentLevel.get().getEndingMessage();
    }
    
    public final boolean hasNextStage() {
        assertGameRunning();
        return this.currentLevel.get().hasNextStage();
    }
    
    public final void nextStage() {
        assertGameRunning();
        this.currentLevel.get().nextStage();
    }

    public final Objective getObjective() {
        this.assertGameRunning();
        return this.currentLevel.get().getObjective();
    }

    public final Map<Point2D, Optional<Candy>> getGrid() {
        this.assertGameRunning();
        return this.currentLevel.get().getGrid();
    }

    public final boolean move(final Point2D first, final Point2D second) {
        this.assertGameRunning();
        return this.currentLevel.get().move(first, second);
    }

    public final Status getCurrentScore() {
        this.assertGameRunning();
        return this.currentLevel.get().getCurrentScore();
    }

    public final Integer getRemainingMoves() {
        this.assertGameRunning();
        return this.currentLevel.get().getObjective().getMaxMoves() - 
                this.currentLevel.get().getCurrentScore().getMoves();
    }
    
    public final GameResult getResult() {
        if(this.currentLevel.isEmpty()) {
            return GameResult.CHALLENGE_COMPLETED;
    	}
        this.assertGameRunning();
        final GameResult result = currentLevel.get().getResult();
        if(result==GameResult.MIN_SCORE_REACHED || result==GameResult.CHALLENGE_COMPLETED) {
            currentLevel.get().getCurrentScore().complete();
        }
        return result;
    }

    public final void end() {
        this.currentLevel = Optional.empty();
    }

    private final void assertGameRunning() {
        if(this.currentLevel.isEmpty()) {
            throw new IllegalStateException("Game must be running to do this.");
        }
    }

    public final void addPlayer(final String name) {
    	Objects.requireNonNull(name);
        pm.addPlayer(name);
    }

    public final void setPlayerStats(final String name, final Status status, final int level) {
    	Objects.requireNonNull(name);
    	Objects.requireNonNull(status);
    	Objects.requireNonNull(level);
        pm.setStat(name, status, level);
    }

    public final void updatePlayer(final List<Map<String, Object>> list, final FileTypes type) {
    	Objects.requireNonNull(list);
    	Objects.requireNonNull(type);
        pm.update(list, type);
    }

    public final List<Map<String, Object>> getPlayers(final FileTypes type) {
    	Objects.requireNonNull(type);
        return pm.getPlayers(type);
    }
    
    public final void removePlayer(final String name) {
    	Objects.requireNonNull(name);
    	pm.removePlayer(name);
    }

    public final int getNumLevels() {
        return lm.getNumLevels();
    }

    public final Level getCurrentLevel() {
        this.assertGameRunning();
        return currentLevel.get();
    }

    public final List<Point2D> getHint() {
    	this.assertGameRunning();
        return currentLevel.get().getHint();
    }
    
    public final void consumeRemainingMoves() {
    	this.assertGameRunning();
    	this.currentLevel.get().consumeRemainingMoves();
    }
    
    public final Optional<Map<Point2D, Integer>> getJelly(){
    	this.assertGameRunning();
    	return this.currentLevel.get().getJelly();
    }
    
    public final void resetGoals() {
    	this.gm = new GoalManager(controller);
    }
    
    public final List<Goal> getAchievement(){
        this.gm.resetPlayerMap();
    	return this.gm.getAchievement();
    }
    
    public final List<Pair<String,Integer>> getGeneralScoreRank(){
    	return this.sb.rankByGeneralScore();
    }
    
    public final List<Pair<String,Integer>> getLevelScoreRank(int lvlNumber){
    	return this.sb.rankByScoreInLevel(lvlNumber);
    }
    
    public final List<Boost> getBoostsList(){
    	return this.bs.getBoosts();
    }
    
    public final void makePayment(final String name, final Boost bst) {
    	this.bs.payment(name, bst);
    }
    
    public final boolean mutateCandy(final Point2D cord, final Candy cnd) {
        assertGameRunning();
        return currentLevel.get().mutateCandy(cord, cnd);
    }
    
    public final void resetShop() {
    	this.bs.generateShop();
    }
}
