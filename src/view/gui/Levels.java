package view.gui;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import controller.Controller;
import view.View;

/**
 * GUI to see all available levels.
 * @author Filippo Barbari
 *
 */
public final class Levels extends GUI {

    private static final long serialVersionUID = -8843512028871525894L;
    private final int numLevels;
    private final int lastLevelUnlocked;

    protected Levels(final Controller controller, final View view) {
        super(controller, view);
        this.numLevels = controller.getNumLevels();
        this.lastLevelUnlocked = controller.getLastLevelUnlocked();

        //Panel inside Frame
        final JPanel mainPanel = new JPanel();
        this.add(mainPanel);
        mainPanel.setLayout(new BorderLayout());

        //Title label
        final JLabel title = new JLabel("LEVELS", SwingConstants.CENTER);
        mainPanel.add(title, BorderLayout.NORTH);

        //Panel to divide tutorial and other levels
        final JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.Y_AXIS));
        mainPanel.add(centerPanel, BorderLayout.CENTER);

        //Tutorial panel
        final JPanel tutorialPanel = new JPanel();
        final JButton tutorialButton = new JButton("Tutorial");
        tutorialButton.addActionListener(e -> {
            controller.getSound().playSound("button_press");
            controller.startTutorial();
            var tmpView = new Game(controller, view);
            this.load(tmpView);
            tmpView.setTitle("Tutorial");
        });
        tutorialPanel.add(tutorialButton);
        centerPanel.add(tutorialPanel);

        //Grid of levels
        final JPanel levelsPanel = new JPanel();
        levelsPanel.setLayout(new GridLayout(2,5));
        centerPanel.add(levelsPanel);

        //Filling the grid
        for(int i=0; i<this.numLevels; i++) {
            final Integer levelIndex = i+1;
            final JButton jb = new JButton(levelIndex.toString());
            levelsPanel.add(jb);

            jb.addActionListener(e -> {
                controller.getSound().playSound("button_press");
                controller.startLevel(levelIndex);
                var tmpView = new Game(controller, view);
                this.load(tmpView);
                tmpView.setTitle("Level " + levelIndex);

                JOptionPane.showMessageDialog(null, controller.getObjective().objectiveText());
            });
            jb.setEnabled(i+1 <= lastLevelUnlocked);
        }

        // South panel with back button.
        JButton backButton = new JButton("Back");
        backButton.addActionListener(e -> {
            this.load(new MainMenu(controller, view));
            controller.getSound().playSound("button_press");
        });
        mainPanel.add(backButton, BorderLayout.SOUTH);

        //Default on close
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.pack();
    }

}
