package net.agentgaming.mcwar.classes;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.ArrayList;

public class Tank implements MCWarClass {
    private ArrayList<ItemStack> inventory;
    private ArrayList<PotionEffect> passiveEffects;
    private Integer spec;

    public Tank(Integer spec) {
        this.spec = spec;

        this.inventory = new ArrayList<>();
        this.inventory.add(new ItemStack(Material.DIAMOND_SWORD));

        this.passiveEffects = new ArrayList<>();
        this.passiveEffects.add(new PotionEffect(PotionEffectType.SLOW, 200, 2));
        this.passiveEffects.add(new PotionEffect(PotionEffectType.FIRE_RESISTANCE, 200, 2));
    }

    public ArrayList<ItemStack> getInventory() {
        return inventory;
    }

    public ItemStack getChestArmor() {
        return new ItemStack(Material.DIAMOND_CHESTPLATE);
    }

    public ItemStack getLegArmor() {
        return new ItemStack(Material.DIAMOND_LEGGINGS);
    }

    public ItemStack getFootArmor() {
        return new ItemStack(Material.DIAMOND_BOOTS);
    }

    public ArrayList<PotionEffect> getPassiveEffects() {
        return this.passiveEffects;
    }

    public void setSpec(Integer i) {
        this.spec = i;
    }

    public Integer getSpec() {
       return spec;
    }
}
