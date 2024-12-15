package model.game.level;

/**
 * An interface to manage all available {@link Level}s.
 * 
 * @author Filippo Barbari
 *
 */
public interface LevelsManager {
    
    /**
     * @param levelNumber
     *          The number of level to be returned.
     *          
     * @return The level corresponding to the given number.
     * 
     * @throws IllegalStateException
     *          If a level with the given number doesn't exist
     */
    Level getLevel(final int levelNumber);
    
    /**
     * @return
     *          The {@link Level} representing the tutorial.
     */
    Level getTutorial();
    
    /**
     * @return The number of available levels excluding tutorial.
     */
    int getNumLevels();

}
