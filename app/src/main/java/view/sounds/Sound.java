package view.sounds;

/**
 * An interface that allows to play the sounds of the game
 * 
 * @author Emanuele Lamagna
 *
 */
public interface Sound {

	/**
	 * Plays the sound passed by parameter (with his name) 
	 * 
	 * @param sound  the sound to be played
	 * @throws IOException  if there are problems with the I/O (like finding the audio)
	 * @throws LineUnavailableException  if the line cannot be oper because it's unavailable
	 * @throws UnsupportedAudioFileException  if the format of the audio is not supported
	 */
	void playSound(String sound);
	
	/**
	 * If the sound is on, sets it off, otherwise the opposite
	 */
	void setSoundEnabled();
	
}
