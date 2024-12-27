package ytminecraft.main;

import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.Bukkit;

public final class Main extends JavaPlugin {

    @Override
    public void onEnable() {
        // Plugin startup logic
        TitleManager titleManager = new TitleManager();
        AttendanceManager attendanceManager = new AttendanceManager();
        ShopManager shopManager = new ShopManager();
        ScoreboardManager scoreboardManager = new ScoreboardManager(this);

        Bukkit.getPluginManager().registerEvents(new Welcome(), this);
        Bukkit.getPluginManager().registerEvents(titleManager, this);
        Bukkit.getPluginManager().registerEvents(shopManager, this);
        Bukkit.getPluginManager().registerEvents(new TitleDisplayListener(), this);
        Bukkit.getPluginManager().registerEvents(new ExplosionPrevention(), this);
        Bukkit.getPluginManager().registerEvents(new InventoryManager(), this);
        this.getCommand("VIP추가").setExecutor(titleManager);
        this.getCommand("한글닉").setExecutor(titleManager);
        this.getCommand("출석체크").setExecutor(attendanceManager);
        this.getCommand("상점").setExecutor(shopManager);
        this.getCommand("야생생성").setExecutor(new WorldManager());
        this.getCommand("워프").setExecutor(new WorldTeleportManager());
        this.getCommand("평지생성").setExecutor(new FlatWorldManager());
        this.getCommand("리로드").setExecutor(new PluginReloader(this));
        this.getCommand("월드리스트").setExecutor(new WorldListCommand());

        // Schedule a repeating task to update the scoreboard every 20 ticks (1 second)
        Bukkit.getScheduler().scheduleSyncRepeatingTask(this, new Runnable() {
            @Override
            public void run() {
                for (Player player : Bukkit.getOnlinePlayers()) {
                    int attendanceCoins = getAttendanceCoins(player); // Retrieve attendance coins for the player
                    scoreboardManager.createScoreboard(player, attendanceCoins);
                }
            }
        }, 0L, 20L); // 0L delay, 20L period (1 second)
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    private int getAttendanceCoins(Player player) {
        // Retrieve the attendance coins for the player from the map
        return AttendanceManager.getAttendanceCoins(player.getUniqueId());
    }
}