package net.agentgaming.mcwar;

import net.agentgaming.mcwar.classes.MCWarClass;

import java.util.ArrayList;

public class MCWarPlayer {
    private Integer kills;
    private Integer deaths;
    private Integer scores;

    private ArrayList<String> firstSpecs;

    public MCWarPlayer() {
        kills = 0;
        deaths = 0;
        scores = 0;
        firstSpecs = new ArrayList<>();
    }

    public Integer getKills() {
        return kills;
    }

    public void setKills(Integer kills) {
        this.kills = kills;
    }

    public Integer getDeaths() {
        return deaths;
    }

    public void setDeaths(Integer deaths) {
        this.deaths = deaths;
    }

    public Integer getScores() {
        return scores;
    }

    public void setScores(Integer scores) {
        this.scores = scores;
    }

    public boolean hasFirstSpec(MCWarClass mwc) {
        return firstSpecs.contains(mwc.getName());
    }

    public void addFirstSpec(MCWarClass mwc) {
        firstSpecs.add(mwc.getName());
    }

    public void removeFirstSpec(MCWarClass mwc) {
        firstSpecs.remove(mwc.getName());
    }
}
