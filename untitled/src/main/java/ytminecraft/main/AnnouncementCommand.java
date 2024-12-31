package ytminecraft.main;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class AnnouncementCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (command.getName().equalsIgnoreCase("공지")) {
            if (!sender.isOp()) {
                sender.sendMessage(ChatColor.RED + "You do not have permission to use this command.");
                return true;
            }

            if (args.length == 0) {
                sender.sendMessage(ChatColor.RED + "Usage: /공지 [message]");
                return false;
            }

            String message = String.join(" ", args);
            String formattedMessage = ChatColor.GOLD + "[" + ChatColor.GREEN + "공지" + ChatColor.GOLD + "]: " + ChatColor.WHITE + message;
            Bukkit.broadcastMessage(formattedMessage);
            return true;
        }
        return false;
    }
}