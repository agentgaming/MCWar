package net.agentgaming.mcwar.classes;

import org.bukkit.ChatColor;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.LeatherArmorMeta;
import org.bukkit.potion.PotionEffect;

import java.util.ArrayList;

public class Agent implements MCWarClass {
    private ArrayList<ItemStack> inventory;
    private ArrayList<PotionEffect> passiveEffects;
    private Integer spec;

    public Agent(Integer spec) {
        this.spec = spec;

        this.inventory = new ArrayList<>();
        ItemStack bow = new ItemStack(Material.BOW);
        bow.getItemMeta().setDisplayName(ChatColor.GOLD + "Agent's \"Gun\"");
        bow.addEnchantment(Enchantment.ARROW_INFINITE, 1);
        this.inventory.add(bow);

        this.passiveEffects = new ArrayList<>();
    }

    public ArrayList<ItemStack> getInventory() {
        return inventory;
    }

    public ItemStack getChestArmor() {
        ItemStack chest = new ItemStack(Material.LEATHER_CHESTPLATE);
        ((LeatherArmorMeta) chest.getItemMeta()).setColor(Color.BLUE);
        return chest;
    }

    public ItemStack getLegArmor() {
        ItemStack legs = new ItemStack(Material.LEATHER_LEGGINGS);
        ((LeatherArmorMeta) legs.getItemMeta()).setColor(Color.BLUE);
        return legs;
    }

    public ItemStack getFootArmor() {
        ItemStack boots = new ItemStack(Material.LEATHER_BOOTS);
        ((LeatherArmorMeta) boots.getItemMeta()).setColor(Color.BLUE);
        return boots;
    }

    public ArrayList<PotionEffect> getPassiveEffects() {
        return passiveEffects;
    }

    public boolean isImmuneToPoison() {
        return false;
    }

    public boolean hasInstaShoot() {
        return true;
    }

    public void setSpec(Integer i) {
        this.spec = i;
    }

    public Integer getSpec() {
        return spec;
    }

    public String getName() {
        return "Agent";
    }
}
