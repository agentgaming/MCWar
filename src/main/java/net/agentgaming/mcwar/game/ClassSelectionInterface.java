package net.agentgaming.mcwar.game;

import com.mike724.motoapi.interfaces.InterfaceClick;
import com.mike724.motoapi.interfaces.InterfaceOption;
import net.agentgaming.mcwar.MCWar;
import net.agentgaming.mcwar.classes.*;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;

public class ClassSelectionInterface {
    @InterfaceOption(slot = 0, name = "Tank", description = "Low speed and agility with a lot of damage and armor", itemId = 276)
    private static void Tank(InterfaceClick ic) {
        setClass(ic.getPlayer(), new Tank(0));
    }

    @InterfaceOption(slot = 1, name = "Scout", description = "High speed and agility, moderate damage and armor", itemId = 288)
    private static void Scout(InterfaceClick ic) {
        setClass(ic.getPlayer(), new Scout(0));
    }

    @InterfaceOption(slot = 2, name = "Ranger", description = "Shoot arrows to destroy your enemies", itemId = 261)
    private static void Ranger(InterfaceClick ic) {
        setClass(ic.getPlayer(), new Ranger(0));
    }

    @InterfaceOption(slot = 3, name = "Healer", description = "Can heal and feed teammates, low damage", itemId = 339)
    private static void Healer(InterfaceClick ic) {
        setClass(ic.getPlayer(), new Healer(0));
    }

    @InterfaceOption(slot = 4, name = "Thief", description = "Can steal nuggets from enemies, low damage", itemId = 281)
    private static void Thief(InterfaceClick ic) {
        setClass(ic.getPlayer(), new Thief(0));
    }

    @InterfaceOption(slot = 5, name = "Summoner", description = "Have an army of wolves fight for you", itemId = 352)
    private static void Summoner(InterfaceClick ic) {
        setClass(ic.getPlayer(), new Summoner(0));
    }

    @InterfaceOption(slot = 6, name = "Mage", description = "Cast spells to destroy your enemies", itemId = 369)
    private static void Mage(InterfaceClick ic) {
        setClass(ic.getPlayer(), new Mage(0));
    }

    @InterfaceOption(slot = 7, name = "Agent", description = "For experienced players only..", itemId = 92)
    private static void Agent(InterfaceClick ic) {
        setClass(ic.getPlayer(), new Agent(0));
    }
    
    private static void setClass(Player p, MCWarClass c) {
        if(!MCWar.getInstance().getPlayerDataManager().hasPlayerData(p)) {
            MCWar.getInstance().getPlayerDataManager().addPlayerData(new PlayerData(p));
        }

        MCWar.getInstance().getPlayerDataManager().getPlayerData(p).setMCWarClass(c);

        p.getInventory().removeItem(p.getInventory().getContents());

        if(MCWar.getInstance().getTeams().getTeam("Red").contains(p)) {
            p.getInventory().setHelmet(new ItemStack(Material.WOOL, 1, (short) 14));
        } else if(MCWar.getInstance().getTeams().getTeam("Blue").contains(p)) {
            p.getInventory().setHelmet(new ItemStack(Material.WOOL, 1, (short) 11));
        }

        p.getInventory().setChestplate(c.getChestArmor());
        p.getInventory().setLeggings(c.getLegArmor());
        p.getInventory().setBoots(c.getFootArmor());

        ItemStack[] inv = new ItemStack[c.getInventory().size()];
        c.getInventory().toArray(inv);
        p.getInventory().addItem(inv);

        for(PotionEffect pe : c.getPassiveEffects()) {
            p.addPotionEffect(pe, true);
        }

        p.closeInventory();
    }
}
