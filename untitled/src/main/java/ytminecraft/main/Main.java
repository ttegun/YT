package ytminecraft.main;

import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.Bukkit;

public final class Main extends JavaPlugin {

    @Override
    public void onEnable() {
        // Plugin startup logic
        TitleManager titleManager = new TitleManager();
        Bukkit.getPluginManager().registerEvents(new Welcome(), this);
        Bukkit.getPluginManager().registerEvents(titleManager, this);
        this.getCommand("VIP추가").setExecutor(titleManager);
        this.getCommand("한글닉").setExecutor(titleManager);
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}