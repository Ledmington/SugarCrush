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
	private boolean slowShow = false;
	private final JPanel gameGrid = new JPanel();
	private final JPanel mainPanel;
	private final transient Map<String, Integer> playerBoosts = controller.getObtatinedBoosts();
	private final transient List<JButton> boostsBtn = new LinkedList<>();
	private transient Optional<JButton> tmpCandy = Optional.empty();
	private transient Optional<String> boostSelected = Optional.empty();
	private final JLabel currentBoost = new JLabel();
	private final transient ActionListener candyAL = (e) -> {
		JButton bt = (JButton) e.getSource();

		// If clicked after having selected a boost
		if (boostSelected.isPresent()) {
			final String name = boostSelected.get();
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
				tmpCandy.get().setBackground(Color.WHITE);
				Thread thr = new Thread(() -> {
					controller.move(buttons.get(tmpCandy.get()), buttons.get(bt));
					tmpCandy = Optional.empty();
				});
				thr.start();
			}
		}
	};

	protected Game(final Controller controller, final View view) {
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
		labelsPanel.add(
				new JLabel("/ " + String.valueOf(controller.getObjective().getMaxScore()) + " "));
		labelsPanel.add(new JLabel("Rem. moves : "));
		labelsPanel.add(movesLabel);

		// West panel with boosts
		final JPanel westPanel = new JPanel();
		westPanel.setLayout(new BoxLayout(westPanel, BoxLayout.Y_AXIS));
		mainPanel.add(westPanel, BorderLayout.WEST);

		// Checking that at least one boost is available
		if (playerBoosts.keySet().stream().mapToInt(s -> playerBoosts.get(s)).sum() > 0) {

			// 'Boosts' title label
			final JLabel boosts = new JLabel("Boosts");
			westPanel.add(boosts);

			// Adding usable boosts
			for (String s : playerBoosts.keySet()) {
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
		objectiveButton.addActionListener(e -> {
			JOptionPane.showMessageDialog(null, controller.getObjective().objectiveText());
		});
		northPanel.add(objectiveButton, BorderLayout.EAST);

		if (controller.getObjective().getChallenge().isPresent()
				&& !controller.getObjective().getChallenge().get().isJellyToDestroy()) {
			labelsPanel.add(new JLabel("Progression: "));
			labelsPanel.add(progrLabel);
		}

		// Hint button.
		final JButton hintButton = new JButton("Hint");
		hintButton.addActionListener(e -> {
			new Thread(() -> {
						var cords = controller.getHint();
						SwingUtilities.invokeLater(() -> {
							buttons.forEach((btn, crd) -> {
								if (cords.contains(crd)) {
									btn.setBackground(Color.CYAN);
								}
							});
						});

						try {
							Thread.sleep(3000);
						} catch (InterruptedException e1) {
							e1.printStackTrace();
						}

						SwingUtilities.invokeLater(() -> {
							buttons.forEach((btn, crd) -> {
								if (cords.contains(crd)) {
									if (btn.getBackground() == Color.CYAN) {
										btn.setBackground(Color.WHITE);
									}
								}
							});
						});
					})
					.start();
		});
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

	private final void updateBoostsButtons() {
		final Iterator<String> it = playerBoosts.keySet().iterator();
		for (JButton jb : boostsBtn) {
			final String boostName = it.next();
			jb.setText(boostName + " (" + playerBoosts.get(boostName) + ")");
			if (playerBoosts.get(boostName) == 0) {
				jb.setEnabled(false);
			}
		}
	}

	private final void initializeGrid() {

		// Eliminating the previous grid
		gameGrid.removeAll();
		buttons.keySet().stream().forEach(jb -> jb.setVisible(false));
		buttons.clear();

		// Visualizing starting message
		if (controller.getStartingMessage().isPresent()) {
			JOptionPane.showMessageDialog(null, controller.getStartingMessage().get());
		}

		// Central grid of candies.
		final Point2D gridSize = controller.getGridSize();
		final Set<Point2D> positions = controller.getGrid().keySet();
		gameGrid.setLayout(new GridLayout(gridSize.getX(), gridSize.getY()));

		// For each coordinate
		for (int x = 0; x < gridSize.getX(); x++) {
			for (int y = 0; y < gridSize.getY(); y++) {
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

	private final void updateImages() {
		var grid = controller.getGrid();
		buttons.forEach((jbt, crd) -> {
			if (grid.get(crd).isPresent()) {
				jbt.setIcon(im.getCandyImage(grid.get(crd).get()));
			} else {
				jbt.setIcon(null);
			}
		});

		var jelly = controller.getJelly();
		if (jelly.isPresent()) {
			buttons.forEach((jbt, crd) -> {
				if (jbt.getBackground() != Color.YELLOW) {
					switch (jelly.get().get(crd)) {
						case 2:
							jbt.setBackground(Color.GRAY);
							break;
						case 1:
							jbt.setBackground(Color.LIGHT_GRAY);
							break;
						case 0:
							jbt.setBackground(Color.WHITE);
							break;
					}
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

	public final void updateGrid() {
		updateImages();

		scoreLabel.setText(String.format("%04d", controller.getCurrentScore().getScore()));
		movesLabel.setText(String.valueOf(controller.getRemainingMoves()));
		progrLabel.setText(Integer.toString((int) controller.getPercent()) + "%");

		if (!SwingUtilities.isEventDispatchThread()) {
			try {
				SwingUtilities.invokeAndWait(() -> {
					revalidate();
					repaint();
				});
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}
		if (slowShow) {
			try {
				Thread.sleep(50);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	public final void nextStage() {
		initializeGrid();
	}

	public final void stageEnd() {
		JOptionPane.showMessageDialog(null, controller.getResult());
		if (controller.getEndingMessage().isPresent()) {
			JOptionPane.showMessageDialog(null, controller.getEndingMessage().get());
		}
	}

	public final void levelEnd() {
		load(new Levels(controller, view));
	}
}
