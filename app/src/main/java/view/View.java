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

import model.achievement.Achievement;
import view.gui.GUI;

/** @author Filippo Barbari */
public interface View {

	/**
	 * Retrieves the current {@link GUI} running.
	 *
	 * @return {@link GUI} currently running.
	 */
	GUI getCurrentGUI();

	/**
	 * Set the {@link GUI} to be open.
	 *
	 * @param gui The {@link GUI} to be open.
	 */
	void setCurrentGUI(final GUI gui);

	/** Inform the view that it need to be updated. */
	void updateGrid();

	/** Inform the view that level is ended. */
	void levelEnd();

	/** Inform the view that stage is ended. */
	void stageEnd();

	/** Inform the view that another stage is needed to be load. */
	void nextStage();

	/**
	 * Inform the view that a {@link Achievement} has been reached.
	 *
	 * @param text The text describing the achievement reached.
	 */
	void achievementUnlocked(final String text);
}
