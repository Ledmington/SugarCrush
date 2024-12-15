package model.goals;

import java.util.List;
import java.util.Map;
import java.util.function.Predicate;
import controller.*;
import controller.files.FileTypes;
import static controller.Controller.playerName;
import model.players.PlayerManagerImpl;

/**
 * 
 * @author Davide Degli Esposti
 *
 */
public final class Goal {
	
    private final String title;										//is the main title of the goal
    private final String descr;										//is the short description of the goal
    private boolean reached;										//is the flag that is true if the agoal is reached
    private final Predicate<Map<String,Object>> method;				//the method for check if a goal is reached
    private final PlayerManagerImpl pm = new PlayerManagerImpl();	//variable to get the list of the players
    private final Controller cntrlImpl;								

    /**
     * constructor for initializing the achievement
     * @param title  the title of the goal
     * @param descr  the description of the goal
     * @param method  the method for check if a goal is reached
     */
    public Goal(final Controller controller, final String title, final String descr,
                final Predicate <Map<String,Object>> method){
        this.cntrlImpl = controller;
        this.title = title;
        this.descr = descr;
        this.reached = false;
        this.method = method;

    }

    /**
     * 
     * @return the title of the goal
     */
    public final String getTitle(){
        return this.title;
    }

    /**
     * 
     * @return the description of the goal
     */
    public final String getDescription(){
        return this.descr;
    }

    /**
     * 
     * @return true if the goal is reached
     */
    public final boolean isReached(){
        return this.reached;
    }

    /**
     * check through "method" if the goal is reached 
     * @return true if is reached
     */
    public final boolean checkIfReached() {
    	final List<Map<String,Object>> list = pm.getPlayers(FileTypes.STATS);
        for(var map: list) {
            if(map.get(playerName).toString().equals("\"" + cntrlImpl.getCurrentPlayer() + "\"")) {
                this.reached = this.method.test(map);
                break;
            }
        }
        return this.reached;
    }

}
