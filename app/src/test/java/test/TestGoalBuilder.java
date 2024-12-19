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
