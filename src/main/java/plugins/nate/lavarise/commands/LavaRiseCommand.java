package plugins.nate.lavarise.commands;

import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import plugins.nate.lavarise.LavaRiseManager;
import plugins.nate.lavarise.utils.ChatUtils;

public class LavaRiseCommand implements CommandExecutor {
    private final LavaRiseManager manager;

    public LavaRiseCommand(LavaRiseManager manager) {
        this.manager = manager;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player player)) {
            sender.sendMessage("This command can only be used by a player.");
            return true;
        }

        World world = player.getWorld();

        if (args.length > 0) {
            switch (args[0].toLowerCase()) {
                case "start" -> {
                    manager.setCoords(player.getLocation());
                    manager.startLavaRise(world);
                    player.sendMessage(ChatUtils.coloredChat("&8[&cLavaRise&8] &cLava has started spilling from the ground!"));
                }
                case "stop" -> {
                    manager.stopLavaRise(world);
                    player.sendMessage(ChatUtils.coloredChat("&8[&cLavaRise&8] &cStopping Lava rise!"));
                }
                default ->
                        player.sendMessage(ChatUtils.coloredChat("&8[&cLavaRise&8] &cInvalid command. Use /lr start or /lr stop"));
            }
            return true;
        }
        player.sendMessage(ChatUtils.coloredChat("&8[&cLavaRise&8] &cInvalid command. Use /lr start or /lr stop"));
        return true;
    }
}
