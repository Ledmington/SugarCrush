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

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import controller.Controller;
import controller.files.BoostsTypes;
import controller.files.FileTypes;
import controller.files.StatsTypes;
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
					FileTypes.STATS,
					new Triple<>(
							SUGAR_CRUSH_HOME + File.separator + "stats.json",
							new File(SUGAR_CRUSH_HOME, "stats.json"),
							new JsonArray()));
			put(
					FileTypes.BOOSTS,
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
		obMap.put(FileTypes.STATS, new JsonObject());
		obMap.put(FileTypes.BOOSTS, new JsonObject());
		this.initializeProperties(obMap.get(FileTypes.STATS), obMap.get(FileTypes.BOOSTS), name);
		this.createFiles(
				FILES_MAP.get(FileTypes.STATS).second(),
				FILES_MAP.get(FileTypes.BOOSTS).second());

		// for every file type, it adds the player
		for (final FileTypes type : FileTypes.values()) {
			if (FILES_MAP.get(type).second().length() != 0) {
				try (final BufferedReader reader =
						Files.newBufferedReader(Path.of(FILES_MAP.get(type).first()), StandardCharsets.UTF_8)) {
					FILES_MAP.put(
							type,
							new Triple<>(
									FILES_MAP.get(type).first(),
									FILES_MAP.get(type).second(),
									(JsonArray) JsonParser.parseReader(reader)));
				} catch (final IOException e) {
					throw new RuntimeException(e);
				}
			}
			if (!FILES_MAP.get(type).third().contains(obMap.get(type))) {
				FILES_MAP.get(type).third().add(obMap.get(type));
				try (final BufferedWriter bw =
						Files.newBufferedWriter(Path.of(FILES_MAP.get(type).first()), StandardCharsets.UTF_8)) {
					bw.write(FILES_MAP.get(type).third().toString());
					bw.flush();
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
		this.createFiles(
				FILES_MAP.get(FileTypes.STATS).second(),
				FILES_MAP.get(FileTypes.BOOSTS).second());
		try (final BufferedReader reader =
				Files.newBufferedReader(Path.of(FILES_MAP.get(FileTypes.STATS).first()), StandardCharsets.UTF_8)) {
			FILES_MAP.put(
					FileTypes.STATS,
					new Triple<>(
							FILES_MAP.get(FileTypes.STATS).first(),
							FILES_MAP.get(FileTypes.STATS).second(),
							(JsonArray) JsonParser.parseReader(reader)));
		} catch (final IOException e) {
			throw new RuntimeException(e);
		}
		// put every single information in the JsonObject
		FILES_MAP.get(FileTypes.STATS).third().forEach(jse -> {
			if (((JsonObject) jse).get(Controller.playerName).getAsString().equals(name)) {
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
		try (final FileWriter fileName =
				new FileWriter(FILES_MAP.get(FileTypes.STATS).first(), StandardCharsets.UTF_8)) {
			fileName.write(FILES_MAP.get(FileTypes.STATS).third().toString());
			fileName.flush();
		} catch (final IOException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
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
		try (final BufferedWriter bw =
				Files.newBufferedWriter(Path.of(FILES_MAP.get(type).first()), StandardCharsets.UTF_8)) {
			bw.write(jse.toString());
			bw.flush();
		} catch (final IOException e) {
			throw new RuntimeException(e);
		}
	}

	public List<Map<String, Object>> getPlayers(final FileTypes type) {
		Objects.requireNonNull(type);
		final List<Map<String, Object>> list = new LinkedList<>();
		this.createFiles(
				FILES_MAP.get(FileTypes.STATS).second(),
				FILES_MAP.get(FileTypes.BOOSTS).second());
		// Initializes the list checking the correct file type
		if (FILES_MAP.get(type).second().length() != 0) {
			try (final FileReader reader = new FileReader(FILES_MAP.get(type).first(), StandardCharsets.UTF_8)) {
				FILES_MAP.put(
						type,
						new Triple<>(
								FILES_MAP.get(type).first(), FILES_MAP.get(type).second(), (JsonArray)
										JsonParser.parseReader(reader)));
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

	@Override
	public void removePlayer(final String name) {
		Objects.requireNonNull(name);
		this.stringCheck(name);

		// Removes the player in every file
		for (final FileTypes type : FileTypes.values()) {
			JsonElement el = null;
			if (FILES_MAP.get(type).second().length() != 0) {
				try (final BufferedReader reader =
						Files.newBufferedReader(Path.of(FILES_MAP.get(type).first()), StandardCharsets.UTF_8)) {
					FILES_MAP.put(
							type,
							new Triple<>(
									FILES_MAP.get(type).first(),
									FILES_MAP.get(type).second(),
									(JsonArray) JsonParser.parseReader(reader)));
				} catch (final IOException e) {
					throw new RuntimeException(e);
				}
			}
			for (final JsonElement jse : FILES_MAP.get(type).third()) {
				if (((JsonObject) jse).get(Controller.playerName).toString().equals("\"" + name + "\"")) {
					el = jse;
				}
			}
			if (el != null) {
				FILES_MAP.get(type).third().remove(el);
			}
			try (final BufferedWriter bw =
					Files.newBufferedWriter(Path.of(FILES_MAP.get(type).first()), StandardCharsets.UTF_8)) {
				bw.write(FILES_MAP.get(type).third().toString());
				bw.flush();
			} catch (final IOException e) {
				throw new RuntimeException(e);
			}
		}
	}

	// Initializes stats and boost
	private void initializeProperties(final JsonObject player, final JsonObject boosts, final String name) {
		player.addProperty(Controller.playerName, name);
		boosts.addProperty(Controller.playerName, name);
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
