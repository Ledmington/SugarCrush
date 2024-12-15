package view.gui;

import static controller.files.FileTypes.*;

import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Map;

import javax.swing.*;

import controller.Controller;
import view.View;
import view.sounds.*;

import static controller.Controller.playerName;

/**
 * A {@link GUI} that sets the current player and add him if he isn't already saved
 * 
 * @author Emanuele Lamagna
 *
 */
public final class Login extends GUI {

    private static final long serialVersionUID = -1392377673421616906L;

    public Login(final Controller controller, final View view) {
        super(controller, view);

        final JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(4, 1));
        this.getContentPane().add(panel);

        panel.add(new JLabel("WHAT'S YOUR NAME?"));

        final JTextField jtf = new JTextField();
        panel.add(jtf);

        final JLabel comment = new JLabel();
        panel.add(comment);

        final ActionListener al = (e)->{
            this.performAction(jtf, comment);
        };

        final JButton bt = new JButton("ENTER");
        bt.addActionListener(al);

        jtf.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    performAction(jtf, comment);
                }
            }
        });

        panel.add(bt);
        panel.setBackground(Color.PINK);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.pack();
        this.setVisible(true);
    }

    //contains the action of the login (verify the name and access to the main menu)
    private final void performAction(final JTextField jtf, final JLabel comment) {
    	new SoundImpl().playSound("button_press");
        if(!jtf.getText().equals("") && !jtf.getText().contains("\"")) {
            boolean isPresent = false;
            for(Map<String, Object> map: controller.getPlayers(STATS)){
                if(map.get(playerName).toString().equals("\"" + jtf.getText() + "\"")){
                    isPresent = true;
                    break;
                }
            }
            if(!isPresent){
                controller.addPlayer(jtf.getText());
                JOptionPane.showMessageDialog(this, "First time here? Try the tutorial!");
            }
            controller.setCurrentPlayer(jtf.getText());
            this.load(new MainMenu(controller, this.view));
        } else {
            comment.setText("Enter a valid name");
            comment.setForeground(Color.RED);
        }
    }

}
