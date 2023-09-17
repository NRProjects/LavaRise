package plugins.nate.lavarise;

import org.bukkit.*;
import org.bukkit.scheduler.BukkitRunnable;

public class LavaRiseManager {
    private final LavaRise plugin;
    private static final int WORLDBORDER_SIZE = 100;
    private static final int GRACE_PERIOD_SEC = 600;

    public LavaRiseManager(LavaRise plugin) {
        this.plugin = plugin;
    }

    public void startLavaRise(World world) {
        setupWorldBorder(world);

        int bedrockLevel = -63;
        int seaLevel = 62;

        long gracePeriodTimeTicks = GRACE_PERIOD_SEC * 20;
        int levelsToRiseDuringGrace = seaLevel - bedrockLevel;

        long delayBetweenRises = gracePeriodTimeTicks / levelsToRiseDuringGrace;

        new BukkitRunnable() {
            int yLevel = bedrockLevel;

            @Override
            public void run() {
                int halfSize = WORLDBORDER_SIZE / 2;
                for (int x = -halfSize; x <= halfSize; x++) {
                    for (int z = -halfSize; z <= halfSize; z++) {
                        world.getBlockAt(x, yLevel, z).setType(Material.LAVA);
                    }
                }

                yLevel++;

                if (yLevel == seaLevel) {
                    this.cancel();
                    startLavaRisePhaseTwo(world);
                }
            }
        }.runTaskTimer(plugin, 0L, delayBetweenRises);
    }

    private void startLavaRisePhaseTwo(World world) {
        long fasterDelay = 100L;
        new BukkitRunnable() {
            int yLevel = 62;

            @Override
            public void run() {
                for (int x = -WORLDBORDER_SIZE / 2; x <= WORLDBORDER_SIZE / 2; x++) {
                    for (int z = -WORLDBORDER_SIZE / 2; z <= WORLDBORDER_SIZE / 2; z++) {
                        world.getBlockAt(x, yLevel, z).setType(Material.LAVA);
                    }
                }
                yLevel++;

            }
        }.runTaskTimer(plugin, 0L, fasterDelay);
    }

    private void setupWorldBorder(World world) {
        WorldBorder border = world.getWorldBorder();
        border.setCenter(0, 0);
        border.setSize(WORLDBORDER_SIZE);
    }

//    private static final int[][] DIRECTIONS = {
//        {1, 0, 0},  // East
//        {-1, 0, 0}, // West
//        {0, 1, 0},  // Up
//        {0, -1, 0}, // Down
//        {0, 0, 1},  // South
//        {0, 0, -1}  // North
//    };
//
//    public static void handleWaterInteraction(World world, int x, int y, int z) {
//        for (int[] direction : DIRECTIONS) {
//            int dx = x + direction[0];
//            int dy = y + direction[1];
//            int dz = z + direction[2];
//
//            Block adjacentBlock = world.getBlockAt(dx, dy, dz);
//            if (adjacentBlock.getType() == Material.WATER) {
//                adjacentBlock.setType(Material.COBBLESTONE);
//            }
//        }
//    }
}
