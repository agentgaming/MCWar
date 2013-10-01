package net.agentgaming.mcwar.events;

import net.agentgaming.mcwar.game.CoolDownManager;
import net.agentgaming.mcwar.MCWar;
import net.agentgaming.mcwar.classes.*;
import net.agentgaming.mcwar.game.PlayerData;
import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.*;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

public class ClassEvents implements Listener {
    private HashMap<Player, ArrayList<Player>> healedPlayers = new HashMap<>();

    @EventHandler
    public void onEntityDamage(EntityDamageEvent e) {
        if (e.isCancelled()) return;
        if (!MCWar.getInstance().isGameInProgress()) return;

        //Recieve any damage
        if (e.getEntity() instanceof Player) {
            Player p = (Player) e.getEntity();

            //Immune to poison
            MCWarClass c = getPlayerClass(p);
            if (c.isImmuneToPoison() && e.getCause().equals(EntityDamageEvent.DamageCause.POISON)) {
                e.setCancelled(true);
            }

            //Tank
            if (getPlayerClass(p) instanceof Tank) {
                Tank t = (Tank) getPlayerClass(p);
                //Block 10% of damage
                if (t.getSpec() >= 1 && randomBoolean(.10F)) {
                    e.setCancelled(true);
                }
            }

            //Scout
            else if (getPlayerClass(p) instanceof Scout) {
                Scout s = (Scout) getPlayerClass(p);
                //Block fall damage
                if (e.getCause().equals(EntityDamageEvent.DamageCause.FALL)) {
                    e.setCancelled(true);
                }
            }
        }
    }

    @EventHandler
    public void onEntityDamageByEntity(EntityDamageByEntityEvent e) {
        if (e.isCancelled()) return;
        if (!MCWar.getInstance().isGameInProgress()) return;
        if (e.getDamager() instanceof Player && e.getEntity() instanceof Player) {
            if (MCWar.getInstance().onSameTeam((Player) e.getDamager(), (Player) e.getEntity())) {
                e.setCancelled(true);
                e.setDamage(0.0);
                return;
            }
        }

        if (e.getEntity() instanceof Player && e.getDamager() instanceof Projectile) {
            Player p = (Player) e.getEntity();
            Projectile pr = (Projectile) e.getDamager();

            //Agent deflect projectiles
            if (getPlayerClass(p) instanceof Agent) {
                e.setCancelled(true);
            }

            //Mage steal health
            else if (pr.getShooter() instanceof Player && getPlayerClass((Player) pr.getShooter()) instanceof Mage) {
                pr.getShooter().setHealth((double) (pr.getShooter().getHealth() + (e.getDamage() / 2.0D)));
            }
        }

        //Deal damage
        if (e.getDamager() instanceof Player) {
            Player p = (Player) e.getDamager();
            //Tank
            if (getPlayerClass(p) instanceof Tank) {
                Tank t = (Tank) getPlayerClass(p);
                //Heal base damage
                if (t.getSpec() >= 3) {
                    p.setHealth((double) (p.getHealth() + 1.0D));
                }
                //Deal double damage
                if (t.getSpec() >= 2 && randomBoolean(.10F)) {
                    e.setDamage((double) (e.getDamage() * 2.0D));
                }
            }

            //Scout
            else if (getPlayerClass(p) instanceof Scout) {
                Scout s = (Scout) getPlayerClass(p);
                //Set on fire
                if (s.getSpec() >= 2 && randomBoolean(.20F)) {
                    e.getEntity().setFireTicks(140);
                }
                //Poison
                if (s.getSpec() >= 2 && randomBoolean(.20F)) {
                    if (e.getEntity() instanceof LivingEntity) {
                        ((LivingEntity) e.getEntity()).addPotionEffect(new PotionEffect(PotionEffectType.POISON, 0, 140));
                    }
                }
            }
        } else if (e.getDamager() instanceof Arrow) {
            Arrow a = (Arrow) e.getDamager();
            if (a.getShooter() instanceof Player) {
                Player p = (Player) a.getShooter();

                //Ranger arrows
                if (getPlayerClass(p) instanceof Ranger) {
                    Ranger r = (Ranger) getPlayerClass(p);

                    //Poison
                    if (r.getSpec() == 2 || r.getSpec() >= 3 && randomBoolean(.10F)) {
                        if (e.getEntity() instanceof LivingEntity) {
                            ((LivingEntity) e.getEntity()).addPotionEffect(new PotionEffect(PotionEffectType.POISON, 0, 140));
                        }
                    }
                }

                //Agent arrows
                else if (getPlayerClass(p) instanceof Agent) {
                    Agent g = (Agent) getPlayerClass(p);

                    //Deal half health
                    if (g.getSpec() >= 1) {
                        if (e.getEntity() instanceof LivingEntity) {
                            double damage = ((LivingEntity) e.getEntity()).getHealth() / 2;
                            if (g.getSpec() >= 2) if (damage < 1) damage = 1;
                            else if (damage < 1) damage = 0;
                            e.setDamage(damage);
                        }
                    }

                    //On agent kill entity with arrow
                    if (e.getEntity() instanceof LivingEntity) {
                        if (e.getDamage() > ((LivingEntity) e.getEntity()).getHealth()) {
                            //Deal five hearts of damage
                            if (g.getSpec() >= 2) p.damage(5.0D, p);
                            //Give a random potion effect
                            if (g.getSpec() >= 3) {
                                PotionEffectType effectType = PotionEffectType.values()[new Random().nextInt(PotionEffectType.values().length)];
                                ((LivingEntity) e.getEntity()).addPotionEffect(new PotionEffect(effectType, 1, 100));
                            }
                        }
                    }
                }
            }
        }
    }

    @EventHandler
    public void onProjectileHit(ProjectileHitEvent e) {
        if (!MCWar.getInstance().isGameInProgress()) return;

        if (e.getEntity().getShooter() instanceof Player) {
            Player p = (Player) e.getEntity().getShooter();

            //Ranger
            if (getPlayerClass(p) instanceof Ranger) {
                Ranger r = (Ranger) getPlayerClass(p);

                if (e.getEntity() instanceof Arrow) {
                    //Exploding arrows
                    if (r.getSpec() == 1 || r.getSpec() >= 3 && randomBoolean(.10F)) {
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
        if (e.isCancelled()) return;
        if (!MCWar.getInstance().isGameInProgress()) return;

        if (e.getEntity() instanceof Player) {
            Player p = (Player) e.getEntity();

            //Instashoot
            MCWarClass c = getPlayerClass(p);
            if (c.hasInstaShoot()) e.setCancelled(true);
        }
    }

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent e) {
        if (e.isCancelled()) return;
        if (!MCWar.getInstance().isGameInProgress()) return;

        Player p = e.getPlayer();
        CoolDownManager cdm = MCWar.getInstance().getCoolDownManager();
        MCWarClass c = getPlayerClass(p);

        //Instashoot
        if (c.hasInstaShoot() && cdm.isCooledDown("instashoot", p)) {
            if (e.getAction() == Action.RIGHT_CLICK_AIR || e.getAction() == Action.RIGHT_CLICK_BLOCK && p.getItemInHand().getType().equals(Material.BOW)) {
                cdm.setCoolDown("instashoot", p, 15);
                p.launchProjectile(Arrow.class);
            }
        }

        //Mage attack
        if (c instanceof Mage && cdm.isCooledDown("mageattack", p)) {
            if (e.getAction() == Action.RIGHT_CLICK_AIR || e.getAction() == Action.RIGHT_CLICK_BLOCK && p.getItemInHand().getType().equals(Material.BLAZE_ROD)) {
                cdm.setCoolDown("mageattack", p, 20);
                p.launchProjectile(WitherSkull.class);
            }

            //Special attack
            else if (!e.getAction().equals(Action.PHYSICAL) && p.getItemInHand().getType().equals(Material.FIREBALL)) {
                if (cdm.isCooledDown("magespec", p)) {
                    cdm.setCoolDown("magespec", p, 800);
                    ((Mage) c).spec3(p);
                } else {
                    p.sendMessage("Your special attack is not cooled down.");
                }
            }
        }
    }

    @EventHandler
    public void onPlayerInteractEntity(PlayerInteractEntityEvent e) {
        if (e.isCancelled()) return;
        if (!MCWar.getInstance().isGameInProgress()) return;

        Player p = e.getPlayer();
        CoolDownManager cdm = MCWar.getInstance().getCoolDownManager();
        MCWarClass c = getPlayerClass(p);

        if (e.getRightClicked() instanceof Player) {
            Player clicked = (Player) e.getRightClicked();
            //Healer
            if (c instanceof Healer) {
                //Regular heal health
                if (p.getItemInHand().getType().equals(Material.PAPER) && MCWar.getInstance().onSameTeam(p, clicked)) {
                    addHealedPlayer(p, clicked);
                    clicked.setHealth((double) (clicked.getHealth() + 1.0D));
                    p.playEffect(clicked.getLocation(), Effect.MOBSPAWNER_FLAMES, (Object) 0);
                    p.playEffect(clicked.getEyeLocation(), Effect.MOBSPAWNER_FLAMES, (Object) 0);
                }

                //Super heal health
                else if (p.getItemInHand().equals(Material.BONE) && MCWar.getInstance().onSameTeam(p, clicked) && c.getSpec() >= 1) {
                    if (cdm.isCooledDown("superheal", p)) {
                        cdm.setCoolDown("superheal", p, 600);
                        addHealedPlayer(p, clicked);
                        clicked.setHealth((double) clicked.getMaxHealth());
                        p.playEffect(clicked.getLocation(), Effect.MOBSPAWNER_FLAMES, (Object) 0);
                        p.playEffect(clicked.getEyeLocation(), Effect.MOBSPAWNER_FLAMES, (Object) 0);
                    } else {
                        p.sendMessage("Your super heal is not yet cooled down!");
                    }
                }

                //Hunger health healh
                else if (p.getItemInHand().equals(Material.CAKE_BLOCK) && MCWar.getInstance().onSameTeam(p, clicked) && c.getSpec() >= 3) {
                    clicked.setFoodLevel(clicked.getFoodLevel() + 1);
                    p.playEffect(clicked.getLocation(), Effect.MOBSPAWNER_FLAMES, (Object) 0);
                    p.playEffect(clicked.getEyeLocation(), Effect.MOBSPAWNER_FLAMES, (Object) 0);
                }
            }
        }
    }

    @EventHandler
    public void onPlayerMove(PlayerMoveEvent e) {
        if (e.isCancelled()) return;
        if (!MCWar.getInstance().isGameInProgress()) return;

        //If they actually moved along x, y or z
        if (e.getFrom().getX() == e.getTo().getX() && e.getFrom().getX() == e.getTo().getX() && e.getFrom().getX() == e.getTo().getX()) {
            Player p = e.getPlayer();
            List<Entity> ents = p.getNearbyEntities(5, 5, 5);
            //Poison aura effect
            if (!p.hasPotionEffect(PotionEffectType.POISON)) {
                for (Entity ent : ents) {
                    if (!(ent instanceof Player)) continue;
                    Player near = (Player) ent;
                    if (getPlayerClass(near) instanceof Mage) {
                        p.addPotionEffect(new PotionEffect(PotionEffectType.POISON, 0, 100));
                        break;
                    }
                }
            }
        }
    }

    //Helpers
    private void addHealedPlayer(Player healer, Player healed) {
        if (!healedPlayers.containsKey(healer)) {
            ArrayList<Player> l = new ArrayList<>();
            l.add(healed);
            healedPlayers.put(healer, l);
        } else {
            healedPlayers.get(healer).add(healed);
        }
    }

    private boolean randomBoolean(Float probability) {
        return new Random().nextFloat() < probability;
    }

    private MCWarClass getPlayerClass(Player p) {
        return MCWar.getInstance().getPlayerDataManager().getPlayerData(p).getMCWarClass();
    }
}
