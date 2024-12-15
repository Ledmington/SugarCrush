package model.rank;

import java.util.ArrayList;
import java.util.List;

import controller.files.FileTypes;
import controller.files.StatsTypes;

import static controller.Controller.playerName;
import model.players.*;
import utils.Pair;

/**
 * 
 * @author Davide Degli Esposti
 *
 */
public final class ScoreBoard {

    private final PlayerManagerImpl pl = new PlayerManagerImpl();	//variable for get player's properties
    private List<Pair<String,Integer>> rankPlayer;					//list for represent certain rank of player

    /**
     * 
     * @return list of players sorted by general score
     */
    public final List<Pair<String,Integer>> rankByGeneralScore(){
        this.rankPlayer = new ArrayList<>();
        pl.getPlayers(FileTypes.STATS).stream().sorted((a,b)-> Integer.valueOf((b.get(StatsTypes.totalScore.name())).toString()).compareTo(Integer.valueOf(a.get(StatsTypes.totalScore.name()).toString())))
        							  .forEach(p-> this.rankPlayer.add(new Pair<>(p.get(playerName).toString(),Integer.parseInt(p.get(StatsTypes.totalScore.name()).toString()))));

        return this.rankPlayer;
    }
    
    /**
     * @param lvlNumber  the level to show the rank
     * 
     * @return list of players sorted by score in the given level
     */
    public final List<Pair<String,Integer>> rankByScoreInLevel(final int lvlNumber){
        this.rankPlayer = new ArrayList<>();
        pl.getPlayers(FileTypes.STATS).stream().sorted((a,b)-> Integer.valueOf((b.get("level"+lvlNumber+"Score")).toString()).compareTo(Integer.valueOf(a.get("level"+lvlNumber+"Score").toString())))
		  							  .forEach(p-> this.rankPlayer.add(new Pair<>(p.get(playerName).toString(),Integer.parseInt(p.get("level"+lvlNumber+"Score").toString()))));

        return this.rankPlayer;
    }

}
