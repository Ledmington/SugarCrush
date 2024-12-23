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
import model.achievement.AchievementBuilder;
import model.achievement.AchievementBuilderImpl;

final class TestAchievementBuilder {

	private AchievementBuilder gb;
	private Controller ctrl;

	@BeforeEach
	public void prepare() {
		this.gb = new AchievementBuilderImpl();
		this.ctrl = new ControllerImpl();
	}

	@Test
	void titleCantBeAVoidString() {
		assertThrows(IllegalArgumentException.class, () -> this.gb.title(""));
	}

	@Test
	void descrCantBeAVoidString() {
		assertThrows(IllegalArgumentException.class, () -> this.gb.description(""));
	}

	@Test
	void methodCantBeNull() {
		assertThrows(NullPointerException.class, () -> this.gb.check(null));
	}

	@Test
	void titleNeedToBeSet() {
		this.gb.description("prova descrizione");
		this.gb.check(e -> false);
		assertThrows(NullPointerException.class, () -> gb.build());
	}

	@Test
	void descrNeedToBeSet() {
		this.gb.title("prova titolo");
		this.gb.check(e -> true);
		assertThrows(NullPointerException.class, () -> gb.build());
	}

	@Test
	void methodNeedToBeSet() {
		this.gb.description("prova descrizione");
		this.gb.title("prova titolo");
		assertThrows(NullPointerException.class, () -> gb.build());
	}

	@Test
	void cantBuildTwice() {
		this.gb.description("prova descrizione");
		this.gb.title("prova titolo");
		this.gb.check(e -> false);
		this.gb.setController(ctrl);
		gb.build();
		assertThrows(IllegalStateException.class, () -> gb.build());
	}
}
