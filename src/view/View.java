package view;

import view.gui.GUI;

/**
 * 
 * @author Filippo Barbari
 *
 */
public interface View {
    
	/**
	 * Retrieves the current {@link GUI} running.
	 * @return {@link GUI} currently running.
	 */
    GUI getCurrentGUI();
    
    /**
     * Set the {@link GUI} to be open.
     * @param gui The {@link GUI} to be open.
     */
    void setCurrentGUI(final GUI gui);

    /**
     * Inform the view that it need to be updated.
     */
    void updateGrid();
    
    /**
     * Inform the view that level is ended.
     */
    void levelEnd();
    
    /**
     * Inform the view that stage is ended.
     */
    void stageEnd();
    
    /**
     * Inform the view that another stage is needed to be load.
     */
    void nextStage();
    
    /**
     * Inform the view that a {@link Goal} has been reached.
     * @param text The text describing the achievement reached.
     */
    void achievementUnlocked(final String text);
}
