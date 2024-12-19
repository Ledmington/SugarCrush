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
package model.score;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Stream;

import controller.Controller;
import controller.files.*;
import model.game.grid.candies.Candy;
import model.game.grid.candies.CandyColors;
import model.game.grid.candies.CandyTypes;
import model.game.grid.shapes.Shapes;

/**
 * A class that implements {@link Status}
 *
 * @author Emanuele Lamagna
 */
public final class StatusImpl implements Status {

	// a map with all the colors in the keyset: the values are the candies destroyed of that color
	private final Map<CandyColors, Integer> colorsMap = new HashMap<>() {

		private static final long serialVersionUID = -3324656098490067594L;

		{
			put(CandyColors.RED, 0);
			put(CandyColors.BLUE, 0);
			put(CandyColors.YELLOW, 0);
			put(CandyColors.GREEN, 0);
			put(CandyColors.ORANGE, 0);
			put(CandyColors.PURPLE, 0);
			put(CandyColors.CHOCOLATE, 0);
			put(CandyColors.FRECKLES, 0);
		}
	};

	// a map with all the types in the keyset: the values are the candies destroyed of that type
	private final Map<CandyTypes, Integer> typesMap = new HashMap<>() {

		private static final long serialVersionUID = 1617710278091762949L;

		{
			put(CandyTypes.FRECKLES, 0);
			put(CandyTypes.STRIPED_HORIZONTAL, 0);
			put(CandyTypes.STRIPED_VERTICAL, 0);
			put(CandyTypes.WRAPPED, 0);
			put(CandyTypes.NORMAL, 0);
			put(CandyTypes.CHOCOLATE, 0);
		}
	};

	private final Controller controller;
	private boolean jellyDestroyed = false;
	private int totalLevelScore = 0;
	private int levelMoney = 0;
	private int moves = 0;
	private boolean levelCompleted = false;

	public StatusImpl(final Controller controller) {
		super();
		this.controller = Objects.requireNonNull(controller);
	}

	public final void update(final Shapes shape) {
		Objects.requireNonNull(shape);
		this.totalLevelScore = this.totalLevelScore
				+ Ratios.DEF.get()
						* (shape.getCoordinates().getRelativeCoordinates().size() + 1);
		this.levelMoney = this.totalLevelScore / Ratios.MONEY.get();
		this.typesMap.put(shape.getCandyType(), this.typesMap.get(shape.getCandyType()) + 1);
	}

	public final void update(final Candy candy) {
		Objects.requireNonNull(candy);
		Stream.of(Ratios.values())
				.filter(ratio -> ratio.name().equals(candy.getType().name()))
				.forEach(ratio -> this.totalLevelScore = this.totalLevelScore + ratio.get());
		this.levelMoney = this.totalLevelScore / Ratios.MONEY.get();
		this.colorsMap.put(candy.getColor(), this.colorsMap.get(candy.getColor()) + 1);
	}

	public final void setJelly() {
		if (controller.getJelly().isPresent() && this.checkJelly()) {
			this.jellyDestroyed = true;
		}
	}

	public final int getColors(final CandyColors color) {
		Objects.requireNonNull(color);
		return this.colorsMap.get(color);
	}

	public final int getTypes(final StatsTypes type) {
		Objects.requireNonNull(type);
		switch (type) {
			case FRECKLES:
				return this.typesMap.get(CandyTypes.FRECKLES);
			case STRIPED:
				return this.typesMap.get(CandyTypes.STRIPED_HORIZONTAL)
						+ this.typesMap.get(CandyTypes.STRIPED_VERTICAL);
			case WRAPPED:
				return this.typesMap.get(CandyTypes.WRAPPED);
			case CHOCOLATE:
				return this.typesMap.get(CandyTypes.CHOCOLATE);
			default:
				throw new IllegalArgumentException("Type not freckles, striped, wrapped or chocolate");
		}
	}

	public final boolean isJellyDestroyed() {
		return this.jellyDestroyed;
	}

	public final int getScore() {
		return this.totalLevelScore;
	}

	public final void updateMoves() {
		this.moves++;
	}

	public final int getMoves() {
		return this.moves;
	}

	public final int getMoney() {
		return this.levelMoney;
	}

	public final boolean isCompleted() {
		return this.levelCompleted;
	}

	public final void complete() {
		this.levelCompleted = true;
	}

	public final void isFirstTime(final boolean firstTime) {
		Objects.requireNonNull(firstTime);
		if (!firstTime) {
			this.levelMoney = this.getMoney() / Ratios.REDUCE_MONEY.get();
		}
	}

	// checks if the jelly is present or not in the grid
	private final boolean checkJelly() {
		for (var c : controller.getJelly().get().values()) {
			if (c != 0) {
				return false;
			}
		}
		return true;
	}
}
