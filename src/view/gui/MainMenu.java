package view.gui;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import controller.Controller;
import view.View;

/**
 * A class to implement the main menu of the game.
 * @author Filippo Barbari
 * @author Emanuele Lamagna
 * @author Davide Degli Esposti
 *
 */
public final class MainMenu extends GUI {
    
    private static final long serialVersionUID = 7023236724274367846L;
    private static final JLabel titleLabel = new JLabel("Welcome in Sugar Crush", SwingConstants.CENTER);
    private static final JLabel versionLabel = new JLabel("Version 1.1", SwingConstants.RIGHT);
    private static Shop bstShop;
    
    protected MainMenu(final Controller controller, final View view) {
        super(controller, view);
        bstShop = new Shop(controller, view);
        
        this.setLayout(new BorderLayout());
        
        this.add(titleLabel, BorderLayout.NORTH);
        this.add(versionLabel, BorderLayout.SOUTH);
        
        final JPanel menuPanel = new JPanel(new GridLayout(0, 3));
        this.add(menuPanel, BorderLayout.CENTER);
        
        
        //'Play' button
        final JButton play = new JButton("Play");
        menuPanel.add(play);
        play.addActionListener(e -> {
            this.load(new Levels(controller, view));
            controller.getSound().playSound("button_press");
        });
        
        //'Shop' button
        final JButton shop = new JButton("Shop");
        menuPanel.add(shop);
        shop.addActionListener(e -> {
        	if(bstShop.isShopEmpty()) {
        		bstShop=new Shop(controller, view);
        	}
            this.load(bstShop);
            controller.getSound().playSound("button_press");
        });

        //'Stats' button
        final JButton stats = new JButton("Stats");
        menuPanel.add(stats);
        stats.addActionListener(e -> {
            this.load(new  Stats(controller, view));
            controller.getSound().playSound("button_press");
        });
        
        //'ScoreBoard' button
        final JButton scoreBoard = new JButton("ScoreBoard");
        menuPanel.add(scoreBoard);
        scoreBoard.addActionListener(e -> {
            this.load(new Ranking(controller, view));
            controller.getSound().playSound("button_press");
        });
        
        //'Achievement' button
        final JButton achvmnt=new JButton("Achievement");
        menuPanel.add(achvmnt);
        achvmnt.addActionListener(e -> {
            this.load(new Achievement(controller, view));
            controller.getSound().playSound("button_press");
        });
        
        //'Settings' button
        final JButton settings = new JButton("Settings");
        menuPanel.add(settings);
        settings.addActionListener(e -> {
            this.load(new Settings(controller, view));
            controller.getSound().playSound("button_press");
        });
        
        //'Credits' button
        final JButton credits = new JButton("Credits");
        menuPanel.add(credits);
        credits.addActionListener(e -> {
            this.load(new Credits(controller, view));
            controller.getSound().playSound("button_press");
        });
        
        //'Logout' button
        final JButton logout = new JButton("Logout");
        menuPanel.add(logout);
        logout.addActionListener(e -> {
        	bstShop=new Shop(controller, view);
            this.load(new Login(controller, view));
            controller.getSound().playSound("button_press");
        });
        
        //'Exit' button
        final JButton exit = new JButton("Exit");
        menuPanel.add(exit);
        exit.addActionListener(e -> System.exit(0));
        
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.pack();
    }

}
