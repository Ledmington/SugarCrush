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
package controller.image;

import javax.swing.ImageIcon;

import model.game.grid.candies.Candy;

/**
 * Manages the usage of candies' images in memory.
 *
 * @author Filippo Barbari
 */
public interface ImageManager {

	/**
	 * Given a {@link Candy} it return the image of that {@link Candy}.
	 *
	 * @param candy The candy describing the image you need.
	 * @return An {@link ImageIcon} of the {@link Candy} passed.
	 */
	ImageIcon getCandyImage(final Candy candy);
}
