package test;

import utils.Point2D;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Arrays;

import org.junit.Test;

import model.game.grid.shapes.ShapeCoordinates;
import model.game.grid.shapes.Shapes;

/**
 * 
 * @author Filippo Benvenuti
 *
 */
public final class TestShapeCoordinates {
    
	private ShapeCoordinates cc;
	
	 @Test(expected = IllegalStateException.class)
	 public final void cantBeOdd() {
		 cc = new ShapeCoordinates(1, 2, 3, 4, 5, 6, 7);
	 }
	 
	 @Test(expected = NullPointerException.class)
	 public final void cantBeNull() {
		 cc = new ShapeCoordinates(Arrays.asList(new Point2D(1, 2), null));
	 }
	 
	 @Test
	 public final void correctRotation() {
		 cc = new ShapeCoordinates(0, 1, 1, 0);
		 assertEquals(Arrays.asList(new Point2D(1, 0), new Point2D(0, -1)),
				 cc.getNextRotatedCandyCoordinates().getRelativeCoordinates());
	 }
	 
	 @Test
	 public final void stripedVertical() {
		 cc = Shapes.LINE_FOUR_VERTICAL.getCoordinates();
		 assertEquals(Shapes.LINE_FOUR_VERTICAL.getCoordinates().getRelativeCoordinates(),
				 cc.getRelativeCoordinates());
	 }
}
