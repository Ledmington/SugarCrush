package test;

import org.junit.Before;
import org.junit.Test;

import controller.Controller;
import controller.ControllerImpl;
import model.goals.GoalBuilder;
import model.goals.GoalBuilderImpl;

public final class TestGoalBuilder {

	private GoalBuilder gb;
	private Controller ctrl;
	
	@Before
	public final void prepare() {
		this.gb = new GoalBuilderImpl();
		this.ctrl = new ControllerImpl();
	}
	
	@Test(expected = IllegalStateException.class)
	public final void titleCantBeAVoidString() {
		this.gb.setTitle("");
	}
	
	@Test(expected = IllegalStateException.class)
	public final void descrCantBeAVoidString() {
		this.gb.setDescr("");
	}
	
	@Test(expected = NullPointerException.class)
	public final void methodCantBeNull() {
		this.gb.setMethod(null);
	}
	
	@Test(expected = NullPointerException.class)
	public final void titleNeedToBeSet() {
		this.gb.setDescr("prova descrizione");
		this.gb.setMethod(e -> false);
		gb.build();
	}
	
	@Test(expected = NullPointerException.class)
	public final void descrNeedToBeSet() {
		this.gb.setTitle("prova titolo");
		this.gb.setMethod(e -> true);
		gb.build();
	}
	
	@Test(expected = NullPointerException.class)
	public final void methodNeedToBeSet() {
		this.gb.setDescr("prova descrizione");
		this.gb.setTitle("prova titolo");
		gb.build();
	}
	
	@Test(expected = IllegalStateException.class)
	public final void cantBuiltTwice() {
		this.gb.setDescr("prova descrizione");
		this.gb.setTitle("prova titolo");
		this.gb.setMethod(e -> false);
		this.gb.setController(ctrl);
		gb.build();
		gb.build();
	}
	
}
