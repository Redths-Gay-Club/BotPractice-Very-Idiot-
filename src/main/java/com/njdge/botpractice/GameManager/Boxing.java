package com.njdge.botpractice.GameManager;

import org.bukkit.entity.Player;
import net.citizensnpcs.api.npc.NPC;

public class Boxing {
    private Player player;
    private NPC npc;
    private int playerHits = 0;
    private int npcHits = 0;
    private boolean isRunning = false;

    public Boxing(Player player, NPC npc) {
        this.player = player;
        this.npc = npc;
    }

    public void startMatch() {
        isRunning = true;
        player.sendMessage("Boxing match started! Punch the NPC to increase your hits.");
        npc.spawn(player.getLocation()); // Spawn NPC for the match
    }

    public void playerPunch() {
        if (!isRunning) {
            player.sendMessage("The match hasn't started yet.");
            return;
        }

        playerHits++;
        player.sendMessage("You punched the NPC! Your hits: " + playerHits);

        checkHits();
    }

    public void npcPunch() {
        if (!isRunning) {
            return;
        }

        npcHits++;
        checkHits();
    }

    private void checkHits() {
        if (playerHits >= 100) {
            player.sendMessage("Congratulations! You win the match!");
            endMatch();
        } else if (npcHits >= 100) {
            player.sendMessage("You lost the match. The NPC wins.");
            endMatch();
        }
    }

    private void endMatch() {
        isRunning = false;
        playerHits = 0;
        npcHits = 0;
        npc.despawn();
    }
}
