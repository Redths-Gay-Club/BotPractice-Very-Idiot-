package com.njdge.botpractice.MultiWorld;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class MultiWorld implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("This command can only be used by players.");
            return true;
        }

        Player player = (Player) sender;

        if (args.length == 2 && args[0].equalsIgnoreCase("tp")) {
            String worldName = args[1];
            World targetWorld = Bukkit.getWorld(worldName);

            if (targetWorld != null) {
                Location targetLocation = targetWorld.getSpawnLocation();
                player.teleport(targetLocation);
                player.sendMessage("Teleported to world: " + worldName);
            } else {
                player.sendMessage("World '" + worldName + "' not found.");
            }
            return true;
        }
        return true;
    }
}
