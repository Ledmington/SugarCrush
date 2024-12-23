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

import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import javax.swing.ImageIcon;

import model.game.grid.candies.Candy;

/** @author Filippo Barbari */
public final class ImageManager {

	private static final Map<Candy, ImageIcon> candyImages = new HashMap<>();

	private ImageManager() {}

	private static String getCandyImageFileName(final Candy c) {
		return "candyImages/" + c.getType().name() + "_" + c.getColor().name() + ".jpeg";
	}

	private static ImageIcon loadImage(final Candy cnd) {
		Objects.requireNonNull(cnd);
		final URL imageUrl = ClassLoader.getSystemResource(getCandyImageFileName(cnd));
		return new ImageIcon(imageUrl);
	}

	/**
	 * Given a {@link Candy} it returns the image of that {@link Candy}.
	 *
	 * @param candy The candy describing the image you need.
	 * @return An {@link ImageIcon} of the {@link Candy} passed.
	 */
	public static ImageIcon getCandyImage(final Candy candy) {
		// Lazy update
		if (!candyImages.containsKey(candy)) {
			candyImages.put(candy, loadImage(candy));
		}
		return candyImages.get(candy);
	}
}
