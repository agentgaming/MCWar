package net.agentgaming.mcwar.game;

import net.agentgaming.mcwar.classes.MCWarClass;
import org.bukkit.entity.Player;

import java.util.HashMap;

public class PlayerData {
    private Player p;

    private int kills;
    private int deaths;
    private int score;

    private MCWarClass c;

    private HashMap<MCWarClass, Integer> specs;

    public PlayerData(Player p) {
        this.p = p;
        kills = 0;
        deaths = 0;
        score = 0;
        this.c = null;
    }

    public int getKills() {
        return kills;
    }

    public void setKills(int kills) {
        this.kills = kills;
    }

    public int getDeaths() {
        return deaths;
    }

    public void setDeaths(int deaths) {
        this.deaths = deaths;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public MCWarClass getMCWarClass() {
        return c;
    }

    public void setMCWarClass(MCWarClass c) {
        this.c = c;
    }

    public boolean hasMCWarClass() {
        return this.c != null;
    }

    public void setSpec(MCWarClass c, Integer s) {
        specs.put(c, s);
        c.setSpec(s);
    }

    public Integer getSpec(MCWarClass c) {
        return specs.get(c);
    }

    public Player getPlayer() {
        return p;
    }
}
