package model.game.level.stage;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

import model.score.Status;
import model.game.grid.candies.Candy;
import model.game.grid.GridManager;
import model.objectives.Objective;
import utils.Point2D;

public final class StageImpl implements Stage {
    
    private final GridManager manager;
    private final Objective objective;
    private final Optional<String> startingMessage;
    private final Optional<String> endingMessage;
    
    public StageImpl(final GridManager manager, final Objective objective, final Optional<String> startMsg, final Optional<String> endMsg) {
        super();
        this.manager = Objects.requireNonNull(manager);
        this.objective = Objects.requireNonNull(objective);
        this.startingMessage = Objects.requireNonNull(startMsg);
        this.endingMessage = Objects.requireNonNull(endMsg);
    }

    public final Optional<String> getStartingMessage() {
        return this.startingMessage;
    }

    public final Map<Point2D, Optional<Candy>> getGrid() {
        return this.manager.getGrid();
    }

    public final Optional<String> getEndingMessage() {
        return this.endingMessage;
    }

    public final Objective getObjective() {
        return this.objective;
    }

    public final List<Point2D> getHint() {
        return this.manager.getHint();
    }

    public final boolean move(final Point2D first, final Point2D second) {
        return this.manager.move(first, second);
    }
    
    public final Status getCurrentScore() {
        return this.manager.getCurrentScore();
    }

    public final void consumeRemainingMoves() {
    	this.manager.consumeRemainingMoves();
    }
    
    public final Optional<Map<Point2D, Integer>> getJelly() {
    	return this.manager.getJelly();
    }
    
    public final boolean mutateCandy(final Point2D cord, final Candy cnd) {
        return manager.mutateCandy(cord, cnd);
    }
}
