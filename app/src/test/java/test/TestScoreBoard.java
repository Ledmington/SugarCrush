package test;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;

import static controller.Controller.playerName;
import controller.files.FileTypes;
import model.players.PlayerManagerImpl;
import model.rank.ScoreBoard;
import controller.files.*;
import utils.*;

/**
 * 
 * @author Davide Degli Esposti
 *
 */
public final class TestScoreBoard {

	private PlayerManagerImpl pm;
	private ScoreBoard sb;
	private List<Pair<String,Integer>> testList;
 
	
	@Before
    public final void prepare() {
    	sb = new ScoreBoard();
    	pm = new PlayerManagerImpl();
    	testList = new ArrayList<>();
    	testList.add(new Pair<>("\"davide\"", 138));
    	testList.add(new Pair<>("\"filippo\"", 90));
    	testList.add(new Pair<>("\"samuele\"", 0));
    }
	
	public final void addPlayerAndScore(final String name, final Integer score) {
		pm.addPlayer(name);
		List<Map<String, Object>> list = pm.getPlayers(FileTypes.STATS);
		for(Map<String, Object> map: list) {
			if(map.get(playerName).toString().equals("\"" + name + "\"")) {
				map.put(StatsTypes.totalScore.name(), Integer.parseInt(map.get(StatsTypes.totalScore.name()).toString()) + score);
			}
		}
		pm.update(list, FileTypes.STATS);
	}
	
	public final void addPlayerAndLvlScore(final String name, final Integer score) {
		pm.addPlayer(name);
		List<Map<String, Object>> list = pm.getPlayers(FileTypes.STATS);
		for(Map<String, Object> map: list) {
			if(map.get(playerName).toString().equals("\"" + name + "\"")) {
				map.put(StatsTypes.level3Score.name(), Integer.parseInt(map.get(StatsTypes.level3Score.name()).toString()) + score);
			}
		}
		pm.update(list, FileTypes.STATS);
	}
	
	@Test
	public final void testGeneralScore() {
		addPlayerAndScore("filippo", 90);
		addPlayerAndScore("samuele", 0);
		addPlayerAndScore("davide", 138);
		assertEquals(testList, sb.rankByGeneralScore());
		pm.removePlayer("davide");
		pm.removePlayer("filippo");
		pm.removePlayer("samuele");
	}
	
	@Test
	public final void testScoreByLevel() {
		addPlayerAndLvlScore("filippo", 90);
		addPlayerAndLvlScore("samuele", 0);
		addPlayerAndLvlScore("davide", 138);
		assertEquals(testList, sb.rankByScoreInLevel(3));
		pm.removePlayer("davide");
		pm.removePlayer("filippo");
		pm.removePlayer("samuele");
	}
}
