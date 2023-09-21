package plugins.nate.lavarise;

import org.bukkit.command.CommandExecutor;
import org.bukkit.command.PluginCommand;
import org.bukkit.plugin.java.JavaPlugin;
import plugins.nate.lavarise.commands.LavaRiseCommand;
import plugins.nate.lavarise.listeners.WaterBucketListener;

public final class LavaRise extends JavaPlugin {
    private static LavaRise plugin;

    @Override
    public void onEnable() {
        super.onEnable();
        plugin = this;
        saveDefaultConfig();

        LavaRiseManager lavaRiseManager = new LavaRiseManager(this);
        setupCommand("lr", new LavaRiseCommand(lavaRiseManager));

        getServer().getPluginManager().registerEvents(new WaterBucketListener(), this);
    }

    private void setupCommand(String commandLabel, CommandExecutor executor) {
        PluginCommand command = getCommand(commandLabel);
        if (command != null) {
            command.setExecutor(executor);
        }
    }
}