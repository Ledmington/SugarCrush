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
import java.util.Objects;
import java.util.Optional;
import java.util.function.Predicate;

import controller.Controller;

/** @author Davide Degli Esposti */
public final class AchievementBuilderImpl implements AchievementBuilder {

	private Optional<String> title = Optional.empty();
	private Optional<String> descr = Optional.empty();
	private Optional<Predicate<Map<String, Object>>> method = Optional.empty();
	private boolean alreadyBuilt = false;
	private Optional<Controller> controller = Optional.empty();

	@Override
	public AchievementBuilder title(final String title) {
		Objects.requireNonNull(title);
		if (title.isBlank()) {
			throw new IllegalArgumentException("Title can't be null");
		}
		this.title = Optional.of(title);
		return this;
	}

	@Override
	public AchievementBuilder description(final String description) {
		Objects.requireNonNull(description);
		if (description.isBlank()) {
			throw new IllegalArgumentException("Description can't be null");
		}
		this.descr = Optional.of(description);
		return this;
	}

	public AchievementBuilder setController(final Controller controller) {
		this.controller = Optional.of(Objects.requireNonNull(controller));
		return this;
	}

	@Override
	public Achievement build() {
		if (this.alreadyBuilt) {
			throw new IllegalStateException("Can't build the same achievement twice.");
		}

		if (this.title.isEmpty()) {
			throw new NullPointerException("Title not set.");
		}
		if (this.descr.isEmpty()) {
			throw new NullPointerException("Description not set.");
		}
		if (this.method.isEmpty()) {
			throw new NullPointerException("Method not set.");
		}
		if (this.controller.isEmpty()) {
			throw new NullPointerException("Controller not set.");
		}

		this.alreadyBuilt = true;
		return new Achievement(
				this.controller.orElseThrow(),
				this.title.orElseThrow(),
				this.descr.orElseThrow(),
				this.method.orElseThrow());
	}

	@Override
	public AchievementBuilder check(final Predicate<Map<String, Object>> method) {
		Objects.requireNonNull(method);
		this.method = Optional.of(method);
		return this;
	}
}
