package utils;

/**
 * A more pragmatic way to use Pair.
 * 
 * @author Filippo Barbari
 *
 */
public final class Point2D extends Pair<Integer, Integer> {

    public Point2D(final Integer newX, final Integer newY) {
        super(newX, newY);
    }
    
    /**
     * Creates a new instance of Point2D which the result
     * of vectorial sum between this and otherPoint.
     * 
     * @return
     *          A new Point2D.
     */
    public final Point2D add(final Point2D otherPoint) {
        return new Point2D(this.x+otherPoint.getX(), this.y+otherPoint.getY());
    }
    
    @Override
    public final String toString() {
        return "Point2D [x=" + x + ", y=" + y + "]";
    }

}
