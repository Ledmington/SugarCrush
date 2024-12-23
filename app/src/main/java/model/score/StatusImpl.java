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

import java.util.EnumMap;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Stream;

import controller.Controller;
import controller.files.StatsTypes;
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
	private final Map<CandyColors, Integer> colorsMap = new EnumMap<>(CandyColors.class);

	// a map with all the types in the keyset: the values are the candies destroyed of that type
	private final Map<CandyTypes, Integer> typesMap = new EnumMap<>(CandyTypes.class);

	private final Controller controller;
	private boolean jellyDestroyed = false;
	private int totalLevelScore = 0;
	private int levelMoney = 0;
	private int moves = 0;
	private boolean levelCompleted = false;

	public StatusImpl(final Controller controller) {
		super();
		this.controller = Objects.requireNonNull(controller);

		for (final CandyColors cc : CandyColors.values()) {
			colorsMap.put(cc, 0);
		}

		for (final CandyTypes ct : CandyTypes.values()) {
			typesMap.put(ct, 0);
		}
	}

	@Override
	public void update(final Shapes shape) {
		Objects.requireNonNull(shape);
		this.totalLevelScore = this.totalLevelScore
				+ Ratios.DEF.get()
						* (shape.getCoordinates().getRelativeCoordinates().size() + 1);
		this.levelMoney = this.totalLevelScore / Ratios.MONEY.get();
		this.typesMap.put(shape.getCandyType(), this.typesMap.get(shape.getCandyType()) + 1);
	}

	@Override
	public void update(final Candy candy) {
		Objects.requireNonNull(candy);
		Stream.of(Ratios.values())
				.filter(ratio -> ratio.name().equals(candy.getType().name()))
				.forEach(ratio -> this.totalLevelScore = this.totalLevelScore + ratio.get());
		this.levelMoney = this.totalLevelScore / Ratios.MONEY.get();
		this.colorsMap.put(candy.getColor(), this.colorsMap.get(candy.getColor()) + 1);
	}

	@Override
	public void setJelly() {
		if (controller.getJelly().isPresent() && this.checkJelly()) {
			this.jellyDestroyed = true;
		}
	}

	@Override
	public int getColors(final CandyColors color) {
		Objects.requireNonNull(color);
		return this.colorsMap.get(color);
	}

	@Override
	public int getTypes(final StatsTypes type) {
		Objects.requireNonNull(type);
		return switch (type) {
			case FRECKLES -> this.typesMap.get(CandyTypes.FRECKLES);
			case STRIPED -> this.typesMap.get(CandyTypes.STRIPED_HORIZONTAL)
					+ this.typesMap.get(CandyTypes.STRIPED_VERTICAL);
			case WRAPPED -> this.typesMap.get(CandyTypes.WRAPPED);
			case CHOCOLATE -> this.typesMap.get(CandyTypes.CHOCOLATE);
			default -> throw new IllegalArgumentException("Type not freckles, striped, wrapped or chocolate");
		};
	}

	@Override
	public boolean isJellyDestroyed() {
		return this.jellyDestroyed;
	}

	@Override
	public int getScore() {
		return this.totalLevelScore;
	}

	@Override
	public void updateMoves() {
		this.moves++;
	}

	@Override
	public int getMoves() {
		return this.moves;
	}

	@Override
	public int getMoney() {
		return this.levelMoney;
	}

	@Override
	public boolean isCompleted() {
		return this.levelCompleted;
	}

	@Override
	public void complete() {
		this.levelCompleted = true;
	}

	@Override
	public void isFirstTime(final boolean firstTime) {
		if (!firstTime) {
			this.levelMoney = this.getMoney() / Ratios.REDUCE_MONEY.get();
		}
	}

	// checks if the jelly is present or not in the grid
	private boolean checkJelly() {
		for (final int c : controller.getJelly().orElseThrow().values()) {
			if (c != 0) {
				return false;
			}
		}
		return true;
	}
}
