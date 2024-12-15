package model.goals;

import java.util.Map;
import java.util.function.Predicate;

import controller.Controller;

/**
 * 
 * @author Davide Degli Esposti
 *
 */
public interface GoalBuilder {

    /**
     * Sets the title of the achievement, can't be null
     * @param title  the title of the goal
     * 
     * @return this instance of GoalBuilder
     * 
     * @throws IllegalStateException
     *       If title is a void string.
     */
    GoalBuilder setTitle(final String title);

    /**
     * Sets the description of the achievement, can't be null
     * @param descr  the description of the goal
     * 
     * @return this instance of GoalBuilder
     * 
     * @throws IllegalStateException
     *       If descr is a void string.
     */
    GoalBuilder setDescr(final String descr);

    /**
     *  
     * @param method  the method for check if a goal is reached
     * 
     * @return this instance of GoalBuilder
     * 
     * @throws NullPointerException
     *       If method is null.
     */
    GoalBuilder setMethod(final Predicate<Map<String,Object>> method);

    /**
     *  Allows to set the {@link Controller}.
     * @param controller  the controller passed
     * 
     * @return this instance of GoalBuilder
     * 
     * @throws NullPointerException
     *       If controller is null.
     */
    GoalBuilder setController(final Controller controller);

    /**
     * If everything is ok, it creates the goal
     * 
     * @return a new object Goal
     * 
     * @throws IllegalStateException If isBuilt is true
     *          NullPointerException if some variable is not set
     */
    Goal build();
}

