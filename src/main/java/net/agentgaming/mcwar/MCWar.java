package net.agentgaming.mcwar;

import com.mike724.motoapi.games.Game;
import com.mike724.motoapi.games.TeamManager;
import com.mike724.motoapi.push.ServerState;
import com.mike724.motoapi.push.ServerType;
import com.mike724.motoserver.MotoServer;
import net.agentgaming.mcwar.classes.MCWarClass;
import net.agentgaming.mcwar.classes.Mage;
import net.agentgaming.mcwar.events.ClassEvents;
import net.agentgaming.mcwar.game.LobbyState;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.HashMap;

public class MCWar extends JavaPlugin {
    private static MCWar instance;
    private CoolDownManager cdm;
    private Game game;

    private TeamManager teams;
    private boolean gameInProgress;
    private HashMap<Player, MCWarClass> playerClasses;

    private int redScore;
    private int blueScore;

    @Override
    public void onEnable() {
        //Set our instance
        instance = this;

        //Register the server with MotoPush (needs to be changed to ServerType.MCWar when exists)
        MotoServer.getInstance().getMotoPush().setIdentity(ServerType.UNKNOWN, ServerState.OPEN);

        //Register Events
        getServer().getPluginManager().registerEvents(new ClassEvents(), this);

        //Set up cooldown manager
        cdm = new CoolDownManager();

        //Setup game and teams
        teams = new TeamManager();
        game = new Game("MCWar", new LobbyState("LobbyState"), teams);
        game.run();

        this.getLogger().info("MCWar Enabled");
    }

    @Override
    public void onDisable() {
        this.getLogger().info("MCwar Disabled");
    }

    public boolean onSameTeam(Player p1, Player p2) {
        if(teams.getAmountOfTeams() == 2 && teams.getAllPlayers().length > 1) {
            if(teams.getTeam("Blue").contains(p1) && teams.getTeam("Blue").contains(p2)) return true;
            if(teams.getTeam("Red").contains(p1) && teams.getTeam("Red").contains(p2)) return true;
        }
        return false;
    }

    public MCWarClass getPlayerClass(Player p) {
        if(playerClasses.containsKey(p)) return null;
        return playerClasses.get(p);
    }

    public void setPlayerClass(Player p, MCWarClass c) {
        playerClasses.put(p, c);
    }

    public void clearPlayerClasses() {
        playerClasses = new HashMap<Player, MCWarClass>();
    }

    public Game getGame() {
        return game;
    }

    public CoolDownManager getCoolDownManager() {
        return cdm;
    }

    public void setGameInProgress(boolean gameInProgress) {
        this.gameInProgress = gameInProgress;
    }

    public boolean isGameInProgress() {
        return gameInProgress;
    }

    public TeamManager getTeams() {
        return teams;
    }

    public int getRedScore() {
        return redScore;
    }

    public void setRedScore(int i) {
        redScore = i;
    }

    public int getBlueScore() {
        return blueScore;
    }

    public void setBlueScore(int i) {
        blueScore = i;
    }

    public static MCWar getInstance() {
        return instance;
    }
}
