package plugins.nate.lavarise;

import org.bukkit.*;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

public class LavaRiseManager {
    private final LavaRise plugin;
    private final int worldBorderSize;
    private final int gracePeriodSec;
    private final long secondsPerBlockAfterSeaLevel;
    private Location coords;
    private BukkitTask phaseOneTask;
    private BukkitTask phaseTwoTask;

    public LavaRiseManager(LavaRise plugin) {
        this.plugin = plugin;
        this.worldBorderSize = plugin.getConfig().getInt("worldborder.size");
        this.gracePeriodSec = plugin.getConfig().getInt("grace-period.seconds");
        this.secondsPerBlockAfterSeaLevel = plugin.getConfig().getLong("lava-rise-after-sea-level.seconds-per-block");
    }

    public void setCoords(Location coords) {
        this.coords = coords;
    }

    private void setupWorldBorder(World world) {
        WorldBorder border = world.getWorldBorder();
        border.setCenter(coords.getX(), coords.getZ());
        border.setSize(worldBorderSize);
    }

    public void startLavaRise(World world) {
        setupWorldBorder(world);
        long delayBetweenRises = (gracePeriodSec * 20L) / (62 + 63);
        runLavaRiseTask(world, delayBetweenRises, 62, () -> startLavaRisePhaseTwo(world));
    }

    private void startLavaRisePhaseTwo(World world) {
        long delay = secondsPerBlockAfterSeaLevel * 20;
        runLavaRiseTask(world, delay, Integer.MAX_VALUE, null);
    }

    private void runLavaRiseTask(World world, long delay, int maxYLevel, Runnable onComplete) {
        int startX = (int) coords.getX() - worldBorderSize / 2;
        int endX = (int) coords.getX() + worldBorderSize / 2;
        int startZ = (int) coords.getZ() - worldBorderSize / 2;
        int endZ = (int) coords.getZ() + worldBorderSize / 2;

        BukkitTask task = new BukkitRunnable() {
            int yLevel = -63;

            @Override
            public void run() {
                for (int x = startX; x <= endX; x++) {
                    for (int z = startZ; z <= endZ; z++) {
                        world.getBlockAt(x, yLevel, z).setType(Material.LAVA);
                    }
                }

                yLevel++;

                if (yLevel == maxYLevel) {
                    this.cancel();
                    if (onComplete != null) onComplete.run();
                }
            }
        }.runTaskTimer(plugin, 0L, delay);

        if (maxYLevel == 62) phaseOneTask = task;
        else phaseTwoTask = task;
    }

    public void stopLavaRise(World world) {
        WorldBorder border = world.getWorldBorder();
        border.reset();

        if (phaseOneTask != null) {
            phaseOneTask.cancel();
            phaseOneTask = null;
        }
        if (phaseTwoTask != null) {
            phaseTwoTask.cancel();
            phaseTwoTask = null;
        }
    }
}
