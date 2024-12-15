package utils;

/**
 * Class that models a tuple of elements
 *
 * @param <X>  the first element
 * @param <Y>  the second element
 */
public class Pair<X,Y> {
    
    protected final X x;
    protected final Y y;
    
    public Pair(final X newX, final Y newY) {
        this.x = newX;
        this.y = newY;
    }
    
    /**
     * 
     * @return the first element
     */
    public X getX() {
        return this.x;
    }
    
    /**
     * 
     * @return the second element
     */
    public Y getY() {
        return this.y;
    }

    public String toString() {
        return "Pair [x=" + ((this.x==null)?"null":x.toString()) + ", y=" + ((this.y==null)?"null":y.toString()) + "]";
    }

    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((x == null) ? 0 : x.hashCode());
        result = prime * result + ((y == null) ? 0 : y.hashCode());
        return result;
    }

    @SuppressWarnings("rawtypes")
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Pair other = (Pair) obj;
        if (x == null) {
            if (other.x != null)
                return false;
        } else if (!x.equals(other.x))
            return false;
        if (y == null) {
            if (other.y != null)
                return false;
        } else if (!y.equals(other.y))
            return false;
        return true;
    }

}
