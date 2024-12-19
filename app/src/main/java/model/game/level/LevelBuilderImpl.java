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
package model.game.level;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import controller.Controller;
import model.game.level.stage.Stage;
import model.score.Status;

/**
 * A basic implementation of {@link LevelBuilder}.
 *
 * @author Filippo Barbari
 */
public final class LevelBuilderImpl implements LevelBuilder {

	private final List<Stage> stages = new LinkedList<>();
	private Optional<Controller> controller = Optional.empty();

	private boolean alreadyBuilt = false;

	public LevelBuilderImpl() {
		super();
	}

	public LevelBuilder addStage(final Stage newStage) {
		check(alreadyBuilt, "Can't call any method if already built.");
		stages.add(Objects.requireNonNull(newStage));
		return this;
	}

	public LevelBuilder setController(final Controller controller) {
		check(alreadyBuilt, "Can't call any method if already built.");
		this.controller = Optional.of(Objects.requireNonNull(controller));
		return this;
	}

	private void check(final boolean condition, final String msg) {
		if (condition) {
			throw new IllegalStateException(msg);
		}
	}

	public Level build() {
		check(alreadyBuilt, "Can't build the same Level twice.");

		check(stages.isEmpty(), "Can't build Level without any Stage.");

		check(controller.isEmpty(), "No controller set.");

		boolean result = false;
		final Iterator<Stage> it = stages.iterator();
		final Status score = it.next().getCurrentScore();
		while (it.hasNext()) {
			if (score == it.next()) {
				result = true;
				break;
			}
		}
		check(result, "All stages in the same level must use different Status.");

		this.alreadyBuilt = true;

		return new LevelImpl(controller.orElseThrow(), stages);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (alreadyBuilt ? 1231 : 1237);
		result = prime * result + controller.hashCode();
		result = prime * result + stages.hashCode();
		return result;
	}

	@Override
	public boolean equals(final Object obj) {
		if (this == obj) return true;
		if (obj == null) return false;
		if (!(obj instanceof final LevelBuilderImpl other)) return false;
		if (alreadyBuilt != other.alreadyBuilt) return false;
		if (controller.isEmpty()) {
			if (other.controller.isPresent()) return false;
		} else if (!controller.equals(other.controller)) return false;
		return stages.equals(other.stages);
	}

	@Override
	public String toString() {
		return "LevelBuilderImpl [stages=" + stages + ", controller="
				+ controller + ", alreadyBuilt="
				+ alreadyBuilt + "]";
	}
}
