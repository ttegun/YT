package ytminecraft.main;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerPortalEvent;
import org.bukkit.plugin.java.JavaPlugin;

public class PortalListener implements Listener {

    private final JavaPlugin plugin;

    public PortalListener(JavaPlugin plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onPlayerPortal(PlayerPortalEvent event) {
        Location from = event.getFrom();
        World fromWorld = from.getWorld();

        if (fromWorld != null && fromWorld.getName().equals("survival1")) {
            World toWorld = Bukkit.getWorld("world_nether");
            if (toWorld != null) {
                Location toLocation = new Location(toWorld, from.getX(), from.getY(), from.getZ());
                event.setTo(toLocation);
            }
        } else if (fromWorld != null && fromWorld.getName().equals("world_nether")) {
            World toWorld = Bukkit.getWorld("survival1");
            if (toWorld != null) {
                Location toLocation = new Location(toWorld, from.getX(), from.getY(), from.getZ());
                event.setTo(toLocation);
            }
        }
    }
}