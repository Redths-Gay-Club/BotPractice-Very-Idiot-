package com.njdge.botpractice.commands;

import com.njdge.botpractice.GameManager.BoxingManager;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class Duel implements CommandExecutor {
    private BoxingManager boxingManager;
    public Duel(BoxingManager boxingManager) {
        this.boxingManager = boxingManager;
    }



    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("This command can only be used by players.");

            return true;

        }

        Player player = (Player) sender;
        if (command.getName().equalsIgnoreCase("duel") && sender instanceof Player) {
            if (args.length == 0) {
                boxingManager.spawnBot(player, "cave");
                boxingManager.teleportToArena(player, "cave");
                boxingManager.playerHits.clear();
                boxingManager.botHits = 0;

                // 给予玩家 Speed 2 效果
                player.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, Integer.MAX_VALUE, 1));

                // 给予玩家一把鋒利十的鑽石劍
                PlayerInventory inventory = player.getInventory();
                inventory.clear();
                ItemStack sharpDiamondSword = new ItemStack(Material.DIAMOND_SWORD);
                sharpDiamondSword.addEnchantment(Enchantment.DAMAGE_ALL, 5);
                inventory.addItem(sharpDiamondSword);
                return true;
            }
            return true;

        }
        return false;

    }
}
