package utils;

/**
 * 
 *
 * @param <X>  the first element
 * @param <Y>  the second element
 * @param <Z>  the third element
 */
public final class Triple<X,Y,Z> {
    
    private final X x;
    private final Y y;
    private final Z z;
    
    public Triple(final X newX, final Y newY, final Z newZ) {
        super();
        this.x = newX;
        this.y = newY;
        this.z = newZ;
    }

    /**
     * 
     * @return the first element
     */
    public final X getX() {
        return x;
    }

    /**
     * 
     * @return the second element
     */
    public final Y getY() {
        return y;
    }

    /**
     * 
     * @return the third element
     */
    public final Z getZ() {
        return z;
    }

    @Override
    public final int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((x == null) ? 0 : x.hashCode());
        result = prime * result + ((y == null) ? 0 : y.hashCode());
        result = prime * result + ((z == null) ? 0 : z.hashCode());
        return result;
    }

    @SuppressWarnings("rawtypes")
    @Override
    public final boolean equals(final Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Triple other = (Triple) obj;
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
        if (z == null) {
            if (other.z != null)
                return false;
        } else if (!z.equals(other.z))
            return false;
        return true;
    }

    @Override
    public final String toString() {
        return "Triple [x=" + x + ", y=" + y + ", z=" + z + "]";
    }

}
