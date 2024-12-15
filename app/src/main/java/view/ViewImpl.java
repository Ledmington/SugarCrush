package view;

import javax.swing.JOptionPane;

import view.gui.GUI;
import view.gui.Game;

/**
 * The class that manages all GUIs.
 * 
 * @author Filippo Barbari
 *
 */
public final class ViewImpl implements View {

    private GUI currentGUI;

    public ViewImpl() {
        super();
    }

    public final void setCurrentGUI(final GUI gui) {
        this.currentGUI = gui;
    }

    public final GUI getCurrentGUI() {
        return this.currentGUI;
    }

    public final void updateGrid() {
        if(this.getCurrentGUI().getClass() == Game.class) {
            ((Game)this.getCurrentGUI()).updateGrid();
        }
    }

    public final void levelEnd() {
        if(this.currentGUI.getClass() == Game.class) {
            ((Game)this.getCurrentGUI()).levelEnd();
        }
    }

    public final void stageEnd() {
        if(this.currentGUI.getClass() == Game.class) {
            ((Game)this.getCurrentGUI()).stageEnd();
        }
    }

    public final void nextStage() {
        if(this.currentGUI.getClass() == Game.class) {
            ((Game)this.getCurrentGUI()).nextStage();
        }
    }
    
    public final void achievementUnlocked(final String text) {
        JOptionPane.showMessageDialog(null, text);
    }
}
