package com.njdge.botpractice.Arena;

import com.njdge.botpractice.Main;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;

import java.io.File;
import java.io.IOException;

public class SetConfig {
    private static Plugin plugin;

    public static void setup(Plugin plugin) {
        SetConfig.plugin = plugin;
    }

    public static void createArenaConfig(String arenaName, String mapName) {
        File arenaFile = new File(Main.instance.getDataFolder(), arenaName + ".yml");
        FileConfiguration arenaConfig = YamlConfiguration.loadConfiguration(arenaFile);
        arenaConfig.set("ArenaName", arenaName);
        arenaConfig.set("MapName", mapName);
        saveArenaConfig(arenaConfig, arenaFile);
    }

    public static Location getPos1Location(String arenaName) {
        FileConfiguration arenaConfig = getArenaConfig(arenaName);
        if (arenaConfig != null) {
            ConfigurationSection pos1Section = arenaConfig.getConfigurationSection("Pos1");
            if (pos1Section != null) {
                double x = pos1Section.getDouble("X");
                double y = pos1Section.getDouble("Y");
                double z = pos1Section.getDouble("Z");
                float yaw = (float) pos1Section.getDouble("Yaw");
                float pitch = (float) pos1Section.getDouble("Pitch");
                return new Location(Bukkit.getWorld("YourWorldName"), x, y, z, yaw, pitch);
            }
        }
        return null;
    }

    public static Location getPos2Location(String arenaName) {
        FileConfiguration arenaConfig = getArenaConfig(arenaName);
        if (arenaConfig != null) {
            ConfigurationSection pos2Section = arenaConfig.getConfigurationSection("Pos2");
            if (pos2Section != null) {
                double x = pos2Section.getDouble("X");
                double y = pos2Section.getDouble("Y");
                double z = pos2Section.getDouble("Z");
                float yaw = (float) pos2Section.getDouble("Yaw");
                float pitch = (float) pos2Section.getDouble("Pitch");
                return new Location(Bukkit.getWorld("YourWorldName"), x, y, z, yaw, pitch);
            }
        }
        return null;
    }

    public static void setPos1(String arenaName, Location location, float yaw, float pitch) {
        File arenaFile = new File(Main.instance.getDataFolder(), arenaName + ".yml");
        FileConfiguration arenaConfig = YamlConfiguration.loadConfiguration(arenaFile);
        if (arenaConfig != null) {
            ConfigurationSection pos1Section = arenaConfig.createSection("Pos1");
            pos1Section.set("X", location.getX());
            pos1Section.set("Y", location.getY());
            pos1Section.set("Z", location.getZ());
            pos1Section.set("Yaw", yaw);
            pos1Section.set("Pitch", pitch);
            saveArenaConfig(arenaConfig, arenaFile);
        }
    }

    public static void setPos2(String arenaName, Location location, float yaw, float pitch) {
        File arenaFile = new File(Main.instance.getDataFolder(), arenaName + ".yml");
        FileConfiguration arenaConfig = YamlConfiguration.loadConfiguration(arenaFile);
        if (arenaConfig != null) {
            ConfigurationSection pos2Section = arenaConfig.createSection("Pos2");
            pos2Section.set("X", location.getX());
            pos2Section.set("Y", location.getY());
            pos2Section.set("Z", location.getZ());
            pos2Section.set("Yaw", yaw);
            pos2Section.set("Pitch", pitch);
            saveArenaConfig(arenaConfig, arenaFile);
        }
    }

    public static FileConfiguration getArenaConfig(String arenaName) {
        File arenaFile = new File(Main.instance.getDataFolder(), arenaName + ".yml");
        return YamlConfiguration.loadConfiguration(arenaFile);
    }

    private static void saveArenaConfig(FileConfiguration arenaConfig, File arenaFile) {
        try {
            arenaConfig.save(arenaFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
