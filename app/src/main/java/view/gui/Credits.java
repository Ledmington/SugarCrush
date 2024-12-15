package view.gui;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import controller.Controller;
import view.View;


/**
 * The GUI to display all team's info.
 * 
 * @author Filippo Barbari
 *
 */
public final class Credits extends GUI {

    private static final long serialVersionUID = -4390892138366186444L;

    protected Credits(final Controller controller, final View view) {
        super(controller, view);
        
        //Main panel
        final JPanel mainPanel = new JPanel(new BorderLayout());
        this.add(mainPanel);
        
        //Credits panel
        final JPanel credits = new JPanel(new GridLayout(4,2));
        mainPanel.add(credits, BorderLayout.CENTER);
        
        //Barbari
        final JLabel barbari1 = new JLabel("Barbari Filippo", SwingConstants.CENTER);
        barbari1.setFont(new Font("Serif", Font.PLAIN, 14));
        credits.add(barbari1);
        final JLabel barbari2 = new JLabel("Levels' management", SwingConstants.CENTER);
        credits.add(barbari2);
        
        //Benvenuti
        final JLabel benvenuti1 = new JLabel("Benvenuti Filippo", SwingConstants.CENTER);
        benvenuti1.setFont(new Font("Serif", Font.PLAIN, 14));
        credits.add(benvenuti1);
        final JLabel benvenuti2 = new JLabel("Candies' management", SwingConstants.CENTER);
        credits.add(benvenuti2);
        
        //Lamagna
        final JLabel lamagna1 = new JLabel("Lamagna Emanuele", SwingConstants.CENTER);
        lamagna1.setFont(new Font("Serif", Font.PLAIN, 14));
        credits.add(lamagna1);
        final JLabel lamagna2 = new JLabel("Players' management and objectives", SwingConstants.CENTER);
        credits.add(lamagna2);
        
        //Degli Esposti
        final JLabel esposti1 = new JLabel("Degli Esposti Davide", SwingConstants.CENTER);
        esposti1.setFont(new Font("Serif", Font.PLAIN, 14));
        credits.add(esposti1);
        final JLabel esposti2 = new JLabel("Achievements and shop", SwingConstants.CENTER);
        credits.add(esposti2);
        
        //'Back' button
        JButton backButton = new JButton("Back");
        backButton.addActionListener(e -> {
            this.load(new MainMenu(controller, view));
            controller.getSound().playSound("button_press");
        });
        mainPanel.add(backButton, BorderLayout.SOUTH);
        
        this.pack();
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
    }

}
