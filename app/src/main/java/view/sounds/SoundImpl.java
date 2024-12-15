package view.sounds;

import java.net.URL;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

/**
 * A class that implements {@link Sound}
 * 
 * @author Emanuele Lamagna
 *
 */
public final class SoundImpl implements Sound {
	
	private boolean soundEnabled = true;
	
	public final void playSound(String sound) {
		if(this.soundEnabled) {
			try {
				final Clip clip = AudioSystem.getClip();
				final URL soundUrl = ClassLoader.getSystemResource("sounds/" + sound + ".wav");
				final AudioInputStream inputStream = AudioSystem.getAudioInputStream(soundUrl);
		        clip.open(inputStream);
		        clip.start();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	public final void setSoundEnabled() {
		this.soundEnabled = !this.soundEnabled;
	}
	
}
