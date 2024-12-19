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
package view.sounds;

/**
 * An interface that allows to play the sounds of the game
 *
 * @author Emanuele Lamagna
 */
public interface Sound {

	/**
	 * Plays the sound passed by parameter (with his name)
	 *
	 * @param sound the sound to be played
	 */
	void playSound(final String sound);

	/** If the sound is on, sets it off, otherwise the opposite */
	void setSoundEnabled();
}
