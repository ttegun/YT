package ytminecraft.main;

import org.bukkit.Bukkit;
import org.bukkit.Difficulty;
import org.bukkit.World;
import org.bukkit.WorldCreator;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.world.WorldLoadEvent;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;

public final class Main extends JavaPlugin implements Listener {

@Override
public void onEnable() {
    // Plugin startup logic
    TitleManager titleManager = new TitleManager();
    AttendanceManager attendanceManager = new AttendanceManager();
    ShopManager shopManager = new ShopManager();
    ScoreboardManager scoreboardManager = new ScoreboardManager(this);
    WorldTeleportManager worldTeleportManager = new WorldTeleportManager();

    Bukkit.getPluginManager().registerEvents(new Welcome(), this);
    Bukkit.getPluginManager().registerEvents(titleManager, this);
    Bukkit.getPluginManager().registerEvents(shopManager, this);
    Bukkit.getPluginManager().registerEvents(new TitleDisplayListener(), this);
    Bukkit.getPluginManager().registerEvents(new ExplosionPrevention(), this);
    Bukkit.getPluginManager().registerEvents(new InventoryManager(), this);
    Bukkit.getPluginManager().registerEvents(this, this); // Register this class as an event listener
    Bukkit.getPluginManager().registerEvents(new PortalListener(this), this);
    Bukkit.getPluginManager().registerEvents(new CommandBlocker(), this);
    Bukkit.getPluginManager().registerEvents(worldTeleportManager, this);
    this.getCommand("VIP추가").setExecutor(titleManager);
    this.getCommand("한글닉").setExecutor(titleManager);
    this.getCommand("출석체크").setExecutor(attendanceManager);
    this.getCommand("상점").setExecutor(shopManager);
    this.getCommand("야생생성").setExecutor(new WorldManager());
    this.getCommand("워프").setExecutor(worldTeleportManager);
    this.getCommand("평지생성").setExecutor(new FlatWorldManager());
    this.getCommand("리로드").setExecutor(new PluginReloader(this));
    this.getCommand("월드리스트").setExecutor(new WorldListCommand());
    this.getCommand("공지").setExecutor(new AnnouncementCommand()); // Register the new command

    // Load all worlds from folders containing level.dat
    File[] files = getServer().getWorldContainer().listFiles();
    if (files != null) {
        for (File file : files) {
            if (file.isDirectory() && new File(file, "level.dat").exists()) {
                String sanitizedWorldName = sanitizeWorldName(file.getName());
                World world = Bukkit.createWorld(new WorldCreator(sanitizedWorldName));
                setWorldDifficulty(world);
            }
        }
    }

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

    private void setWorldDifficulty(World world) {
        switch (world.getName()) {
            case "world":
                world.setDifficulty(Difficulty.PEACEFUL);
                break;
            case "world_nether":
                world.setDifficulty(Difficulty.HARD);
                break;
            case "world_the_end":
                world.setDifficulty(Difficulty.HARD);
                break;
            case "survival1":
                world.setDifficulty(Difficulty.HARD);
                break;
            case "flat1":
                world.setDifficulty(Difficulty.PEACEFUL);
                break;
            default:
                world.setDifficulty(Difficulty.NORMAL);
                break;
        }
    }

    @EventHandler
    public void onWorldLoad(WorldLoadEvent event) {
        setWorldDifficulty(event.getWorld());
    }

    private String sanitizeWorldName(String worldName) {
        return worldName.replaceAll("[^a-z0-9/._-]", "").toLowerCase();
    }
}