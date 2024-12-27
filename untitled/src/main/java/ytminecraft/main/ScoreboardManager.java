package ytminecraft.main;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scoreboard.*;

import java.text.SimpleDateFormat;
import java.util.Date;

public class ScoreboardManager {

    private final JavaPlugin plugin;

    public ScoreboardManager(JavaPlugin plugin) {
        this.plugin = plugin;
    }

    public void createScoreboard(Player player, int attendanceCoins) {
        org.bukkit.scoreboard.ScoreboardManager manager = Bukkit.getScoreboardManager();
        Scoreboard board = manager.getNewScoreboard();

        // Create the objective
        Objective objective = board.registerNewObjective("info", "dummy", ChatColor.RED + "" + ChatColor.BOLD + "유튜브 소통방");
        objective.setDisplaySlot(DisplaySlot.SIDEBAR);

        // Set the current real-world time
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
        String currentTime = sdf.format(new Date());
        Score timeScore = objective.getScore(ChatColor.GREEN + "현실시각: " + ChatColor.WHITE + currentTime);
        timeScore.setScore(3);

        // Set the attendance coins
        Score coinsScore = objective.getScore(ChatColor.GREEN + "출석코인: " + ChatColor.WHITE + attendanceCoins);
        coinsScore.setScore(2);

        // Set the player's title
        String title = TitleManager.getTitle(player.getUniqueId());
        Score titleScore = objective.getScore(ChatColor.GREEN + "당신의 칭호: " + ChatColor.WHITE + title);
        titleScore.setScore(1);

        // Assign the scoreboard to the player
        player.setScoreboard(board);
    }
}