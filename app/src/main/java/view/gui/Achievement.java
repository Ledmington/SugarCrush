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
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import controller.Controller;
import utils.Triple;
import view.View;

/** @author Davide Degli Esposti */
public final class Achievement extends GUI {

	private static final long serialVersionUID = -9032952895798678108L;
	private JButton back = new JButton("Back"); // button to go back to Main Menu
	private static JLabel titleLabel = new JLabel("Your Achievements"); // The title of the gui
	private final JPanel achvPanel = new JPanel(); // the main panel

	protected Achievement(final Controller controller, final View view) {
		super(controller, view);

		// initialize and add element to the main panel
		this.setLayout(new BorderLayout());
		this.add(titleLabel, BorderLayout.NORTH);
		titleLabel.setFont(new Font("System", Font.BOLD, 20));

		this.add(achvPanel, BorderLayout.CENTER);
		this.add(back, BorderLayout.SOUTH);

		// add to panel labels with title and description of each achievement distinguishing between achieved and not
		// through the background
		List<Triple<String, String, Boolean>> acv = controller.getAchievements();
		achvPanel.setLayout(new GridLayout(acv.size() * 2, 2));
		for (int i = 0; i < acv.size(); i++) {
			JLabel tmpTitleLabel = new JLabel(); // contains the title of an achievement
			JLabel tmpDescrLabel = new JLabel(); // contains the description of an achievement
			tmpTitleLabel.setOpaque(true);
			tmpDescrLabel.setOpaque(true);
			tmpTitleLabel.setText(acv.get(i).getX() + ":");
			tmpTitleLabel.setFont(new Font("System", Font.BOLD, 16));
			tmpDescrLabel.setText(acv.get(i).getY());
			if (acv.get(i).getZ()) {
				tmpTitleLabel.setBackground(Color.GREEN);
				tmpDescrLabel.setBackground(Color.GREEN);
			} else {
				tmpTitleLabel.setBackground(Color.PINK);
				tmpDescrLabel.setBackground(Color.PINK);
			}
			achvPanel.add(tmpTitleLabel);
			achvPanel.add(tmpDescrLabel);
			achvPanel.add(new JLabel(" "));
			achvPanel.add(new JLabel(" "));
		}

		// event on back button
		back.addActionListener(e -> {
			this.load(new MainMenu(controller, view));
			controller.getSound().playSound("button_press");
		});

		this.pack();
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
	}
}
