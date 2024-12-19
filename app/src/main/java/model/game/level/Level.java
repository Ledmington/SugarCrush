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
package model.game.level;

import java.util.Map;
import java.util.Optional;

import model.game.GameResult;
import model.game.grid.candies.Candy;
import model.game.level.stage.Stage;
import model.score.Status;
import utils.Point2D;

/**
 * The interface that allows to interact with a {@link Level} of the game.
 *
 * @author Filippo Barbari
 */
public interface Level extends Stage {

	/** @return The result of the current {@link Stage} in the form of a {@link GameResult} enumeration. */
	GameResult getResult();

	/** @return The score of the current {@link Stage} in the form of an object with the {@link Status} interface. */
	Status getCurrentScore();

	/** @return true if the current {@link Stage} of this level is not the last {@link Stage}. false otherwise. */
	boolean hasNextStage();

	/** If the current {@link Stage} isn't the last one, the next {@link Stage} is set to be the current one. */
	void nextStage();

	/** Consume all remaining moves, spawning random special candies and blowing them up. */
	void consumeRemainingMoves();

	/**
	 * Retrieves a map representing state of jelly.
	 *
	 * @return A map with jelly lives.
	 */
	Optional<Map<Point2D, Integer>> getJelly();

	/**
	 * Change a candy with another candy in determined coordinates.
	 *
	 * @param cord The coordinates of the candy to be mutated.
	 * @param cnd The new candy to be mutated into.
	 * @return False if mutation wasn't possible.
	 */
	boolean mutateCandy(Point2D cord, Candy cnd);
}
