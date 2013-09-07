package net.agentgaming.mcwar.classes;

import net.agentgaming.mcwar.MCWar;
import org.bukkit.ChatColor;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.entity.WitherSkull;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.LeatherArmorMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.util.Vector;

import java.util.ArrayList;
import java.util.List;

public class Mage implements MCWarClass {
    private ArrayList<ItemStack> inventory;
    private ArrayList<PotionEffect> passiveEffects;
    private Integer spec;

    public Mage(Integer spec) {
        this.spec = spec;

        this.inventory = new ArrayList<>();
        ItemStack wand = new ItemStack(Material.STICK);
        wand.getItemMeta().setDisplayName(ChatColor.GOLD + "Mage's Wand");
        wand.addEnchantment(Enchantment.KNOCKBACK, 1);
        this.inventory.add(wand);

        this.passiveEffects = new ArrayList<>();
    }

    public ArrayList<ItemStack> getInventory() {
        if(spec >= 3) {
            ItemStack charge = new ItemStack(Material.FIREBALL);
            charge.getItemMeta().setDisplayName(ChatColor.AQUA + "Special Attack");
            ArrayList<String> chargeLore = new ArrayList<>();
            chargeLore.add(ChatColor.DARK_BLUE + "Cooldown: 40s");
            chargeLore.add(ChatColor.GRAY + "Right click to unleash madness");
            charge.getItemMeta().setLore(chargeLore);
            this.inventory.add(charge);
        }

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
        return false;
    }

    public void setSpec(Integer i) {
        this.spec = i;
    }

    public Integer getSpec() {
        return spec;
    }

    public String getName() {
        return "Mage";
    }

    public void spec3(Player p) {
        if(spec < 3) return;
        List<Entity> ents = p.getNearbyEntities(10, 10, 10);

        for(Entity ent : ents) {
            if(!(ent instanceof Player)) continue;
            Player near = (Player) ent;

            //Not sure if these will work the way I want it
            Vector v = new Vector(near.getLocation().getX() - p.getLocation().getX(), near.getLocation().getY() - p.getLocation().getY(), near.getLocation().getZ() - p.getLocation().getZ());

            //Launch players
            ent.setVelocity(v.clone().add(new Vector(0,0,5)).multiply(3));

            //Shoot skull at player
            WitherSkull skull =  p.getWorld().spawn(p.getEyeLocation().add(near.getLocation().toVector()), WitherSkull.class);
            skull.setShooter(p);
            skull.setVelocity(v.multiply(3));
        }
    }
}
