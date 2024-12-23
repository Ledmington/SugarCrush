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
package model.shop;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.random.RandomGenerator;
import java.util.random.RandomGeneratorFactory;

import controller.Controller;
import controller.files.FileTypes;
import model.game.grid.candies.Candy;
import model.game.grid.candies.CandyColors;
import model.game.grid.candies.CandyFactory;
import model.game.grid.candies.CandyTypes;
import model.players.PlayerManagerImpl;
import model.score.Status;

/** @author Davide Degli Esposti */
public final class BoostShop {

	private static final int MAXELEMSHOP = 4; // number of item in the shop
	private static final int FRECKLESPRICE = 1000; // price of Freckles candies
	private static final int WRAPPEDPRICE = 750; // price of Wrapped candies
	private static final int STRIPEDPRICE = 500; // price of Striped candies

	private final List<Boost> boosts = new ArrayList<>(); // contains the boost to sell
	private final PlayerManagerImpl pm = new PlayerManagerImpl(); // variable to get the list of players

	/** generate the items sold in the shop */
	public void generateShop() {
		boosts.clear();
		for (int i = 0; i < MAXELEMSHOP; i++) {
			generateBoost();
		}
	}

	/** generate the boost to add at the shop */
	private void generateBoost() {
		final RandomGenerator rng = RandomGeneratorFactory.getDefault().create(System.nanoTime());

		// variable for the random color
		CandyColors color;
		do {
			color = CandyColors.values()[rng.nextInt(CandyColors.values().length)];
		} while (color == CandyColors.CHOCOLATE || color == CandyColors.FRECKLES);

		// if true, the candy have type=chocolate or normal
		boolean flag;
		do {
			Candy candy;
			switch (rng.nextInt(CandyTypes.values().length)) {
				case 0:
					flag = false;
					// boost to add at the shop
					candy = CandyFactory.getFreckles();
					this.boosts.add(new Boost(CandyTypes.FRECKLES.name(), FRECKLESPRICE, candy));
					break;
				case 1:
					flag = false;
					candy = CandyFactory.getHorizontalStriped(color);
					this.boosts.add(new Boost(CandyTypes.STRIPED_HORIZONTAL.name(), STRIPEDPRICE, candy));
					break;
				case 2:
					flag = false;
					candy = CandyFactory.getVerticalStripedCandy(color);
					this.boosts.add(new Boost(CandyTypes.STRIPED_VERTICAL.name(), STRIPEDPRICE, candy));
					break;
				case 3:
					flag = false;
					candy = CandyFactory.getWrapped(color);
					this.boosts.add(new Boost(CandyTypes.WRAPPED.name(), WRAPPEDPRICE, candy));
					break;
				default:
					flag = true;
			}
		} while (flag);
	}

	/**
	 * If player has enough money update the set of player properties.
	 *
	 * @param name the name of the player
	 * @param bst the boost bought by the player
	 */
	public void payment(final String name, final Boost bst) {
		final PlayerManagerImpl pl = new PlayerManagerImpl();
		final List<Map<String, Object>> list = pl.getPlayers(FileTypes.STATS);
		for (final Map<String, Object> map : list) {
			if (map.get(Controller.playerName).toString().equals("\"" + name + "\"")) {
				if ((Integer.parseInt(map.get(Status.Ratios.MONEY.name()).toString())) >= bst.getPrice()) {
					map.put(
							Status.Ratios.MONEY.name(),
							(Integer.parseInt(
											map.get(Status.Ratios.MONEY.name()).toString())
									- bst.getPrice()));
					pl.update(list, FileTypes.STATS);
					addBoostToPlayer(name, bst);
					return;
				} else {
					throw new IllegalStateException("Soldi Insufficienti");
				}
			}
		}
		throw new IllegalStateException("giocatore non registrato");
	}

	/** @return the list of boosts on sale */
	public List<Boost> getBoosts() {
		return this.boosts;
	}

	/**
	 * Update the {@link FileTypes#BOOSTS} of the player with the boost just bought
	 *
	 * @param name the name of the player
	 * @param bst the boost to add to the file
	 */
	private void addBoostToPlayer(final String name, final Boost bst) {
		final List<Map<String, Object>> list = pm.getPlayers(FileTypes.BOOSTS);
		for (final Map<String, Object> map : list) {
			if (map.get(Controller.playerName).toString().equals("\"" + name + "\"")) {
				map.put(bst.getName(), Integer.parseInt(map.get(bst.getName()).toString()) + 1);
			}
		}
		pm.update(list, FileTypes.BOOSTS);
	}
}
