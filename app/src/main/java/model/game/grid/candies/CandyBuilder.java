package model.game.grid.candies;

public interface CandyBuilder {
	
	/**
	 * Sets the {@link CandyColors} of the {@link Candy}.
	 * @param cndCol The {@link CandyColors}.
	 * @return This instance of {@link CandyBuilder}.
	 */
	CandyBuilder setColor(CandyColors cndCol);
	
	/**
	 * Sets the type of the {@link Candy}.
	 * @param cndTyp The {@link CandyTypes}.
	 * @return This instance of {@link CandyBuilder}.
	 */
	CandyBuilder setType(CandyTypes cndTyp);
	
	/**
	 * Build the {@link Candy} with settings set.
	 * @return The {@link Candy} if settings were set.
	 */
	Candy build();
}
