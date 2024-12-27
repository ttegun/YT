package ytminecraft.main;

import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.event.entity.ExplosionPrimeEvent;
import org.bukkit.entity.EntityType;

public class ExplosionPrevention implements Listener {

    @EventHandler
    public void onExplosionPrime(ExplosionPrimeEvent event) {
        if (event.getEntity().getWorld().getName().equals("world") && event.getEntityType() != EntityType.CREEPER) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onEntityExplode(EntityExplodeEvent event) {
        if (event.getLocation().getWorld().getName().equals("world") && event.getEntityType() != EntityType.CREEPER) {
            event.setCancelled(true);
        }
    }
}