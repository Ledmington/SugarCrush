package model.game.level;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.function.Supplier;

import controller.Controller;
import model.game.grid.candies.Candy;
import model.game.grid.candies.CandyColors;
import model.game.grid.candies.CandyFactory;
import model.game.grid.candies.CandyFactoryImpl;
import model.game.level.stage.Stage;
import model.game.level.stage.StageBuilderImpl;
import model.objectives.ChallengeBuilder;
import model.objectives.ChallengeBuilderImpl;
import model.objectives.ObjectiveBuilder;
import model.objectives.ObjectiveBuilderImpl;
import model.objectives.ObjectiveFactory;
import model.objectives.ObjectiveFactoryImpl;

import utils.Point2D;

/**
 * A basic implementation of {@link LevelsManager}.
 * @author Filippo Barbari
 *
 */
public final class LevelsManagerImpl implements LevelsManager {

    /*
     * Instead of saving directly the {@link Level}, i save a {@link Supplier}
     * with the construction of the {@link Level}.
     * Saving {@link Level}s this way optimizes memory usage.
     */
    private final Map<Integer, Supplier<Level>> normalLevels = new HashMap<>();
    private final Supplier<Level> tutorial;

    public LevelsManagerImpl(final Controller controller) {
        super();
        final ObjectiveFactory of = new ObjectiveFactoryImpl();
        final CandyFactory cf = new CandyFactoryImpl();

        /*
         * SCHEME OF TUTORIAL, STAGE 1
         *    G Y B
         *    Y O B
         *    G Y G
         */
        final Map<Point2D, Candy> tutorialMap1 = new HashMap<>();
        tutorialMap1.put(new Point2D(0,0), cf.getNormalCandy(CandyColors.GREEN));
        tutorialMap1.put(new Point2D(0,1), cf.getNormalCandy(CandyColors.YELLOW));
        tutorialMap1.put(new Point2D(0,2), cf.getNormalCandy(CandyColors.BLUE));
        tutorialMap1.put(new Point2D(1,0), cf.getNormalCandy(CandyColors.YELLOW));
        tutorialMap1.put(new Point2D(1,1), cf.getNormalCandy(CandyColors.ORANGE));
        tutorialMap1.put(new Point2D(1,2), cf.getNormalCandy(CandyColors.BLUE));
        tutorialMap1.put(new Point2D(2,0), cf.getNormalCandy(CandyColors.GREEN));
        tutorialMap1.put(new Point2D(2,1), cf.getNormalCandy(CandyColors.YELLOW));
        tutorialMap1.put(new Point2D(2,2), cf.getNormalCandy(CandyColors.GREEN));

        /*
         * SCHEME OF TUTORIAL, STAGE 2
         *    O G O B
         *    B G O O
         *    B Y Y B
         *    Y O Y Y
         */
        final Map<Point2D, Candy> tutorialMap2 = new HashMap<>();
        tutorialMap2.put(new Point2D(0,0), cf.getNormalCandy(CandyColors.ORANGE));
        tutorialMap2.put(new Point2D(0,1), cf.getNormalCandy(CandyColors.GREEN));
        tutorialMap2.put(new Point2D(0,2), cf.getNormalCandy(CandyColors.ORANGE));
        tutorialMap2.put(new Point2D(0,3), cf.getNormalCandy(CandyColors.BLUE));
        tutorialMap2.put(new Point2D(1,0), cf.getNormalCandy(CandyColors.BLUE));
        tutorialMap2.put(new Point2D(1,1), cf.getNormalCandy(CandyColors.GREEN));
        tutorialMap2.put(new Point2D(1,2), cf.getNormalCandy(CandyColors.ORANGE));
        tutorialMap2.put(new Point2D(1,3), cf.getNormalCandy(CandyColors.ORANGE));
        tutorialMap2.put(new Point2D(2,0), cf.getNormalCandy(CandyColors.BLUE));
        tutorialMap2.put(new Point2D(2,1), cf.getNormalCandy(CandyColors.YELLOW));
        tutorialMap2.put(new Point2D(2,2), cf.getNormalCandy(CandyColors.YELLOW));
        tutorialMap2.put(new Point2D(2,3), cf.getNormalCandy(CandyColors.BLUE));
        tutorialMap2.put(new Point2D(3,0), cf.getNormalCandy(CandyColors.YELLOW));
        tutorialMap2.put(new Point2D(3,1), cf.getNormalCandy(CandyColors.ORANGE));
        tutorialMap2.put(new Point2D(3,2), cf.getNormalCandy(CandyColors.YELLOW));
        tutorialMap2.put(new Point2D(3,3), cf.getNormalCandy(CandyColors.YELLOW));

        /*
         * SCHEME OF TUTORIAL, STAGE 3
         *     ? ? ? ? ?
         *     ? ? ? ? ?
         *     ? ? S W ?
         *     ? ? ? ? ?
         *     ? ? ? ? ?
         */
        final Map<Point2D, Candy> tutorialMap3 = new HashMap<>();
        tutorialMap3.put(new Point2D(2,2), cf.getHorizontalStriped(CandyColors.BLUE));
        tutorialMap3.put(new Point2D(2,3), cf.getWrapped(CandyColors.RED));


        tutorial = () -> {
            ObjectiveBuilder ob = new ObjectiveBuilderImpl();
            final Stage s1 = new StageBuilderImpl()
                    .setDimensions(3, 3)
                    .setCandies(tutorialMap1)
                    .setObjective(ob.setMaxMoves(1)
                            .setMaxScore(1)
                            .setChallenge(Optional.empty())
                            .setObjectiveText("Click on the yellow candy and then on the orange one. :)")
                            .build())
                    .setStartingMessage("This game has one simple rule: you have to create shapes with candies in order to make points.\n"
                            + "Try to click on the yellow candy and then on the orange one, they will swap.")
                    .setEndingMessage("Congratulations! You learned the basic rule of this game.\n"
                            + "Now let's move on to Stage 2.")
                    .setController(controller)
                    .build();

            ob = new ObjectiveBuilderImpl();
            ChallengeBuilder cb = new ChallengeBuilderImpl();

            final Stage s2 = new StageBuilderImpl()
                    .setDimensions(4,4)
                    .setCandies(tutorialMap2)
                    .setObjective(ob.setMaxMoves(10)
                            .setMaxScore(1)
                            .setChallenge(Optional.of(cb.setStriped(1)
                                    .build()))
                            .setObjectiveText("Try to create a 4-candies line by swapping the yellow candy with the one below.")
                            .build())
                    .setStartingMessage("Making shapes is good to make points, but if you create bigger shapes you can make more points!\n"
                            + "Try to make a 4-candies line by swapping the yellow candy with the one below.")
                    .setEndingMessage("Congratulations! This is just the beginning, you can make many more shapes to get other special candies.\n"
                            + "You'll have time to try that. Now let's move on to the final Stage.")
                    .setController(controller)
                    .build();

            ob = new ObjectiveBuilderImpl();
            cb = new ChallengeBuilderImpl();

            final Stage s3 = new StageBuilderImpl()
                    .setDimensions(5,4)
                    .setCandies(tutorialMap3)
                    .addAvailableColor(CandyColors.GREEN)
                    .addAvailableColor(CandyColors.ORANGE)
                    .setObjective(ob.setMaxMoves(10)
                            .setMaxScore(1)
                            .setChallenge(Optional.empty())
                            .setObjectiveText("Try to swap the two special candies.")
                            .build())
                    .setStartingMessage("If you swap two special candies, their special effects will produce more magic together.\n"
                            + "Try to swap those two and see what happens :)")
                    .setEndingMessage("That's incredible! You completed the tutorial!\n"
                            + "You're ready to face the hardest campaign ever made.\n"
                            + "Good luck ;)")
                    .setController(controller)
                    .build();

            return new LevelBuilderImpl()
                    .addStage(s1)
                    .addStage(s2)
                    .addStage(s3)
                    .setController(controller)
                    .build();
        };

        /*
         * Level 1 :
         * Normal square grid (6x6)
         * normal objective
         * 4 colors
         */
        this.normalLevels.put(1, () -> {
            return new LevelBuilderImpl()
                    .addStage(new StageBuilderImpl()
                            .setDimensions(6, 6)
                            .addAvailableColor(CandyColors.BLUE)
                            .addAvailableColor(CandyColors.RED)
                            .addAvailableColor(CandyColors.YELLOW)
                            .addAvailableColor(CandyColors.ORANGE)
                            .setObjective(of.normal())
                            .setController(controller)
                            .build())
                    .setController(controller)
                    .build();
        });

        /*
         * Level 2 :
         * Smaller grid (5x5) without corners
         * normal objective
         * 4 colors
         */
        this.normalLevels.put(2, () -> {
            final Set<Point2D> empty = new HashSet<>();
            empty.add(new Point2D(0,0));
            empty.add(new Point2D(0,4));
            empty.add(new Point2D(4,0));
            empty.add(new Point2D(4,4));

            return new LevelBuilderImpl()
                    .addStage(new StageBuilderImpl()
                            .setDimensions(5, 5)
                            .setEmptyCells(empty)
                            .addAvailableColor(CandyColors.BLUE)
                            .addAvailableColor(CandyColors.RED)
                            .addAvailableColor(CandyColors.YELLOW)
                            .addAvailableColor(CandyColors.ORANGE)
                            .setObjective(of.normal())
                            .setController(controller)
                            .build())
                    .setController(controller)
                    .build();
        });

        /*
         * Level 3 :
         * Normal square grid (6x6) without center
         * 'primary' objective
         * 4 colors
         */
        this.normalLevels.put(3, () -> {
            final Set<Point2D> empty = new HashSet<>();
            empty.add(new Point2D(2,2));
            empty.add(new Point2D(2,3));
            empty.add(new Point2D(3,2));
            empty.add(new Point2D(3,3));

            return new LevelBuilderImpl()
                    .addStage(new StageBuilderImpl()
                            .setDimensions(6, 6)
                            .setEmptyCells(empty)
                            .addAvailableColor(CandyColors.BLUE)
                            .addAvailableColor(CandyColors.RED)
                            .addAvailableColor(CandyColors.YELLOW)
                            .setObjective(of.primary())
                            .setController(controller)
                            .build())
                    .setController(controller)
                    .build();
        });

        /*
         * Level 4 :
         * big horizontal rectangle grid (6x8)
         * 'jelly' objective
         * 4 colors
         */
        this.normalLevels.put(4, () -> {
            return new LevelBuilderImpl()
                    .addStage(new StageBuilderImpl()
                            .setDimensions(6, 8)
                            .addJelly()
                            .addAvailableColor(CandyColors.BLUE)
                            .addAvailableColor(CandyColors.RED)
                            .addAvailableColor(CandyColors.YELLOW)
                            .addAvailableColor(CandyColors.ORANGE)
                            .setObjective(of.jelly())
                            .setController(controller)
                            .build())
                    .setController(controller)
                    .build();
        });

        /*
         * Level 5 :
         * big vertical rectangle grid (8x6) without corners
         * 'multiBombs' objective
         * chocolate (on bottom)
         * 4 colors
         */
        this.normalLevels.put(5, () -> {
            final Set<Point2D> empty = new HashSet<>();
            empty.add(new Point2D(0,0));
            empty.add(new Point2D(0,5));
            empty.add(new Point2D(7,0));
            empty.add(new Point2D(7,5));

            return new LevelBuilderImpl()
                    .addStage(new StageBuilderImpl()
                            .setDimensions(8,6)
                            .setEmptyCells(empty)
                            .addChocolatePosition(new Point2D(4,4))
                            .addAvailableColor(CandyColors.BLUE)
                            .addAvailableColor(CandyColors.RED)
                            .addAvailableColor(CandyColors.YELLOW)
                            .addAvailableColor(CandyColors.ORANGE)
                            .setObjective(of.multiBombs())
                            .setController(controller)
                            .build())
                    .setController(controller)
                    .build();
        });

        /*
         * Level 6:
         * normal square grid (6x6)
         * 'lineParty' objective
         * chocolate in bottom-left corner
         * 5 colors
         */
        this.normalLevels.put(6, () -> {
            return new LevelBuilderImpl()
                    .addStage(new StageBuilderImpl()
                            .setDimensions(6, 6)
                            .addChocolatePosition(new Point2D(5,5))
                            .addAvailableColor(CandyColors.BLUE)
                            .addAvailableColor(CandyColors.RED)
                            .addAvailableColor(CandyColors.YELLOW)
                            .addAvailableColor(CandyColors.ORANGE)
                            .addAvailableColor(CandyColors.GREEN)
                            .setObjective(of.lineParty())
                            .setController(controller)
                            .build())
                    .setController(controller)
                    .build();
        });

        /*
         * Level 7:
         * smaller square grid (5x5)
         * 'explode' objective
         * 5 colors
         */
        this.normalLevels.put(7, () -> {
            return new LevelBuilderImpl()
                    .addStage(new StageBuilderImpl()
                            .setDimensions(5, 5)
                            .addAvailableColor(CandyColors.BLUE)
                            .addAvailableColor(CandyColors.RED)
                            .addAvailableColor(CandyColors.YELLOW)
                            .addAvailableColor(CandyColors.ORANGE)
                            .addAvailableColor(CandyColors.GREEN)
                            .setObjective(of.explode())
                            .setController(controller)
                            .build())
                    .setController(controller)
                    .build();
        });

        /*
         * Level 8:
         * big square grid (8x8)
         * chocolate in one position
         * 'lineParty' objective
         * 6 colors
         */
        this.normalLevels.put(8, () -> {
            return new LevelBuilderImpl()
                    .addStage(new StageBuilderImpl()
                            .setDimensions(8, 8)
                            .addAvailableColor(CandyColors.BLUE)
                            .addAvailableColor(CandyColors.RED)
                            .addAvailableColor(CandyColors.YELLOW)
                            .addAvailableColor(CandyColors.ORANGE)
                            .addAvailableColor(CandyColors.GREEN)
                            .addAvailableColor(CandyColors.PURPLE)
                            .addChocolatePosition(new Point2D(7, 2))
                            .setObjective(of.lineParty())
                            .setController(controller)
                            .build())
                    .setController(controller)
                    .build();
        });

        /*
         * Level 9:
         * big square grid (8x8)
         * empty cells set as defined below
         * chocolate in one position in bottom line
         * 'jelly' objective
         * 6 colors
         * 
         * GRID:
         * (X means empty, O means not empty)
         *  01234567
         *  XOOOOOOX 0
         *  OOOOOOOO 1
         *  OOXOOXOO 2
         *  OOOOOOOO 3
         *  OOOOOOOO 4
         *  OOXOOXOO 5
         *  OOOOOOOO 6
         *  XOOOOOOX 7
         */
        this.normalLevels.put(9, () -> {
            final Set<Point2D> empty = new HashSet<>();
            empty.add(new Point2D(0,0));
            empty.add(new Point2D(0,7));
            empty.add(new Point2D(7,0));
            empty.add(new Point2D(7,7));
            empty.add(new Point2D(2,2));
            empty.add(new Point2D(2,5));
            empty.add(new Point2D(5,2));
            empty.add(new Point2D(5,5));

            return new LevelBuilderImpl()
                    .addStage(new StageBuilderImpl()
                            .setDimensions(8, 8)
                            .setEmptyCells(empty)
                            .addJelly()
                            .addAvailableColor(CandyColors.BLUE)
                            .addAvailableColor(CandyColors.RED)
                            .addAvailableColor(CandyColors.YELLOW)
                            .addAvailableColor(CandyColors.ORANGE)
                            .addAvailableColor(CandyColors.GREEN)
                            .addAvailableColor(CandyColors.PURPLE)
                            .setObjective(of.jelly())
                            .setController(controller)
                            .build())
                    .setController(controller)
                    .build();
        });

        /*
         * Level 10:
         * normal square grid (6x6)
         * chocolate in 6 positions (all positions in bottom line)
         * 'lineParty' objective
         * 6 colors
         */
        this.normalLevels.put(10, () -> {
            return new LevelBuilderImpl()
                    .addStage(new StageBuilderImpl()
                            .setDimensions(6, 6)
                            .addChocolatePosition(new Point2D(5,0))
                            .addChocolatePosition(new Point2D(5,1))
                            .addChocolatePosition(new Point2D(5,2))
                            .addChocolatePosition(new Point2D(5,3))
                            .addChocolatePosition(new Point2D(5,4))
                            .addChocolatePosition(new Point2D(5,5))
                            .addAvailableColor(CandyColors.BLUE)
                            .addAvailableColor(CandyColors.RED)
                            .addAvailableColor(CandyColors.YELLOW)
                            .addAvailableColor(CandyColors.ORANGE)
                            .addAvailableColor(CandyColors.GREEN)
                            .addAvailableColor(CandyColors.PURPLE)
                            .setObjective(of.lineParty())
                            .setController(controller)
                            .build())
                    .setController(controller)
                    .build();
        });
    }

    public final Level getLevel(final int levelNumber) {
        if(levelNumber<=0 || !this.normalLevels.containsKey(levelNumber)) {
            throw new IllegalStateException("Level number not valid.");
        }

        return this.normalLevels.get(levelNumber).get();
    }

    public final Level getTutorial() {
        return tutorial.get();
    }

    public final int getNumLevels() {
        return this.normalLevels.keySet().size();
    }

    @Override
    public final int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((normalLevels == null) ? 0 : normalLevels.hashCode());
        result = prime * result + ((tutorial == null) ? 0 : tutorial.hashCode());
        return result;
    }

    @Override
    public final boolean equals(final Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        LevelsManagerImpl other = (LevelsManagerImpl) obj;
        if (normalLevels == null) {
            if (other.normalLevels != null)
                return false;
        } else if (!normalLevels.equals(other.normalLevels))
            return false;
        if (tutorial == null) {
            if (other.tutorial != null)
                return false;
        } else if (!tutorial.equals(other.tutorial))
            return false;
        return true;
    }

    @Override
    public final String toString() {
        return "LevelsManager [normalLevels=" + normalLevels + ", tutorial=" + tutorial + "]";
    }

}