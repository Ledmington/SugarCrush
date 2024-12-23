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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

import controller.Controller;
import model.game.grid.GridManagerImpl;
import model.game.grid.candies.Candy;
import model.game.grid.candies.CandyColors;
import model.game.grid.candies.CandyFactory;
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

	public StageBuilder setDimensions(final int width, final int height) {
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

	public StageBuilder setController(final Controller controller) {
		this.controller = Optional.of(Objects.requireNonNull(controller));
		return this;
	}

	public StageBuilder setEmptyCells(final Set<Point2D> positions) {
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

	public StageBuilder setObjective(final Objective newObjective) {
		check(alreadyBuilt, "This stage has already been built.");
		this.objective = Optional.of(Objects.requireNonNull(newObjective));

		return this;
	}

	public StageBuilder addChocolatePosition(final Point2D chocolatePosition) {
		check(alreadyBuilt, "This stage has already been built.");
		Objects.requireNonNull(chocolatePosition);
		if (this.grid.containsKey(chocolatePosition) == false) {
			throw new IllegalArgumentException("Grid does not contain this position.");
		}

		this.chocolate.add(chocolatePosition);

		return this;
	}

	public StageBuilder addJelly() {
		check(alreadyBuilt, "This stage has already been built.");
		this.jelly = true;

		return this;
	}

	public StageBuilder addAvailableColor(final CandyColors newColor) {
		check(alreadyBuilt, "This stage has already been built.");
		Objects.requireNonNull(newColor);

		this.colors.add(newColor);

		return this;
	}

	public StageBuilder setCandies(final Map<Point2D, Candy> candies) {
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

	public StageBuilder setStartingMessage(final String startMsg) {
		check(alreadyBuilt, "This stage has already been built.");
		this.startingMessage = Optional.of(Objects.requireNonNull(startMsg));
		return this;
	}

	public StageBuilder setEndingMessage(final String endMsg) {
		check(alreadyBuilt, "This stage has already been built.");
		this.endingMessage = Optional.of(Objects.requireNonNull(endMsg));
		return this;
	}

	private void check(final boolean condition, final String msg) {
		if (condition) {
			throw new IllegalStateException(msg);
		}
	}

	public Stage build() {
		check(alreadyBuilt, "Can't build the same Stage twice.");

		check(grid.isEmpty(), "Stage grid empty.");

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

		final List<CandyColors> colorList = new ArrayList<>(colors);

		// Adding chocolate to map
		chocolate.forEach(p -> grid.put(p, Optional.of(CandyFactory.getChocolate())));

		return new StageImpl(
				new GridManagerImpl(
						controller.orElseThrow(), grid, new StatusImpl(controller.orElseThrow()), colorList, jelly),
				objective.orElseThrow(),
				startingMessage,
				endingMessage);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int h = 1;
		h = prime * h + (alreadyBuilt ? 1 : 0);
		h = prime * h + chocolate.hashCode();
		h = prime * h + colors.hashCode();
		h = prime * h + controller.hashCode();
		h = prime * h + endingMessage.hashCode();
		h = prime * h + grid.hashCode();
		h = prime * h + (jelly ? 1 : 0);
		h = prime * h + objective.hashCode();
		h = prime * h + startingMessage.hashCode();
		return h;
	}

	@Override
	public boolean equals(final Object other) {
		if (!(other instanceof final StageBuilderImpl sbi)) {
			return false;
		}
		return jelly == sbi.jelly
				&& alreadyBuilt == sbi.alreadyBuilt
				&& Objects.equals(grid, sbi.grid)
				&& Objects.equals(colors, sbi.colors)
				&& Objects.equals(chocolate, sbi.chocolate)
				&& Objects.equals(objective, sbi.objective)
				&& Objects.equals(startingMessage, sbi.startingMessage)
				&& Objects.equals(endingMessage, sbi.endingMessage)
				&& Objects.equals(controller, sbi.controller);
	}

	@Override
	public String toString() {
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
