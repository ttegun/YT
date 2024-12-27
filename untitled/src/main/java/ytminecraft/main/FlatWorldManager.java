package ytminecraft.main;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.WorldCreator;
import org.bukkit.WorldType;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class FlatWorldManager implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (command.getName().equalsIgnoreCase("평지생성")) {
            if (!(sender instanceof Player) || !sender.isOp()) {
                sender.sendMessage("You do not have permission to use this command.");
                return true;
            }

            if (args.length != 1) {
                sender.sendMessage("Usage: /평지생성 <월드 이름>");
                return false;
            }

            String worldName = args[0].toLowerCase().replaceAll("[^a-z0-9가-힣/._-]", "");
            if (worldName.isEmpty()) {
                sender.sendMessage("Invalid world name. Must contain only [a-z0-9가-힣/._-]");
                return false;
            }

            World world = Bukkit.getWorld(worldName);

            if (world != null) {
                sender.sendMessage("A world with this name already exists.");
                return false;
            }

            WorldCreator worldCreator = new WorldCreator(worldName);
            worldCreator.type(WorldType.FLAT);
            world = Bukkit.createWorld(worldCreator);

            if (world != null) {
                sender.sendMessage("Flat world " + worldName + " has been created successfully.");
                if (sender instanceof Player) {
                    Player player = (Player) sender;
                    player.teleport(world.getSpawnLocation());
                }
            } else {
                sender.sendMessage("Failed to create the flat world.");
            }

            return true;
        }
        return false;
    }
}