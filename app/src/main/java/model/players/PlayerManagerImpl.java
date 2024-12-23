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
package model.players;

import static controller.Controller.playerName;
import static controller.files.FileTypes.*;

import java.io.*;
import java.util.*;
import java.util.stream.*;

import com.google.gson.*;

import controller.files.*;
import controller.files.FileTypes;
import model.game.grid.candies.CandyColors;
import model.score.Status;
import utils.Triple;

/**
 * A class that implements {@link PlayerManager}.
 *
 * @author Emanuele Lamagna
 */
public final class PlayerManagerImpl implements PlayerManager {

	// creates the File to be used to create the folder where the json files will be
	private static final File SUGAR_CRUSH_HOME = new File(System.getProperty("user.home"), ".sugarcrush");
	// a map that keeps a filetype as key, and a triple of <path, file, jsonArray> as value
	private static final Map<FileTypes, Triple<String, File, JsonArray>> FILES_MAP = new HashMap<>() {
		{
			put(
					STATS,
					new Triple<>(
							SUGAR_CRUSH_HOME + File.separator + "stats.json",
							new File(SUGAR_CRUSH_HOME, "stats.json"),
							new JsonArray()));
			put(
					BOOSTS,
					new Triple<>(
							SUGAR_CRUSH_HOME + File.separator + "boosts.json",
							new File(SUGAR_CRUSH_HOME, "boosts.json"),
							new JsonArray()));
		}
	};

	public void addPlayer(final String name) {
		Objects.requireNonNull(name);
		this.stringCheck(name);
		final Map<FileTypes, JsonObject> obMap = new HashMap<>();
		obMap.put(STATS, new JsonObject());
		obMap.put(BOOSTS, new JsonObject());
		this.initializeProperties(obMap.get(STATS), obMap.get(BOOSTS), name);
		this.createFiles(FILES_MAP.get(STATS).second(), FILES_MAP.get(BOOSTS).second());

		// for every file type, it adds the player
		for (final FileTypes type : FileTypes.values()) {
			if (FILES_MAP.get(type).second().length() != 0) {
				final JsonParser parser = new JsonParser();
				try (final FileReader reader =
						new FileReader(FILES_MAP.get(type).first())) {
					FILES_MAP.put(
							type,
							new Triple<>(
									FILES_MAP.get(type).first(),
									FILES_MAP.get(type).second(),
									(JsonArray) parser.parse(reader)));
				} catch (final IOException e) {
					throw new RuntimeException(e);
				}
			}
			if (!FILES_MAP.get(type).third().contains(obMap.get(type))) {
				FILES_MAP.get(type).third().add(obMap.get(type));
				try (final FileWriter fileName =
						new FileWriter(FILES_MAP.get(type).first())) {
					fileName.write(FILES_MAP.get(type).third().toString());
					fileName.flush();
				} catch (final IOException e) {
					throw new RuntimeException(e);
				}
			}
		}
	}

	public void setStat(final String name, final Status status, final int level) {
		Objects.requireNonNull(name);
		Objects.requireNonNull(status);
		this.levelCheck(level);
		this.stringCheck(name);
		final String lev = "LEVEL_" + level + "_SCORE";
		this.createFiles(FILES_MAP.get(STATS).second(), FILES_MAP.get(BOOSTS).second());
		final JsonParser parser = new JsonParser();
		try (final FileReader reader = new FileReader(FILES_MAP.get(STATS).first())) {
			FILES_MAP.put(
					STATS,
					new Triple<>(
							FILES_MAP.get(STATS).first(), FILES_MAP.get(STATS).second(), (JsonArray)
									parser.parse(reader)));
		} catch (final IOException e) {
			throw new RuntimeException(e);
		}
		// put every single information in the JsonObject
		FILES_MAP.get(STATS).third().forEach(jse -> {
			if (((JsonObject) jse).get(playerName).getAsString().equals(name)) {
				final JsonObject jso = (JsonObject) jse;
				Stream.of(CandyColors.values())
						.filter(color -> !color.equals(CandyColors.FRECKLES))
						.forEach(color -> jso.addProperty(
								color.name(), jso.get(color.name()).getAsInt() + status.getColors(color)));
				jso.addProperty(
						StatsTypes.FRECKLES.name(),
						jso.get(StatsTypes.FRECKLES.name()).getAsInt() + status.getTypes(StatsTypes.FRECKLES));
				jso.addProperty(
						StatsTypes.STRIPED.name(),
						jso.get(StatsTypes.STRIPED.name()).getAsInt() + status.getTypes(StatsTypes.STRIPED));
				jso.addProperty(
						StatsTypes.WRAPPED.name(),
						jso.get(StatsTypes.WRAPPED.name()).getAsInt() + status.getTypes(StatsTypes.WRAPPED));
				// only if the level is completed adds the money and, if the score is higher than the top score,
				// refreshes it
				if (status.isCompleted()) {
					jso.addProperty(
							StatsTypes.MONEY.name(),
							jso.get(StatsTypes.MONEY.name()).getAsInt() + status.getMoney());
					jso.keySet().stream()
							.filter(s -> s.equals(lev)
									&& Integer.parseInt(jso.get(lev).toString()) < status.getScore())
							.forEach(s -> jso.addProperty(s, status.getScore()));
				}
				// resets totalScore, to recalculate it from scratch
				jso.addProperty(StatsTypes.TOTAL_SCORE.name(), 0);
				IntStream.range(1, 11)
						.forEach(i -> jso.addProperty(
								StatsTypes.TOTAL_SCORE.name(),
								jso.get("LEVEL_" + i + "_SCORE").getAsInt()
										+ jso.get(StatsTypes.TOTAL_SCORE.name()).getAsInt()));
			}
		});
		// writes in stats.json
		try (final FileWriter fileName = new FileWriter(FILES_MAP.get(STATS).first())) {
			fileName.write(FILES_MAP.get(STATS).third().toString());
			fileName.flush();
		} catch (final IOException e) {
			throw new RuntimeException(e);
		}
	}

	public void update(final List<Map<String, Object>> list, final FileTypes type) {
		Objects.requireNonNull(list);
		Objects.requireNonNull(type);
		final JsonArray jse = new JsonArray();
		// for every map in the list, updates the JsonArray
		list.forEach(map -> {
			final JsonObject jso = new JsonObject();
			map.keySet().forEach(s -> {
				if (this.isNumber(map.get(s).toString())) {
					jso.addProperty(s, Integer.parseInt(map.get(s).toString()));
				} else {
					jso.addProperty(s, map.get(s).toString().replaceAll("\"", ""));
				}
			});
			jse.add(jso);
		});
		// Writes in the correct file, depending on the file types passed as parameter
		try (final FileWriter fileName = new FileWriter(FILES_MAP.get(type).first())) {
			fileName.write(jse.toString());
			fileName.flush();
		} catch (final IOException e) {
			throw new RuntimeException(e);
		}
	}

	public List<Map<String, Object>> getPlayers(final FileTypes type) {
		Objects.requireNonNull(type);
		final List<Map<String, Object>> list = new LinkedList<>();
		this.createFiles(FILES_MAP.get(STATS).second(), FILES_MAP.get(BOOSTS).second());
		// Initializes the list checking the correct file type
		if (FILES_MAP.get(type).second().length() != 0) {
			final JsonParser parser = new JsonParser();
			try (final FileReader reader = new FileReader(FILES_MAP.get(type).first())) {
				FILES_MAP.put(
						type,
						new Triple<>(
								FILES_MAP.get(type).first(), FILES_MAP.get(type).second(), (JsonArray)
										parser.parse(reader)));
			} catch (final IOException e) {
				throw new RuntimeException(e);
			}
		}
		// adds every map to the list
		FILES_MAP.get(type).third().forEach(jse -> {
			final Map<String, Object> map = new HashMap<>();
			((JsonObject) jse).keySet().forEach(s -> map.put(s, ((JsonObject) jse).get(s)));
			list.add(map);
		});
		return list;
	}

	public void removePlayer(final String name) {
		Objects.requireNonNull(name);
		this.stringCheck(name);

		// Removes the player in every file
		for (final FileTypes type : FileTypes.values()) {
			JsonElement el = null;
			if (FILES_MAP.get(type).second().length() != 0) {
				final JsonParser parser = new JsonParser();
				try (final FileReader reader =
						new FileReader(FILES_MAP.get(type).first())) {
					FILES_MAP.put(
							type,
							new Triple<>(
									FILES_MAP.get(type).first(),
									FILES_MAP.get(type).second(),
									(JsonArray) parser.parse(reader)));
				} catch (final IOException e) {
					throw new RuntimeException(e);
				}
			}
			for (final JsonElement jse : FILES_MAP.get(type).third()) {
				if (((JsonObject) jse).get(playerName).toString().equals("\"" + name + "\"")) {
					el = jse;
				}
			}
			if (el != null) {
				FILES_MAP.get(type).third().remove(el);
			}
			try (final FileWriter fileName = new FileWriter(FILES_MAP.get(type).first())) {
				fileName.write(FILES_MAP.get(type).third().toString());
				fileName.flush();
			} catch (final IOException e) {
				throw new RuntimeException(e);
			}
		}
	}

	// Initializes stats and boost
	private void initializeProperties(final JsonObject player, final JsonObject boosts, final String name) {
		player.addProperty(playerName, name);
		boosts.addProperty(playerName, name);
		Stream.of(StatsTypes.values()).forEach(type -> player.addProperty(type.name(), 0));
		Stream.of(BoostsTypes.values()).forEach(type -> boosts.addProperty(type.name(), 0));
	}

	// Creates the files (if they don't exist)
	private void createFiles(final File st, final File boo) {
		SUGAR_CRUSH_HOME.mkdir();
		try {
			st.createNewFile();
			boo.createNewFile();
		} catch (final IOException e1) {
			throw new RuntimeException(e1);
		}
	}

	// Checks if the string is a number
	private boolean isNumber(final String s) {
		for (int i = 0; i < s.length(); i++) {
			if (!Character.isDigit(s.charAt(i))) {
				return false;
			}
		}
		return true;
	}

	// If the number of the level is < 1 or > 10, throws an exception
	private void levelCheck(final int lev) {
		if (lev < 1 || lev > 10) {
			throw new IllegalArgumentException("Level must be between 1 and 10.");
		}
	}

	// If the string is empty or contains '\', throws an exception
	private void stringCheck(final String name) {
		if (name.isBlank() || name.contains("\"")) {
			throw new IllegalArgumentException("Invalid name.");
		}
	}
}
