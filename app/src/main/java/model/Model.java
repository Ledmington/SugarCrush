package model;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import controller.files.FileTypes;
import model.score.Status;
import model.shop.Boost;
import model.game.grid.candies.Candy;
import model.game.level.Level;
import model.goals.Goal;
import model.game.GameResult;
import model.objectives.Objective;
import utils.Pair;
import utils.Point2D;

/**
 * 
 * @author Filippo Barbari
 * @author Filippo Benvenuti
 * @author Emanuele Lamagna
 * @author Davide Degli Esposti
 *
 */
public interface Model {
	
    /**
     * Start a new game.
     * @param levelIndex
     *          If empty starts the tutorial
     *          otherwise starts the requested level.
     */
    void startNewGame(final Optional<Integer> levelIndex);
    
    /**
     * @return
     *          The message that needs to be printed at the beginning of the current {@link Stage}.
     *          If Optional.empty() is returned, no message is to be printed.
     */
    Optional<String> getStartingMessage();
    
    /**
     * @return
     *          The message that needs to be printed at the end of the current {@link Stage}.
     *          If Optional.empty() is returned, no message is to be printed.
     */
    Optional<String> getEndingMessage();

    /**
     * @return
     *          true if the current {@link Stage} of the current {@link Level}
     *          is not the last {@link Stage}.
     *          false otherwise.
     */
    boolean hasNextStage();
    
    /**
     * If the current {@link Stage} isn't the last one, the next {@link Stage}
     * is set to be the current one.
     */
    void nextStage();

    /**
     * Retrieves the objective of the current level.
     * @return Objective of current level.
     */
    Objective getObjective();

    /**
     * Returns the grid of the level in a certain moment in the form of a Map object
     * containing all positions and types of the candies in the grid.
     * 
     * @return
     *       A Map in which each key is the position
     *       and each value is the candy.
     */
    Map<Point2D, Optional<Candy>> getGrid();

    /**
     * Move two adjacent candies in current game.
     * @param first One candy.
     * @param second The one to swap with.
     * @return True if movement was correct.
     */
    boolean move(final Point2D first, final Point2D second);

    /**
     * Retrieves current score of current game.
     * @return The score of the current level.
     */
    Status getCurrentScore();

    /**
     * Retrieves remaining moves of current game.
     * @return The number of remaining moves.
     */
    Integer getRemainingMoves();
    
    /**
     * @return
     *          The result of current level in the form of a GameResult enum.
     */
    GameResult getResult();
    
    /*
     * Force to close the match without saving anything.
     */
    void end();

    /**
     * Adds a new player in the list of the players.
     * 
     * @param name  the name of the player
     */
    void addPlayer(final String name);

    /**
     * Checks the values in Status and refreshes the informations of that player
     * 
     * @param name  the name of the player
     * @param status  the status of the current player
     * @param level  the number of the actual level
     */
    void setPlayerStats(final String name, final Status status, final int level);

    /**
     * Updates the informations of the player, with the received list of maps
     * 
     * @param list  the list of the players (as maps)
     * @param type  the type of file to update (boosts or stats)
     */
    void updatePlayer(final List<Map<String, Object>> list, final FileTypes type);

    /**
	 * Reads the right file to get all the players stats or boost (it depends by "type" parameter)
	 * 
	 * @param type  the type of file to get (boosts or stats)
	 * @return a list of all the players (as maps)
	 */
    List<Map<String, Object>> getPlayers(final FileTypes type);
    
    /**
	 * Removes a player from all the files
	 * 
	 * @param name  the name of the player
	 */
    void removePlayer(final String name);
    
    /**
     * @return The number of currently available levels.
     */
    int getNumLevels();
    
    /**
     * @return
     *          The current level.
     */
    Level getCurrentLevel();
    
    /**
     * Retrieves the tip for a move in game.
     * @return A list containing hint coordinates.
     */
    List<Point2D> getHint();
    
    /**
     * Consumes remaining moves spawning random special candies around, and blowing all them up.
     */
    void consumeRemainingMoves();
    
    /**
     * Retrieves the map of current jelly in the grid.
     * @return A map with jelly lives.
     */
    Optional<Map<Point2D, Integer>> getJelly();
    
    /**
     * reset the achievement of the player
     */
    void resetGoals();
    
    /**
     * 
     * @return the list of all achievements 
     */
    List<Goal> getAchievement();
    
    /**
     * 
     * @return the list of players sort by general score
     */
    List<Pair<String,Integer>> getGeneralScoreRank();
    
    /**
     * 
     * @param lvlNumber
     * @return the list of players sort by the score of the lvlNumber 
     */
    List<Pair<String,Integer>> getLevelScoreRank(int lvlNumber);
    
    /**
     * Change a candy with another candy in determined coordinates.
     * @param cord The coordinates of the candy to be mutated.
     * @param cnd The new candy to be mutated into.
     * @return False if mutation wasn't possible.
     */
    boolean mutateCandy(Point2D cord, Candy cnd);

    /**
     * 
     * @return the list of the boost on sale in the shop
     */
    List<Boost> getBoostsList();
    
    /**
     * make the player do the payment for the boost selected
     * @param name
     * @param bst
     */
    void makePayment(String name,Boost bst);
    
    /**
     * refresh the shop 
     */
    void resetShop();
}