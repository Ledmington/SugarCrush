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

	private final ImageIcon loadImage(final Candy cnd) {

		final URL imageUrl = ClassLoader.getSystemResource(
				"candyImages/" + cnd.getType().name() + "_" + cnd.getColor().name() + ".jpeg");
		return new ImageIcon(imageUrl);
	}

	public final ImageIcon getCandyImage(final Candy candy) {
		// Lazy update
		if (!this.candyImages.containsKey(candy)) {
			this.candyImages.put(candy, this.loadImage(candy));
		}
		return this.candyImages.get(candy);
	}

	public final int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((candyImages == null) ? 0 : candyImages.hashCode());
		return result;
	}

	public final boolean equals(final Object obj) {
		if (this == obj) return true;
		if (obj == null) return false;
		if (getClass() != obj.getClass()) return false;
		ImageManagerImpl other = (ImageManagerImpl) obj;
		if (candyImages == null) {
			if (other.candyImages != null) return false;
		} else if (!candyImages.equals(other.candyImages)) return false;
		return true;
	}

	public final String toString() {
		return "CandyImageManager [candyImages=" + candyImages + "]";
	}
}
