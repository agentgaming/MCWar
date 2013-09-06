package net.agentgaming.mcwar.classes;

import org.bukkit.ChatColor;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.LeatherArmorMeta;
import org.bukkit.potion.PotionEffect;

import java.util.ArrayList;

public class Thief implements MCWarClass {
    private ArrayList<ItemStack> inventory;
    private ArrayList<PotionEffect> passiveEffects;
    private Integer spec;

    public Thief(Integer spec) {
        this.spec = spec;

        this.inventory = new ArrayList<>();
        ItemStack sword = new ItemStack(Material.WOOD_SWORD);
        sword.getItemMeta().setDisplayName(ChatColor.GOLD + "Thief's Sword");
        this.inventory.add(sword);

        this.passiveEffects = new ArrayList<>();
    }

    public ArrayList<ItemStack> getInventory() {
        return inventory;
    }

    public ItemStack getChestArmor() {
        ItemStack chest = new ItemStack(Material.LEATHER_CHESTPLATE);
        ((LeatherArmorMeta) chest.getItemMeta()).setColor(Color.BLACK);
        return chest;
    }

    public ItemStack getLegArmor() {
        ItemStack legs = new ItemStack(Material.LEATHER_LEGGINGS);
        ((LeatherArmorMeta) legs.getItemMeta()).setColor(Color.BLACK);
        return legs;
    }

    public ItemStack getFootArmor() {
        ItemStack boots = new ItemStack(Material.LEATHER_BOOTS);
        ((LeatherArmorMeta) boots.getItemMeta()).setColor(Color.BLACK);
        return boots;
    }

    public ArrayList<PotionEffect> getPassiveEffects() {
        return passiveEffects;
    }

    public boolean isImmuneToPoison() {
        return false;
    }

    public boolean hasInstaShoot() {
        return false;
    }

    public void setSpec(Integer i) {
        this.spec = i;
    }

    public Integer getSpec() {
        return spec;
    }

    public String getName() {
        return "Thief";
    }
}
