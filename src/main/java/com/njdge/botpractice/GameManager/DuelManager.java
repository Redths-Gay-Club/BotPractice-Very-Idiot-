package com.njdge.botpractice.GameManager;

import com.njdge.botpractice.Arena.SetArenaList;
import com.njdge.botpractice.Arena.SetConfig;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import net.citizensnpcs.api.npc.NPC;
import org.bukkit.configuration.file.FileConfiguration;

import java.util.List;
import java.util.Random;

public class DuelManager {
    private static final Random random = new Random();

    public static void initiateDuel(Player player, NPC npc) {
        List<String> arenaNames = SetArenaList.getArenaNames();
        if (arenaNames.isEmpty()) {
            player.sendMessage("There are no arenas available for dueling.");
            return;
        }

        String selectedArena = arenaNames.get(random.nextInt(arenaNames.size()));
        Location playerPos1 = readPosLocation(selectedArena, "pos1");
        Location npcPos2 = readPosLocation(selectedArena, "pos2");


        if (playerPos1 != null && npcPos2 != null) {
            player.teleport(playerPos1);
            npc.spawn(npcPos2);
            player.sendMessage("Duel started! You are now facing " + npc.getName() + ".");
        } else {
            player.sendMessage("Duel initialization failed. Please check the arena positions.");
        }
    }

    private static Location readPosLocation(String arenaName, String posKey) {
        FileConfiguration arenaConfig = SetConfig.getArenaConfig(arenaName);

        double x = arenaConfig.getDouble(posKey + ".x");
        double y = arenaConfig.getDouble(posKey + ".y");
        double z = arenaConfig.getDouble(posKey + ".z");
        float yaw = (float) arenaConfig.getDouble(posKey + ".yaw");
        float pitch = (float) arenaConfig.getDouble(posKey + ".pitch");

        return new Location(Bukkit.getWorld("yourWorldName"), x, y, z, yaw, pitch);
    }
}
