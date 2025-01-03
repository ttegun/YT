package ytminecraft.main;

import org.bukkit.GameMode;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.Bukkit;
import org.bukkit.World;

public class RespawnListener implements Listener {

    @EventHandler
    public void onPlayerRespawn(PlayerRespawnEvent event) {
        World mainWorld = Bukkit.getWorld("world"); // Replace "world" with the name of your main world
        if (event.getRespawnLocation().getWorld().equals(mainWorld)) {
            event.getPlayer().setGameMode(GameMode.SURVIVAL);
        }
    }
}