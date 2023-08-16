package com.njdge.botpractice.commands;

import net.citizensnpcs.api.CitizensAPI;
import net.citizensnpcs.api.npc.NPC;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class kill implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (cmd.getName().equalsIgnoreCase("k")) {
            if (sender instanceof Player) {
                Player player = (Player) sender;
                // 删除所有NPC
                for (NPC npc : CitizensAPI.getNPCRegistry()) {
                    npc.destroy();
                }
                player.sendMessage("所有NPC已删除！");
            }
        }
        return true;
    }
}
