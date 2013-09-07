package net.agentgaming.mcwar.events;

import net.agentgaming.mcwar.CoolDownManager;
import net.agentgaming.mcwar.MCWar;
import net.agentgaming.mcwar.classes.MCWarClass;
import net.agentgaming.mcwar.classes.Ranger;
import net.agentgaming.mcwar.classes.Scout;
import net.agentgaming.mcwar.classes.Tank;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.TNTPrimed;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.*;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.Random;

public class ClassEvents implements Listener {
    @EventHandler
    public void onEntityDamage(EntityDamageEvent e) {
        if(e.isCancelled()) return;

        //Recieve any damage
        if(e.getEntity() instanceof Player) {
            Player p = (Player) e.getEntity();

            //Immune to poison
            MCWarClass c = MCWar.getInstance().getPlayerClass(p);
            if(c.isImmuneToPoison() && e.getCause().equals(EntityDamageEvent.DamageCause.POISON)) {
                e.setCancelled(true);
            }

            //Tank
            if(MCWar.getInstance().getPlayerClass(p) instanceof Tank) {
                Tank t = (Tank) MCWar.getInstance().getPlayerClass(p);
                //Block 10% of damage
                if(t.getSpec() >= 1 && randomBoolean(.10F)) {
                    e.setCancelled(true);
                }
            }

        }
    }

    @EventHandler
    public void onEntityDamageByEntity(EntityDamageByEntityEvent e) {
        if(e.isCancelled()) return;

        //Deal damage
        if(e.getDamager() instanceof Player) {
            Player p = (Player) e.getDamager();
            //Tank
            if(MCWar.getInstance().getPlayerClass(p) instanceof Tank) {
                Tank t = (Tank) MCWar.getInstance().getPlayerClass(p);
                //Heal base damage
                if(t.getSpec() >= 3) {
                    p.setHealth((double) (p.getHealth() + 1.0D));
                }
                //Deal double damage
                if(t.getSpec() >= 2 && randomBoolean(.10F)) {
                    e.setDamage((double) (e.getDamage() * 2.0D));
                }
            }

            //Scout
            else if(MCWar.getInstance().getPlayerClass(p) instanceof Scout) {
                Scout s = (Scout) MCWar.getInstance().getPlayerClass(p);
                //Set on fire
                if(s.getSpec() >= 2 && randomBoolean(.20F)) {
                    e.getEntity().setFireTicks(140);
                }
                //Poison
                if(s.getSpec() >= 2 && randomBoolean(.20F)) {
                    if(e.getEntity() instanceof LivingEntity) {
                        ((LivingEntity) e.getEntity()).addPotionEffect(new PotionEffect(PotionEffectType.POISON, 0, 140));
                    }
                }
            }
        }

        //Ranger (arrows)
        if(e.getDamager() instanceof Arrow) {
            Arrow a = (Arrow) e.getDamager();
            if(a.getShooter() instanceof Player) {
                Player p = (Player) a.getShooter();
                Ranger r = (Ranger) MCWar.getInstance().getPlayerClass(p);

                //Poison
                if(r.getSpec() == 2 || r.getSpec() >= 3 && randomBoolean(.10F)) {
                    if(e.getEntity() instanceof LivingEntity) {
                        ((LivingEntity) e.getEntity()).addPotionEffect(new PotionEffect(PotionEffectType.POISON, 0, 140));
                    }
                }
            }
        }
    }

    @EventHandler
    public void onProjectileHit(ProjectileHitEvent e) {
        if(e.getEntity().getShooter() instanceof Player) {
            Player p = (Player) e.getEntity().getShooter();

            //Ranger
            if(MCWar.getInstance().getPlayerClass(p) instanceof Ranger) {
                Ranger r = (Ranger) MCWar.getInstance().getPlayerClass(p);

                if(e.getEntity() instanceof Arrow) {
                    //Exploding arrows
                    if(r.getSpec() == 1 || r.getSpec() >= 3 && randomBoolean(.10F)) {
                        Location tntLoc = e.getEntity().getLocation();
                        TNTPrimed tnt = tntLoc.getWorld().spawn(tntLoc, TNTPrimed.class);
                        tnt.setFuseTicks(20);
                    }
                }
            }
        }
    }

    @EventHandler
    public void onEntityShootBow(EntityShootBowEvent e) {
        if(e.getEntity() instanceof Player) {
            Player p = (Player) e.getEntity();

            //Instashoot
            MCWarClass c = MCWar.getInstance().getPlayerClass(p);
            if(c.hasInstaShoot()) e.setCancelled(true);
        }
    }

    @EventHandler
    public void onEntityInteract(PlayerInteractEvent e) {
        Player p = e.getPlayer();
        CoolDownManager cdm = MCWar.getInstance().getCoolDownManager();

        //Instashoot
        MCWarClass c = MCWar.getInstance().getPlayerClass(p);
        if(c.hasInstaShoot() && cdm.isCooledDown("instashoot", p)) {
            if(e.getAction() == Action.RIGHT_CLICK_AIR || e.getAction() == Action.RIGHT_CLICK_BLOCK && p.getItemInHand().getType().equals(Material.BOW)) {
                cdm.setCoolDown("instashoot", p, 20);
                p.launchProjectile(Arrow.class);
            }
        }
    }


    private boolean randomBoolean(Float probability) {
        return new Random().nextFloat() < probability;
    }
}
