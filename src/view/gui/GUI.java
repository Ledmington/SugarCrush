package view.gui;

import java.net.URL;

import javax.swing.ImageIcon;
import javax.swing.JFrame;

import controller.Controller;
import view.View;

/**
 * An abstract gui to avoid code repetitions.
 * 
 * @author Filippo Barbari
 *
 */
public abstract class GUI extends JFrame {
    
    private static final long serialVersionUID = 7011873514281012033L;
    protected View view;
    protected final Controller controller;
    
    protected GUI(final Controller controller, final View view) {
        super();
        this.controller = controller;
        this.view = view;
        final URL imageUrl = ClassLoader.getSystemResource("candyImages/FRECKLES_FRECKLES.jpeg");
        this.setIconImage(new ImageIcon(imageUrl).getImage());
    }
    
    /**
     * Added to avoid code repeatitions.
     * Makes the given GUI object visible and sets its location properly.
     * 
     * @param gui
     *          A GUI object to be loaded.
     */
    protected final void load(final GUI gui) {
        this.view.getCurrentGUI().setVisible(false);
        gui.setLocation(this.view.getCurrentGUI().getLocation());
        gui.setVisible(true);
        this.view.setCurrentGUI(gui);
    }
}
