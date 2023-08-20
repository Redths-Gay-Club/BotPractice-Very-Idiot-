package com.njdge.botpractice.Listener;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.inventory.meta.ItemMeta;


import static com.njdge.botpractice.GUI.SelMap.openGUI;
import static com.njdge.botpractice.MultiWorld.MultiWorld.teleportToLobby;

public class onPlayerJoin implements Listener {
    @EventHandler
    public void onPlayerJoinEvent(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        teleportToLobby(player);
        PlayerInventory inventory = player.getInventory();
        inventory.clear();
        ItemStack BotDuel = new ItemStack(Material.GOLD_SWORD);
        ItemMeta meta = BotDuel.getItemMeta();
        meta.setDisplayName(ChatColor.RED + "Bot Duel");
        BotDuel.setItemMeta(meta);
        player.getInventory().addItem(BotDuel);
    }
    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        ItemStack item = player.getInventory().getItemInHand();
        if (item != null && item.getType() == Material.GOLD_SWORD && ChatColor.stripColor(item.getItemMeta().getDisplayName()).equals("Bot Duel")) {
            if (event.getAction() == Action.RIGHT_CLICK_BLOCK || event.getAction() == Action.RIGHT_CLICK_AIR) {
                openGUI(player);
            }
        }
    }

}
