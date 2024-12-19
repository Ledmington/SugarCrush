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

import static controller.Controller.playerName;
import static controller.files.StatsTypes.money;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;

import controller.files.FileTypes;
import model.game.grid.candies.*;
import model.players.*;

/** @author Davide Degli Esposti */
public final class BoostShop {

	private static final int MAXELEMSHOP = 4; // number of item in the shop
	private static final int FRECKLESPRICE = 1000; // price of Freckles candies
	private static final int WRAPPEDPRICE = 750; // price of Wrapped candies
	private static final int STRIPEDPRICE = 500; // price of Striped candies
	private CandyColors color; // variable for the random color
	private Candy candy; // boost to add at the shop
	private final List<Boost> boosts = new ArrayList<>(); // contains the boost to sell
	private boolean flag; // if true, the candy have type=chocolate or normal
	private final PlayerManagerImpl pm = new PlayerManagerImpl(); // variable to get the list of players

	/** generate the items sold in the shop */
	public final void generateShop() {
		boosts.removeAll(boosts);
		for (int i = 0; i < MAXELEMSHOP; i++) {
			generateBoost();
		}
		return;
	}

	/**
	 * generate the boost to add at the shop
	 *
	 * @return a candy item
	 */
	private final void generateBoost() {
		Random rnd = new Random();
		do {
			this.color = CandyColors.values()[rnd.nextInt(CandyColors.values().length)];
		} while (this.color == CandyColors.CHOCOLATE || this.color == CandyColors.FRECKLES);

		do {
			switch (rnd.nextInt(CandyTypes.values().length)) {
				case 0:
					this.flag = false;
					this.candy = new CandyFactoryImpl().getFreckles();
					this.boosts.add(new Boost(CandyTypes.FRECKLES.name(), FRECKLESPRICE, this.candy));

					break;
				case 1:
					this.flag = false;
					this.candy = new CandyFactoryImpl().getHorizontalStriped(color);
					this.boosts.add(new Boost(CandyTypes.STRIPED_HORIZONTAL.name(), STRIPEDPRICE, this.candy));
					break;
				case 2:
					this.flag = false;
					this.candy = new CandyFactoryImpl().getVerticalStripedCandy(color);
					this.boosts.add(new Boost(CandyTypes.STRIPED_VERTICAL.name(), STRIPEDPRICE, this.candy));
					break;
				case 3:
					this.flag = false;
					this.candy = new CandyFactoryImpl().getWrapped(color);
					this.boosts.add(new Boost(CandyTypes.WRAPPED.name(), WRAPPEDPRICE, this.candy));
					break;
				default:
					flag = true;
			}
		} while (this.flag);
	}

	/**
	 * if player have enough money update the set of player properties
	 *
	 * @param name the name of the player
	 * @param bst the boost bought by the player
	 */
	public final void payment(final String name, final Boost bst) {
		final PlayerManagerImpl pl = new PlayerManagerImpl();
		final List<Map<String, Object>> list = pl.getPlayers(FileTypes.STATS);
		for (Map<String, Object> map : list) {
			if (map.get(playerName).toString().equals("\"" + name + "\"")) {
				if ((Integer.parseInt(map.get(money.name()).toString())) >= bst.getPrice()) {
					map.put(
							money.name(),
							(Integer.parseInt(map.get(money.name()).toString()) - bst.getPrice()));
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
	public final List<Boost> getBoosts() {
		return this.boosts;
	}

	/**
	 * update the FileTypes.BOOSTS of the player with the boost just bought
	 *
	 * @param name the name of the player
	 * @param bst the boost to add to the file
	 */
	private final void addBoostToPlayer(final String name, final Boost bst) {
		final List<Map<String, Object>> list = pm.getPlayers(FileTypes.BOOSTS);
		for (var map : list) {
			if (map.get(playerName).toString().equals("\"" + name + "\"")) {
				map.put(bst.getName(), Integer.parseInt(map.get(bst.getName()).toString()) + 1);
			}
		}
		pm.update(list, FileTypes.BOOSTS);
	}
}
