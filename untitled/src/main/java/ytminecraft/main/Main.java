package ytminecraft.main;

import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.Bukkit;

public final class Main extends JavaPlugin {

    @Override
    public void onEnable() {
        // Plugin startup logic
        TitleManager titleManager = new TitleManager();
        AttendanceManager attendanceManager = new AttendanceManager();
        ShopManager shopManager = new ShopManager();
        Bukkit.getPluginManager().registerEvents(new Welcome(), this);
        Bukkit.getPluginManager().registerEvents(titleManager, this);
        Bukkit.getPluginManager().registerEvents(shopManager, this);
        this.getCommand("VIP추가").setExecutor(titleManager);
        this.getCommand("한글닉").setExecutor(titleManager);
        this.getCommand("출석체크").setExecutor(attendanceManager);
        this.getCommand("상점").setExecutor(shopManager);
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}