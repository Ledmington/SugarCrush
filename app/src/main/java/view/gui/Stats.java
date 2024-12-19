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

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.io.Serial;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

import javax.swing.*;

import controller.Controller;
import controller.files.*;
import view.View;

/**
 * A {@link GUI} that shows the stats of the current player
 *
 * @author Emanuele Lamagna
 */
public final class Stats extends GUI {

	@Serial
	private static final long serialVersionUID = -6538372787351542506L;

	private transient Map<String, Object> player = new HashMap<>();

	Stats(final Controller controller, final View view) {
		super(controller, view);
		// initialize the map
		controller.getPlayers(STATS).stream()
				.filter(map -> map.get(playerName).toString().equals(("\"" + controller.getCurrentPlayer() + "\"")))
				.forEach(map -> this.player = map);
		this.setLayout(new BorderLayout());
		final JPanel stats = new JPanel();

		// set the stats panel
		stats.setLayout(new GridLayout(player.size() + 1, 2));
		stats.add(new JLabel("STATS"));
		stats.add(new JLabel("VALUES"));
		stats.add(new JLabel("Player"));
		stats.add(new JLabel(controller.getCurrentPlayer()));
		Stream.of(StatsTypes.values()).forEach(s -> {
			stats.add(new JLabel(s.getDescription()));
			stats.add(new JLabel(player.get(s.name()).toString()));
		});
		Stream.of(stats.getComponents()).forEach(c -> ((JLabel) c).setHorizontalAlignment(JLabel.CENTER));

		this.getContentPane().add(stats, BorderLayout.CENTER);

		// set the back button
		final JButton back = new JButton("BACK");
		back.addActionListener(e -> {
			this.load(new MainMenu(controller, view));
			controller.getSound().playSound("button_press");
		});
		this.add(back, BorderLayout.SOUTH);

		// set the graphics
		stats.setBackground(Color.pink);
		stats.setBorder(BorderFactory.createLineBorder(Color.black));

		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.pack();
		this.setVisible(true);
	}
}
