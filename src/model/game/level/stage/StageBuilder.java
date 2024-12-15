package model.game.level.stage;

import java.util.Map;
import java.util.Set;

import controller.Controller;
import model.game.grid.candies.Candy;
import model.game.grid.candies.CandyColors;
import model.objectives.Objective;
import utils.Point2D;

/**
 * A Builder interface for {@link StageImpl}.
 * 
 * @author Filippo Barbari
 *
 */
public interface StageBuilder {
    
    /**
     * Allows to set the dimensions of the {@link Stage}'s grid.
     * 
     * @param height
     *       The height of the {@link Stage} expressed as number of cells.
     * @param width
     *       The width of the {@link Stage} expressed as number of cells.
     *       
     * @return
     *       This instance of {@link StageBuilder}.
     *       
     * @throws IllegalArgumentException
     *       If negative or zero values are passed.
     */
    StageBuilder setDimensions(final int width, final int height);
    
    /**
     * Allows to set a {@link Controller} for the current {@link StageBuilder}.
     * 
     * @return
     *       This instance of {@link StageBuilder}.
     */
    StageBuilder setController(final Controller controller);
    
    /**
     * Allows to set some specific cells of the grid as empty and unusable.
     * 
     * @param positions
     *       The Set of all the positions (x,y) to be set as empty.
     *       
     * @return
     *       This instance of {@link StageBuilder}.
     *       
     * @throws NullPointerException
     *       If positions is null.
     * @throws IllegalStateException
     *       If grid has not been set.
     */
    StageBuilder setEmptyCells(final Set<Point2D> positions);
    
    /**
     * Allows to set the {@link Stage}'s objective.
     * 
     * @param newObjective
     *        The new {@link Objective} to be set.
     *        
     * @return
     *       This instance of {@link StageBuilder}.
     */
    StageBuilder setObjective(final Objective newObjective);
    
    /**
     * Adds a new position where the chocolate needs to start.
     * 
     * @param chocolatePosition
     *          The position where chocolate needs to start.
     *          
     * @return
     *          This instance of {@link StageBuilder}.
     *          
     * @throws NullPointerException
     *          If passed chocolatePosition is null.
     * @throws IllegalArgumentException
     *          If either
     *          x coordinate of chocolatePosition is < 0 or
     *          y coordinate of chocolatePosition is < 0 or
     *          grid does not contain the passed chocolatePosition.
     */
    StageBuilder addChocolatePosition(final Point2D chocolatePosition);
    
    /**
     * Allows to set if jelly needs to be present.
     * 
     * @return
     *          This instance of {@link StageBuilder}.
     */
    StageBuilder addJelly();
    
    /**
     * Allows to add an available {@link CandyColors} for the {@link Stage}.
     * 
     * @param newColor
     *          The new available {@link CandyColors}.
     *          
     * @return
     *          This instance of {@link StageBuilder}.
     */
    StageBuilder addAvailableColor(final CandyColors newColor);
    
    /**
     * Allows to set specific candies in specific positions.
     * 
     * @param candies
     *          A Map telling which {@link Candy} is in each position.
     *          
     * @return
     *          This instance of {@link StageBuilder}.
     * 
     * @throws IllegalArgumentException
     *          If at least one position is not set
     *          or
     *          if at least one position is already mapped to a candy.
     */
    StageBuilder setCandies(final Map<Point2D, Candy> candies);
    
    /**
     * Allows to set a message that needs to be shown at the beginning of this {@link Stage}.
     * 
     * @param startMsg
     *          The message to be printed.
     *          
     * @return
     *          This instance of {@link StageBuilder}.
     */
    StageBuilder setStartingMessage(final String startMsg);
    
    /**
     * Allows to set a message that needs to be shown after the end of this {@link Stage}.
     * 
     * @param endMsg
     *          The message to be printed.
     *          
     * @return
     *          This instance of {@link StageBuilder}.
     */
    StageBuilder setEndingMessage(final String endMsg);
    
    /**
     * This call ends the {@link Stage}'s building process.
     * 
     * @return
     *          An object implementing the {@link Stage} interface.
     *          
     * @throws IllegalStateException
     *          If trying to build the same {@link Stage} twice,
     *          if {@link Stage}'s grid isn't set,
     *          if no {@link CandyColors} are set,
     *          if grid is filled with {@link CandyColors.CHOCOLATE},
     *          if both jelly and {@link CandyColors.CHOCOLATE} are present,
     *          if at least one {@link Candy} is in the same position of one {@link CandyColors.CHOCOLATE} piece,
     *          if no {@link Objective} is set,
     *          if no {@link Controller} is set.
     */
    Stage build();

}
