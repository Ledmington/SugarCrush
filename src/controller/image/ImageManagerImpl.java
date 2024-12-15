package controller.image;

import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import javax.swing.ImageIcon;

import model.game.grid.candies.Candy;

/**
 * Implementation of ImageManager
 * @author Filippo Barbari
 */
public final class ImageManagerImpl implements ImageManager{
    
    private final Map<Candy, ImageIcon> candyImages = new HashMap<>();
    
    public ImageManagerImpl() {
        super();
    }
    
    private final ImageIcon loadImage(final Candy cnd) {
    	
    	final URL imageUrl = ClassLoader.getSystemResource("candyImages/" + cnd.getType().name() + "_" + cnd.getColor().name() + ".jpeg");
    	return new ImageIcon(imageUrl);
    }
    
    public final ImageIcon getCandyImage(final Candy candy) {
        //Lazy update
        if(!this.candyImages.containsKey(candy)) {
            this.candyImages.put(candy, this.loadImage(candy));
        }
        return this.candyImages.get(candy);
    }

    public final int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((candyImages == null) ? 0 : candyImages.hashCode());
        return result;
    }

    public final boolean equals(final Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        ImageManagerImpl other = (ImageManagerImpl) obj;
        if (candyImages == null) {
            if (other.candyImages != null)
                return false;
        } else if (!candyImages.equals(other.candyImages))
            return false;
        return true;
    }

    public final String toString() {
        return "CandyImageManager [candyImages=" + candyImages + "]";
    }

}
