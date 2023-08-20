package com.njdge.botpractice.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class BotName implements CommandExecutor {
    private static String botName;

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("This command can only be used by players.");
            return true;
        }

        Player player = (Player) sender;
        if (command.getName().equalsIgnoreCase("BotName")) {
            if (args.length == 1) {
                botName = args[0];
                return true;
            }
        }
        return false;
    }

    public static String getBotName() {
        return botName;
    }
}