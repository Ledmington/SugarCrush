/*
 * Sugar Crush
 * Copyright (C) 2020 Filippo Benvenuti, Filippo Barbari, Lamagna Emanuele, Degli Esposti Davide
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package view.gui;

import static controller.Controller.playerName;
import static controller.files.FileTypes.*;

import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.Serial;
import java.util.Map;

import javax.swing.*;

import controller.Controller;
import view.View;
import view.sounds.*;

/**
 * A {@link GUI} that sets the current player and add him if he isn't already saved
 *
 * @author Emanuele Lamagna
 */
public final class Login extends GUI {

	@Serial
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

		final ActionListener al = (e) -> this.performAction(jtf, comment);

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

	// contains the action of the login (verify the name and access to the main menu)
	private void performAction(final JTextField jtf, final JLabel comment) {
		new SoundImpl().playSound("button_press");
		if (!jtf.getText().isEmpty() && !jtf.getText().contains("\"")) {
			boolean isPresent = false;
			for (final Map<String, Object> map : controller.getPlayers(STATS)) {
				if (map.get(playerName).toString().equals("\"" + jtf.getText() + "\"")) {
					isPresent = true;
					break;
				}
			}
			if (!isPresent) {
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
