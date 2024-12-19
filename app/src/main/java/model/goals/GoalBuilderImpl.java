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
package model.goals;

import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Predicate;

import controller.Controller;

/** @author Davide Degli Esposti */
public final class GoalBuilderImpl implements GoalBuilder {

	private Optional<String> title = Optional.empty(); // is the main title of the achievement
	private Optional<String> descr = Optional.empty(); // is the short description of the achievement
	private Optional<Predicate<Map<String, Object>>> method =
			Optional.empty(); // is the method for check if the achievement is reached
	private boolean isBuilt = false; // is the flag that is true if i have already built the achievement
	private Optional<Controller> controller = Optional.empty();

	@Override
	public final GoalBuilder setTitle(final String title) {
		if (title == null || title.equals("")) {
			throw new IllegalStateException("Title can't be null");
		}
		this.title = Optional.of(title);
		return this;
	}

	@Override
	public final GoalBuilder setDescr(final String descr) {
		if (descr == null || descr.equals("")) {
			throw new IllegalStateException("Description can't be null");
		}
		this.descr = Optional.of(descr);
		return this;
	}

	public final GoalBuilder setController(final Controller controller) {
		this.controller = Optional.of(Objects.requireNonNull(controller));
		return this;
	}

	@Override
	public final Goal build() {
		if (this.isBuilt) {
			throw new IllegalStateException("Can't build the same achievement twice.");
		}
		if (this.title == null) {
			throw new NullPointerException("Title not set.");
		}

		if (this.descr == null) {
			throw new NullPointerException("Description not set.");
		}

		if (this.method == null) {
			throw new NullPointerException("Method not set.");
		}

		if (this.controller.isEmpty()) {
			throw new NullPointerException("Controller not set.");
		}
		this.isBuilt = true;
		return new Goal(this.controller.get(), this.title.get(), this.descr.get(), this.method.get());
	}

	@Override
	public final GoalBuilder setMethod(final Predicate<Map<String, Object>> method) {
		if (method == null) {
			throw new NullPointerException("The method can't be null ");
		}
		this.method = Optional.of(method);
		return this;
	}
}
