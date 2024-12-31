package ytminecraft.main;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.InvalidDescriptionException;
import org.bukkit.plugin.InvalidPluginException;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;

import java.io.File;

public class PluginReloader implements CommandExecutor {

    private final Plugin plugin;

    public PluginReloader(Plugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (command.getName().equalsIgnoreCase("리로드")) {
            if (!sender.isOp()) {
                sender.sendMessage("You do not have permission to use this command.");
                return true;
            }

            PluginManager pluginManager = plugin.getServer().getPluginManager();
            File pluginFile = new File(plugin.getClass().getProtectionDomain().getCodeSource().getLocation().getPath());

            try {
                pluginManager.disablePlugin(plugin);
                pluginManager.loadPlugin(pluginFile);
                pluginManager.enablePlugin(pluginManager.getPlugin(plugin.getName()));
                sender.sendMessage("Plugin reloaded successfully.");
            } catch (InvalidPluginException | InvalidDescriptionException e) {
                sender.sendMessage("Failed to reload the plugin: " + e.getMessage());
                e.printStackTrace();
            }

            return true;
        }
        return false;
    }
}