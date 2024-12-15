package model.game.level;

import controller.Controller;
import model.game.level.stage.Stage;

/**
 * An interface with builder pattern that allows an easier construction of a {@link Level} object.
 * Each {@link LevelBuilder} can be used to build only one {@link Level} object and then it becomes
 * no more usable.
 * 
 * @author Filippo Barbari
 */
public interface LevelBuilder {
    
    /**
     * Allows to add a new {@link Stage} to this {@link Level}.
     * Each new {@link Stage} is added at the end of the queue.
     * 
     * @param newStage
     *          The {@link Stage} that is to be added.
     *          
     * @return
     *          This instance of {@link LevelBuilder}.
     */
    LevelBuilder addStage(final Stage newStage);
    
    /**
     * Allows to set a {@link Controller} for the current {@link LevelBuilder}.
     * 
     * @return
     *       This instance of {@link LevelBuilder}.
     */
    LevelBuilder setController(final Controller controller);
    
    /**
     * Returns an object implementing the {@link Level} interface
     * with all the data inserted.
     * 
     * @throws IllegalStateException
     *          If build is called twice on the same instance of {@link LevelBuilder}
     *          or
     *          if no {@link Stage} has been added
     *          or
     *          if at least two {@link Stage}s use the same instance of {@link Status}
     *          or
     *          if no {@link Controller} has been set.
     */
    Level build();

}
