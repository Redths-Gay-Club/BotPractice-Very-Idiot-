package com.njdge.botpractice.Arena;

import org.bukkit.Location;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;

public class SetConfig {
    public static FileConfiguration getArenaConfig(String arenaName) {
        File configFile = new File("plugins/BotPractice/arenas", arenaName + ".yml");
        return YamlConfiguration.loadConfiguration(configFile);
    }

    public static void saveArenaConfig(FileConfiguration config, String arenaName) {
        File configFile = new File("plugins/BotPractice/arenas", arenaName + ".yml");
        try {
            config.save(configFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void createArenaConfig(String arenaName, String mapName) {
        FileConfiguration config = getArenaConfig(arenaName);

        config.set("GameName", arenaName);
        config.set("World", mapName);

        saveArenaConfig(config, arenaName);
    }


    public static void setPos1(String arenaName, Location location, float yaw, float pitch) {
        FileConfiguration config = getArenaConfig(arenaName);

        config.set("pos1.x", location.getX());
        config.set("pos1.y", location.getY());
        config.set("pos1.z", location.getZ());
        config.set("pos1.yaw", yaw);
        config.set("pos1.pitch", pitch);

        saveArenaConfig(config, arenaName);
    }

    public static void setPos2(String arenaName, Location location, float yaw, float pitch) {
        FileConfiguration config = getArenaConfig(arenaName);

        config.set("pos2.x", location.getX());
        config.set("pos2.y", location.getY());
        config.set("pos2.z", location.getZ());
        config.set("pos2.yaw", yaw);
        config.set("pos2.pitch", pitch);

        saveArenaConfig(config, arenaName);
    }
}


