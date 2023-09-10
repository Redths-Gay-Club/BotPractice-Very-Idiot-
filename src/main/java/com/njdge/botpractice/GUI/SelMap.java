package com.njdge.botpractice.GUI;

import com.njdge.botpractice.Arena.SetArenaList;
import com.njdge.botpractice.GameManager.BoxingManager;
import com.njdge.botpractice.GameManager.GameManager;
import com.njdge.botpractice.Main;
import com.njdge.botpractice.commands.BotName;
import net.citizensnpcs.api.CitizensAPI;
import net.citizensnpcs.api.ai.NavigatorParameters;
import net.citizensnpcs.api.npc.NPC;
import net.citizensnpcs.api.trait.trait.Equipment;
import org.bukkit.*;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


import static com.njdge.botpractice.GameManager.BoxingManager.*;
import static com.njdge.botpractice.MultiWorld.MultiWorld.loadMaps;
import static com.njdge.botpractice.MultiWorld.MultiWorld.teleportToLobby;

public class SelMap implements Listener {
    private static Map<Player, Boolean> playerInGame = new HashMap<>();

    public SelMap(BoxingManager boxingManager) {
        gui = Bukkit.createInventory(null, 54, "Select Map");
        Bukkit.getPluginManager().registerEvents(this, Main.instance);
        createGUI();
    }
    private static Inventory gui = null;
    private void createGUI() {
        for (String arenaName : getArenaNames()) {
            ItemStack paper = new ItemStack(Material.PAPER);
            ItemMeta meta = paper.getItemMeta();
            meta.setDisplayName(arenaName);
            paper.setItemMeta(meta);
            gui.addItem(paper);
        }
    }

    private List<String> getArenaNames() {
        FileConfiguration arenaListConfig = SetArenaList.getArenaListConfig();
        ConfigurationSection arenaListSection = arenaListConfig.getConfigurationSection("ArenaList");
        List<String> arenaNames = new ArrayList<>();

        if (arenaListSection != null) {
            for (String key : arenaListSection.getKeys(false)) {
                String arenaName = arenaListSection.getString(key);
                arenaNames.add(arenaName);
            }
        }

        return arenaNames;
    }
    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        if (event.getInventory().equals(gui)) {
            event.setCancelled(true);
            Player player = (Player) event.getWhoClicked();
            ItemStack clickedItem = event.getCurrentItem();
            if (event.getClick() == ClickType.LEFT) {
                player.closeInventory();
                if (clickedItem != null && clickedItem.getType() == Material.PAPER) {
                    String arenaName = clickedItem.getItemMeta().getDisplayName();
                    if (!playerInGame.containsKey(player) || !playerInGame.get(player)) {
                        if(BotName.getBotName() != null) {
                            startGame(player, arenaName);
                            playerInGame.put(player, true);
                        }else{
                            player.sendMessage("§c請先設定機器人名稱/botname <name>");
                        }
                    }
                }
            }
        }
    }

    private static void startGame(Player player, String arenaName) {
        String mapFolderPath = Bukkit.getWorldContainer().getPath() + "/Maps/" + arenaName;
        File mapFolder = new File(mapFolderPath);

        if (mapFolder.exists() && mapFolder.isDirectory()) {
            try {
                loadMaps(arenaName);
                World world = Bukkit.createWorld(new org.bukkit.WorldCreator(arenaName));
                if (world != null) {
                    player.teleport(world.getSpawnLocation());
                    player.sendMessage("§eYou have been teleported to the map: " + arenaName);
                } else {
                    player.sendMessage("§cFailed to teleport to map: " + arenaName);
                }
            } catch (Exception e) {
                e.printStackTrace();
                player.sendMessage("§cAn error occurred while loading the map: " + arenaName);
                return;
            }
        } else {
            player.sendMessage("§cMap not found: " + arenaName);
            return;
        }
        GameManager.setGameState(player, GameManager.GameState.IN_GAME);

        spawnBot(player, arenaName);
        teleportToArena(player, arenaName);
        player.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, Integer.MAX_VALUE, 1));
        PlayerInventory inventory = player.getInventory();
        inventory.clear();
        ItemStack sharpDiamondSword = new ItemStack(Material.DIAMOND_SWORD);
        sharpDiamondSword.addEnchantment(Enchantment.DAMAGE_ALL, 5);
        inventory.addItem(sharpDiamondSword);
    }

    public static void openGUI(Player player) {
        player.openInventory(gui);
    }

}