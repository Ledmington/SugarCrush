package test;

import java.util.List;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;

import static controller.Controller.playerName;
import controller.files.*;
import model.players.PlayerManagerImpl;
import model.shop.BoostShop;

/**
 * 
 * @author Davide Degli Esposti
 *
 */
public final class TestBoostShop {
	
    private BoostShop bs;
    private PlayerManagerImpl pm;

    @Before
    public final void prepare() {
        bs = new BoostShop();
        pm = new PlayerManagerImpl();
        bs.generateShop();
    }

    @Test
    public final void enoughMoney() {
        pm.addPlayer("Player");

        Integer moneyGained = 2000000;
        List<Map<String, Object>> list = pm.getPlayers(FileTypes.STATS);
        for(Map<String, Object> map: list) {
            if(map.get(playerName).toString().equals("\"Player\"")) {
                map.put(StatsTypes.money.name(), Integer.parseInt(map.get(StatsTypes.money.name()).toString()) + moneyGained);
            }
        }
        pm.update(list, FileTypes.STATS);

        bs.payment("Player", bs.getBoosts().get(0));
        pm.removePlayer("Player");
    }

    @Test (expected = IllegalStateException.class)
    public final void notEnoughMoney() {
        pm.addPlayer("Player");
        List<Map<String, Object>> list = pm.getPlayers(FileTypes.STATS);
        for(Map<String, Object> map: list) {
            if(map.get(playerName).toString().equals("\"Player\"")) {
                map.put(StatsTypes.money.name(), 0);
                break;
            }
        }
        pm.update(list, FileTypes.STATS);

        bs.payment("Player", bs.getBoosts().get(1));
        pm.removePlayer("Player");
    }

    @Test (expected = IllegalStateException.class)
    public final void playerNotRegistered() {
        bs.payment("notRegisteredPlayer", bs.getBoosts().get(1));
    }



}
