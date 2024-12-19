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
import java.awt.FlowLayout;
import java.io.Serial;
import java.util.Objects;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;

import controller.Controller;
import view.View;

/** @author Davide Degli Esposti */
public final class Ranking extends GUI {

	@Serial
	private static final long serialVersionUID = 4592677871472067270L;

	private static final JLabel titleLabel = new JLabel("ScoreBoard"); // contains the title of the view
	private static final JLabel levelNumber = new JLabel("Level n°:"); // label that introduce the combobox

	private final JComboBox<String> levelsList; // combobox to indicate which rank of level shows

	Ranking(final Controller controller, final View view) {
		super(controller, view);
		// contains the number of levels as a string
		final String[] lvls = new String[controller.getNumLevels()];
		final JPanel scoreBoardPanel = new JPanel(new FlowLayout());
		this.add(scoreBoardPanel);
		for (int i = 0; i < lvls.length; i++) {
			lvls[i] = String.valueOf(i + 1);
		}
		levelsList = new JComboBox<>(lvls);

		// add labels and buttons to the main panel
		scoreBoardPanel.add(titleLabel, BorderLayout.NORTH);
		// button to see the general ranking
		final JButton generalScoreRank = new JButton("GeneralRank");
		scoreBoardPanel.add(generalScoreRank);
		scoreBoardPanel.add(levelNumber);
		scoreBoardPanel.add(levelsList);

		// button to see the ranking of a certain level
		final JButton levelScoreRank = new JButton("levelScoreRank");
		scoreBoardPanel.add(levelScoreRank);

		// button to go back to the main menu
		final JButton back = new JButton("Back");
		scoreBoardPanel.add(back);

		// event on back button
		back.addActionListener(e -> {
			this.load(new MainMenu(controller, view));
			controller.getSound().playSound("button_press");
		});

		// event on generalScoreRank button
		generalScoreRank.addActionListener(e -> {
			this.load(new ViewRank(controller, view, controller.getRankByGeneralScore(), "General"));
			controller.getSound().playSound("button_press");
		});

		// event on levelScoreRank button
		levelScoreRank.addActionListener(e -> {
			this.load(new ViewRank(
					controller,
					view,
					controller.getRankByLevelScore(levelsList.getSelectedIndex() + 1),
					("Level " + Objects.requireNonNull(levelsList.getSelectedItem()))));
			controller.getSound().playSound("button_press");
		});

		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.pack();
	}
}
