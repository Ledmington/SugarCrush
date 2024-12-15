package model.game.level;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import controller.Controller;
import model.score.Status;
import model.game.level.stage.Stage;

/**
 * A basic implementation of {@link LevelBuilder}.
 * @author Filippo Barbari
 *
 */
public final class LevelBuilderImpl implements LevelBuilder {

    private final List<Stage> stages = new LinkedList<>();
    private Optional<Controller> controller = Optional.empty();

    private boolean alreadyBuilt = false;

    public LevelBuilderImpl() {
        super();
    }

    public final LevelBuilder addStage(final Stage newStage) {
        check(alreadyBuilt, "Can't call any method if already built.");
        stages.add( Objects.requireNonNull(newStage) );
        return this;
    }

    public final LevelBuilder setController(final Controller controller) {
        check(alreadyBuilt, "Can't call any method if already built.");
        this.controller = Optional.of( Objects.requireNonNull(controller) );
        return this;
    }

    private final void check(final boolean condition, final String msg) {
        if(condition) {
            throw new IllegalStateException(msg);
        }
    }

    public final Level build() {
        check(alreadyBuilt, "Can't build the same Level twice.");

        check(stages.isEmpty(), "Can't build Level without any Stage.");

        check(controller.isEmpty(), "No controller set.");

        boolean result = false;
        final Iterator<Stage> it = stages.iterator();
        final Status score = it.next().getCurrentScore();
        while(it.hasNext()) {
            if(score == it.next()) {
                result = true;
                break;
            }
        }
        check(result, "All stages in the same level must use different Status.");

        this.alreadyBuilt = true;

        return new LevelImpl(controller.get(), stages);
    }

    @Override
    public final int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + (alreadyBuilt ? 1231 : 1237);
        result = prime * result + ((controller == null) ? 0 : controller.hashCode());
        result = prime * result + ((stages == null) ? 0 : stages.hashCode());
        return result;
    }

    @Override
    public final boolean equals(final Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        LevelBuilderImpl other = (LevelBuilderImpl) obj;
        if (alreadyBuilt != other.alreadyBuilt)
            return false;
        if (controller == null) {
            if (other.controller != null)
                return false;
        } else if (!controller.equals(other.controller))
            return false;
        if (stages == null) {
            if (other.stages != null)
                return false;
        } else if (!stages.equals(other.stages))
            return false;
        return true;
    }

    @Override
    public final String toString() {
        return "LevelBuilderImpl [stages=" + stages + 
                ", controller=" + controller + 
                ", alreadyBuilt=" + alreadyBuilt + "]";
    }
}
