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
package model.game.level.stage;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import controller.Controller;
import model.game.grid.GridManagerImpl;
import model.game.grid.candies.Candy;
import model.game.grid.candies.CandyColors;
import model.game.grid.candies.CandyFactory;
import model.game.grid.candies.CandyFactoryImpl;
import model.objectives.Objective;
import model.score.StatusImpl;
import utils.Point2D;

/**
 * A basic implementation of {@link StageBuilder}.
 *
 * @author Filippo Barbari
 */
public final class StageBuilderImpl implements StageBuilder {

	private final Map<Point2D, Optional<Candy>> grid = new HashMap<>();
	private final Set<CandyColors> colors = new HashSet<>();
	private final Set<Point2D> chocolate = new HashSet<>();
	private boolean jelly = false;
	private Optional<Objective> objective = Optional.empty();
	private Optional<String> startingMessage = Optional.empty();
	private Optional<String> endingMessage = Optional.empty();
	private Optional<Controller> controller = Optional.empty();

	private boolean alreadyBuilt = false;

	public StageBuilderImpl() {
		super();
	}

	public final StageBuilder setDimensions(final int width, final int height) {
		check(alreadyBuilt, "This stage has already been built.");
		if (width <= 0 || height <= 0) {
			throw new IllegalArgumentException("Level's height and width must be at least one.");
		}

		for (int x = 0; x < width; x++) {
			for (int y = 0; y < height; y++) {
				this.grid.put(new Point2D(x, y), Optional.empty());
			}
		}

		return this;
	}

	public final StageBuilder setController(final Controller controller) {
		this.controller = Optional.of(Objects.requireNonNull(controller));
		return this;
	}

	public final StageBuilder setEmptyCells(final Set<Point2D> positions) {
		check(alreadyBuilt, "This stage has already been built.");
		if (positions == null) {
			throw new NullPointerException("Given Set of points cannot be null.");
		}
		if (this.grid.isEmpty()) {
			throw new IllegalStateException("Grid not set.");
		}

		for (Point2D p : positions) {
			this.grid.remove(p);
		}

		return this;
	}

	public final StageBuilder setObjective(final Objective newObjective) {
		check(alreadyBuilt, "This stage has already been built.");
		this.objective = Optional.of(Objects.requireNonNull(newObjective));

		return this;
	}

	public final StageBuilder addChocolatePosition(final Point2D chocolatePosition) {
		check(alreadyBuilt, "This stage has already been built.");
		Objects.requireNonNull(chocolatePosition);
		if (this.grid.containsKey(chocolatePosition) == false) {
			throw new IllegalArgumentException("Grid does not contain this position.");
		}

		this.chocolate.add(chocolatePosition);

		return this;
	}

	public final StageBuilder addJelly() {
		check(alreadyBuilt, "This stage has already been built.");
		this.jelly = true;

		return this;
	}

	public final StageBuilder addAvailableColor(final CandyColors newColor) {
		check(alreadyBuilt, "This stage has already been built.");
		Objects.requireNonNull(newColor);

		this.colors.add(newColor);

		return this;
	}

	public final StageBuilder setCandies(final Map<Point2D, Candy> candies) {
		check(alreadyBuilt, "This stage has already been built.");
		Objects.requireNonNull(candies);

		for (Point2D p : candies.keySet()) {
			if (!this.grid.containsKey(p)) {
				throw new IllegalArgumentException("Can't set candies in unexisting positions.");
			}
			if (this.grid.get(p).isPresent()) {
				throw new IllegalArgumentException("This position already contains a Candy.");
			}

			this.grid.put(p, Optional.of(Objects.requireNonNull(candies.get(p))));
			this.colors.add(candies.get(p).getColor());
		}

		return this;
	}

	public final StageBuilder setStartingMessage(final String startMsg) {
		check(alreadyBuilt, "This stage has already been built.");
		this.startingMessage = Optional.of(Objects.requireNonNull(startMsg));
		return this;
	}

	public final StageBuilder setEndingMessage(final String endMsg) {
		check(alreadyBuilt, "This stage has already been built.");
		this.endingMessage = Optional.of(Objects.requireNonNull(endMsg));
		return this;
	}

	private final void check(final boolean condition, final String msg) {
		if (condition) {
			throw new IllegalStateException(msg);
		}
	}

	public final Stage build() {
		check(alreadyBuilt, "Can't build the same Stage twice.");

		check(grid.keySet().isEmpty(), "Stage grid empty.");

		check(colors.isEmpty(), "No colors set.");

		check(grid.keySet().equals(chocolate), "Grid can't be only chocolate.");

		check(jelly && !chocolate.isEmpty(), "Can't have chocolate and jelly in the same level.");

		check(
				grid.keySet().stream()
						.anyMatch(p -> chocolate.contains(p) && grid.get(p).isPresent()),
				"Can't have a candy and chocolate in the same position.");

		check(objective.isEmpty(), "No objective set.");

		check(controller.isEmpty(), "No controller set.");

		alreadyBuilt = true;

		List<CandyColors> colorList = colors.stream().collect(Collectors.toList());

		CandyFactory cf = new CandyFactoryImpl();

		// Adding chocolate to map
		chocolate.forEach(p -> grid.put(p, Optional.of(cf.getChocolate())));

		return new StageImpl(
				new GridManagerImpl(controller.get(), grid, new StatusImpl(controller.get()), colorList, jelly),
				objective.get(),
				startingMessage,
				endingMessage);
	}

	@Override
	public final int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (alreadyBuilt ? 1231 : 1237);
		result = prime * result + ((chocolate == null) ? 0 : chocolate.hashCode());
		result = prime * result + ((colors == null) ? 0 : colors.hashCode());
		result = prime * result + ((controller == null) ? 0 : controller.hashCode());
		result = prime * result + ((endingMessage == null) ? 0 : endingMessage.hashCode());
		result = prime * result + ((grid == null) ? 0 : grid.hashCode());
		result = prime * result + (jelly ? 1231 : 1237);
		result = prime * result + ((objective == null) ? 0 : objective.hashCode());
		result = prime * result + ((startingMessage == null) ? 0 : startingMessage.hashCode());
		return result;
	}

	@Override
	public final boolean equals(final Object obj) {
		if (this == obj) return true;
		if (obj == null) return false;
		if (getClass() != obj.getClass()) return false;
		StageBuilderImpl other = (StageBuilderImpl) obj;
		if (alreadyBuilt != other.alreadyBuilt) return false;
		if (chocolate == null) {
			if (other.chocolate != null) return false;
		} else if (!chocolate.equals(other.chocolate)) return false;
		if (colors == null) {
			if (other.colors != null) return false;
		} else if (!colors.equals(other.colors)) return false;
		if (controller == null) {
			if (other.controller != null) return false;
		} else if (!controller.equals(other.controller)) return false;
		if (endingMessage == null) {
			if (other.endingMessage != null) return false;
		} else if (!endingMessage.equals(other.endingMessage)) return false;
		if (grid == null) {
			if (other.grid != null) return false;
		} else if (!grid.equals(other.grid)) return false;
		if (jelly != other.jelly) return false;
		if (objective == null) {
			if (other.objective != null) return false;
		} else if (!objective.equals(other.objective)) return false;
		if (startingMessage == null) {
			if (other.startingMessage != null) return false;
		} else if (!startingMessage.equals(other.startingMessage)) return false;
		return true;
	}

	@Override
	public final String toString() {
		return "StageBuilderImpl [grid=" + grid + ", colors="
				+ colors + ", chocolate="
				+ chocolate + ", jelly="
				+ jelly + ", objective="
				+ objective + ", startingMessage="
				+ startingMessage + ", endingMessage="
				+ endingMessage + ", controller="
				+ controller + ", alreadyBuilt="
				+ alreadyBuilt + "]";
	}
}
