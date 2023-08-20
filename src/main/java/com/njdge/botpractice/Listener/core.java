package com.njdge.botpractice.Listener;

import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;

public class core implements Listener {
    @EventHandler
    public void onCreatureSpawn(CreatureSpawnEvent event) {
        if (event.getSpawnReason() ==
                CreatureSpawnEvent.SpawnReason.NATURAL) {
            event.setCancelled(true);
        }
    }
    @EventHandler
    public void onBlockBreak(BlockBreakEvent event) {

        if (event.getPlayer().getGameMode() != GameMode.CREATIVE) {
            Player player = event.getPlayer();
            player.sendMessage("§c您不可以破壞方塊");

            event.setCancelled(true);
        }

    }
    @EventHandler

    public void FoodLevelChangeEvent(FoodLevelChangeEvent e) {
        e.setFoodLevel(20);
    }
    @EventHandler
    public void onBlockPlace(BlockPlaceEvent event) {  //禁止放置

        if (event.getPlayer().getGameMode() != GameMode.CREATIVE) {
            Player player = event.getPlayer();
            player.sendMessage("§c您不可以放置方塊");

            event.setCancelled(true);
        }


    }
    @EventHandler
    public void Event(org.bukkit.event.player.PlayerDropItemEvent e) { //禁止DropItem
        Player player = e.getPlayer();
        if (e.getPlayer().getGameMode() != GameMode.CREATIVE) {
            e.setCancelled(true);
        }

    }

}
