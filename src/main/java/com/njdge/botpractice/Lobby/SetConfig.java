package com.njdge.botpractice.Lobby;

import org.bukkit.Location;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;

public class SetConfig {
    public static FileConfiguration getLobbyConfig() {
        File configFile = new File("plugins/BotPractice", "lobby.yml");
        return YamlConfiguration.loadConfiguration(configFile);
    }

    public static void saveLobbyConfig(FileConfiguration config) {
        File configFile = new File("plugins/BotPractice", "lobby.yml");
        try {
            config.save(configFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void setLobby(Location location, float yaw, float pitch) {
        FileConfiguration config = getLobbyConfig();
        config.set("lobby.world", location.getWorld());
        config.set("lobby.x", location.getX());
        config.set("lobby.y", location.getY());
        config.set("lobby.z", location.getZ());
        config.set("lobby.yaw", yaw);
        config.set("lobby.pitch", pitch);

        saveLobbyConfig(config);
    }
}
