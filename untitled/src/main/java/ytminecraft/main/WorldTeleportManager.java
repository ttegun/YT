package ytminecraft.main;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class WorldTeleportManager implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (command.getName().equalsIgnoreCase("월드이동")) {
            if (args.length != 1) {
                sender.sendMessage("Usage: /월드이동 <월드 폴더 이름>");
                return false;
            }

            String worldName = args[0];
            World world = Bukkit.getWorld(worldName);

            if (world == null) {
                sender.sendMessage("The world with this name does not exist.");
                return false;
            }

            if (sender instanceof Player) {
                Player player = (Player) sender;
                player.teleport(world.getSpawnLocation());
                sender.sendMessage("Teleported to world " + worldName);
            } else {
                sender.sendMessage("This command can only be used by players.");
            }

            return true;
        }
        return false;
    }
}