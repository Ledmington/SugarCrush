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

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

import model.game.grid.GridManager;
import model.game.grid.candies.Candy;
import model.objectives.Objective;
import model.score.Status;
import utils.Point2D;

public final class StageImpl implements Stage {

	private final GridManager manager;
	private final Objective objective;
	private final Optional<String> startingMessage;
	private final Optional<String> endingMessage;

	public StageImpl(
			final GridManager manager,
			final Objective objective,
			final Optional<String> startMsg,
			final Optional<String> endMsg) {
		super();
		this.manager = Objects.requireNonNull(manager);
		this.objective = Objects.requireNonNull(objective);
		this.startingMessage = Objects.requireNonNull(startMsg);
		this.endingMessage = Objects.requireNonNull(endMsg);
	}

	public final Optional<String> getStartingMessage() {
		return this.startingMessage;
	}

	public final Map<Point2D, Optional<Candy>> getGrid() {
		return this.manager.getGrid();
	}

	public final Optional<String> getEndingMessage() {
		return this.endingMessage;
	}

	public final Objective getObjective() {
		return this.objective;
	}

	public final List<Point2D> getHint() {
		return this.manager.getHint();
	}

	public final boolean move(final Point2D first, final Point2D second) {
		return this.manager.move(first, second);
	}

	public final Status getCurrentScore() {
		return this.manager.getCurrentScore();
	}

	public final void consumeRemainingMoves() {
		this.manager.consumeRemainingMoves();
	}

	public final Optional<Map<Point2D, Integer>> getJelly() {
		return this.manager.getJelly();
	}

	public final boolean mutateCandy(final Point2D cord, final Candy cnd) {
		return manager.mutateCandy(cord, cnd);
	}
}
