package net.agentgaming.mcwar.game;

import com.mike724.motoapi.interfaces.InterfaceClick;
import com.mike724.motoapi.interfaces.InterfaceOption;
import net.agentgaming.mcwar.MCWar;
import net.agentgaming.mcwar.classes.*;

public class ClassSelectionInterface {
    @InterfaceOption(slot = 0, name = "Tank", description = "Low speed and agility with a lot of damage and armor", itemId = 276)
    private static void Tank(InterfaceClick ic) {
        MCWar.getInstance().setPlayerClass(ic.getPlayer(), new Tank(0));
        ic.getPlayer().closeInventory();
    }

    @InterfaceOption(slot = 1, name = "Scout", description = "High speed and agility, moderate damage and armor", itemId = 288)
    private static void Scout(InterfaceClick ic) {
        MCWar.getInstance().setPlayerClass(ic.getPlayer(), new Scout(0));
        ic.getPlayer().closeInventory();
    }

    @InterfaceOption(slot = 2, name = "Ranger", description = "Shoot arrows to destroy your enemies", itemId = 261)
    private static void Ranger(InterfaceClick ic) {
        MCWar.getInstance().setPlayerClass(ic.getPlayer(), new Ranger(0));
        ic.getPlayer().closeInventory();
    }

    @InterfaceOption(slot = 3, name = "Healer", description = "Can heal and feed teammates, low damage", itemId = 339)
    private static void Healer(InterfaceClick ic) {
        MCWar.getInstance().setPlayerClass(ic.getPlayer(), new Healer(0));
        ic.getPlayer().closeInventory();
    }

    @InterfaceOption(slot = 4, name = "Thief", description = "Can steal nuggets from enemies, low damage", itemId = 281)
    private static void Thief(InterfaceClick ic) {
        MCWar.getInstance().setPlayerClass(ic.getPlayer(), new Thief(0));
        ic.getPlayer().closeInventory();
    }

    @InterfaceOption(slot = 5, name = "Summoner", description = "Have an army of wolves fight for you", itemId = 352)
    private static void Summoner(InterfaceClick ic) {
        MCWar.getInstance().setPlayerClass(ic.getPlayer(), new Summoner(0));
        ic.getPlayer().closeInventory();
    }

    @InterfaceOption(slot = 6, name = "Mage", description = "Cast spells to destroy your enemies", itemId = 369)
    private static void Mage(InterfaceClick ic) {
        MCWar.getInstance().setPlayerClass(ic.getPlayer(), new Mage(0));
        ic.getPlayer().closeInventory();
    }

    @InterfaceOption(slot = 7, name = "Agent", description = "For experienced players only..", itemId = 92)
    private static void Agent(InterfaceClick ic) {
        MCWar.getInstance().setPlayerClass(ic.getPlayer(), new Agent(0));
        ic.getPlayer().closeInventory();
    }
}