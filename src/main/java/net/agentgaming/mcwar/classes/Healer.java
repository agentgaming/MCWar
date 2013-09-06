package net.agentgaming.mcwar.classes;

import org.bukkit.ChatColor;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.LeatherArmorMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.ArrayList;

public class Healer implements MCWarClass {
    private ArrayList<ItemStack> inventory;
    private ArrayList<PotionEffect> passiveEffects;
    private Integer spec;

    public Healer(Integer spec) {
        this.spec = spec;

        this.inventory = new ArrayList<>();
        ItemStack sword = new ItemStack(Material.STONE_SWORD);
        sword.getItemMeta().setDisplayName(ChatColor.GOLD + "Healer's Sword");
        this.inventory.add(sword);

        this.passiveEffects = new ArrayList<>();
    }

    public ArrayList<ItemStack> getInventory() {
        return inventory;
    }

    public ItemStack getChestArmor() {
        ItemStack chest = new ItemStack(Material.LEATHER_CHESTPLATE);
        ((LeatherArmorMeta) chest.getItemMeta()).setColor(Color.WHITE);
        return chest;
    }

    public ItemStack getLegArmor() {
        ItemStack legs = new ItemStack(Material.LEATHER_LEGGINGS);
        ((LeatherArmorMeta) legs.getItemMeta()).setColor(Color.WHITE);
        return legs;
    }

    public ItemStack getFootArmor() {
        ItemStack boots = new ItemStack(Material.LEATHER_BOOTS);
        ((LeatherArmorMeta) boots.getItemMeta()).setColor(Color.WHITE);
        return boots;
    }

    public ArrayList<PotionEffect> getPassiveEffects() {
        if(spec >= 2) passiveEffects.add(new PotionEffect(PotionEffectType.FIRE_RESISTANCE, 200, 2));
        return passiveEffects;
    }

    public boolean isImmuneToPoison() {
        return spec >= 2;
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
}
