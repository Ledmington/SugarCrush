package model.objectives;

/**
 * Contains the methods of a general {@link Challenge}
 * 
 * @author Emanuele Lamagna
 */
public interface Challenge {

	/**
	 * Getter of the red candies to destroy
	 *
	 * @return the red candies to destroy
	 */
	int getRedToDestroy();
	
	/**
	 * Getter of the yellow candies to destroy
	 *
	 * @return the yellow candies to destroy
	 */
	int getYellowToDestroy();
	
	/**
	 * Getter of the blue candies to destroy
	 * 
	 * @return the blue candies to destroy
	 */
	int getBlueToDestroy();
	
	/**
	 * Getter of the green candies to destroy
	 * 
	 * @return the green candies to destroy
	 */
	int getGreenToDestroy();
	
	/**
	 * Getter of the purple candies to destroy
	 * 
	 * @return the purple candies to destroy
	 */
	int getPurpleToDestroy();
	
	/**
	 * Getter of the orange candies to destroy
	 * 
	 * @return the orange candies to destroy
	 */
	int getOrangeToDestroy();
	
	/**
	 * Getter of the freckles candies to farm
	 * 
	 * @return the freckles candies to farm
	 */
	int getFrecklesToFarm();
	
	/**
	 * Getter of the striped candies to farm
	 *
	 * @return the striped candies to farm
	 */
	int getStripedToFarm();
	
	/**
	 * Getter of the wrapped candies to farm
	 * 
	 * @return the wrapped candies to farm 
	 */
	int getWrappedToFarm();
	
	/**
	 * Getter of the jelly condition (if is to be destroyed or not)
	 * 
	 * @return if the jelly should be all destroyed
	 */
	boolean isJellyToDestroy();
	
}
