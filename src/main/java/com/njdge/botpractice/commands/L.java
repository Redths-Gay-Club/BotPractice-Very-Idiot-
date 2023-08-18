package com.njdge.botpractice.commands;

import net.citizensnpcs.api.CitizensAPI;
import net.citizensnpcs.api.npc.NPC;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import static com.njdge.botpractice.MultiWorld.MultiWorld.teleportToLobby;

public class L implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (cmd.getName().equalsIgnoreCase("l")) {
            if (sender instanceof Player) {
                Player player = (Player) sender;
                teleportToLobby(player);
            }
        }
        return true;
    }
}
