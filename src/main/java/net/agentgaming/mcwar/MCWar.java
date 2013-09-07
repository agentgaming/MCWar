package net.agentgaming.mcwar;

import com.mike724.motoapi.push.ServerState;
import com.mike724.motoapi.push.ServerType;
import com.mike724.motoserver.MotoServer;
import net.agentgaming.mcwar.classes.MCWarClass;
import net.agentgaming.mcwar.classes.Mage;
import net.agentgaming.mcwar.events.ClassEvents;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public class MCWar extends JavaPlugin {
    private static MCWar instance;
    private CoolDownManager cdm;

    @Override
    public void onEnable() {
        //Set our instance
        instance = this;

        //Register the server with MotoPush (needs to be changed to ServerType.MCWar when exists)
        MotoServer.getInstance().getMotoPush().setIdentity(ServerType.UNKNOWN, ServerState.OPEN);

        //Register Events
        getServer().getPluginManager().registerEvents(new ClassEvents(), this);

        //Set cooldown manager
        CoolDownManager cdm = new CoolDownManager();

        this.getLogger().info("MCWar Enabled");
    }

    @Override
    public void onDisable() {
        this.getLogger().info("MCwar Disabled");
    }

    public CoolDownManager getCoolDownManager() {
        return cdm;
    }

    public boolean isGameInProgress() {
        return true;
    }

    public boolean onSameTeam(Player p1, Player p2) {
        return true;
    }

    public MCWarClass getPlayerClass(Player p) {
        return new Mage(0);
    }

    @SuppressWarnings("unused")
    public static MCWar getInstance() {
        return instance;
    }
}
