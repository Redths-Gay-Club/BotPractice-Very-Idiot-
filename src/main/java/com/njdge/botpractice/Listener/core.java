package com.njdge.botpractice.Listener;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.CreatureSpawnEvent;

public class core implements Listener {
    @EventHandler
    public void onCreatureSpawn(CreatureSpawnEvent event) {
        if (event.getSpawnReason() ==
                CreatureSpawnEvent.SpawnReason.NATURAL) {
            // 禁止自然生成的怪物和动物
            event.setCancelled(true);
        }
    }
}
