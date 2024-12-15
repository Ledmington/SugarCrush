package test;

import static org.junit.Assert.fail;

import org.junit.Before;
import org.junit.Test;

import controller.Controller;
import controller.ControllerImpl;
import model.game.grid.candies.CandyColors;
import model.game.level.LevelBuilder;
import model.game.level.LevelBuilderImpl;
import model.game.level.stage.StageBuilderImpl;
import model.objectives.ObjectiveFactoryImpl;

/**
 * 
 * @author Filippo Barbari
 *
 */
public final class TestLevelBuilder {
    
    private LevelBuilder lb;
    private Controller controller;
    
    public TestLevelBuilder() {}
    
    @Before
    public final void prepare() {
        lb = new LevelBuilderImpl();
        controller = new ControllerImpl();
    }
    
    @Test(expected = NullPointerException.class)
    public final void nullStage() {
        lb.addStage(null);
        fail("Passing null pointers shoudln't be allowed.");
    }
    
    @Test(expected = IllegalStateException.class)
    public final void cantBuildTwice() {
        lb.addStage(new StageBuilderImpl()
                            .setDimensions(3, 3)
                            .addAvailableColor(CandyColors.BLUE)
                            .addAvailableColor(CandyColors.GREEN)
                            .addAvailableColor(CandyColors.RED)
                            .setObjective(new ObjectiveFactoryImpl().explode())
                            .build())
            .build();
        lb.build();
        fail("Building the same Level twice shouldn't be allowed.");
    }
    
    @Test(expected = IllegalStateException.class)
    public final void emptyStages() {
        lb.build();
        fail("Building a Level with no Stages inside shouldn't be allowed.");
    }
    
    @Test
    public final void cantCallMethodsAfterBuilding() {
        lb.addStage(new StageBuilderImpl()
                            .setDimensions(3, 3)
                            .addAvailableColor(CandyColors.BLUE)
                            .addAvailableColor(CandyColors.GREEN)
                            .addAvailableColor(CandyColors.RED)
                            .setObjective(new ObjectiveFactoryImpl().explode())
                            .setController(controller)
                            .build())
            .setController(controller)
            .build();
        final String msg = "Calling methods after building shouldn't be allowed.";
        
        try {
            lb.addStage(new StageBuilderImpl()
                            .setDimensions(3, 3)
                            .addAvailableColor(CandyColors.BLUE)
                            .addAvailableColor(CandyColors.GREEN)
                            .addAvailableColor(CandyColors.RED)
                            .setObjective(new ObjectiveFactoryImpl().explode())
                            .setController(controller)
                            .build());
            fail(msg);
        } catch (IllegalStateException e) {}
        
        try {
            lb.setController(controller);
            fail(msg);
        } catch (IllegalStateException e) {}
    }
    
}
