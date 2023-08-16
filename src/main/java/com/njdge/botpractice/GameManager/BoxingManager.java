package com.njdge.botpractice.GameManager;

import com.njdge.botpractice.Arena.SetConfig;
import com.njdge.botpractice.Main;
import net.citizensnpcs.api.CitizensAPI;
import net.citizensnpcs.api.npc.NPC;
import net.citizensnpcs.api.npc.NPCRegistry;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;

import java.io.File;
import java.util.HashMap;

public class BoxingManager {

    private final HashMap<Player, Integer> hits = new HashMap<>();
    private final NPCRegistry npcRegistry;
    private final int targetHits;

    public BoxingManager(NPCRegistry npcRegistry, int targetHits) {
        this.npcRegistry = npcRegistry;
        this.targetHits = targetHits;
    }


    public void addHit(Player player) {
        if (hits.containsKey(player)) {
            int currentHits = hits.get(player) + 1;
            hits.put(player, currentHits);

            if (currentHits >= targetHits) {
                // 玩家达到目标击打次数，玩家获胜逻辑
                // 例如：结束游戏，显示获胜信息等
            }
        } else {
            hits.put(player, 1);
        }
    }

    public int getHits(Player player) {
        return hits.getOrDefault(player, 0);
    }

    public void resetHits(Player player) {
        hits.remove(player);
    }

    public void resetAllHits() {
        hits.clear();
    }

    public void npcHit(Player player) {
        addHit(player); // 将被NPC打到的玩家的Hit次数加1
    }



    public void teleportToArena(Player player, String arenaName) {
        File configFile = new File(Main.instance.getDataFolder(), arenaName + ".yml");
        FileConfiguration arenaConfig = YamlConfiguration.loadConfiguration(configFile);
        if (arenaConfig != null) {
            World world = Bukkit.getWorld("cave");
            System.out.println(world);
            double x = arenaConfig.getDouble("Pos1.X");
            double y = arenaConfig.getDouble("Pos1.Y");
            double z = arenaConfig.getDouble("Pos1.Z");
            float yaw = (float) arenaConfig.getDouble("Pos1.Yaw");
            float pitch = (float) arenaConfig.getDouble("Pos1.Pitch");

            Location arenaSpawn = new Location(world, x, y, z, yaw, pitch);
            player.teleport(arenaSpawn);
        } else {
            player.sendMessage("Arena configuration not found.");
        }
    }

    public void spawnBot(Player player, String arenaName) {
        File configFile = new File(Main.instance.getDataFolder(), arenaName + ".yml");

        FileConfiguration arenaConfig = YamlConfiguration.loadConfiguration(configFile);
        if (arenaConfig != null) {
            World world = Bukkit.getWorld(arenaConfig.getString("MapName"));
            double x = arenaConfig.getDouble("Pos2.X");
            double y = arenaConfig.getDouble("Pos2.Y");
            double z = arenaConfig.getDouble("Pos2.Z");
            float yaw = (float) arenaConfig.getDouble("Pos2.Yaw");
            float pitch = (float) arenaConfig.getDouble("Pos2.Pitch");

            Location botSpawnLocation = new Location(world, x, y, z, yaw, pitch);

            NPCRegistry registry = CitizensAPI.getNPCRegistry();
            NPC npc = registry.createNPC(EntityType.PLAYER, "Random");
            npc.spawn(botSpawnLocation);
            npc.setProtected(false);

            ArmorStand dummyEntity = (ArmorStand) botSpawnLocation.getWorld().spawnEntity(botSpawnLocation, EntityType.ARMOR_STAND);
            dummyEntity.setGravity(false);
            Bukkit.getScheduler().runTaskLater(Main.instance, () -> {
                npc.getNavigator().setTarget(dummyEntity, true);  // 设置导航目标
                dummyEntity.remove();
            }, 20L);
        } else {
            player.sendMessage("Arena configuration not found.");
        }
    }



    public void spawnDummyEntity(Location location) {
        ArmorStand dummy = (ArmorStand) location.getWorld().spawnEntity(location, EntityType.ARMOR_STAND);
        dummy.setGravity(false);
        dummy.setVisible(false);
        dummy.setMarker(true); // This makes the ArmorStand invisible and immobile
    }


}
