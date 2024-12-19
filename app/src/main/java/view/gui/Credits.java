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

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.io.Serial;

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
 */
public final class Credits extends GUI {

	@Serial
	private static final long serialVersionUID = -4390892138366186444L;

	Credits(final Controller controller, final View view) {
		super(controller, view);

		// Main panel
		final JPanel mainPanel = new JPanel(new BorderLayout());
		this.add(mainPanel);

		// Credits panel
		final JPanel credits = new JPanel(new GridLayout(4, 2));
		mainPanel.add(credits, BorderLayout.CENTER);

		// Barbari
		final JLabel barbari1 = new JLabel("Barbari Filippo", SwingConstants.CENTER);
		barbari1.setFont(new Font("Serif", Font.PLAIN, 14));
		credits.add(barbari1);
		final JLabel barbari2 = new JLabel("Levels' management", SwingConstants.CENTER);
		credits.add(barbari2);

		// Benvenuti
		final JLabel benvenuti1 = new JLabel("Benvenuti Filippo", SwingConstants.CENTER);
		benvenuti1.setFont(new Font("Serif", Font.PLAIN, 14));
		credits.add(benvenuti1);
		final JLabel benvenuti2 = new JLabel("Candies' management", SwingConstants.CENTER);
		credits.add(benvenuti2);

		// Lamagna
		final JLabel lamagna1 = new JLabel("Lamagna Emanuele", SwingConstants.CENTER);
		lamagna1.setFont(new Font("Serif", Font.PLAIN, 14));
		credits.add(lamagna1);
		final JLabel lamagna2 = new JLabel("Players' management and objectives", SwingConstants.CENTER);
		credits.add(lamagna2);

		// Degli Esposti
		final JLabel esposti1 = new JLabel("Degli Esposti Davide", SwingConstants.CENTER);
		esposti1.setFont(new Font("Serif", Font.PLAIN, 14));
		credits.add(esposti1);
		final JLabel esposti2 = new JLabel("Achievements and shop", SwingConstants.CENTER);
		credits.add(esposti2);

		// 'Back' button
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
