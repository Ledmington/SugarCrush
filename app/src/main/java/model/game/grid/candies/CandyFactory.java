package model.game.grid.candies;

/**
 * 
 * @author Filippo Benvenuti
 *
 */
public interface CandyFactory {

	/**
	 * Return normal {@link Candy} with specified {@link CandyColors}.
	 * @param cndColor The {@link CandyColors} of the {@link Candy}.
	 * @return The {@link Candy} obtained.
	 */
	Candy getNormalCandy(CandyColors cndColor);

	/**
	 * Return vertical striped {@link Candy} with specified {@link CandyColors}.
	 * @param cndColor The {@link CandyColors} of the {@link Candy}.
	 * @return The {@link Candy} obtained.
	 */
	Candy getVerticalStripedCandy(CandyColors cndColor);

	/**
	 * Return horizontal striped {@link Candy} with specified {@link CandyColors}.
	 * @param cndColor The {@link CandyColors} of the {@link Candy}.
	 * @return The {@link Candy} obtained.
	 */
	Candy getHorizontalStriped(CandyColors cndColor);

	/**
	 * Return wrapped {@link Candy} with specified {@link CandyColors}.
	 * @param cndColor The {@link CandyColors} of the {@link Candy}.
	 * @return The {@link Candy} obtained.
	 */
	Candy getWrapped(CandyColors cndColor);

	/**
	 * Return freckles {@link Candy}.
	 * @return The {@link Candy} obtained.
	 */
	Candy getFreckles();

	/**
	 * Return chocolate {@link Candy}.
	 * @return The {@link Candy} obtained.
	 */
	Candy getChocolate();
}
