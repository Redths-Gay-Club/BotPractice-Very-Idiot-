package com.njdge.botpractice.GameManager;

import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;

public class GameManager {
    public enum GameState {
        NONE,
        IN_GAME
    }
    private static Map<Player, GameState> playerGameStates = new HashMap<>();

    // 設定玩家的遊戲狀態
    public static void setGameState(Player player, GameState state) {
        playerGameStates.put(player, state);
    }

    // 獲取玩家的遊戲狀態
    public static GameState getGameState(Player player) {
        return playerGameStates.getOrDefault(player, GameState.NONE);
    }


}
