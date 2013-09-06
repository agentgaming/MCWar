package net.agentgaming.mcwar.classes;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;

import java.util.ArrayList;

public class Ranger implements MCWarClass {
    private ArrayList<ItemStack> inventory;
    private ArrayList<PotionEffect> passiveEffects;
    private Integer spec;

    public Ranger(Integer spec) {
        this.spec = spec;

        this.inventory = new ArrayList<>();
        ItemStack bow = new ItemStack(Material.BOW);
        bow.addEnchantment(Enchantment.ARROW_DAMAGE, 2);
        bow.addEnchantment(Enchantment.ARROW_INFINITE, 1);
        bow.getItemMeta().setDisplayName(ChatColor.GOLD + "Ranger's Bow");
        this.inventory.add(bow);

        this.passiveEffects = new ArrayList<>();
    }

    public ArrayList<ItemStack> getInventory() {
        return inventory;
    }

    public ItemStack getChestArmor() {
        return new ItemStack(Material.CHAINMAIL_CHESTPLATE);
    }

    public ItemStack getLegArmor() {
        return new ItemStack(Material.CHAINMAIL_LEGGINGS);
    }

    public ItemStack getFootArmor() {
        return new ItemStack(Material.CHAINMAIL_BOOTS);
    }

    public ArrayList<PotionEffect> getPassiveEffects() {
        return passiveEffects;
    }

    public boolean isImmuneToPoison() {
        return false;
    }

    public boolean hasInstaShoot() {
        return spec >= 3;
    }

    public void setSpec(Integer i) {
        this.spec = i;
    }

    public Integer getSpec() {
        return spec;
    }
}
