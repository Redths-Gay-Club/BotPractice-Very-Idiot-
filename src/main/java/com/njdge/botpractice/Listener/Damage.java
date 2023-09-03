package com.njdge.botpractice.Listener;


import net.citizensnpcs.api.CitizensAPI;
import net.citizensnpcs.api.event.NPCDamageByEntityEvent;
import net.citizensnpcs.api.npc.NPC;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.util.Vector;

import java.util.HashMap;
import java.util.Map;

import static com.njdge.botpractice.GameManager.BoxingManager.botHits;

public class Damage implements Listener {
    private final Map<NPC, Long> lastAttackTimeMap = new HashMap<>();

    @EventHandler
    public void onEntityDamage(EntityDamageEvent event) {
        if (event.getEntity() instanceof Player) {
            event.setCancelled(true);
            Player player = (Player) event.getEntity();
            player.damage(0);


        }
    }

        @EventHandler
        public void onDamage(EntityDamageByEntityEvent e) {
            if (e.getDamager() instanceof Player && e.getEntity() instanceof Player) {
                final Player attacker = (Player) e.getDamager();
                Player victim = (Player) e.getEntity();
                victim.setMaximumNoDamageTicks(20);
                victim.setNoDamageTicks(20);


                if (victim.hasMetadata("NPC")) {
                    NPC npc = CitizensAPI.getNPCRegistry().getNPC(victim);
                    if (npc != null) {
                        Location victimLocation = victim.getLocation();
                        Location attackerLocation = attacker.getLocation();
                        double knockbackStrength = 0.8D; // 擊退強度
                        double verticalKnockback = 0.35;  // 垂直擊退
                        Vector direction = victimLocation.clone().subtract(attackerLocation).toVector().normalize();
                        Vector knockbackVector = direction.multiply(knockbackStrength).setY(verticalKnockback);

                        if (!victim.isOnGround()) {
                            double AirKnockbackStrength = 0.55D;
                            double AirVerticalKnockback = 0.21;
                            Vector Airdirection = victimLocation.clone().subtract(attackerLocation).toVector().normalize();
                            Vector AirknockbackVector = Airdirection.multiply(AirKnockbackStrength).setY(AirVerticalKnockback);
                            victim.setVelocity(AirknockbackVector);

                        }

                        victim.setVelocity(knockbackVector);
                    }
                }
            }
        }
    @EventHandler

    public void onPlayerDamage(EntityDamageByEntityEvent e) {

        if (e.getDamager() instanceof Player) {
            if (e.getEntity() instanceof Player) {
                final Player attacker = (Player) e.getDamager();
                Player victim = (Player) e.getEntity();
                victim.setMaximumNoDamageTicks(20);
                victim.setNoDamageTicks(20);

                if (attacker.hasMetadata("NPC")) {
                    Location victimLocation = victim.getLocation();
                    Location attackerLocation = attacker.getLocation();
                    double knockbackStrength = 0.8D;
                    double verticalKnockback = 0.35;
                    Vector direction = victimLocation.clone().subtract(attackerLocation).toVector().normalize();
                    Vector knockbackVector = direction.multiply(knockbackStrength).setY(verticalKnockback);
                    victim.setVelocity(knockbackVector);
                    if (!victim.isOnGround()) {
                        double AirKnockbackStrength = 0.55D;
                        double AirVerticalKnockback = 0.21;
                        Vector Airdirection = victimLocation.clone().subtract(attackerLocation).toVector().normalize();
                        Vector AirknockbackVector = Airdirection.multiply(AirKnockbackStrength).setY(AirVerticalKnockback);
                        victim.setVelocity(AirknockbackVector);

                    }
                }
            }
        }
    }
}
