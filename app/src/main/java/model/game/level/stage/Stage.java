package model.game.level.stage;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import model.score.Status;
import model.game.grid.candies.Candy;
import model.objectives.Objective;
import utils.Point2D;

/**
 * 
 * @author Filippo Barbari
 *
 */
public interface Stage {
    
    /**
     * @return
     *          The message that needs to be printed at the beginning of the {@link Stage}.
     *          If Optional.empty() is returned, no message is to be printed.
     */
    Optional<String> getStartingMessage();
    
    /**
     * @return
     *          The grid of the {@link Level} in a certain moment in the form of a Map object
     *          containing all {@link Point2D} positions and {@link Candy} in the grid.
     */
    Map<Point2D, Optional<Candy>> getGrid();
    
    /**
     * @return
     *          The message that needs to be printed after the end of the {@link Stage}.
     *          If Optional.empty() is returned, no message is to be printed.
     */
    Optional<String> getEndingMessage();
    
    /**
     * @return
     *       An {@link Objective} interface.
     */
    Objective getObjective();

    /**
     * Retrieves a list of coordinates describing a possible shape that can be formed.
     * @return A list filled with coordinates of shape.
     */
    List<Point2D> getHint();
    
    /**
     * Tells the {@link Level} to swap a specific pair of adjacent candies.
     * 
     * @param first
     *          The first candy.
     * @param second
     *          The second candy.
     *          
     * @return 
     *          true if swapping the given candies has resulted
     *               in a good move,
     *          false otherwise.
     */
    boolean move(final Point2D first, final Point2D second);
    
    /**
     * 
     * @return the score of the stage
     */
    Status getCurrentScore();

    /**
     * Consume all remaining moves, spawning random special candies and blowing them up.
     */
    void consumeRemainingMoves();
    
    /**
     * Retrieves a map with state of jelly.
     * @return A map with jelly lives.
     */
    Optional<Map<Point2D, Integer>> getJelly();
    
    /**
     * Change a candy with another candy in determined coordinates.
     * @param cord The coordinates of the candy to be mutated.
     * @param cnd The new candy to be mutated into.
     * @return False if mutation wasn't possible.
     */
    boolean mutateCandy(Point2D cord, Candy cnd);
}
