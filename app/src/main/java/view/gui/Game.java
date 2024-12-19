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
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionListener;
import java.io.Serial;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import controller.Controller;
import controller.image.ImageManager;
import controller.image.ImageManagerImpl;
import model.game.grid.candies.Candy;
import utils.Point2D;
import view.View;

/**
 * The GUI effectively used for a game.
 *
 * @author Filippo Barbari
 * @author Filippo Benvenuti
 */
public final class Game extends GUI {

	@Serial
	private static final long serialVersionUID = 6519600363046645690L;

	private final JLabel scoreLabel = new JLabel();
	private final JLabel movesLabel = new JLabel();
	private final JLabel progrLabel = new JLabel("0%");
	private final transient Map<JButton, Point2D> buttons = new HashMap<>();
	private final transient ImageManager im = new ImageManagerImpl();
	private final boolean slowShow;
	private final JPanel gameGrid = new JPanel();
	private final JPanel mainPanel;
	private final transient Map<String, Integer> playerBoosts = controller.getObtatinedBoosts();
	private final transient List<JButton> boostsBtn = new LinkedList<>();
	private transient Optional<JButton> tmpCandy = Optional.empty();
	private transient Optional<String> boostSelected = Optional.empty();
	private final JLabel currentBoost = new JLabel();
	private final transient ActionListener candyAL = (e) -> {
		final JButton bt = (JButton) e.getSource();

		// If clicked after having selected a boost
		if (boostSelected.isPresent()) {
			final String name = boostSelected.orElseThrow();
			controller.useBoost(name, buttons.get(bt));

			// Decrementing boost counter
			playerBoosts.replace(name, playerBoosts.get(name) - 1);

			// Updating boosts' buttons
			updateBoostsButtons();

			boostSelected = Optional.empty();
			currentBoost.setText("(none)");
		} else {

			// If no candy is selected.
			if (tmpCandy.isEmpty()) {
				tmpCandy = Optional.of(bt);
				bt.setBackground(Color.YELLOW);
			} else {
				tmpCandy.orElseThrow().setBackground(Color.WHITE);
				final Thread thr = new Thread(() -> {
					controller.move(buttons.get(tmpCandy.orElseThrow()), buttons.get(bt));
					tmpCandy = Optional.empty();
				});
				thr.start();
			}
		}
	};

	Game(final Controller controller, final View view) {
		super(controller, view);

		// Main panel of jframe
		mainPanel = new JPanel(new BorderLayout());
		add(mainPanel);

		// North panel
		final JPanel northPanel = new JPanel(new BorderLayout());
		mainPanel.add(northPanel, BorderLayout.NORTH);

		// Panel with labels about remaining moves and score
		final JPanel labelsPanel = new JPanel(new FlowLayout());
		northPanel.add(labelsPanel, BorderLayout.CENTER);
		scoreLabel.setText(String.format("%04d", controller.getCurrentScore().getScore()));
		movesLabel.setText(String.valueOf(controller.getRemainingMoves()));
		labelsPanel.add(new JLabel("Score : "));
		labelsPanel.add(scoreLabel);
		labelsPanel.add(new JLabel("/ " + controller.getObjective().getMaxScore() + " "));
		labelsPanel.add(new JLabel("Rem. moves : "));
		labelsPanel.add(movesLabel);

		// West panel with boosts
		final JPanel westPanel = new JPanel();
		westPanel.setLayout(new BoxLayout(westPanel, BoxLayout.Y_AXIS));
		mainPanel.add(westPanel, BorderLayout.WEST);

		// Checking that at least one boost is available
		if (playerBoosts.keySet().stream().mapToInt(playerBoosts::get).sum() > 0) {

			// 'Boosts' title label
			final JLabel boosts = new JLabel("Boosts");
			westPanel.add(boosts);

			// Adding usable boosts
			for (final String s : playerBoosts.keySet()) {
				if (playerBoosts.get(s) > 0) {
					final JButton boost = new JButton(s + " (" + playerBoosts.get(s) + ")");
					westPanel.add(boost);
					boost.addActionListener(e -> {
						if (boostSelected.isEmpty()) {
							boostSelected = Optional.of(s);
							currentBoost.setText(s);
						} else {
							boostSelected = Optional.empty();
							currentBoost.setText("(none)");
						}
					});
					boostsBtn.add(boost);
					boost.setVisible(true);
				}
			}

			westPanel.add(new JLabel("Current boost:"));
			// Current boost label
			currentBoost.setText("(none)");
			westPanel.add(currentBoost);
		}

		// Objective button
		final JButton objectiveButton = new JButton("Objective");
		objectiveButton.addActionListener(e ->
				JOptionPane.showMessageDialog(null, controller.getObjective().objectiveText()));
		northPanel.add(objectiveButton, BorderLayout.EAST);

		if (controller.getObjective().getChallenge().isPresent()
				&& !controller.getObjective().getChallenge().orElseThrow().isJellyToDestroy()) {
			labelsPanel.add(new JLabel("Progression: "));
			labelsPanel.add(progrLabel);
		}

		// Hint button.
		final JButton hintButton = new JButton("Hint");
		hintButton.addActionListener(e -> new Thread(() -> {
					final List<Point2D> cords = controller.getHint();
					SwingUtilities.invokeLater(() -> buttons.forEach((btn, crd) -> {
						if (cords.contains(crd)) {
							btn.setBackground(Color.CYAN);
						}
					}));

					try {
						Thread.sleep(3000);
					} catch (final InterruptedException ex) {
						throw new RuntimeException(ex);
					}

					SwingUtilities.invokeLater(() -> buttons.forEach((btn, crd) -> {
						if (cords.contains(crd)) {
							if (btn.getBackground() == Color.CYAN) {
								btn.setBackground(Color.WHITE);
							}
						}
					}));
				})
				.start());
		northPanel.add(hintButton, BorderLayout.WEST);

		slowShow = true;

		initializeGrid();

		// South panel with back button.
		final JButton backButton = new JButton("Back");
		backButton.addActionListener(e -> {
			controller.levelEnd();
			load(new Levels(controller, view));
			controller.getSound().playSound("button_press");
		});
		mainPanel.add(backButton, BorderLayout.SOUTH);

		updateGrid();

		// Default on close.
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		pack();
	}

	private void updateBoostsButtons() {
		final Iterator<String> it = playerBoosts.keySet().iterator();
		for (final JButton jb : boostsBtn) {
			final String boostName = it.next();
			jb.setText(boostName + " (" + playerBoosts.get(boostName) + ")");
			if (playerBoosts.get(boostName) == 0) {
				jb.setEnabled(false);
			}
		}
	}

	private void initializeGrid() {
		// Eliminating the previous grid
		gameGrid.removeAll();
		buttons.keySet().forEach(jb -> jb.setVisible(false));
		buttons.clear();

		// Visualizing starting message
		if (controller.getStartingMessage().isPresent()) {
			JOptionPane.showMessageDialog(null, controller.getStartingMessage().orElseThrow());
		}

		// Central grid of candies.
		final Point2D gridSize = controller.getGridSize();
		final Set<Point2D> positions = controller.getGrid().keySet();
		gameGrid.setLayout(new GridLayout(gridSize.x(), gridSize.y()));

		// For each coordinate
		for (int x = 0; x < gridSize.x(); x++) {
			for (int y = 0; y < gridSize.y(); y++) {
				final JButton jb = new JButton();
				jb.setBorderPainted(false);
				jb.setFocusPainted(false);
				jb.setContentAreaFilled(false);
				gameGrid.add(jb);

				// If actual game grid does not contain position (x,y)
				if (!positions.contains(new Point2D(x, y))) {
					// Add an unusable JButton and don't add it to the 'buttons' map
					jb.setEnabled(false);
				} else {
					jb.setOpaque(true);
					jb.setEnabled(true);
					jb.setBackground(Color.WHITE);
					jb.addActionListener(candyAL);
					buttons.put(jb, new Point2D(x, y));
				}
			}
		}

		updateImages();

		mainPanel.add(gameGrid, BorderLayout.CENTER);
		pack();
	}

	private void updateImages() {
		final Map<Point2D, Optional<Candy>> grid = controller.getGrid();
		buttons.forEach((jbt, crd) -> {
			if (grid.get(crd).isPresent()) {
				jbt.setIcon(im.getCandyImage(grid.get(crd).orElseThrow()));
			} else {
				jbt.setIcon(null);
			}
		});

		final Optional<Map<Point2D, Integer>> jelly = controller.getJelly();
		if (jelly.isPresent()) {
			buttons.forEach((jbt, crd) -> {
				if (jbt.getBackground() != Color.YELLOW) {
					jbt.setBackground(
							switch (jelly.orElseThrow().get(crd)) {
								case 2 -> Color.GRAY;
								case 1 -> Color.LIGHT_GRAY;
								case 0 -> Color.WHITE;
								default -> throw new IllegalStateException("Unexpected value: "
										+ jelly.orElseThrow().get(crd));
							});
				}
			});
		} else {
			buttons.forEach((jbt, crd) -> {
				if (jbt.getBackground() != Color.YELLOW) {
					jbt.setBackground(Color.WHITE);
				}
			});
		}
	}

	public void updateGrid() {
		updateImages();

		scoreLabel.setText(String.format("%04d", controller.getCurrentScore().getScore()));
		movesLabel.setText(String.valueOf(controller.getRemainingMoves()));
		progrLabel.setText((int) controller.getPercent() + "%");

		if (!SwingUtilities.isEventDispatchThread()) {
			try {
				SwingUtilities.invokeAndWait(() -> {
					revalidate();
					repaint();
				});
			} catch (final Exception ex) {
				throw new RuntimeException(ex);
			}
		}
		if (slowShow) {
			try {
				Thread.sleep(50);
			} catch (final InterruptedException e) {
				throw new RuntimeException(e);
			}
		}
	}

	public void nextStage() {
		initializeGrid();
	}

	public void stageEnd() {
		JOptionPane.showMessageDialog(null, controller.getResult());
		if (controller.getEndingMessage().isPresent()) {
			JOptionPane.showMessageDialog(null, controller.getEndingMessage().orElseThrow());
		}
	}

	public void levelEnd() {
		load(new Levels(controller, view));
	}
}
