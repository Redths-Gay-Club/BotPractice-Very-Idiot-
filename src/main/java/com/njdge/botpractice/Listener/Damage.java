package com.njdge.botpractice.Listener;


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

public class Damage implements Listener {
    @EventHandler
    public void onEntityDamage(EntityDamageEvent event) {
        if (event.getEntity() instanceof Player) {
            event.setCancelled(true);
            event.setDamage(0);
            Player player = (Player) event.getEntity();

        }
    }
    @EventHandler

    public void onDamage(EntityDamageByEntityEvent e) {
        if (e.getDamager() instanceof Player) {
            if (e.getEntity() instanceof Player) {
                final Player attacker = (Player) e.getDamager();
                Player victim = (Player) e.getEntity();
                if (victim.hasMetadata("NPC")) {
                    e.setCancelled(true);
                    victim.setHealth(20.0D);
                    Location victimLocation = victim.getLocation();
                    Location attackerLocation = attacker.getLocation();
                    double knockbackStrength = 0.8D; // 设置击退强度
                    double verticalKnockback = 0.3;  // 设置垂直击退强度
                    attacker.playSound(attacker.getLocation(), Sound.HURT_FLESH, 1.0F, 1.0F);


                    // 计算从玩家到NPC的方向向量
                    Vector direction = victimLocation.clone().subtract(attackerLocation).toVector().normalize();

                    // 计算击退向量，将方向向量乘以击退强度
                    Vector knockbackVector = direction.multiply(knockbackStrength).setY(verticalKnockback);

                    victim.setVelocity(knockbackVector);


                }
            }
        }
    }
    @EventHandler
    public void onPlayerAttack(EntityDamageByEntityEvent event) {
        if (event.getEntity() instanceof NPC) {
            NPC npc = (NPC) event.getEntity();
            Player player = null;

            // 获取攻击者
            if (event.getDamager() instanceof Player) {
                player = (Player) event.getDamager();
            }

            if (npc != null && player != null) {
                if (!isAttackedRecently(npc)) {
                    // 在这里执行对NPC被攻击的逻辑
                    // 例如增加玩家的击打次数等

                    // 标记NPC已被攻击
                    markAttacked(npc);

                    // 取消伤害
                    event.setCancelled(true);
                }
            }
        }
    }


    // 存储NPC的上次被攻击时间
    private final Map<NPC, Long> lastAttackedMap = new HashMap<>();
    private final long damageTickInterval = 2000; // 2秒

    // 判断NPC是否在伤害间隔内被攻击过
    private boolean isAttackedRecently(NPC npc) {
        if (lastAttackedMap.containsKey(npc)) {
            long lastAttackedTime = lastAttackedMap.get(npc);
            long currentTime = System.currentTimeMillis();
            return (currentTime - lastAttackedTime) < damageTickInterval;
        }
        return false;
    }

    // 标记NPC被攻击的时间
    private void markAttacked(NPC npc) {
        lastAttackedMap.put(npc, System.currentTimeMillis());
    }
}
