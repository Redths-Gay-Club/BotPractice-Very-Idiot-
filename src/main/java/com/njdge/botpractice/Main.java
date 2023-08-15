package com.njdge.botpractice;

import com.njdge.botpractice.MultiWorld.MultiWorld;
import com.njdge.botpractice.commands.ArenaSetup;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public final class Main extends JavaPlugin {
    public static Main instance; // 靜態變數來存儲插件實例

    @Override
    public void onEnable() {
        instance = this;

        System.out.println("Plugin made by Njdge");
        System.out.println("BotPractice is on");
        Bukkit.getPluginCommand("bp").setExecutor(new ArenaSetup());
        Bukkit.getPluginCommand("mw").setExecutor(new MultiWorld());


    }


    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
