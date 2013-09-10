package net.agentgaming.mcwar.game;

import com.mike724.motoapi.games.Game;
import com.mike724.motoapi.push.ServerState;
import com.mike724.motoapi.push.ServerType;
import com.mike724.motoserver.MotoServer;
import net.agentgaming.mcwar.MCWar;

public class LobbyState extends com.mike724.motoapi.games.GameState {

    private int ticks;
    private Game game;
    private MCWar mcwar;

    private int minPlayers = 4;
    private int lobbyTicks = 2500;

    public LobbyState(String name) {
        super(name);
        ticks = 0;
        game = MCWar.getInstance().getGame();
        mcwar = MCWar.getInstance();
    }

    @Override
    public void loop() {
        ticks++;

        //Wait until we have enough players before starting the lobby process
        if(mcwar.getServer().getOnlinePlayers().length < minPlayers && ticks > 20) {
            ticks = 0;
            game.announce("We need at least " + minPlayers + " to start.");
        }

        //Start the game after x time with enough players
        if(ticks > lobbyTicks) {
            game.startState(new GameState("Game"));
        }
    }

    @Override
    public void setupState() {
        //TODO: change to mcwar
        MotoServer.getInstance().getMotoPush().setIdentity(ServerType.UNKNOWN, ServerState.OPEN);
        ticks = 0;
    }

    @Override
    public void endState() {
        //TODO: change to mcwar
        MotoServer.getInstance().getMotoPush().setIdentity(ServerType.UNKNOWN, ServerState.CLOSED);
    }
}
