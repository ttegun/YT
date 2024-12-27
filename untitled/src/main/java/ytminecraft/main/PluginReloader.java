package ytminecraft.main;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;

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
            pluginManager.disablePlugin(plugin);
            pluginManager.enablePlugin(plugin);
            sender.sendMessage("Plugin reloaded successfully.");
            return true;
        }
        return false;
    }
}