package net.agentgaming.mcwar;

import org.bukkit.entity.Player;

import java.util.HashMap;

public class CoolDownManager {
    private HashMap<String, HashMap<Player, Long>> cooldowns;

    public CoolDownManager() {
        cooldowns = new HashMap<>();
    }

    public void setCoolDown(String key, Player p, Integer ticks) {
        int ms = (ticks * 20) * 1000;
        if(!cooldowns.containsKey(key)) {
            HashMap<Player, Long> cooldown = new HashMap<Player, Long>();
            cooldown.put(p, System.currentTimeMillis() + ms);
            cooldowns.put(key, cooldown);
            return;
        } else {
            cooldowns.get(key).put(p, System.currentTimeMillis() + ms);
        }
    }

    public void resetCooldown(String key, Player p) {
        setCoolDown(key, p, -1);
    }

    public void resetAllCooldowns() {
        for(String k1 : cooldowns.keySet()) {
            for(Player k : cooldowns.get(k1).keySet()) {
                cooldowns.get(k1).put(k, System.currentTimeMillis() - 20000);
            }
        }
    }

    public boolean isCooledDown(String key, Player p) {
        if(!cooldowns.containsKey(key)) return true;
        if(!cooldowns.get(key).containsKey(p)) return true;
        return System.currentTimeMillis() > cooldowns.get(key).get(p);
    }
}
