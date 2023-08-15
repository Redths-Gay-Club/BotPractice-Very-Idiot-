package com.njdge.botpractice.commands;

import com.njdge.botpractice.Arena.SetArenaList;
import com.njdge.botpractice.Arena.SetConfig;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class ArenaSetup implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("This command can only be used by players.");
            return true;
        }

        Player player = (Player) sender;

        if (args.length >= 3) {
            String subCommand = args[0];
            String arenaName = args[1];

            if (subCommand.equalsIgnoreCase("create")) {
                String mapName = args[2];
                SetConfig.createArenaConfig(arenaName, mapName);
                SetArenaList.addArena(arenaName);

                player.sendMessage("Arena " + arenaName + " created successfully.");
                return true;
            } else if (subCommand.equalsIgnoreCase("setpos1")) {
                if (args.length < 3) {
                    player.sendMessage("Usage: /bp setpos1 <ArenaName>");
                    return true;
                }

                Location location = player.getLocation();
                float yaw = player.getLocation().getYaw();
                float pitch = player.getLocation().getPitch();
                SetConfig.setPos1(arenaName, location, yaw, pitch);
                player.sendMessage("Position 1 set for arena " + arenaName);
                return true;
            } else if (subCommand.equalsIgnoreCase("setpos2")) {
                if (args.length < 3) {
                    player.sendMessage("Usage: /bp setpos2 <ArenaName>");
                    return true;
                }

                Location location = player.getLocation();
                float yaw = player.getLocation().getYaw();
                float pitch = player.getLocation().getPitch();
                SetConfig.setPos2(arenaName, location, yaw, pitch);
                player.sendMessage("Position 2 set for arena " + arenaName);
                return true;
            }
        }

        if (args.length == 1 && args[0].equalsIgnoreCase("setlobby")) {
            Location location = player.getLocation();
            float yaw = player.getLocation().getYaw();
            float pitch = player.getLocation().getPitch();
            com.njdge.botpractice.Lobby.SetConfig.setLobby(location, yaw, pitch);
            player.sendMessage("Lobby location set.");
            return true;
        }
        return true;
    }
}
