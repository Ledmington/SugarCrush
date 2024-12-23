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

	@Override
	public void setCurrentGUI(final GUI gui) {
		this.currentGUI = gui;
	}

	@Override
	public GUI getCurrentGUI() {
		return this.currentGUI;
	}

	@Override
	public void updateGrid() {
		if (this.getCurrentGUI().getClass() == Game.class) {
			((Game) this.getCurrentGUI()).updateGrid();
		}
	}

	@Override
	public void levelEnd() {
		if (this.currentGUI.getClass() == Game.class) {
			((Game) this.getCurrentGUI()).levelEnd();
		}
	}

	@Override
	public void stageEnd() {
		if (this.currentGUI.getClass() == Game.class) {
			((Game) this.getCurrentGUI()).stageEnd();
		}
	}

	@Override
	public void nextStage() {
		if (this.currentGUI.getClass() == Game.class) {
			((Game) this.getCurrentGUI()).nextStage();
		}
	}

	@Override
	public void achievementUnlocked(final String text) {
		JOptionPane.showMessageDialog(null, text);
	}
}
