package model.goals;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import controller.*;
import controller.files.FileTypes;
import static controller.files.StatsTypes.*;
import static controller.Controller.playerName;
import model.players.PlayerManagerImpl;

/**
 * 
 * @author Davide Degli Esposti
 *
 */
public final class GoalManager {
	
    private final List<Goal> achievement = new ArrayList<>();					//list of all achievement of the player
    private GoalBuilderImpl gb;													//builder for the achievement
    private Controller cntrlImpl;												//variable to get the current player
    private Map<String,Object> mapCurrentPlayer;								//contains the map with the stats of the current player
    private final PlayerManagerImpl pm = new PlayerManagerImpl();				//variable to get the players

    /**
     * the constructor of the class
     */
    public GoalManager(final Controller controller) {
        this.cntrlImpl = controller;
        //first achievement
        gb = new GoalBuilderImpl();
        this.gb.setTitle("Gotta eat 'em all");
        this.gb.setDescr("Finish the first level");
        this.gb.setMethod(e -> {
            resetPlayerMap();
            if(Integer.parseInt(mapCurrentPlayer.get(level1Score.name()).toString())>0) {
                return true;
            }
            else {
                return false;
            }
        });
        this.gb.setController(cntrlImpl);
        this.achievement.add(this.gb.build());

        //second achievement
        gb = new GoalBuilderImpl();
        this.gb.setTitle("Nayan Ca...ndy!");
        this.gb.setDescr("Farm 5 freckles");
        this.gb.setMethod(e -> {
            resetPlayerMap();
            if(Integer.parseInt(mapCurrentPlayer.get(FRECKLES.name()).toString())>=5) {
                return true;
            }
            else {
                return false;
            }
        });
        this.gb.setController(cntrlImpl);
        this.achievement.add(this.gb.build());

        //third achievement
        gb = new GoalBuilderImpl();
        this.gb.setTitle("Be like Greta Thunberg ");
        this.gb.setDescr("Destroy 250 green candy");
        this.gb.setMethod(e -> {
            resetPlayerMap();
            if(Integer.parseInt(mapCurrentPlayer.get(GREEN.name()).toString())>=250) {
                return true;
            }
            else {
                return false;
            }
        });
        this.gb.setController(cntrlImpl);
        this.achievement.add(this.gb.build());

        //4th achievement
        gb = new GoalBuilderImpl();
        this.gb.setTitle("Im Blue...");
        this.gb.setDescr("Destroy 250 blue candy");
        this.gb.setMethod(e -> {
            resetPlayerMap();
            if(Integer.parseInt(mapCurrentPlayer.get(BLUE.name()).toString())>=250) {
                return true;
            }
            else {
                return false;
            }
        });
        this.gb.setController(cntrlImpl);
        this.achievement.add(this.gb.build());

        //5th achievement
        gb = new GoalBuilderImpl();
        this.gb.setTitle("...Da Ba Dee Da Ba Daa");
        this.gb.setDescr("Destroy 500 blue candy");
        this.gb.setMethod(e -> {
            resetPlayerMap();
            if(Integer.parseInt(mapCurrentPlayer.get(BLUE.name()).toString())>=500) {
                return true;
            }
            else {
                return false;
            }
        });
        this.gb.setController(cntrlImpl);
        this.achievement.add(this.gb.build());

        //6th achievement
        gb = new GoalBuilderImpl();
        this.gb.setTitle("A Red Bull for you");
        this.gb.setDescr("Destroy 250 red candy");
        this.gb.setMethod(e -> {
            resetPlayerMap();
            if(Integer.parseInt(mapCurrentPlayer.get(RED.name()).toString())>=250) {
                return true;
            }
            else {
                return false;
            }
        });
        this.gb.setController(cntrlImpl);
        this.achievement.add(this.gb.build());

        //7th achievement
        gb = new GoalBuilderImpl();
        this.gb.setTitle("Praise the Sun!!!");
        this.gb.setDescr("Destroy 250 yellow candy");
        this.gb.setMethod(e -> {
            resetPlayerMap();
            if(Integer.parseInt(mapCurrentPlayer.get(YELLOW.name()).toString())>=250) {
                return true;
            }
            else {
                return false;
            }
        });
        this.gb.setController(cntrlImpl);
        this.achievement.add(this.gb.build());

        //8th achievement
        gb = new GoalBuilderImpl();
        this.gb.setTitle("I am inevitable.");
        this.gb.setDescr("Destroy 250 purple candy");
        this.gb.setMethod(e -> {
            resetPlayerMap();
            if(Integer.parseInt(mapCurrentPlayer.get(PURPLE.name()).toString())>=250) {
                return true;
            }
            else {
                return false;
            }
        });
        this.gb.setController(cntrlImpl);
        this.achievement.add(this.gb.build());

        //9th achievement
        gb = new GoalBuilderImpl();
        this.gb.setTitle("Immune to Scurvy");
        this.gb.setDescr("Destroy 250 orange candy");
        this.gb.setMethod(e -> {
            resetPlayerMap();
            if(Integer.parseInt(mapCurrentPlayer.get(ORANGE.name()).toString())>=250) {
                return true;
            }
            else {
                return false;
            }
        });
        this.gb.setController(cntrlImpl);
        this.achievement.add(this.gb.build());

        //10th achievement
        gb = new GoalBuilderImpl();
        this.gb.setTitle("Lactose intolerance");
        this.gb.setDescr("Destroy 50 pieces of chocolate");
        this.gb.setMethod(e -> {
            resetPlayerMap();
            if(Integer.parseInt(mapCurrentPlayer.get(CHOCOLATE.name()).toString())>=50) {
                return true;
            }
            else {
                return false;
            }
        });
        this.gb.setController(cntrlImpl);
        this.achievement.add(this.gb.build());

        //11th achievement
        gb = new GoalBuilderImpl();
        this.gb.setTitle("THIS...IS...SUGAR CRUSH!!!");
        this.gb.setDescr("Destroy 300 candy");
        this.gb.setMethod(e -> {
            resetPlayerMap();
            if((Integer.parseInt(mapCurrentPlayer.get(BLUE.name()).toString())
                    + Integer.parseInt(mapCurrentPlayer.get(GREEN.name()).toString())
                    + Integer.parseInt(mapCurrentPlayer.get(ORANGE.name()).toString())
                    + Integer.parseInt(mapCurrentPlayer.get(PURPLE.name()).toString())
                    + Integer.parseInt(mapCurrentPlayer.get(RED.name()).toString())
                    + Integer.parseInt(mapCurrentPlayer.get(YELLOW.name()).toString()))>=300){
                return true;
            }
            else
            {
                return false;
            }
        });
        this.gb.setController(cntrlImpl);
        this.achievement.add(this.gb.build());



        //12th achievement
        gb = new GoalBuilderImpl();
        this.gb.setTitle("Finish!!");
        this.gb.setDescr("Finish the game");
        this.gb.setMethod(e -> {
            resetPlayerMap();
            if(Integer.parseInt(mapCurrentPlayer.get(level10Score.name()).toString())>0)
                return true;
            else {
                return false;
            }
        });
        this.gb.setController(cntrlImpl);
        this.achievement.add(this.gb.build());
    }
    
    /**
     * Tells the {@link GoalManager} to reset the internal data about current player.
     */
    public final void resetPlayerMap() {
        for(var map: pm.getPlayers(FileTypes.STATS)) {
            if(map.get(playerName).toString().equals("\"" + cntrlImpl.getCurrentPlayer() + "\"")) {
                mapCurrentPlayer = map;
                break;
            }
        }
    }

    /**
     * 
     * @return the list of achievements of the player
     */
    public final List<Goal> getAchievement() {
        return this.achievement;
    }

}
