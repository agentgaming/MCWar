package net.agentgaming.mcwar.game;

import com.mike724.motoapi.games.Game;
import com.mike724.motoapi.games.TeamMeta;
import net.agentgaming.mcwar.MCWar;
import org.bukkit.entity.Player;

public class GameState extends com.mike724.motoapi.games.GameState {

    private int ticks;
    private Game game;
    private MCWar mcwar;

    private int gameTicks = 6000;

    public GameState(String name) {
        super(name);
        ticks = 0;
        game = MCWar.getInstance().getGame();
        mcwar = MCWar.getInstance();
    }


    @Override
    public void loop() {
        ticks++;

        if(gameTicks >= 6000) {
            game.announce("The game has ended!");
            game.announce("X team won!");
            game.endCurrentState();
        }

        if(mcwar.getTeams().getTeam("Blue").size() < 1 || mcwar.getTeams().getTeam("Red").size() < 1) {
            game.announce("We don't have enough players to continue. This game will be aborted.");
            game.endCurrentState();
        }
    }

    @Override
    public void setupState() {
        mcwar.setGameInProgress(true);

        mcwar.clearPlayerClasses();
        mcwar.getTeams().removeAllPlayers();

        mcwar.getTeams().addTeam(new TeamMeta("Blue"));
        mcwar.getTeams().addTeam(new TeamMeta("Red"));

        mcwar.getTeams().addPlayers(mcwar.getServer().getOnlinePlayers());
    }

    @Override
    public void endState() {
        mcwar.setGameInProgress(false);
        for(Player p : mcwar.getServer().getOnlinePlayers()) {
            //TODO: send to hub
        }
    }
}
