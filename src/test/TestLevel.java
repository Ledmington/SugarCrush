package test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import controller.Controller;
import controller.ControllerImpl;
import model.game.grid.candies.CandyColors;
import model.game.level.Level;
import model.game.GameResult;
import model.game.level.LevelBuilderImpl;
import model.game.level.stage.Stage;
import model.game.level.stage.StageBuilderImpl;
import model.objectives.ObjectiveFactoryImpl;

/**
 * 
 * @author Filippo Barbari
 *
 */
public final class TestLevel {
    
    private Level l;
    public TestLevel() {}
    
    @Before
    public final void prepare() {
        final Controller controller = new ControllerImpl();
        final Stage s1 = new StageBuilderImpl()
                                .setDimensions(3, 3)
                                .addAvailableColor(CandyColors.BLUE)
                                .addAvailableColor(CandyColors.GREEN)
                                .addAvailableColor(CandyColors.ORANGE)
                                .addAvailableColor(CandyColors.PURPLE)
                                .setObjective(new ObjectiveFactoryImpl().explode())
                                .setController(controller)
                                .build();
        final Stage s2 = new StageBuilderImpl()
                                .setDimensions(3, 3)
                                .addAvailableColor(CandyColors.BLUE)
                                .addAvailableColor(CandyColors.GREEN)
                                .addAvailableColor(CandyColors.ORANGE)
                                .addAvailableColor(CandyColors.PURPLE)
                                .setObjective(new ObjectiveFactoryImpl().explode())
                                .setController(controller)
                                .build();
        l = new LevelBuilderImpl()
                .addStage(s1)
                .addStage(s2)
                .setController(controller)
                .build();
    }
    
    @Test
    public final void checkResult() {
        assertTrue(l.getResult() == GameResult.STILL_PLAYING);
    }
    
    @Test
    public final void stageIterationCorrect() {
        assertTrue(l.hasNextStage());
        l.nextStage();
        assertFalse(l.hasNextStage());
    }
    
}
