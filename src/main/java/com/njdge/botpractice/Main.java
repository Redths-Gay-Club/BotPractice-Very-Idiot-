package com.njdge.botpractice;

import com.njdge.botpractice.MultiWorld.MultiWorld;
import com.njdge.botpractice.commands.ArenaSetup;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public final class Main extends JavaPlugin {

    @Override
    public void onEnable() {
        System.out.println("Plugin made by Njdge");
        System.out.println("BotPractice is on");
        Bukkit.getPluginCommand("botpractice").setExecutor(new ArenaSetup());
        Bukkit.getPluginCommand("mw").setExecutor(new MultiWorld());




    }


    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
