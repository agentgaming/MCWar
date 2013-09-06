package net.agentgaming.mcwar.classes;

import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;

import java.util.ArrayList;

public interface MCWarClass {
    public ArrayList<ItemStack> getInventory();

    public ItemStack getChestArmor();

    public ItemStack getLegArmor();

    public ItemStack getFootArmor();

    public ArrayList<PotionEffect> getPassiveEffects();

    public boolean isImmuneToPoison();

    public boolean hasInstaShoot();

    public void setSpec(Integer i);

    public Integer getSpec();

    public String getName();
}
