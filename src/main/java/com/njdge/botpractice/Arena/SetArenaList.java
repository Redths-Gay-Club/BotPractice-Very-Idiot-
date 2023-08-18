package com.njdge.botpractice.Arena;

import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class SetArenaList {

    public static FileConfiguration getArenaListConfig() {
        File configFile = new File("plugins/BotPractice", "ArenaList.yml");
        return YamlConfiguration.loadConfiguration(configFile);
    }

    public static void saveArenaListConfig(FileConfiguration config) {
        File configFile = new File("plugins/BotPractice", "ArenaList.yml");
        try {
            config.save(configFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void addArena(String arenaName) {
        FileConfiguration arenaListConfig = getArenaListConfig();
        ConfigurationSection arenaListSection = arenaListConfig.getConfigurationSection("ArenaList");
        if (arenaListSection == null) {
            arenaListSection = arenaListConfig.createSection("ArenaList");
        }
        int arenaCount = arenaListSection.getKeys(false).size() + 1;
        arenaListSection.set(String.valueOf(arenaCount), arenaName);
        saveArenaListConfig(arenaListConfig);
    }

    public static List<String> getArenaNames() {
        FileConfiguration arenaListConfig = plugin.getConfig();

        ConfigurationSection arenaListSection = arenaListConfig.getConfigurationSection("ArenaList");

        if (arenaListSection != null) {
            for (String key : arenaListSection.getKeys(false)) {
                String arenaName = arenaListSection.getString(key);
                System.out.println("Arena Name: " + arenaName);
            }
        }
        return null;
    }


        private static Plugin plugin;

        public SetArenaList(Plugin plugin) {
            SetArenaList.plugin = plugin;
        }


    }
