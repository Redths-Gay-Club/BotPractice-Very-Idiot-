package com.njdge.botpractice.commands;

import com.njdge.botpractice.GameManager.DuelManager;
import com.njdge.botpractice.MojangAPI;
import net.citizensnpcs.api.npc.NPC;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import java.util.UUID;

public class BotPractice implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if (!(commandSender instanceof Player)) {
            commandSender.sendMessage("This command can only be used by players.");
            return true;
        }

        Player player = (Player) commandSender;

        if (strings.length == 2 && strings[0].equalsIgnoreCase("duel")) {
            String botName = strings[1];
            UUID botUUID = UUID.fromString(MojangAPI.getUUIDFromName(botName)); // Replace with actual method to get UUID

            if (botUUID != null) {
                NPC npc = createNPCWithSkin(botUUID, botName);

                if (npc != null) {
                    DuelManager.initiateDuel(player, npc);
                } else {
                    player.sendMessage("Failed to create NPC for dueling.");
                }
            } else {
                player.sendMessage("Failed to get UUID for the specified bot.");
            }
            return true;
        }

        // ... 其他代码 ...

        return false;
    }

    private NPC createNPCWithSkin(UUID botUUID, String botName) {
        // Implement this method to create NPC with specified skin
        // You can use CitizensAPI to create NPCs and apply skins
        // Return the created NPC
        return null; // Placeholder
    }
}
