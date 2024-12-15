package model.game.grid.shapes;

import model.game.grid.candies.CandyTypes;

/**
 * List of all existing {@link Shapes}, with useful methods. 
 * @author Filippo Benvenuti
 *
 */
public enum Shapes {		// Sorted by importance.
	LINE_FIVE,
	FORM_T,
	FORM_L,
	FORM_X,					// That's actually a +.
	LINE_FOUR_VERTICAL,
	LINE_FOUR_HORIZONTAL,
	LINE_THREE;
	
	private CandyTypes candyType;		// The candy spawned if shape found (normal stays for nothing).
	
	private int rotations;				// The number of rotations shape need to be rotated.
	
	private ShapeCoordinates coordinates;	// Relative coordinates from implicit starting point.
	
	static {
		LINE_FIVE.candyType = CandyTypes.FRECKLES;
		FORM_T.candyType = CandyTypes.WRAPPED;
		FORM_L.candyType = CandyTypes.WRAPPED;
		FORM_X.candyType = CandyTypes.WRAPPED;
		LINE_FOUR_VERTICAL.candyType = CandyTypes.STRIPED_VERTICAL;
		LINE_FOUR_HORIZONTAL.candyType = CandyTypes.STRIPED_HORIZONTAL;
		LINE_THREE.candyType = CandyTypes.NORMAL;	// NOTHING SPAWNED.
	}
	
	static {
		LINE_FIVE.rotations = 2;
		FORM_T.rotations = 4;
		FORM_L.rotations = 4;
		FORM_X.rotations = 1;
		LINE_FOUR_VERTICAL.rotations = 1;
		LINE_FOUR_HORIZONTAL.rotations = 1;
		LINE_THREE.rotations = 2;
	}
	
	static {
		LINE_FIVE.coordinates = new ShapeCoordinates(0, -2, 0, -1, 0, 1, 0, 2);
		FORM_T.coordinates = new ShapeCoordinates(0, -1, 0, 1, 1, 0, 2, 0);
		FORM_L.coordinates = new ShapeCoordinates(-1, 0, -2, 0, 0, 1, 0, 2);
		FORM_X.coordinates = new ShapeCoordinates(0, -1, 0, 1, -1, 0, 1, 0);
		LINE_FOUR_VERTICAL.coordinates = new ShapeCoordinates(-1, 0, 1, 0, 2, 0);
		LINE_FOUR_HORIZONTAL.coordinates = new ShapeCoordinates(0, -1, 0, 1, 0, 2);
		LINE_THREE.coordinates = new ShapeCoordinates(0, -1, 0, 1);
	}
	
	/**
	 * Retrieves the candy will be spawned if this shape is found.
	 * @return the type of the candy to spawn.
	 */
	public final CandyTypes getCandyType() {
		return this.candyType;
	}
	
	/**
	 * Retrieves number of rotations this shape needs to be rotated while searching for it.
	 * @return number of rotations of this shape.
	 */
	public final int getRotations() {
		return this.rotations;
	}
	
	/**
	 * Retrieves relative coordinates from implicit starting point forming this shape.
	 * @return coordinates of this shape.
	 */
	public final ShapeCoordinates getCoordinates(){
		return this.coordinates;
	}
	
}
