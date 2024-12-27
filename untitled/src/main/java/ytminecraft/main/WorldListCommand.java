package ytminecraft.main;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class WorldListCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (command.getName().equalsIgnoreCase("월드리스트")) {
            StringBuilder worldList = new StringBuilder("Current worlds:\n");
            for (World world : Bukkit.getWorlds()) {
                worldList.append(world.getName()).append("\n");
            }
            sender.sendMessage(worldList.toString());
            return true;
        }
        return false;
    }
}