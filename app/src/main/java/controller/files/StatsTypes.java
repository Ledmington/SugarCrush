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
package controller.files;

import java.util.Objects;

/**
 * Enum of all the stats types, with their description to show in the view
 *
 * @author Emanuele Lamagna
 */
public enum StatsTypes {
	RED("Red destroyed"),
	YELLOW("Yellow destroyed"),
	BLUE("Blue destroyed"),
	PURPLE("Purple destroyed"),
	GREEN("Green destroyed"),
	ORANGE("Orange destroyed"),
	FRECKLES("Freckles farmed"),
	STRIPED("Striped farmed"),
	WRAPPED("Wrapped farmed"),
	CHOCOLATE("Chocolate destroyed"),
	MONEY("Total money"),
	TOTAL_SCORE("Total score"),
	LEVEL_1_SCORE("Level 1 top score"),
	LEVEL_2_SCORE("Level 2 top score"),
	LEVEL_3_SCORE("Level 3 top score"),
	LEVEL_4_SCORE("Level 4 top score"),
	LEVEL_5_SCORE("Level 5 top score"),
	LEVEL_6_SCORE("Level 6 top score"),
	LEVEL_7_SCORE("Level 7 top score"),
	LEVEL_8_SCORE("Level 8 top score"),
	LEVEL_9_SCORE("Level 9 top score"),
	LEVEL_10_SCORE("Level 10 top score");

	private final String description;

	StatsTypes(final String desc) {
		Objects.requireNonNull(desc);
		if (desc.isBlank()) {
			throw new IllegalArgumentException("Empty description.");
		}
		this.description = desc;
	}

	/**
	 * Getter of the description of the element
	 *
	 * @return the description of an element
	 */
	public final String getDescription() {
		return this.description;
	}
}
