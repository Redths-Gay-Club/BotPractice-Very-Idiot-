package com.njdge.botpractice.GameManager;

import com.njdge.botpractice.Main;
import net.citizensnpcs.api.CitizensAPI;
import net.citizensnpcs.api.ai.NavigatorParameters;
import net.citizensnpcs.api.event.NPCDamageByEntityEvent;
import net.citizensnpcs.api.event.NPCKnockbackEvent;
import net.citizensnpcs.api.npc.NPC;
import net.citizensnpcs.api.npc.NPCRegistry;
import net.citizensnpcs.api.trait.trait.Equipment;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

public class BoxingManager {

    private final NPCRegistry npcRegistry;
    public final Map<Player, Integer> playerHits = new HashMap<>();
    public int botHits = 0;

    private final int targetHits;

    public BoxingManager(NPCRegistry npcRegistry, int targetHits) {
        this.npcRegistry = npcRegistry;
        this.targetHits = targetHits;
    }

    @EventHandler
    public void onEntityDamageByPlayer(EntityDamageByEntityEvent event) {
        if (event.getDamager() instanceof Player) {
            Player player = (Player) event.getDamager();
            playerHits.put(player, playerHits.getOrDefault(player, 0) + 1);
        }
    }

    @EventHandler
    public void onNPCDamageByEntity(NPCDamageByEntityEvent event) {
        if (CitizensAPI.getNPCRegistry().isNPC(event.getDamager())) {
            botHits++;
        }
    }

    public int getPlayerHits(Player player) {
        return playerHits.getOrDefault(player, 0);
    }

    public int getBotHits() {
        return botHits;
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
            World world = Bukkit.getWorld("cave");
            double x = arenaConfig.getDouble("Pos2.X");
            double y = arenaConfig.getDouble("Pos2.Y");
            double z = arenaConfig.getDouble("Pos2.Z");
            float yaw = (float) arenaConfig.getDouble("Pos2.Yaw");
            float pitch = (float) arenaConfig.getDouble("Pos2.Pitch");

            Location botSpawnLocation = new Location(world, x, y, z, yaw, pitch);

            NPC npc = npcRegistry.createNPC(EntityType.PLAYER, "louehoi");
            double playerSpeed = player.getWalkSpeed();
            npc.spawn(botSpawnLocation);
            npc.setProtected(false);
            npc.getEntity().setVelocity(player.getLocation().getDirection().multiply(playerSpeed));
            npc.setUseMinecraftAI(false);  // 禁用Minecraft AI
            npc.faceLocation(player.getLocation());
            ItemStack sharpDiamondSword = new ItemStack(Material.DIAMOND_SWORD);
            sharpDiamondSword.addEnchantment(Enchantment.DAMAGE_ALL, 5);
            npc.getTrait(Equipment.class).set(Equipment.EquipmentSlot.HAND, sharpDiamondSword);

            Bukkit.getScheduler().runTaskTimer(Main.instance, () -> {
                npc.getNavigator().setTarget(player, true);  // 设置导航目标
                NavigatorParameters navParams = npc.getNavigator().getDefaultParameters();

                navParams.speedModifier(1.8F); // 设置导航速度为1.8

            }, 0L, 20L); // 每隔一秒（20个游戏刻）更新一次导航目标
        }
    }
}
