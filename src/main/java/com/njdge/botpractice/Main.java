package com.njdge.botpractice;

import com.njdge.botpractice.GameManager.BoxingManager;
import com.njdge.botpractice.Listener.BotAttackListener;
import com.njdge.botpractice.MultiWorld.MultiWorld;
import com.njdge.botpractice.commands.ArenaSetup;
import com.njdge.botpractice.commands.Duel;
import net.citizensnpcs.api.CitizensAPI;
import net.citizensnpcs.api.npc.NPC;
import net.citizensnpcs.api.npc.NPCRegistry;
import org.bukkit.Bukkit;
import org.bukkit.entity.EntityType;
import org.bukkit.plugin.java.JavaPlugin;

public final class Main extends JavaPlugin {
    public static Main instance; // 靜態變數來存儲插件實例
    private BoxingManager boxingManager;

    @Override
    public void onEnable() {
        instance = this;

        System.out.println("Plugin made by Njdge");
        System.out.println("BotPractice is on");
        Bukkit.getPluginCommand("bp").setExecutor(new ArenaSetup());
        Bukkit.getPluginCommand("mw").setExecutor(new MultiWorld());

        NPCRegistry npcRegistry = CitizensAPI.getNPCRegistry(); // 获取NPCRegistry实例
        boxingManager = new BoxingManager(npcRegistry, 100); // 将npcRegistry传递给BoxingManager构造函数

        getCommand("duel").setExecutor(new Duel(boxingManager));
        BotAttackListener botAttackListener = new BotAttackListener(boxingManager);
        Bukkit.getPluginManager().registerEvents(botAttackListener, this);
    }



    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
