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

import javax.swing.ImageIcon;

import model.game.grid.candies.Candy;

/**
 * Implementation of ImageManager
 *
 * @author Filippo Barbari
 */
public final class ImageManagerImpl implements ImageManager {

	private final Map<Candy, ImageIcon> candyImages = new HashMap<>();

	public ImageManagerImpl() {
		super();
	}

	private ImageIcon loadImage(final Candy cnd) {

		final URL imageUrl = ClassLoader.getSystemResource(
				"candyImages/" + cnd.getType().name() + "_" + cnd.getColor().name() + ".jpeg");
		return new ImageIcon(imageUrl);
	}

	public ImageIcon getCandyImage(final Candy candy) {
		// Lazy update
		if (!this.candyImages.containsKey(candy)) {
			this.candyImages.put(candy, this.loadImage(candy));
		}
		return this.candyImages.get(candy);
	}

	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + candyImages.hashCode();
		return result;
	}

	public boolean equals(final Object obj) {
		if (this == obj) return true;
		if (obj == null) return false;
		if (!(obj instanceof final ImageManagerImpl other)) return false;
		return candyImages.equals(other.candyImages);
	}

	public String toString() {
		return "CandyImageManager [candyImages=" + candyImages + "]";
	}
}
