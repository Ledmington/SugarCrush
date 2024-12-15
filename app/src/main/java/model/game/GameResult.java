package model.game;

/**
 * Contains the possible results of a game.
 * @author Filippo Barbari
 *
 */
public enum GameResult {
    
    /**
     * The "normal" victory.
     */
    MIN_SCORE_REACHED("Congrats! You reached the minimum score!"),
    
    /**
     * Obtained if (and only if) the level's minimum score is reached and
     * the relative challenge is fulfilled.
     */
    CHALLENGE_COMPLETED("You're a champion! You completed the challenge!"),
    
    /**
     * Happens when the number of moves has reached zero before reaching the minimum score.
     */
    OUT_OF_MOVES("Oh no! You just run out of moves without completing the objective."),
    
    /**
     * Happens if (and only if) a game becomes unplayable because of an enemy/obstacle.
     * (example: the chocolate has filled the entire map)
     */
    ENEMY_WIN("What a disaster! Chocolate has filled the entire grid =( ."),
    
    /**
     * Happens when the level is not ended
     */
    STILL_PLAYING(null);
    
    private String description;
    
    private GameResult(final String desc){
        this.description = desc;
    }
    
    public final String getDescription() {
        return this.description;
    }

}
