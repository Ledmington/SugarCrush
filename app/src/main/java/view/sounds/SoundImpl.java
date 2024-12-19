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

import java.io.IOException;
import java.net.URL;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

/**
 * A class that implements {@link Sound}
 *
 * @author Emanuele Lamagna
 */
public final class SoundImpl implements Sound {

	private boolean soundEnabled = false;

	public void playSound(final String sound) {
		if (this.soundEnabled) {
			try {
				final Clip clip = AudioSystem.getClip();
				final URL soundUrl = ClassLoader.getSystemResource("sounds/" + sound + ".wav");
				final AudioInputStream inputStream = AudioSystem.getAudioInputStream(soundUrl);
				clip.open(inputStream);
				clip.start();
			} catch (final UnsupportedAudioFileException | IOException | LineUnavailableException e) {
				throw new RuntimeException(e);
			}
		}
	}

	public void setSoundEnabled() {
		this.soundEnabled = !this.soundEnabled;
	}
}
