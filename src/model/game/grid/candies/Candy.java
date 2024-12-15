package model.game.grid.candies;

/**
 * 
 * @author Filippo Benvenuti
 *
 */
public interface Candy {
	/**
	 * Retrieves the {@link CandyColors} of the {@link Candy}.
	 * @return the {@link CandyColors} of the {@link Candy}.
	 */
	CandyColors getColor();
	
	/**
	 * Retrieves the {@link CandyTypes} of the {@link Candy}.
	 * @return the {@link CandyTypes} of the {@link Candy}.
	 */
	CandyTypes getType();
	
	int hashCode();
	boolean equals(Object obj);
	String toString();
}
