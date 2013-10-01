package net.agentgaming.mcwar.game;

import org.bukkit.entity.Player;

import java.util.HashMap;

public class PlayerDataManager {
    private HashMap<Player, PlayerData> playerData;

    public PlayerDataManager() {
        playerData = new HashMap<Player, PlayerData>();
    }

    public boolean hasPlayerData(Player p) {
        return playerData.containsKey(p);
    }

    public PlayerData getPlayerData(Player p) {
        if(!hasPlayerData(p)) {
            addPlayerData(new PlayerData(p));
        }

        return playerData.get(p);
    }

    public void addPlayerData(PlayerData pd) {
        playerData.put(pd.getPlayer(), pd);
    }

    public void removePlayerData(Player p) {
        playerData.remove(p);
    }

    public void clearPlayerData() {
        playerData = new HashMap<Player, PlayerData>();
    }
}
