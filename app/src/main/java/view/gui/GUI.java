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

import java.io.Serial;
import java.net.URL;
import java.util.Objects;

import javax.swing.ImageIcon;
import javax.swing.JFrame;

import controller.Controller;
import view.View;

/**
 * An abstract gui to avoid code repetitions.
 *
 * @author Filippo Barbari
 */
public abstract class GUI extends JFrame {

	@Serial
	private static final long serialVersionUID = 7011873514281012033L;

	protected transient View view;
	protected final transient Controller controller;

	protected GUI(final Controller controller, final View view) {
		super();
		this.controller = Objects.requireNonNull(controller);
		this.view = Objects.requireNonNull(view);
	}

	/**
	 * Added to avoid code repeatitions. Makes the given GUI object visible and sets its location properly.
	 *
	 * @param gui A GUI object to be loaded.
	 */
	protected final void load(final GUI gui) {
		this.view.getCurrentGUI().setVisible(false);
		gui.setLocation(this.view.getCurrentGUI().getLocation());
		gui.setVisible(true);
		this.view.setCurrentGUI(gui);
		final URL imageUrl = ClassLoader.getSystemResource("candyImages/FRECKLES_FRECKLES.jpeg");
		gui.setIconImage(new ImageIcon(imageUrl).getImage());
	}
}
