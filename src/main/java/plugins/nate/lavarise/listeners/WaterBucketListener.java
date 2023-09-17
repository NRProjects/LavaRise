package plugins.nate.lavarise.listeners;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerBucketEmptyEvent;

public class WaterBucketListener implements Listener {
    @EventHandler
    public void onPlayerBucketEmpty(PlayerBucketEmptyEvent event) {
        if (event.getBucket() == Material.WATER_BUCKET) {
            event.setCancelled(true);

            Location location = event.getPlayer().getLocation();
            event.getPlayer().playSound(location, Sound.ENTITY_GENERIC_EXTINGUISH_FIRE, 0.1f, 0.1f);
        }
    }
}
