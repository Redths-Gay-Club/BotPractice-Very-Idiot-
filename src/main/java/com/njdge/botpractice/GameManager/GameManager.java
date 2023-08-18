package com.njdge.botpractice.GameManager;

import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;

public class GameManager {
    private Map<Player, PlayerState> playerStates = new HashMap<>();

    public enum PlayerState {
        IN_GAME,
        NONE
    }

    public PlayerState getPlayerState(Player player) {
        return playerStates.getOrDefault(player, PlayerState.NONE);
    }

    public void setPlayerState(Player player, PlayerState state) {
        playerStates.put(player, state);
    }
}
