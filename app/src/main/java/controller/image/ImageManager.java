package controller.image;

import javax.swing.ImageIcon;

import model.game.grid.candies.Candy;

/**
 * Manages the usage of candies' images in memory.
 * @author Filippo Barbari
 */
public interface ImageManager {
    
    /** 
     * Given a {@link Candy} it return the image of that {@link Candy}.
     * @param candy The candy describing the image you need.
     * @return An {@link ImageIcon} of the {@link Candy} passed.
     */
    ImageIcon getCandyImage(final Candy candy);

}
