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

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import controller.Controller;
import controller.ControllerImpl;
import model.goals.GoalBuilder;
import model.goals.GoalBuilderImpl;

public final class TestGoalBuilder {

	private GoalBuilder gb;
	private Controller ctrl;

	@BeforeEach
	public void prepare() {
		this.gb = new GoalBuilderImpl();
		this.ctrl = new ControllerImpl();
	}

	@Test
	public void titleCantBeAVoidString() {
		assertThrows(IllegalStateException.class, () -> this.gb.setTitle(""));
	}

	@Test
	public void descrCantBeAVoidString() {
		assertThrows(IllegalStateException.class, () -> this.gb.setDescr(""));
	}

	@Test
	public void methodCantBeNull() {
		assertThrows(NullPointerException.class, () -> this.gb.setMethod(null));
	}

	@Test
	public void titleNeedToBeSet() {
		this.gb.setDescr("prova descrizione");
		this.gb.setMethod(e -> false);
		assertThrows(NullPointerException.class, () -> gb.build());
	}

	@Test
	public void descrNeedToBeSet() {
		this.gb.setTitle("prova titolo");
		this.gb.setMethod(e -> true);
		assertThrows(NullPointerException.class, () -> gb.build());
	}

	@Test
	public void methodNeedToBeSet() {
		this.gb.setDescr("prova descrizione");
		this.gb.setTitle("prova titolo");
		assertThrows(NullPointerException.class, () -> gb.build());
	}

	@Test
	public void cantBuildTwice() {
		this.gb.setDescr("prova descrizione");
		this.gb.setTitle("prova titolo");
		this.gb.setMethod(e -> false);
		this.gb.setController(ctrl);
		gb.build();
		assertThrows(IllegalStateException.class, () -> gb.build());
	}
}
