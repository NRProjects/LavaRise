package plugins.nate.lavarise.commands;

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

        if (command.getName().equalsIgnoreCase("lr")) {
            manager.startLavaRise(player.getWorld());
            player.sendMessage(ChatUtils.coloredChat("&8[&cLavaRise&8] &cLava has started spilling from the ground!"));
        }

        return true;
    }
}
