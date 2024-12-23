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
import java.io.Serial;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import controller.Controller;
import controller.image.ImageManager;
import controller.image.ImageManagerImpl;
import view.View;

/** @author Davide Degli Esposti */
public final class Shop extends GUI {

	@Serial
	private static final long serialVersionUID = -5474639315359936325L;

	private static final int MAX_ITEMS_SHOP = 4; // is the max number of boost on sale in the shop
	private static final JLabel titleLabel = new JLabel("SHOP", SwingConstants.CENTER); // title of the view

	private final JPanel mainPanel =
			new JPanel(new BorderLayout()); // the panel that contain all the element of the view
	private final JPanel shopPanel = new JPanel(); // panel that contain the item on sale
	private final transient List<JButton> itemBtns =
			new ArrayList<>(); // list of buttons that represents the boost on sale
	private final transient ImageManager im = new ImageManagerImpl(); // variable to get the image of the candy
	private int btnNotEnable = 4; // represents the number of button not enable
	private final JLabel moneyLabel =
			new JLabel("Money: " + controller.getCurrentMoney()); // show the current amount of money of the player

	Shop(final Controller controller, final View view) {
		super(controller, view);

		// initialize and add elements on the frame
		this.add(mainPanel);

		// North panel
		final JPanel northPanel = new JPanel(new BorderLayout());
		northPanel.add(titleLabel, BorderLayout.CENTER);
		northPanel.add(moneyLabel, BorderLayout.EAST);
		mainPanel.add(northPanel, BorderLayout.NORTH);

		titleLabel.setFont(new Font("System", Font.BOLD, 20));

		controller.resetShop();
		createShopView();

		mainPanel.setBackground(Color.green);
		// the button for turn back on main menu
		final JButton back = new JButton("Back");
		mainPanel.add(back, BorderLayout.SOUTH);

		// event on back button
		back.addActionListener(e -> {
			this.load(new MainMenu(controller, view));
			controller.getSound().playSound("button_press");
		});

		// event on each button that represents items on sale in the shop
		for (final JButton btn : itemBtns) {
			btn.addActionListener(e -> {
				controller.getSound().playSound("button_press");
				final int index;
				final JButton jb = (JButton) e.getSource();
				index = itemBtns.indexOf(jb);
				try {
					controller.pay(
							controller.getCurrentPlayerName(),
							controller.getBoostOnSale().get(index));
					itemBtns.get(index).setEnabled(false);
					btnNotEnable--;
					this.moneyLabel.setText("Money: " + controller.getCurrentMoney());
				} catch (IllegalStateException ex) {
					JOptionPane.showMessageDialog(null, ex.getMessage());
				}
			});
		}
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.pack();
	}

	/** create the view for the shop */
	private void createShopView() {
		shopPanel.setLayout(new GridLayout(2, MAX_ITEMS_SHOP));
		for (int i = 0; i < controller.getBoostOnSale().size(); i++) {
			itemBtns.add(new JButton());
			itemBtns.get(i)
					.setIcon(im.getCandyImage(controller.getBoostOnSale().get(i).getCandy()));
			shopPanel.add(itemBtns.get(i));
		}
		JLabel tmpLabel;
		for (int i = 0; i < controller.getBoostOnSale().size(); i++) {
			tmpLabel = new JLabel(controller.getBoostOnSale().get(i).getPrice().toString(), SwingConstants.CENTER);
			tmpLabel.setOpaque(true);
			tmpLabel.setBackground(Color.green);
			shopPanel.add(tmpLabel);
		}
		mainPanel.add(shopPanel, BorderLayout.CENTER);
	}

	/** @return true if the shop doesn't contain boost */
	public boolean isShopEmpty() {
		return btnNotEnable == 0 || itemBtns.isEmpty();
	}
}
