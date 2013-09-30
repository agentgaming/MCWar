package net.agentgaming.mcwar.game;

import com.mike724.motoapi.games.Game;
import com.mike724.motoapi.games.TeamMeta;
import com.mike724.motoapi.interfaces.InterfaceFactory;
import net.agentgaming.mcwar.MCWar;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.*;

public class GameState extends com.mike724.motoapi.games.GameState {

    private int ticks;
    private Game game;
    private MCWar mcwar;


    private Objective scoreBoard;
    private Score redSb;
    private Score blueSb;

    private InterfaceFactory selectionInterface;

    private int gameTicks = 6000;

    public GameState(String name) {
        super(name);

        ticks = 0;

        game = MCWar.getInstance().getGame();
        mcwar = MCWar.getInstance();

        Scoreboard sb = mcwar.getServer().getScoreboardManager().getNewScoreboard();
        scoreBoard = sb.registerNewObjective("mcwar","timer");
        scoreBoard.setDisplayName("MCWAR - Time Left: " + ((gameTicks - ticks) / 20) + "s");
        scoreBoard.setDisplaySlot(DisplaySlot.SIDEBAR);

        redSb = scoreBoard.getScore(Bukkit.getOfflinePlayer(ChatColor.RED + "Red:"));
        redSb.setScore(0);

        blueSb = scoreBoard.getScore(Bukkit.getOfflinePlayer(ChatColor.BLUE + "Blue:"));
        blueSb.setScore(0);

        selectionInterface = new InterfaceFactory(mcwar, ClassSelectionInterface.class, "Select Your Class");
    }


    @Override
    public void loop() {
        ticks++;

        for(Player p : mcwar.getTeams().getAllPlayers()) {
            scoreBoard.setDisplayName("MCWAR - Time Left: " + ((gameTicks - ticks) / 20) + "s");
            p.setScoreboard(scoreBoard.getScoreboard());

            if(mcwar.getPlayerClass(p) == null && p.getOpenInventory() != null && p.getOpenInventory().getTopInventory() != selectionInterface.getInventory()) {
                p.openInventory(selectionInterface.getInventory());
            }
        }

        redSb.setScore(mcwar.getRedScore());
        blueSb.setScore(mcwar.getBlueScore());

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

        mcwar.setBlueScore(0);
        mcwar.setRedScore(0);
    }

    @Override
    public void endState() {
        mcwar.setGameInProgress(false);
        for(Player p : mcwar.getServer().getOnlinePlayers()) {
            //TODO: send to hub
        }
    }
}
