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
package view;

import javax.swing.JOptionPane;

import view.gui.GUI;
import view.gui.Game;

/**
 * The class that manages all GUIs.
 *
 * @author Filippo Barbari
 */
public final class ViewImpl implements View {

	private GUI currentGUI;

	public ViewImpl() {
		super();
	}

	public final void setCurrentGUI(final GUI gui) {
		this.currentGUI = gui;
	}

	public final GUI getCurrentGUI() {
		return this.currentGUI;
	}

	public final void updateGrid() {
		if (this.getCurrentGUI().getClass() == Game.class) {
			((Game) this.getCurrentGUI()).updateGrid();
		}
	}

	public final void levelEnd() {
		if (this.currentGUI.getClass() == Game.class) {
			((Game) this.getCurrentGUI()).levelEnd();
		}
	}

	public final void stageEnd() {
		if (this.currentGUI.getClass() == Game.class) {
			((Game) this.getCurrentGUI()).stageEnd();
		}
	}

	public final void nextStage() {
		if (this.currentGUI.getClass() == Game.class) {
			((Game) this.getCurrentGUI()).nextStage();
		}
	}

	public final void achievementUnlocked(final String text) {
		JOptionPane.showMessageDialog(null, text);
	}
}
