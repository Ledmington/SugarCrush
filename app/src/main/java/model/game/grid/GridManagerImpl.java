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
package model.game.grid;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.random.RandomGenerator;
import java.util.random.RandomGeneratorFactory;

import controller.Controller;
import model.game.grid.candies.Candy;
import model.game.grid.candies.CandyBuilderImpl;
import model.game.grid.candies.CandyColors;
import model.game.grid.candies.CandyFactory;
import model.game.grid.candies.CandyTypes;
import model.game.grid.shapes.ShapeCoordinates;
import model.game.grid.shapes.Shapes;
import model.score.Status;
import utils.Point2D;

/** @author Filippo Benvenuti */
public final class GridManagerImpl implements GridManager {

	private static final RandomGenerator rng =
			RandomGeneratorFactory.getDefault().create(System.nanoTime());

	private final Map<Point2D, Optional<Candy>> grid;
	private final Status score;
	private final List<CandyColors> spawnedCandyColors;
	private boolean updateScore;
	private final Controller controller;
	private boolean chocolateNeedUpdate = false;
	private Optional<Map<Point2D, Integer>> jelly;

	public GridManagerImpl(
			final Controller controller,
			final Map<Point2D, Optional<Candy>> initialGrid,
			final Status score,
			final List<CandyColors> colors,
			final boolean jelly) {
		this.controller = Objects.requireNonNull(controller);
		this.grid = new HashMap<>(Objects.requireNonNull(initialGrid));
		this.spawnedCandyColors = Objects.requireNonNull(colors);
		this.score = Objects.requireNonNull(score);

		this.updateScore = false;
		this.jelly = Optional.empty();
		this.dropCandies();
		this.updateScore = true;

		if (jelly) {
			this.jelly = Optional.of(new HashMap<>());
			this.grid.forEach((crd, cnd) -> this.jelly.orElseThrow().put(crd, 2));
		}
	}

	@Override
	public Map<Point2D, Optional<Candy>> getGrid() {
		return new HashMap<>(this.grid);
	}

	@Override
	public boolean move(final Point2D cndA, final Point2D cndB) {
		// Presence of coordinates.
		if ((!this.grid.containsKey(cndA)) || (!this.grid.containsKey(cndB))) {
			return false;
		}
		// Coordinates near.
		if (Math.abs(cndA.x() - cndB.x()) + Math.abs(cndA.y() - cndB.y()) != 1) {
			return false;
		}
		// Candies type (chocolate can't be moved).
		if (this.grid.get(cndA).orElseThrow().getType() == CandyTypes.CHOCOLATE
				|| this.grid.get(cndB).orElseThrow().getType() == CandyTypes.CHOCOLATE) {
			return false;
		}

		// Swap of candies.
		Optional<Candy> tmp = this.grid.get(cndB);
		this.grid.put(cndB, this.grid.get(cndA));
		this.grid.put(cndA, tmp);

		//	if (!this.controller.isStageEnded()) {
		this.controller.updateGrid();
		//	}

		boolean niceMove = this.mergeTwoCandies(cndA, cndB);
		if (niceMove) {
			this.dropCandies();
		}
		niceMove = (this.searchDestroyShapes() || niceMove);
		if (niceMove) {
			score.setJelly();
			this.score.updateMoves();
			if (this.chocolateNeedUpdate) {
				this.updateChocolate();
			}
			this.chocolateNeedUpdate = true;
			while (this.getHint() == null) {
				this.shuffle();
				this.searchDestroyShapes();
			}
			return true;
		}
		// Bad swap (no shape found) recover initial state.
		tmp = this.grid.get(cndB);
		this.grid.put(cndB, this.grid.get(cndA));
		this.grid.put(cndA, tmp);
		controller.getSound().playSound("negative_switch_sound1");

		//	if (!this.controller.isStageEnded()) {
		this.controller.updateGrid();
		//	}

		return false;
	}

	@Override
	public boolean forceMove(final Point2D cndA, final Point2D cndB) {
		// Presence of coordinates.
		if ((!this.grid.containsKey(cndA)) || (!this.grid.containsKey(cndB))) {
			return false;
		}
		// Coordinates near.
		if (Math.abs(cndA.x() - cndB.x()) + Math.abs(cndA.y() - cndB.y()) != 1) {
			return false;
		}

		// Swap of candies.
		Optional<Candy> tmp = this.grid.get(cndB);
		this.grid.put(cndB, this.grid.get(cndA));
		this.grid.put(cndA, tmp);

		//	if (!this.controller.isStageEnded()) {
		this.controller.updateGrid();
		//	}

		this.searchDestroyShapes();
		if (this.chocolateNeedUpdate) {
			this.updateChocolate();
		}
		this.chocolateNeedUpdate = true;

		//	if (!this.controller.isStageEnded()) {
		this.controller.updateGrid();
		//	}

		return true;
	}

	@Override
	public boolean mutateCandy(final Point2D cord, final Candy cnd) {
		// Presence of coordinates.
		if (!this.grid.containsKey(cord)) {
			return false;
		}
		// Chocolate can't be mutated.
		if (this.grid.get(cord).orElseThrow().getType() == CandyTypes.CHOCOLATE) {
			return false;
		}

		// Mutation.
		this.grid.put(cord, Optional.of(cnd));

		//	if (!this.controller.isStageEnded()) {
		this.controller.updateGrid();
		//	}

		return true;
	}

	@Override
	public boolean destroyCandy(final Point2D cord) {
		// Coordinates not contained in grid.
		if (!this.grid.containsKey(cord)) {
			return false;
		}
		// Candy already destroyed.
		if (this.grid.get(cord).isEmpty()) {
			return false;
		}

		if (this.grid.get(cord).orElseThrow().getType() == CandyTypes.CHOCOLATE) {
			this.chocolateNeedUpdate = false;
		}

		// Candies to destroy.
		List<Point2D> cndDestroy = new ArrayList<>();

		// Different behavior for each candy.
		switch (this.grid.get(cord).orElseThrow().getType()) {
			case STRIPED_VERTICAL:
				controller.getSound().playSound("line_blast1");
				this.grid.forEach((crd, cnd) -> {
					// Same column.
					if (crd.y() == cord.y()) {
						cndDestroy.add(crd);
					}
				});
				break;
			case STRIPED_HORIZONTAL:
				controller.getSound().playSound("line_blast1");
				this.grid.forEach((crd, cnd) -> {
					// Same row.
					if (crd.x() == cord.x()) {
						cndDestroy.add(crd);
					}
				});
				break;
			case WRAPPED:
				controller.getSound().playSound("bomb_sound1");
				this.grid.forEach((crd, cnd) -> {
					final int dx = Math.abs(crd.x() - cord.x());
					final int dy = Math.abs(crd.y() - cord.y());

					// Contour (even diagonally).
					if ((dx == 0 || dx == 1) && (dy == 0 || dy == 1)) {
						cndDestroy.add(crd);
					}
				});
				break;
			case FRECKLES:
				controller.getSound().playSound("colour_bomb1");
				this.grid.forEach((crd, cnd) -> {
					// Candy not already destroyed.
					if (this.grid.get(crd).isPresent()) {
						// Same color.
						if (cnd.orElseThrow().getColor()
								== this.grid.get(cord).orElseThrow().getColor()) {
							cndDestroy.add(crd);
						}
					}
				});
				break;
			case CHOCOLATE:
				// Inform score that chocolate has been destroyed.
				this.score.update(this.grid.get(cord).orElseThrow());
				this.grid.put(cord, Optional.empty());
				return true;
			default:
				break;
		}
		// Inform score that this candy has been destroyed.
		if (this.updateScore) {
			this.score.update(this.grid.get(cord).orElseThrow());
		}
		// Destroy main candy.
		this.grid.put(cord, Optional.empty());

		// Update jelly on this candy.
		if (this.jelly.isPresent()) {
			this.jelly.orElseThrow().put(cord, Math.max(this.jelly.orElseThrow().get(cord) - 1, 0));
		}

		//	if (!this.controller.isStageEnded()) {
		this.controller.updateGrid();
		//	}

		// Destroy candies.
		for (final Point2D crd : cndDestroy) {
			this.destroyCandy(crd);
		}
		// Destroy chocolate near.
		this.destroyChocolateAround(cord);
		return true;
	}

	public Optional<Map<Point2D, Integer>> getJelly() {
		// A simple getter of jelly.
		return this.jelly;
	}

	public List<Point2D> getHint() {
		// Every shape in order of importance.
		for (final Shapes shp : Shapes.values()) {
			ShapeCoordinates curShp = shp.getCoordinates();
			// For each shape we get every rotation.
			for (int rot = 0; rot < shp.getRotations(); rot++) {
				// For each rotation we get every near shape.
				for (final ShapeCoordinates nearShp : curShp.getNearCoordinateShapes()) {
					// For each near shape we scroll all the candies in the grid.
					for (final Map.Entry<Point2D, Optional<Candy>> entry : this.grid.entrySet()) {
						final Point2D crd = entry.getKey();
						final Optional<Candy> candy = entry.getValue();

						// If candy is present.
						if (candy.isPresent()) {
							// If shape can be created.
							if (this.shapePossible(curShp, crd)) {
								// If we find the shape.
								if (this.findShape(nearShp, crd, true)) {
									final List<Point2D> tmpList = nearShp.getRelativeCoordinates();
									// We move relative coordinates based on crd.
									tmpList.replaceAll(
											point2D -> new Point2D(point2D.x() + crd.x(), point2D.y() + crd.y()));
									// We return this list as a tip.
									return tmpList;
								}
							}
						}
					}
				}
				curShp = curShp.getNextRotatedCandyCoordinates();
			}
		}
		return null;
	}

	public void consumeRemainingMoves() {
		final List<CandyTypes> spawnable =
				Arrays.asList(CandyTypes.WRAPPED, CandyTypes.STRIPED_HORIZONTAL, CandyTypes.STRIPED_VERTICAL);

		// For each remaining move a random special candy is generated in a random
		// position.
		// Excluding positions already occupied.
		while (this.controller.getRemainingMoves() > 0) {
			// Consume a rem. move.
			this.score.updateMoves();

			Point2D tmp;
			do {
				tmp = this.pickRandomCandy();
			} while (this.grid.get(tmp).orElseThrow().getType() != CandyTypes.NORMAL);

			// Mutate the candy, same color but different type.
			this.mutateCandy(
					tmp,
					new CandyBuilderImpl()
							.setColor(this.grid.get(tmp).orElseThrow().getColor())
							.setType(spawnable.get(rng.nextInt(spawnable.size())))
							.build());
		}

		// Destroying special candies until no special candies are present.
		boolean anotherFound;
		do {
			anotherFound = false;
			for (final Map.Entry<Point2D, Optional<Candy>> entry : this.grid.entrySet()) {
				final Point2D crd = entry.getKey();
				final Optional<Candy> candy = entry.getValue();
				if (candy.isPresent()) {
					final Candy cnd = candy.orElseThrow();
					if (cnd.getType() != CandyTypes.NORMAL && cnd.getType() != CandyTypes.CHOCOLATE) {
						// Special candy found, destroy that.
						this.destroyCandy(crd);
						anotherFound = true;
					}
				}
			}

			// Make candies drop, search for shape, repeat until no special candies found.
			this.dropCandies();
		} while (anotherFound);
	}

	public Status getCurrentScore() {
		return this.score;
	}

	private boolean searchDestroyShapes() {
		boolean shapeFound = false;

		// For each shape (ordered) we search for corresponds.
		for (final Shapes shp : Shapes.values()) {
			ShapeCoordinates shpCrd = shp.getCoordinates();
			// For each shape we get every rotation.
			for (int rot = 0; rot < shp.getRotations(); rot++) {
				// For each rotation we scroll every candy in the grid.
				for (final Map.Entry<Point2D, Optional<Candy>> entry : this.grid.entrySet()) {
					final Point2D crd = entry.getKey();
					final Optional<Candy> candy = entry.getValue();
					// If candy is present.
					if (candy.isPresent()) {
						final CandyColors cndCol = candy.orElseThrow().getColor();
						// If shape is found we destroy the shape.
						if (this.findShape(shpCrd, crd, false)) {
							shapeFound = true;
							// Inform score that this shape has been destroyed.
							if (this.updateScore) {
								this.score.update(shp);
							}
							// For each coordinate in shape we destroy candy.
							for (final Point2D relCrd : shpCrd.getRelativeCoordinates()) {
								// Destroying candies in shape.
								this.destroyCandy(new Point2D(crd.x() + relCrd.x(), crd.y() + relCrd.y()));
							}
							// Adding special candy if needed.
							switch (shp.getCandyType()) {
								case FRECKLES:
									this.grid.put(crd, Optional.of(CandyFactory.getFreckles()));
									if (this.updateScore) {
										controller.getSound().playSound("colour_bomb_created");
									}
									break;
								case STRIPED_HORIZONTAL:
									this.grid.put(crd, Optional.of(CandyFactory.getHorizontalStriped(cndCol)));
									if (this.updateScore) {
										controller.getSound().playSound("striped_candy_created1");
									}
									break;
								case STRIPED_VERTICAL:
									this.grid.put(crd, Optional.of(CandyFactory.getVerticalStripedCandy(cndCol)));
									if (this.updateScore) {
										controller.getSound().playSound("striped_candy_created1");
									}
									break;
								case WRAPPED:
									this.grid.put(crd, Optional.of(CandyFactory.getWrapped(cndCol)));
									if (this.updateScore) {
										controller.getSound().playSound("wrapped_candy_created1");
									}
									break;
								default:
									this.destroyCandy(crd);
									break;
							}
							this.controller.updateGrid();
						}
					}
				}
				shpCrd = shpCrd.getNextRotatedCandyCoordinates();
			}
		}
		// If at least one shape is found, we have to make candies drop.
		if (shapeFound) {
			this.dropCandies();
		}
		return shapeFound;
	}

	private void dropCandies() {
		while (this.grid.containsValue(Optional.empty())) {
			this.grid.forEach((crd, cnd) -> {
				if (cnd.isEmpty()) {
					// If found a candy on top of it, bring it down, else generate a new one.
					Optional<Candy> tmp = Optional.empty();
					boolean found = false;
					for (int i = 1; i <= crd.x(); i++) {
						final Point2D p = new Point2D(crd.x() - i, crd.y());

						if (this.grid.containsKey(p)) {
							// If candy is empty, we don't need to drop that one.
							if (this.grid.get(p).isEmpty()) {
								found = true;
								break;
							}
							// Candy found.
							tmp = this.grid.get(p);
							// Here upper candy disappears.
							this.grid.put(p, Optional.empty());
							found = true;
							break;
						}
					}
					// If no candy was found, we generate a new one.
					if (!found) {
						tmp = Optional.of(CandyFactory.getNormalCandy(
								this.spawnedCandyColors.get(rng.nextInt(this.spawnedCandyColors.size()))));
					}

					if (tmp.isPresent()) {
						//	if (!this.controller.isStageEnded()) {
						this.controller.updateGrid();
						//	}
					}

					// Candy at his new place.
					this.grid.put(crd, tmp);
				}
			});
		}
		this.searchDestroyShapes();
	}

	private void destroyChocolateAround(final Point2D cord) {
		final List<Point2D> relativeMovements = Arrays.asList( // Up - Down - Left - Right
				new Point2D(-1, 0), new Point2D(1, 0), new Point2D(0, -1), new Point2D(0, 1));

		for (final Point2D rel : relativeMovements) {
			final Point2D tmp = new Point2D(cord.x() + rel.x(), cord.y() + rel.y());
			// Coordinates in grid.
			if (this.grid.containsKey(tmp)) {
				// Is not empty.
				if (this.grid.get(tmp).isPresent()) {
					// Is chocolate.
					if (this.grid.get(tmp).orElseThrow().getType() == CandyTypes.CHOCOLATE) {
						this.chocolateNeedUpdate = false;
						// Inform score that chocolate has been destroyed.
						this.score.update(this.grid.get(tmp).orElseThrow());
						// Destroy the chocolate.
						this.destroyCandy(tmp);
						controller.getSound().playSound("chocolate_removed");
					}
				}
			}
		}
		//	if (!this.controller.isStageEnded()) {
		this.controller.updateGrid();
		//	}
	}

	private boolean findShape(final ShapeCoordinates shc, final Point2D crd, final boolean near) {
		// Saving last relative coordinates checked.
		Optional<Point2D> last = Optional.empty();
		// For each relative coordinate we check if candy is the same as original one.
		for (final Point2D relCrd : shc.getRelativeCoordinates()) {
			final Point2D relCord = new Point2D(crd.x() + relCrd.x(), crd.y() + relCrd.y());

			// The first one can be checked by itself.
			if (last.isEmpty()) {
				last = Optional.of(relCord);
			}

			// Coordinates in grid.
			if (!this.grid.containsKey(relCord)) {
				return false;
			}
			// Coordinates not empty.
			if (this.grid.get(relCord).isEmpty()) {
				return false;
			}
			// Not chocolate.
			if (this.grid.get(relCord).orElseThrow().getType() == CandyTypes.CHOCOLATE) {
				return false;
			}
			// Not freckles.
			if (this.grid.get(relCord).orElseThrow().getType() == CandyTypes.FRECKLES) {
				return false;
			}
			// Same colour.
			// If the shape is near, we check for the match of colours with last relative
			// coordinates.
			// Else
			// We check for the match of colours with the original one.
			if ((near)
					? this.grid.get(last.orElseThrow()).orElseThrow().getColor()
							!= this.grid.get(relCord).orElseThrow().getColor()
					: this.grid.get(crd).orElseThrow().getColor()
							!= this.grid.get(relCord).orElseThrow().getColor()) {
				return false;
			}
		}
		return true;
	}

	private void updateChocolate() {
		final List<Point2D> relMov = Arrays.asList( // Up - Down - Left - Right
				new Point2D(-1, 0), new Point2D(1, 0), new Point2D(0, -1), new Point2D(0, 1));
		// Choose random chocolate candy.
		final List<Point2D> chcList = new ArrayList<>();
		// Filling chcList with chocolate coordinates (that have no chocolate neighbour).
		for (final Map.Entry<Point2D, Optional<Candy>> entry : this.grid.entrySet()) {
			final Point2D crd = entry.getKey();
			final Optional<Candy> candy = entry.getValue();
			// Not empty.
			if (candy.isPresent()) {
				// Is chocolate.
				if (candy.orElseThrow().getType() == CandyTypes.CHOCOLATE) {
					// Has a non-chocolate neighbour.
					boolean onlyChocolate = true;
					Point2D tmp;
					for (final Point2D rel : relMov) {
						tmp = new Point2D(crd.x() + rel.x(), crd.y() + rel.y());
						// Coordinates inside grid.
						if (this.grid.containsKey(tmp)) {
							// Not empty.
							if (this.grid.get(tmp).isPresent()) {
								// Is non chocolate.
								if (this.grid.get(tmp).orElseThrow().getType() != CandyTypes.CHOCOLATE) {
									onlyChocolate = false;
								}
							}
						}
					}
					// If not only chocolate near.
					if (!onlyChocolate) {
						chcList.add(crd);
					}
				}
			}
		}
		if (chcList.isEmpty()) {
			return;
		}
		// Select random chocolate.
		final Point2D crdRandom = chcList.get(rng.nextInt(chcList.size()));
		Point2D crdToChocolize;
		// Select a random neighbour.
		do {
			int rndInd = rng.nextInt(relMov.size());
			crdToChocolize = new Point2D(
					crdRandom.x() + relMov.get(rndInd).x(),
					crdRandom.y() + relMov.get(rndInd).y());
		} while ((!this.grid.containsKey(crdToChocolize))
				|| this.grid.get(crdToChocolize).isEmpty()
				|| this.grid.get(crdToChocolize).orElseThrow().getType() == CandyTypes.CHOCOLATE);
		// Found a non-chocolate neighbour.
		// CHOCOLIZE IT!!!!
		this.grid.put(crdToChocolize, Optional.of(CandyFactory.getChocolate()));
		controller.getSound().playSound("chocolate_grows");

		//	if (!this.controller.isStageEnded()) {
		this.controller.updateGrid();
		//	}
	}

	private void shuffle() {
		// Random number of times (200 may be enough?).
		int times = rng.nextInt(200);
		while (times > 0) {
			// Choose 2 normal candies and swap them.
			Point2D a, b;
			do {
				a = pickRandomCandy();
			} while (this.grid.get(a).orElseThrow().getType() != CandyTypes.NORMAL);
			do {
				b = pickRandomCandy();
			} while (this.grid.get(b).orElseThrow().getType() != CandyTypes.NORMAL);

			times--;

			// Swap candies.
			Optional<Candy> tmp;
			tmp = this.grid.get(a);
			this.grid.put(a, this.grid.get(b));
			this.grid.put(b, tmp);
		}

		//	if (!this.controller.isStageEnded()) {
		this.controller.updateGrid();
		//	}
	}

	private Point2D pickRandomCandy() {
		// Iterate a random number of times in the map.
		// Random pos.
		int pos = rng.nextInt(this.grid.size());
		// Iterator.
		final Iterator<Point2D> it = this.grid.keySet().iterator();
		// Getting pos° candy.
		while (pos > 0) {
			it.next();
			pos--;
		}
		return it.next();
	}

	private boolean mergeTwoCandies(final Point2D a, final Point2D b) {
		final CandyTypes aT = this.grid.get(a).orElseThrow().getType();
		final CandyTypes bT = this.grid.get(b).orElseThrow().getType();
		// In case they are the same.
		if (aT == bT
				|| (aT == CandyTypes.STRIPED_HORIZONTAL && bT == CandyTypes.STRIPED_VERTICAL)
				|| (aT == CandyTypes.STRIPED_VERTICAL && bT == CandyTypes.STRIPED_HORIZONTAL)) {
			final List<Point2D> toDestroy = new ArrayList<>();
			switch (aT) {
				case FRECKLES:
					// Just destroy every candy.
					this.grid.forEach((crd, cnd) -> toDestroy.add(crd));
					// Destroy all candies.
					for (final Point2D x : toDestroy) {
						this.destroyCandy(x);
					}
					return true;

				case WRAPPED:
					// Destroy candies near (24 around) (grid 5x5).
					this.grid.forEach((crd, cnd) -> {
						// If enough near.
						if (Math.abs(crd.x() - b.x()) <= 2 && Math.abs(crd.y() - b.y()) <= 2) {
							toDestroy.add(crd);
						}
					});
					// Destroy all candies in toDestroy.
					for (final Point2D x : toDestroy) {
						this.destroyCandy(x);
					}
					return true;

				case STRIPED_HORIZONTAL:
				case STRIPED_VERTICAL:
					// Destroy all candies in same row or same column.
					this.grid.forEach((crd, cnd) -> {
						// If same row or same column.
						if (crd.x() == b.x() || crd.y() == b.y()) {
							toDestroy.add(crd);
						}
					});
					// Destroy all candies.
					for (final Point2D x : toDestroy) {
						this.destroyCandy(x);
					}
					return true;

				default:
					return false;
			}
		}

		// If they are different, checks are easier with a map.
		Map<CandyTypes, Point2D> types = new HashMap<>();
		types.put(this.grid.get(a).orElseThrow().getType(), a);
		types.put(this.grid.get(b).orElseThrow().getType(), b);

		// No chocolate allowed.
		if (types.containsKey(CandyTypes.CHOCOLATE)) {
			return false;
		}

		// One is freckles.
		if (types.containsKey(CandyTypes.FRECKLES)) {
			// Remove freckles one.
			final Point2D freckCord = types.get(CandyTypes.FRECKLES);
			types.remove(CandyTypes.FRECKLES);
			final Candy tmp = this.grid.get(types.values().iterator().next()).orElseThrow();

			final List<Point2D> toDestroy = new ArrayList<>();

			// Mutate normal candies into other type candy.
			for (final Map.Entry<Point2D, Optional<Candy>> cnd : this.grid.entrySet()) {
				// Same colors.
				if (cnd.getValue().orElseThrow().getColor() == tmp.getColor()) {
					toDestroy.add(cnd.getKey());
					// If normal candy gets mutated.
					if (cnd.getValue().orElseThrow().getType() == CandyTypes.NORMAL) {
						// Mutate candy, same color but different type.
						this.mutateCandy(
								cnd.getKey(),
								new CandyBuilderImpl()
										.setColor(cnd.getValue().orElseThrow().getColor())
										.setType(tmp.getType())
										.build());
					}
				}
			}
			this.grid.put(freckCord, Optional.empty());
			// Destroy all candies in toDestroy.
			for (final Point2D x : toDestroy) {
				this.destroyCandy(x);
			}
			return true;
		}

		// One is wrapped.
		if (types.containsKey(CandyTypes.WRAPPED)) {
			types.remove(CandyTypes.WRAPPED);
			Point2D tmp = types.values().iterator().next();

			final List<Point2D> toDestroy = new ArrayList<>();

			// If merged with striped.
			if (this.grid.get(tmp).orElseThrow().getType() == CandyTypes.STRIPED_HORIZONTAL
					|| this.grid.get(tmp).orElseThrow().getType() == CandyTypes.STRIPED_VERTICAL) {
				// Destroy all candies in the same row or column (Thick of 3).
				this.grid.forEach((crd, cnd) -> {
					// If in same column or row (Thick of 3).
					if ((crd.x() >= b.x() - 1 && crd.x() <= b.x() + 1)
							|| (crd.y() >= b.y() - 1 && crd.y() <= b.y() + 1)) {
						toDestroy.add(crd);
					}
				});
				// Destroy all candies in toDestroy.
				for (final Point2D x : toDestroy) {
					this.destroyCandy(x);
				}
				return true;
			}
			return false;
		}
		return false;
	}

	private boolean shapePossible(final ShapeCoordinates shp, final Point2D crd) {
		final List<Point2D> lstCrd = shp.getRelativeCoordinates();
		// Adding implicit coordinate 0, 0.
		lstCrd.add(new Point2D(0, 0));
		for (final Point2D cord : lstCrd) {
			final Point2D relCrd = new Point2D(cord.x() + crd.x(), cord.y() + crd.y());
			// Not inside the grid.
			if (!this.grid.containsKey(relCrd)) {
				return false;
			}
			// Not present.
			if (this.grid.get(relCrd).isEmpty()) {
				return false;
			}
			// Is chocolate.
			if (this.grid.get(relCrd).orElseThrow().getType() == CandyTypes.CHOCOLATE) {
				return false;
			}
		}
		return true;
	}
}
