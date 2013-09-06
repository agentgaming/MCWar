package net.agentgaming.mcwar;

import com.mike724.motoapi.push.ServerState;
import com.mike724.motoapi.push.ServerType;
import com.mike724.motoserver.MotoServer;
import org.bukkit.plugin.java.JavaPlugin;

public class MCWar extends JavaPlugin {
    private static MCWar instance;

    @Override
    public void onEnable() {
        //Set our instance
        instance = this;

        //Register the server with MotoPush (needs to be changed to ServerType.MCWar when exists)
        MotoServer.getInstance().getMotoPush().setIdentity(ServerType.UNKNOWN, ServerState.OPEN);

        this.getLogger().info("MotoHub Enabled");
    }

    @Override
    public void onDisable() {
        this.getLogger().info("MotoHub Disabled");
    }


    @SuppressWarnings("unused")
    public static MCWar getInstance() {
        return instance;
    }
}
