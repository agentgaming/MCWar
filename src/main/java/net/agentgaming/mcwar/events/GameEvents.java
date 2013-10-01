package net.agentgaming.mcwar.events;

import net.agentgaming.mcwar.MCWar;
import net.agentgaming.mcwar.game.PlayerData;
import net.agentgaming.mcwar.game.PlayerDataManager;
import org.bukkit.ChatColor;
import org.bukkit.Color;
import org.bukkit.FireworkEffect;
import org.bukkit.Material;
import org.bukkit.entity.Firework;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.FireworkMeta;

public class GameEvents implements Listener {

    @EventHandler
    public void onEntityDeath(EntityDeathEvent e) {
        if (!MCWar.getInstance().isGameInProgress()) return;
        PlayerDataManager pdm = MCWar.getInstance().getPlayerDataManager();

        if (e.getEntity() instanceof Player) {
            Player p = (Player) e.getEntity();
            Player killer = p.getKiller();

            //Spawn firework on death
            Firework firework = p.getWorld().spawn(p.getLocation(), Firework.class);
            FireworkMeta data = (FireworkMeta) firework.getFireworkMeta();

            if(MCWar.getInstance().getTeams().getTeam("Red").contains(p)) {
                data.addEffects(FireworkEffect.builder().withColor(Color.RED).with(FireworkEffect.Type.BURST).build());
            } else if(MCWar.getInstance().getTeams().getTeam("Blue").contains(p)) {
                data.addEffects(FireworkEffect.builder().withColor(Color.BLUE).with(FireworkEffect.Type.BURST).build());
            }

            data.setPower(1);
            firework.setFireworkMeta(data);

            //Don't drop anything and give killer nuggets
            Integer nuggetsToDrop = countItem(p, Material.GOLD_NUGGET) + 1 + (int) Math.floor(countItem(killer, Material.GOLD_NUGGET) * 0.05D);
            e.getDrops().removeAll(e.getDrops());

            killer.getInventory().addItem(new ItemStack(Material.GOLD_NUGGET, nuggetsToDrop));

            //Incremenets kills/deaths
            pdm.getPlayerData(p).setDeaths(pdm.getPlayerData(p).getDeaths() + 1);
            pdm.getPlayerData(killer).setKills(pdm.getPlayerData(p).getKills() + 1);

            MCWar.getInstance().getPlayerDataManager().getPlayerData(p).setMCWarClass(null);
        }
    }

    @EventHandler
    public void onBlockClick(final PlayerInteractEvent e) {
        if (!MCWar.getInstance().isGameInProgress()) return;

        if(e.getPlayer().getItemInHand().getType().equals(Material.GOLD_NUGGET) && e.getAction() == Action.RIGHT_CLICK_BLOCK) {
            e.getPlayer().sendMessage(ChatColor.DARK_AQUA + "Securing nuggets! Do not leave the area.");
            MCWar.getInstance().getServer().getScheduler().runTaskLater(MCWar.getInstance(), new Runnable() {
                @Override
                public void run() {
                    if(e.getPlayer().getLocation().distance(e.getClickedBlock().getLocation()) <= 5) {
                        //Get nuggetCount and player data
                        Integer nuggetCount = countItem(e.getPlayer(), Material.GOLD_NUGGET);
                        PlayerData pd = MCWar.getInstance().getPlayerDataManager().getPlayerData(e.getPlayer());

                        //Remove players nuggets
                        e.getPlayer().getInventory().remove(Material.GOLD_NUGGET);

                        //Set specs
                        switch(pd.getSpec(pd.getMCWarClass())) {
                            case 0:
                                if(nuggetCount >= 10) {
                                    pd.setSpec(pd.getMCWarClass(), 1);
                                    e.getPlayer().sendMessage(ChatColor.DARK_AQUA + "You now have spec one for " + pd.getMCWarClass().getName());
                                }
                                break;
                            case 1:
                                if(nuggetCount >= 30) {
                                    pd.setSpec(pd.getMCWarClass(), 2);
                                    e.getPlayer().sendMessage(ChatColor.DARK_AQUA + "You now have spec two for " + pd.getMCWarClass().getName());
                                }
                                break;
                            case 2:
                                if(nuggetCount >= 65) {
                                    pd.setSpec(pd.getMCWarClass(), 3);
                                    e.getPlayer().sendMessage(ChatColor.DARK_AQUA + "You now have spec three for " + pd.getMCWarClass().getName());
                                }
                                break;
                        }

                        //Increase score
                        if(MCWar.getInstance().getTeams().getTeam("Blue").contains(e.getPlayer())) {
                            MCWar.getInstance().setBlueScore(MCWar.getInstance().getBlueScore() + nuggetCount);
                        } else if(MCWar.getInstance().getTeams().getTeam("Red").contains(e.getPlayer())) {
                            MCWar.getInstance().setRedScore(MCWar.getInstance().getRedScore() + nuggetCount);
                        }

                        pd.setScore(pd.getScore() + nuggetCount);
                    } else {
                        e.getPlayer().sendMessage(ChatColor.RED + "You left the area! Your nuggets were not secured.");
                    }
                }
            }, 100);
        }
    }

    @EventHandler
    public void onFoodChange(FoodLevelChangeEvent e) {
        if (!MCWar.getInstance().isGameInProgress()) return;
        e.setFoodLevel(e.getFoodLevel() - 2);
    }

    private Integer countItem(Player p, Material type) {
        int c = 0;
        for(ItemStack i : p.getInventory().getContents()) {
            if(i.getType() == type) c+= i.getAmount();
        }
        return c;
    }
}
