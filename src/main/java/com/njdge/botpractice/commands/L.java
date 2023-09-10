package com.njdge.botpractice.commands;

import com.njdge.botpractice.GameManager.GameManager;
import net.citizensnpcs.api.CitizensAPI;
import net.citizensnpcs.api.npc.NPC;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.inventory.meta.ItemMeta;

import static com.njdge.botpractice.MultiWorld.MultiWorld.teleportToLobby;

public class L implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (cmd.getName().equalsIgnoreCase("l")) {
            if (sender instanceof Player) {
                Player player = (Player) sender;
                teleportToLobby(player);
                PlayerInventory inventory = player.getInventory();
                inventory.clear();
                ItemStack BotDuel = new ItemStack(Material.GOLD_SWORD);
                ItemMeta meta = BotDuel.getItemMeta();
                meta.setDisplayName(ChatColor.RED + "Bot Duel");
                BotDuel.setItemMeta(meta);
                player.getInventory().addItem(BotDuel);
                GameManager.setGameState(player, GameManager.GameState.NONE);

            }
        }
        return true;
    }
}
