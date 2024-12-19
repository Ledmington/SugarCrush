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
 * A class that implements {@link PlayerManager}
 *
 * @author Emanuele Lamagna
 */
public final class PlayerManagerImpl implements PlayerManager {

	// creates the File to be used to create the folder where the json files will be
	final File folder = new File(System.getProperty("user.home"), ".sugarcrush");
	// a map that keeps a filetype as key, and a triple of <path, file, jsonArray> as value
	private final Map<FileTypes, Triple<String, File, JsonArray>> filesMap = new HashMap<>() {
		{
			put(
					STATS,
					new Triple<>(
							folder.toString() + File.separator + "stats.json",
							new File(folder, "stats.json"),
							new JsonArray()));
			put(
					BOOSTS,
					new Triple<>(
							folder.toString() + File.separator + "boosts.json",
							new File(folder, "boosts.json"),
							new JsonArray()));
		}
	};

	public final void addPlayer(final String name) {
		Objects.requireNonNull(name);
		this.stringCheck(name);
		final Map<FileTypes, JsonObject> obMap = new HashMap<>();
		obMap.put(STATS, new JsonObject());
		obMap.put(BOOSTS, new JsonObject());
		this.initializeProperties(obMap.get(STATS), obMap.get(BOOSTS), name);
		this.createFiles(
				this.filesMap.get(STATS).getY(), this.filesMap.get(BOOSTS).getY());
		// for every file type, it adds the player
		Stream.of(FileTypes.values()).forEach(type -> {
			if (filesMap.get(type).getY().length() != 0) {
				final JsonParser parser = new JsonParser();
				try (final FileReader reader = new FileReader(filesMap.get(type).getX())) {
					filesMap.put(
							type,
							new Triple<>(
									filesMap.get(type).getX(),
									filesMap.get(type).getY(),
									(JsonArray) parser.parse(reader)));
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if (!filesMap.get(type).getZ().contains(obMap.get(type))) {
				filesMap.get(type).getZ().add(obMap.get(type));
				try (final FileWriter fileName =
						new FileWriter(filesMap.get(type).getX())) {
					fileName.write(filesMap.get(type).getZ().toString());
					fileName.flush();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		});
	}

	public final void setStat(final String name, final Status status, final int level) {
		Objects.requireNonNull(name);
		Objects.requireNonNull(status);
		Objects.requireNonNull(level);
		this.levelCheck(level);
		this.stringCheck(name);
		final String lev = new String("level" + level + "Score");
		this.createFiles(
				this.filesMap.get(STATS).getY(), this.filesMap.get(BOOSTS).getY());
		final JsonParser parser = new JsonParser();
		try (final FileReader reader = new FileReader(this.filesMap.get(STATS).getX())) {
			this.filesMap.put(
					STATS,
					new Triple<>(
							this.filesMap.get(STATS).getX(),
							this.filesMap.get(STATS).getY(),
							(JsonArray) parser.parse(reader)));
		} catch (IOException e) {
			e.printStackTrace();
		}
		// put every single information in the JsonObject
		this.filesMap.get(STATS).getZ().forEach(jse -> {
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
							StatsTypes.money.name(),
							jso.get(StatsTypes.money.name()).getAsInt() + status.getMoney());
					jso.keySet().stream()
							.filter(s -> s.equals(lev)
									&& Integer.parseInt(jso.get(lev).toString()) < status.getScore())
							.forEach(s -> jso.addProperty(s, status.getScore()));
				}
				// resets totalScore, to recalculate it from scratch
				jso.addProperty(StatsTypes.totalScore.name(), 0);
				IntStream.range(1, 11)
						.forEach(i -> jso.addProperty(
								StatsTypes.totalScore.name(),
								jso.get("level" + i + "Score").getAsInt()
										+ jso.get(StatsTypes.totalScore.name()).getAsInt()));
			}
		});
		// writes in stats.json
		try (final FileWriter fileName = new FileWriter(this.filesMap.get(STATS).getX())) {
			fileName.write(this.filesMap.get(STATS).getZ().toString());
			fileName.flush();
		} catch (IOException e) {
			e.printStackTrace();
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
		// Writes in the correct file, depending by the file typs passed as parameter
		try (final FileWriter fileName = new FileWriter(filesMap.get(type).getX())) {
			fileName.write(jse.toString());
			fileName.flush();
		} catch (final IOException e) {
			throw new RuntimeException(e);
		}
	}

	public  List<Map<String, Object>> getPlayers(final FileTypes type) {
		Objects.requireNonNull(type);
		final List<Map<String, Object>> list = new LinkedList<>();
		this.createFiles(
				this.filesMap.get(STATS).getY(), this.filesMap.get(BOOSTS).getY());
		// Initializes the list checking the correct file type
		if (filesMap.get(type).getY().length() != 0) {
			final JsonParser parser = new JsonParser();
			try (final FileReader reader = new FileReader(filesMap.get(type).getX())) {
				filesMap.put(
						type,
						new Triple<>(
								filesMap.get(type).getX(), filesMap.get(type).getY(), (JsonArray)
										parser.parse(reader)));
			} catch (final IOException e) {
				throw new RuntimeException(e);
			}
		}
		// adds every map to the list
		filesMap.get(type).getZ().forEach(jse -> {
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
		Stream.of(FileTypes.values()).forEach(type -> {
			JsonElement el = null;
			if (filesMap.get(type).getY().length() != 0) {
				final JsonParser parser = new JsonParser();
				try (final FileReader reader = new FileReader(filesMap.get(type).getX())) {
					filesMap.put(
							type,
							new Triple<>(
									filesMap.get(type).getX(),
									filesMap.get(type).getY(),
									(JsonArray) parser.parse(reader)));
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			for (JsonElement jse : filesMap.get(type).getZ()) {
				if (((JsonObject) jse).get(playerName).toString().equals("\"" + name + "\"")) {
					el = jse;
				}
			}
			if (el != null) {
				filesMap.get(type).getZ().remove(el);
			}
			try (final FileWriter fileName = new FileWriter(filesMap.get(type).getX())) {
				fileName.write(filesMap.get(type).getZ().toString());
				fileName.flush();
			} catch (IOException e) {
				e.printStackTrace();
			}
		});
	}

	// Initializes stats and boost
	private final void initializeProperties(final JsonObject player, final JsonObject boosts, final String name) {
		player.addProperty(playerName, name);
		boosts.addProperty(playerName, name);
		Stream.of(StatsTypes.values()).forEach(type -> player.addProperty(type.name(), 0));
		Stream.of(BoostsTypes.values()).forEach(type -> boosts.addProperty(type.name(), 0));
	}

	// Creates the files (if they don't exist)
	private final void createFiles(final File st, final File boo) {
		folder.mkdir();
		try {
			st.createNewFile();
			boo.createNewFile();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}

	// Checks if the string is a number
	private final boolean isNumber(final String s) {
		for (int i = 0; i < s.length(); i++) {
			if (!Character.isDigit(s.charAt(i))) {
				return false;
			}
		}
		return true;
	}

	// If the number of the level is < 1 or > 10, throws an exception
	private final void levelCheck(final int lev) {
		if (lev < 1 || lev > 10) {
			throw new IllegalArgumentException("Level must be between 1 and 10");
		}
	}

	// If the string is empty or contains '\', throws an exception
	private final void stringCheck(final String name) {
		if (name.equals("") || name.contains("\"")) {
			throw new IllegalArgumentException("Invalid name");
		}
	}
}
