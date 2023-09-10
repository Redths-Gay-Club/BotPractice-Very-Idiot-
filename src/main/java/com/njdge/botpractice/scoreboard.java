package com.njdge.botpractice;

import com.njdge.botpractice.GameManager.BoxingManager;
import com.njdge.botpractice.commands.BotName;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Score;
import org.bukkit.scoreboard.Scoreboard;
public class scoreboard {
    private static Scoreboard scoreboard;
    private static Objective objective;

    public static void setupScoreboard(Player player) {
        scoreboard = Bukkit.getScoreboardManager().getNewScoreboard();
        objective = scoreboard.registerNewObjective("practice", "dummy");
        objective.setDisplaySlot(DisplaySlot.SIDEBAR);
        objective.setDisplayName(ChatColor.LIGHT_PURPLE + " AS PRACTICE");

        String opponentName = BotName.getBotName();
        Score opponentScore = objective.getScore("――――――――――――――――――――――――");
        opponentScore.setScore(0);
        Score opponentNameScore = objective.getScore(ChatColor.WHITE +"Opponent: " +ChatColor.LIGHT_PURPLE+ opponentName);
        opponentNameScore.setScore(-1);

        Score emiptydd = objective.getScore("          ");
        emiptydd.setScore(-2);        Score hitsHeader = objective.getScore(ChatColor.WHITE +"Hits:");
        hitsHeader.setScore(-3);

        int yourHits = BoxingManager.getPlayerHits();
        int theirHits = BoxingManager.getBotHits();

        Score yourHitsScore = objective.getScore(ChatColor.WHITE +"  You: " +ChatColor.LIGHT_PURPLE+ yourHits);
        yourHitsScore.setScore(-4);

        Score theirHitsScore = objective.getScore(ChatColor.WHITE +"  Them: " +ChatColor.LIGHT_PURPLE+ theirHits);
        theirHitsScore.setScore(-5);
        Score emipty = objective.getScore("       ");
        emipty.setScore(-6);
        Score pingHeader = objective.getScore("Your Ping: "+ ChatColor.LIGHT_PURPLE + "0");
        pingHeader.setScore(-7);
        Score theirPingScore = objective.getScore("Their Ping: "+ ChatColor.LIGHT_PURPLE + "-1");
        theirPingScore.setScore(-8);
        Score emipty2 = objective.getScore("    ");
        emipty2.setScore(-9);
        Score footer = objective.getScore(ChatColor.LIGHT_PURPLE +"minemen.club");
        footer.setScore(-10);
        Score aaaaaaa = objective.getScore("――――――――――――――――――――――――  ");
        aaaaaaa.setScore(-11);
        player.setScoreboard(scoreboard);
    }
    public static void lobbyscoreboard(Player player) {
        scoreboard = Bukkit.getScoreboardManager().getNewScoreboard();
        objective = scoreboard.registerNewObjective("lobby", "dummy");
        objective.setDisplaySlot(DisplaySlot.SIDEBAR);
        objective.setDisplayName(ChatColor.LIGHT_PURPLE + " LOBBY");
        Score opponentScore = objective.getScore("――――――――――――――――――――――――");
        opponentScore.setScore(0);
        Score emipty2 = objective.getScore("Player: " +ChatColor.LIGHT_PURPLE + player.getName());
        emipty2.setScore(-1);
        Score footer = objective.getScore(ChatColor.GRAY +"plugin made by Njdge.");
        footer.setScore(-2);
        Score aaaaaaa = objective.getScore("――――――――――――――――――――――――  ");
        aaaaaaa.setScore(-3);
        player.setScoreboard(scoreboard);
    }
}
