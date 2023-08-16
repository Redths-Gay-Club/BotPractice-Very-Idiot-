package com.njdge.botpractice.Listener;

import com.njdge.botpractice.GameManager.BoxingManager;
import net.citizensnpcs.api.npc.NPC;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

import java.util.HashMap;
import java.util.Map;

public class BotAttackListener implements Listener {
    private final BoxingManager boxingManager;
    private final Map<NPC, Integer> npcHits = new HashMap<>();

    public BotAttackListener(BoxingManager boxingManager) {
        this.boxingManager = boxingManager;
    }

    @EventHandler
    public void onEntityDamageByEntity(EntityDamageByEntityEvent event) {
        if (event.getEntityType() == EntityType.PLAYER && event.getDamager() instanceof NPC) {
            Player player = (Player) event.getEntity();
            NPC npc = (NPC) event.getDamager();

            if (npcHits.containsKey(npc)) {
                int currentHits = npcHits.get(npc);
                npcHits.put(npc, currentHits + 1);

                // 更新 BoxingManager 中的 hit 值
                boxingManager.addHit(player);

                // 可以在此处添加更多逻辑，例如检查击打次数是否达到目标等
            }
        }
    }
}
