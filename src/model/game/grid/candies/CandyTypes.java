package model.game.grid.candies;

/**
 * The list of every type of {@link Candy}.
 * @author Filippo Benvenuti
 *
 */
public enum CandyTypes {
	NORMAL,					// Normal behaviour.
	STRIPED_VERTICAL,		// Destroys vertical line where it get destroyed.
	STRIPED_HORIZONTAL,		// Destroys horizontal line where it get destroyed.
	WRAPPED,				// Destroys 8 candyes near-by where it get destroyed.
	FRECKLES,				// Destroys all candies with the same color of the one switched with that. (and itself)
	CHOCOLATE				// Can't be moved, get destroyed by near destroyed candies.
}
