package com.njdge.botpractice.MultiWorld;

import com.njdge.botpractice.Main;
import org.apache.commons.io.FileUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;

import static org.bukkit.Bukkit.getLogger;

public class MultiWorld implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (cmd.getName().equalsIgnoreCase("mw") && sender instanceof Player) {
            Player player = (Player) sender;

            if (args.length == 0) {
                player.sendMessage("§cPlease use /mw help for MultiWorld help.");
            }
            if (args.length >= 2 && args[0].equalsIgnoreCase("tp")) {
                String mapFolderName = args[1];
                importMap(mapFolderName);
                teleportToMap(player, mapFolderName);
                return true;
            }
            if (args.length >= 2 && args[0].equalsIgnoreCase("import")) {
                String mapFolderName = args[1];
                importMap(mapFolderName);
                player.sendMessage("§aMap Imported!");
                return true;
            }
            if (args.length >= 2 && args[0].equalsIgnoreCase("save")) {
                String mapFolderName = args[1];
                player.sendMessage("§aMap Saved!");
                World world = Bukkit.getWorld(mapFolderName);
                if (world != null) {
                    world.save();
                    SaveMap(mapFolderName);
                } else {
                    player.sendMessage("§cCan't find the: " + mapFolderName);
                }
                return true;
            }
            if (args.length >= 2 && args[0].equalsIgnoreCase("del")) {
                teleportToLobby(player);
                String mapFolderName = args[1];
                DelMap(mapFolderName);
                return true;
            }
            if (args.length >= 1 && args[0].equalsIgnoreCase("help")) {
                player.sendMessage(ChatColor.YELLOW + "/mw help §f- 本指令");
                player.sendMessage(ChatColor.YELLOW + "/mw tp <世界資料夾名稱> §f- 傳送到該世界");
                player.sendMessage(ChatColor.YELLOW + "/mw import <世界資料夾名稱> §f- 導入該世界");
                player.sendMessage(ChatColor.YELLOW + "/mw save <世界資料夾名稱> §f- 儲存該世界");
                player.sendMessage(ChatColor.YELLOW + "/mw del <世界資料夾名稱> §c- 刪除該世界");
                return true;
            }
        }
        return false;
    }

    public static void loadMaps(String mapFolderName) {
        // 获取服务器的地图文件夹路径
        String mapsFolderPath = Bukkit.getWorldContainer().getPath() + "/Maps";

        // 地图文件夹的路径
        String mapFolderPath = mapsFolderPath + "/" + mapFolderName;

        File mapFolder = new File(mapFolderPath);
        File destinationFolder = new File(Bukkit.getWorldContainer().getPath(), mapFolderName);

        if (mapFolder.exists() && mapFolder.isDirectory()) {
            if (!destinationFolder.exists()) {
                try {
                    FileUtils.copyDirectory(mapFolder, destinationFolder);
                    getLogger().info("§eCopied map: " + mapFolderName);
                } catch (IOException e) {
                    e.printStackTrace();
                    getLogger().warning("§cFailed to copy map: " + mapFolderName);
                }
            } else {
                getLogger().warning("§cMap already exists: " + mapFolderName);
            }
        } else {
            getLogger().warning("§cMap not found: " + mapFolderName);
        }
    }


    public static void importMap(String mapFolderName) {
        loadMaps(mapFolderName);

    }
    public static void SaveMap(String mapFolderName) {
        File sourceMapFolder = new File(mapFolderName);
        File destinationMapsFolder = new File("Maps");
        File destinationMapFolder = new File(destinationMapsFolder, mapFolderName);

        // 删除目标位置的文件夹（如果存在）
        if (destinationMapFolder.exists() && destinationMapFolder.isDirectory()) {
            try {
                FileUtils.deleteDirectory(destinationMapFolder);
            } catch (IOException e) {
                e.printStackTrace();
                getLogger().warning("§cFailed to delete existing map folder: " + mapFolderName);
                return;
            }
        }

        if (!destinationMapsFolder.exists()) {
            destinationMapsFolder.mkdirs();
        }

        // 复制文件夹
        try {
            FileUtils.copyDirectory(sourceMapFolder, destinationMapFolder);
            getLogger().info("§eSaved map folder: " + mapFolderName);
        } catch (IOException e) {
            e.printStackTrace();
            getLogger().warning("§cFailed to save map folder: " + mapFolderName);
        }
    }

    public static void DelMap(String mapFolderName) {
        UnloadWorld(mapFolderName);
        File destinationMapFolder = new File(mapFolderName);

        if (destinationMapFolder.exists() && destinationMapFolder.isDirectory()) {
            try {
                FileUtils.deleteDirectory(destinationMapFolder);
                getLogger().info("§eDeleted map folder: " + mapFolderName);
            } catch (IOException e) {
                e.printStackTrace();
                getLogger().warning("§cFailed to delete map folder: " + mapFolderName);
            }
        } else {
            getLogger().warning("§cMap folder not found: " + mapFolderName);
        }
    }
    private void teleportToMap(Player player, String mapFolderName) {
        // 获取地图文件夹路径
        String mapFolderPath = Bukkit.getWorldContainer().getPath() + "/Maps/" + mapFolderName;

        File mapFolder = new File(mapFolderPath);

        if (mapFolder.exists() && mapFolder.isDirectory()) {
            loadMaps(mapFolderName);

            World world = Bukkit.createWorld(new org.bukkit.WorldCreator(mapFolderName));
            if (world != null) {
                player.teleport(world.getSpawnLocation());
                player.sendMessage("§eYou have been teleported to the map: " + mapFolderName);
            } else {
                player.sendMessage("§cFailed to teleport to map: " + mapFolderName);
            }
        } else {
            player.sendMessage("§cMap not found: " + mapFolderName);
        }
    }
    public static void teleportToLobby(Player player) {
        File configFile = new File(Main.instance.getDataFolder(), "lobby.yml");
        FileConfiguration config = YamlConfiguration.loadConfiguration(configFile);
        String worldName = config.getString("lobby.world");
        double x = config.getDouble("lobby.x");
        double y = config.getDouble("lobby.y");
        double z = config.getDouble("lobby.z");
        World world = Bukkit.getWorld(worldName);
        if (world != null) {
            Location lobbyLocation = new Location(world, x, y, z);
            player.teleport(lobbyLocation);
        }
    }
    public static void UnloadWorld(String worldName) {
        World world = Bukkit.getWorld(worldName);

        if (world != null) {
            Bukkit.getServer().unloadWorld(world, false);
            getLogger().info("§cUnloaded world: " + worldName);
        } else {
            getLogger().warning("§eWorld not found: " + worldName);
        }
    }



}