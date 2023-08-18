package com.njdge.botpractice;

import com.njdge.botpractice.GUI.SelMap;
import com.njdge.botpractice.GameManager.BoxingManager;
import com.njdge.botpractice.Listener.Damage;
import com.njdge.botpractice.Listener.HitEvent;
import com.njdge.botpractice.Listener.core;
import com.njdge.botpractice.Listener.onPlayerJoin;
import com.njdge.botpractice.MultiWorld.MultiWorld;
import com.njdge.botpractice.commands.ArenaSetup;
import com.njdge.botpractice.commands.Duel;
import com.njdge.botpractice.commands.L;
import com.njdge.botpractice.commands.kill;
import net.citizensnpcs.api.CitizensAPI;
import net.citizensnpcs.api.npc.NPCRegistry;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Score;
import org.bukkit.scoreboard.Scoreboard;

import javax.swing.*;

public final class Main extends JavaPlugin {
    public static Main instance;
    private BoxingManager boxingManager;
    private SelMap selMap; // Add this line

    @Override
    public void onEnable() {
        instance = this;

        System.out.println("Plugin made by Njdge");
        System.out.println("BotPractice is on");
        Bukkit.getPluginCommand("bp").setExecutor(new ArenaSetup());
        Bukkit.getPluginCommand("mw").setExecutor(new MultiWorld());
        NPCRegistry npcRegistry = CitizensAPI.getNPCRegistry();
        boxingManager = new BoxingManager(npcRegistry, 100);
        Bukkit.getPluginManager().registerEvents(new Damage(), this);
        Bukkit.getPluginCommand("k").setExecutor(new kill());
        Bukkit.getPluginManager().registerEvents(new core(), this);
        Bukkit.getPluginCommand("duel").setExecutor(new Duel(boxingManager));
        selMap = new SelMap(boxingManager);
        Bukkit.getPluginManager().registerEvents(selMap, this);
        Bukkit.getPluginCommand("l").setExecutor(new L());
        Bukkit.getPluginManager().registerEvents(new onPlayerJoin(), this);


    }

    @Override
    public void onDisable() {
        }
    }


