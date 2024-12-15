package view.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;

import javax.swing.*;

import controller.Controller;
import view.View;

/**
 * A {@link GUI} that shows various options
 * 
 * @author Emanuele Lamagna
 *
 */
public final class Settings extends GUI {

    private static final long serialVersionUID = 1938134059666408806L;
    private boolean soundActivated = true;

    protected Settings(final Controller controller, final View view) {
        super(controller, view);

        this.setLayout(new BorderLayout());
        final JPanel control = new JPanel();
        this.getContentPane().add(control, BorderLayout.CENTER);

        final JPanel settings = new JPanel();
        settings.setLayout(new GridLayout());
        control.add(settings);

        //removes the player and goes back to the login
        final JButton reset = new JButton("Reset");
        reset.addActionListener(e -> {
            controller.getSound().playSound("button_press");
            final int input = JOptionPane.showConfirmDialog(null, "Are you sure?", "Select an option", 
                    JOptionPane.YES_NO_CANCEL_OPTION);
            if(input==0) {
            	final String tmp = controller.getCurrentPlayer();
                controller.removePlayer(tmp);
                this.load(new Login(controller, view));
            }
        });
        settings.add(reset);

        final  JButton sounds = new JButton("Sounds " + (soundActivated ? "ON" : "OFF"));
        sounds.addActionListener(e -> {
            soundActivated = !soundActivated;
            sounds.setText("Sounds " + (soundActivated?"ON":"OFF"));
            controller.getSound().setSoundEnabled();
            controller.getSound().playSound("button_press");
        });
        settings.add(sounds);

        //set the back button
        final JButton back = new JButton("BACK");
        back.addActionListener(e -> {
            this.load(new MainMenu(controller, view));
            controller.getSound().playSound("button_press");
        });
        this.add(back, BorderLayout.SOUTH);
        control.setBackground(Color.pink);

        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.pack();
    }

}
