package ytminecraft.main;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerChangedWorldEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class InventoryManager implements Listener {

    private final Map<UUID, Map<String, ItemStack[]>> playerInventories = new HashMap<>();

    @EventHandler
    public void onPlayerChangedWorld(PlayerChangedWorldEvent event) {
        Player player = event.getPlayer();
        String fromWorld = event.getFrom().getName();
        String toWorld = player.getWorld().getName();

        saveInventory(player, fromWorld);
        loadInventory(player, toWorld);
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        Player player = event.getPlayer();
        saveInventory(player, player.getWorld().getName());
    }

    private void saveInventory(Player player, String worldName) {
        UUID playerId = player.getUniqueId();
        playerInventories.putIfAbsent(playerId, new HashMap<>());
        playerInventories.get(playerId).put(worldName, player.getInventory().getContents());
        player.getInventory().clear();
    }

    private void loadInventory(Player player, String worldName) {
        UUID playerId = player.getUniqueId();
        Map<String, ItemStack[]> inventories = playerInventories.get(playerId);
        if (inventories != null && inventories.containsKey(worldName)) {
            player.getInventory().setContents(inventories.get(worldName));
        }
    }
}