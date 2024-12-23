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
package model.achievement;

import java.util.Map;
import java.util.function.Predicate;

import controller.Controller;

/** @author Davide Degli Esposti */
public interface AchievementBuilder {

	/**
	 * Sets the title of the achievement, can't be null
	 *
	 * @param title the title of the goal
	 * @return this instance of GoalBuilder
	 * @throws IllegalStateException If title is a void string.
	 */
	AchievementBuilder title(final String title);

	/**
	 * Sets the description of the achievement, can't be null
	 *
	 * @param description the description of the goal
	 * @return this instance of GoalBuilder
	 * @throws IllegalStateException If description is a void string.
	 */
	AchievementBuilder description(final String description);

	/**
	 * @param method the method for check if a goal is reached
	 * @return this instance of GoalBuilder
	 * @throws NullPointerException If method is null.
	 */
	AchievementBuilder check(final Predicate<Map<String, Object>> method);

	/**
	 * Allows to set the {@link Controller}.
	 *
	 * @param controller the controller passed
	 * @return this instance of GoalBuilder
	 * @throws NullPointerException If controller is null.
	 */
	AchievementBuilder setController(final Controller controller);

	/**
	 * If everything is OK, it creates the goal
	 *
	 * @return a new object Goal
	 * @throws IllegalStateException If isBuilt is true NullPointerException if some variable is not set
	 */
	Achievement build();
}
