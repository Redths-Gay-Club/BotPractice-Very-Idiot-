package com.njdge.botpractice.commands;

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
        if (command.getName().equalsIgnoreCase("duel") && sender instanceof Player) {
            if (args.length == 0) {
                boxingManager.spawnBot(player, "cave");
                boxingManager.teleportToArena(player, "cave");
                boxingManager.playerHits.clear();
                boxingManager.botHits = 0;

                return true;
            }
            return true;

        }
        return false;

    }
}
