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

        ItemStack paper = new ItemStack(Material.PAPER);
        paper.getItemMeta().setDisplayName(ChatColor.AQUA + "Bandage");
        ArrayList<String> paperLore = new ArrayList<>();
        paperLore.add(ChatColor.DARK_BLUE + "Cooldown: 0s");
        paperLore.add(ChatColor.GRAY + "Right click on a player to heal them 1/2 heart");
        paper.getItemMeta().setLore(paperLore);
        this.inventory.add(paper);

        this.passiveEffects = new ArrayList<>();
    }

    public ArrayList<ItemStack> getInventory() {
        if(spec >= 1) {
            ItemStack bone = new ItemStack(Material.BONE);
            bone.getItemMeta().setDisplayName(ChatColor.AQUA + "Super Heal");
            ArrayList<String> boneLore = new ArrayList<>();
            boneLore.add(ChatColor.DARK_BLUE + "Cooldown: 30s");
            boneLore.add(ChatColor.GRAY + "Right click on a player to heal them fully");
            bone.getItemMeta().setLore(boneLore);
            this.inventory.add(bone);
        }

        if(spec >= 3) {
            ItemStack cake = new ItemStack(Material.CAKE_BLOCK);
            cake.getItemMeta().setDisplayName(ChatColor.AQUA + "The Lie");
            ArrayList<String> cakeLore = new ArrayList<>();
            cakeLore.add(ChatColor.DARK_BLUE + "Cooldown: 0s");
            cakeLore.add(ChatColor.GRAY + "Right click a player to feed them 1 drumstick");
            cake.getItemMeta().setLore(cakeLore);
            this.inventory.add(cake);
        }

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

    public String getName() {
        return "Healer";
    }
}
