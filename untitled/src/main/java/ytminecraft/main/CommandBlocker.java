package ytminecraft.main;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class CommandBlocker implements Listener {

    private final Set<String> blockedCommands = new HashSet<>(Arrays.asList(
        "/op", "/deop", "/stop", "/reload", "/ban", "/kick"
    ));

    @EventHandler
    public void onPlayerCommandPreprocess(PlayerCommandPreprocessEvent event) {
        String message = event.getMessage().toLowerCase();
        for (String command : blockedCommands) {
            if (message.startsWith(command)) {
                event.setCancelled(true);
                event.getPlayer().sendMessage("This command is blocked.");
                return;
            }
        }
    }
}