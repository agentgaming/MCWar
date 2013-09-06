package net.agentgaming.mcwar.classes;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.ArrayList;

public class Scout implements MCWarClass {
    private ArrayList<ItemStack> inventory;
    private ArrayList<PotionEffect> passiveEffects;
    private Integer spec;

    public Scout(Integer spec) {
        this.spec = spec;

        this.inventory = new ArrayList<>();
        ItemStack sword = new ItemStack(Material.IRON_SWORD);
        sword.getItemMeta().setDisplayName(ChatColor.GOLD + "Scout's Sword");
        this.inventory.add(sword);

        this.passiveEffects = new ArrayList<>();
        this.passiveEffects.add(new PotionEffect(PotionEffectType.SPEED, 200, 3));
        this.passiveEffects.add(new PotionEffect(PotionEffectType.JUMP, 200, 3));
    }

    public ArrayList<ItemStack> getInventory() {
        return inventory;
    }

    public ItemStack getChestArmor() {
        return new ItemStack(Material.GOLD_CHESTPLATE);
    }

    public ItemStack getLegArmor() {
        return new ItemStack(Material.GOLD_LEGGINGS);
    }

    public ItemStack getFootArmor() {
        return new ItemStack(Material.GOLD_BOOTS);
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
        return "Scout";
    }
}
