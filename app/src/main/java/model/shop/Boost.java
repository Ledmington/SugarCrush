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
package model.shop;

import model.game.grid.candies.*;

/** @author Davide Degli Esposti */
public final class Boost {

	private final String boostName; // the name of the boost (wrapped,freckles,etc)
	private final int price; // the price of the boost in the shop
	private final Candy candy; // the type of boost

	/**
	 * the constructor of the object Boost
	 *
	 * @param price the price of the boost
	 * @param candy the candy that is represented by the object Boost
	 */
	public Boost(final String name, final int price, final Candy candy) {
		this.boostName = name;
		this.price = price;
		this.candy = candy;
	}

	/** @return the name of the boost */
	public String getName() {
		return this.boostName;
	}
	/** @return the price of the boost */
	public Integer getPrice() {
		return this.price;
	}

	/** @return the type of the boost */
	public Candy getCandy() {
		return this.candy;
	}
}
