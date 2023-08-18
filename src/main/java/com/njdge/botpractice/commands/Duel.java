package com.njdge.botpractice.commands;

import com.njdge.botpractice.GUI.SelMap;
import com.njdge.botpractice.GameManager.BoxingManager;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Duel implements CommandExecutor {
    private BoxingManager boxingManager;

    public Duel(BoxingManager boxingManager) {
        this.boxingManager = boxingManager;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("This command can only be used by players.");
            return true;
        }

        Player player = (Player) sender;
        if (command.getName().equalsIgnoreCase("duel")) {
            if (args.length == 0) {
                SelMap.openGUI(player);
                return true;
            }
        }
        return false;
    }
}
