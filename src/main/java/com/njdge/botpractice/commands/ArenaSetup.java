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

        if (args.length < 1) {
            return true;
        }

        String subCommand = args[0];

        if (subCommand.equalsIgnoreCase("create")) {
            if (args.length >= 3) {
                String mapName = args[2];
                String arenaName = args[1];
                SetConfig.createArenaConfig(arenaName, mapName);
                SetArenaList.addArena(arenaName);

                player.sendMessage("Arena " + arenaName + " created successfully.");
                return true;
            } else {
                player.sendMessage("Usage: /bp create <ArenaName> <MapName>");
                return true;
            }
        }

        if (subCommand.equalsIgnoreCase("setpos1")) {
            handleSetPos(player, args, 1);
            return true;
        }

        if (subCommand.equalsIgnoreCase("setpos2")) {
            handleSetPos(player, args, 2);
            return true;
        }

        if (subCommand.equalsIgnoreCase("setlobby")) {
            Location location = player.getLocation();
            float yaw = player.getLocation().getYaw();
            float pitch = player.getLocation().getPitch();
            com.njdge.botpractice.Lobby.SetConfig.setLobby(location, yaw, pitch, player);
            player.sendMessage("Lobby location set.");
            return true;
        }

        if (args.length == 1 && args[0].equalsIgnoreCase("setlobby")) {
            Location location = player.getLocation();
            float yaw = player.getLocation().getYaw();
            float pitch = player.getLocation().getPitch();
            com.njdge.botpractice.Lobby.SetConfig.setLobby(location, yaw, pitch, player);
            player.sendMessage("Lobby location set.");
            return true;
        }
        return true;
    }


    private void handleSetPos(Player player, String[] args, int posNumber) {
        if (args.length < 2) {
            player.sendMessage("Usage: /bp setpos" + posNumber + " <ArenaName>");
            return;
        }

        String arenaName = args[1];

        Location location = player.getLocation();
        float yaw = player.getLocation().getYaw();
        float pitch = player.getLocation().getPitch();

        if (posNumber == 1) {
            SetConfig.setPos1(arenaName, location, yaw, pitch);
            player.sendMessage("Position 1 set for arena " + arenaName);
        } else if (posNumber == 2) {
            SetConfig.setPos2(arenaName, location, yaw, pitch);
            player.sendMessage("Position 2 set for arena " + arenaName);
        }
    }




}

