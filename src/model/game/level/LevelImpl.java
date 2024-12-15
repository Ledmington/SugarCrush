package model.game.level;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

import model.score.Status;
import model.game.grid.candies.Candy;
import model.game.grid.candies.CandyColors;
import model.game.level.Level;
import model.game.GameResult;
import model.game.level.stage.Stage;
import model.objectives.Challenge;
import model.objectives.Objective;
import controller.Controller;
import controller.files.*;
import utils.Point2D;

/**
 * A basic implementation of a level.
 * @author Filippo Barbari
 *
 */
public final class LevelImpl implements Level {

    private final Iterator<Stage> itStage;
    private Optional<Stage> currentStage;
    private final Controller controller;

    public LevelImpl(final Controller controller, final List<Stage> stages) {
        super();
        
        this.controller = controller;
        Objects.requireNonNull(stages);
        this.itStage = stages.iterator();
        this.currentStage = Optional.of(itStage.next());
    }
    
    private final void assertCurrentStageSet() {
        if(currentStage.isEmpty()) {
            throw new IllegalStateException("No current state present.");
        }
    }

    public final Objective getObjective() {
        assertCurrentStageSet();
        return currentStage.get().getObjective();
    }

    /**
     * @author Emanuele Lamagna
     */
    public final GameResult getResult() {
        assertCurrentStageSet();
        Objective objective = getObjective();
        final Status status = getCurrentScore();
    	if(status.getScore()>=objective.getMaxScore() && objective.getChallenge().isEmpty()) {
    		return GameResult.MIN_SCORE_REACHED;
    	} else if(status.getScore()>=objective.getMaxScore() && objective.getChallenge().isPresent() && isCompleted()) {
    		return GameResult.CHALLENGE_COMPLETED;
    	} else if(status.getMoves()>=objective.getMaxMoves()) {
    		return GameResult.OUT_OF_MOVES;
    	}
    	return GameResult.STILL_PLAYING;
    }    

    private final boolean isCompleted() {
        assertCurrentStageSet();
        Objective objective = getObjective();
        Challenge challenge = objective.getChallenge().get();
        final Status status = getCurrentScore();
        return status.getColors(CandyColors.BLUE)>=challenge.getBlueToDestroy() && 
                status.getColors(CandyColors.RED)>=challenge.getRedToDestroy() && 
                status.getColors(CandyColors.YELLOW)>=challenge.getYellowToDestroy() && 
                status.getTypes(StatsTypes.FRECKLES)>=challenge.getFrecklesToFarm() && 
                status.getTypes(StatsTypes.STRIPED)>=challenge.getStripedToFarm() &&
                status.getTypes(StatsTypes.WRAPPED)>=challenge.getWrappedToFarm() &&
                ((controller.getJelly().isPresent() && getCurrentScore().isJellyDestroyed()) || (controller.getJelly().isEmpty()));
    }
    
    public final boolean hasNextStage() {
        return itStage.hasNext();
    }
    
    public final void nextStage() {
        if(hasNextStage()) {
            currentStage = Optional.of(itStage.next());
        } else {
            currentStage = Optional.empty();
        }
    }

    public final boolean move(final Point2D first, final Point2D second) {
        assertCurrentStageSet();
    	return currentStage.get().move(first, second);
    }

    public final Status getCurrentScore() {
        assertCurrentStageSet();
        return currentStage.get().getCurrentScore();
    }

    public final Map<Point2D, Optional<Candy>> getGrid() {
        assertCurrentStageSet();
        return currentStage.get().getGrid();
    }
    
    public final List<Point2D> getHint() {
        assertCurrentStageSet();
        return currentStage.get().getHint();
    }

    public final Optional<String> getStartingMessage() {
        assertCurrentStageSet();
        return currentStage.get().getStartingMessage();
    }

    public final Optional<String> getEndingMessage() {
        assertCurrentStageSet();
        return currentStage.get().getEndingMessage();
    }

    public final void consumeRemainingMoves() {
    	currentStage.get().consumeRemainingMoves();
    }
    
    public final Optional<Map<Point2D, Integer>> getJelly(){
    	return currentStage.get().getJelly();
    }
    
    public final boolean mutateCandy(final Point2D cord, final Candy cnd) {
        return currentStage.get().mutateCandy(cord, cnd);
    }
}
