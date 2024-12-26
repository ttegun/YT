package ytminecraft.main;

import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;

public final class Main extends JavaPlugin {

    private ScoreboardManager scoreboardManager;
    private Map<Player, Integer> attendanceCoinsMap;

    @Override
    public void onEnable() {
        // Plugin startup logic
        TitleManager titleManager = new TitleManager();
        AttendanceManager attendanceManager = new AttendanceManager();
        ShopManager shopManager = new ShopManager();
        scoreboardManager = new ScoreboardManager(this);
        attendanceCoinsMap = new HashMap<>();

        Bukkit.getPluginManager().registerEvents(new Welcome(), this);
        Bukkit.getPluginManager().registerEvents(titleManager, this);
        Bukkit.getPluginManager().registerEvents(shopManager, this);
        this.getCommand("VIP추가").setExecutor(titleManager);
        this.getCommand("한글닉").setExecutor(titleManager);
        this.getCommand("출석체크").setExecutor(attendanceManager);
        this.getCommand("상점").setExecutor(shopManager);

        // Example usage: create scoreboard for all online players
        for (Player player : Bukkit.getOnlinePlayers()) {
            int attendanceCoins = getAttendanceCoins(player); // Retrieve attendance coins for the player
            scoreboardManager.createScoreboard(player, attendanceCoins);
        }
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    private int getAttendanceCoins(Player player) {
        // Retrieve the attendance coins for the player from the map
        return attendanceCoinsMap.getOrDefault(player, 0);
    }

    // Example method to add attendance coins for a player
    public void addAttendanceCoins(Player player, int coins) {
        attendanceCoinsMap.put(player, attendanceCoinsMap.getOrDefault(player, 0) + coins);
    }
}