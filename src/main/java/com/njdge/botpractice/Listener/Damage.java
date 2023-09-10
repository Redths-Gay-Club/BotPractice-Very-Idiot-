package com.njdge.botpractice.Listener;


import com.njdge.botpractice.GameManager.BoxingManager;
import com.njdge.botpractice.GameManager.GameManager;
import net.citizensnpcs.api.CitizensAPI;
import net.citizensnpcs.api.event.NPCDamageByEntityEvent;
import net.citizensnpcs.api.npc.NPC;
import net.minecraft.server.v1_8_R3.PacketPlayOutEntityVelocity;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.util.Vector;

import java.util.HashMap;
import java.util.Map;

import static com.njdge.botpractice.GameManager.BoxingManager.*;
import static com.njdge.botpractice.GameManager.BoxingManager.npc;
import static com.njdge.botpractice.MultiWorld.MultiWorld.teleportToLobby;

public class Damage implements Listener {

    @EventHandler
    public void onEntityDamage(EntityDamageEvent event) {
        if (event.getEntity() instanceof Player) {
            event.setDamage(0);
        }
    }

        @EventHandler
        public void onDamage(EntityDamageByEntityEvent e) {
            if (e.getDamager() instanceof Player && e.getEntity() instanceof Player) {
                final Player attacker = (Player) e.getDamager();
                Player victim = (Player) e.getEntity();
                if(victim.getNoDamageTicks() > victim.getMaximumNoDamageTicks() / 2){
                    return;
                }

                if (victim.hasMetadata("NPC")) {
                        PlayerHits++;
                        if (PlayerHits > 99) {
                            BoxingManager.npc.setProtected(false);
                            victim.setHealth(0);
                            botHits = 0;
                            PlayerHits = 0;
                            attacker.playSound(victim.getLocation(), Sound.NOTE_PLING, 1, 1);
                            attacker.sendTitle("§aVICTORY!", "§7You have won the game!");
                            teleportToLobby(attacker);
                            PlayerInventory inventory = attacker.getInventory();
                            inventory.clear();
                            ItemStack BotDuel = new ItemStack(Material.GOLD_SWORD);
                            ItemMeta meta = BotDuel.getItemMeta();
                            meta.setDisplayName(ChatColor.RED + "Bot Duel");
                            BotDuel.setItemMeta(meta);
                            attacker.getInventory().addItem(BotDuel);
                            GameManager.setGameState(attacker, GameManager.GameState.NONE);

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
                if(victim.getNoDamageTicks() > victim.getMaximumNoDamageTicks() / 2){
                    return;
                }
                    botHits++;
                    if (botHits > 99) {
                        BoxingManager.npc.setProtected(false);
                        attacker.setHealth(0);
                        botHits = 0;
                        PlayerHits = 0;
                        victim.sendTitle("§cDEFEAT!", "§7You have lost the game!");
                        victim.playSound(victim.getLocation(), Sound.NOTE_PLING, 1, 1);
                        teleportToLobby(victim);
                        PlayerInventory inventory = victim.getInventory();
                        inventory.clear();
                        ItemStack BotDuel = new ItemStack(Material.GOLD_SWORD);
                        ItemMeta meta = BotDuel.getItemMeta();
                        meta.setDisplayName(ChatColor.RED + "Bot Duel");
                        BotDuel.setItemMeta(meta);
                        victim.getInventory().addItem(BotDuel);
                        GameManager.setGameState(victim, GameManager.GameState.NONE);

                    }
                }
            }
        }
    }

