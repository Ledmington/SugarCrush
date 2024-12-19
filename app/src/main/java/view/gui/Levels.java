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
import java.awt.GridLayout;
import java.io.Serial;

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
 *
 * @author Filippo Barbari
 */
public final class Levels extends GUI {

	@Serial
	private static final long serialVersionUID = -8843512028871525894L;

	Levels(final Controller controller, final View view) {
		super(controller, view);
		final int numLevels = controller.getNumLevels();
		final int lastLevelUnlocked = controller.getLastLevelUnlocked();

		// Panel inside Frame
		final JPanel mainPanel = new JPanel();
		this.add(mainPanel);
		mainPanel.setLayout(new BorderLayout());

		// Title label
		final JLabel title = new JLabel("LEVELS", SwingConstants.CENTER);
		mainPanel.add(title, BorderLayout.NORTH);

		// Panel to divide tutorial and other levels
		final JPanel centerPanel = new JPanel();
		centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.Y_AXIS));
		mainPanel.add(centerPanel, BorderLayout.CENTER);

		// Tutorial panel
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

		// Grid of levels
		final JPanel levelsPanel = new JPanel();
		levelsPanel.setLayout(new GridLayout(2, 5));
		centerPanel.add(levelsPanel);

		// Filling the grid
		for (int i = 0; i < numLevels; i++) {
			final int levelIndex = i + 1;
			final JButton jb = new JButton(String.valueOf(levelIndex));
			levelsPanel.add(jb);

			jb.addActionListener(e -> {
				controller.getSound().playSound("button_press");
				controller.startLevel(levelIndex);
				final Game tmpView = new Game(controller, view);
				this.load(tmpView);
				tmpView.setTitle("Level " + levelIndex);

				JOptionPane.showMessageDialog(null, controller.getObjective().objectiveText());
			});
			jb.setEnabled(i + 1 <= lastLevelUnlocked);
		}

		// South panel with back button.
		JButton backButton = new JButton("Back");
		backButton.addActionListener(e -> {
			this.load(new MainMenu(controller, view));
			controller.getSound().playSound("button_press");
		});
		mainPanel.add(backButton, BorderLayout.SOUTH);

		// Default on close
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.pack();
	}
}
