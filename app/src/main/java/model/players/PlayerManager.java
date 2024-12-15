package model.players;

import java.util.List;
import java.util.Map;

import controller.files.FileTypes;
import model.score.Status;


/**
 * Contains a Map with all the informations of the player. As key there are the {@link FileTypes}, as values there are triples
 * of path, File and JsonArray of that file type. Moreover, contains the enums for all the files, stats and boosts
 * 
 * @author Emanuele Lamagna
 */
public interface PlayerManager {

	/**
	 * Adds a new player in the list of the players.
	 * 
	 * @param name  the name of the player
	 * @throws IOException  if there are problems with the files
	 */
	void addPlayer(final String name);
	
	/**
	 * Checks the values in {@link Status} and refreshes the informations of that player
	 * 
	 * @param name  the name of the player
	 * @param status  the status of the current player
	 * @param level  the current level
	 * @throws IOException  if there are problems with the files
	 */
	void setStat(final String name, final Status status, final int level);

	/**
	 * Updates the informations of the player, with the received list of maps
	 * 
	 * @param list  the list of all the players (all as maps)
	 * @param type  the type of file to use
	 * @throws IOException  if there are problems with the files
	 */
	void update(final List<Map<String, Object>> list, final FileTypes type);
	
	/**
	 * Reads the right file to get all the players stats or boost (it depends by "type" parameter)
	 * 
	 * @param type  the type of file to use
	 * @throws IOException  if there are problems with the files
	 * @return a list of all the players (as maps)
	 */
	List<Map<String, Object>> getPlayers(final FileTypes type);
	
	/**
	 * Removes a player from all the files
	 * 
	 * @param name  the name of the player
	 * @throws IOException  if there are problems with the files
	 */
	void removePlayer(final String name);
	
}
