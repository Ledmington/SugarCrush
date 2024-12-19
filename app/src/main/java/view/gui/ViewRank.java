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
import java.util.List;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import controller.Controller;
import utils.Pair;
import view.View;

/** @author Davide Degli Esposti */
public final class ViewRank extends GUI {

	@Serial
	private static final long serialVersionUID = 9126867684889081452L;

	ViewRank(
			final Controller controller,
			final View view,
			final List<Pair<String, Integer>> rankPlayer,
			final String title) {
		super(controller, view);
		this.setLayout(new BorderLayout());
		// the title of the view
		final JLabel titleLabel = new JLabel("ScoreBoard " + title);
		this.add(titleLabel, BorderLayout.NORTH);

		// the panel that will contain the element of the gui
		// the main panel with the rank
		final JPanel rankPanel = new JPanel();
		this.add(rankPanel, BorderLayout.CENTER);

		// add element to the panel
		rankPanel.setLayout(new GridLayout(0, 2));
		rankPanel.add(new JLabel("Player name"));
		rankPanel.add(new JLabel("Score"));
		// constant that represents the max number of players to show in the rank
		final int topRank = 10;
		for (int i = 0; i < rankPlayer.size() && i < topRank; i++) {
			rankPanel.add(new JLabel(rankPlayer.get(i).first()));
			rankPanel.add(new JLabel(rankPlayer.get(i).second().toString()));
		}

		// event on back button
		// button to go back to the main menu
		final JButton back = new JButton("Back");
		back.addActionListener(e -> {
			this.load(new Ranking(controller, view));
			controller.getSound().playSound("button_press");
		});

		// add back button to the panel
		rankPanel.add(back);

		this.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
		this.pack();
	}
}
