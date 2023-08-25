package uk.sleepylux.heartlessplugin.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import uk.sleepylux.heartlessplugin.HeartlessPlugin;
import uk.sleepylux.heartlessplugin.utility.MessageManager;

public class credits implements CommandExecutor {
    HeartlessPlugin plugin;
    public credits(HeartlessPlugin plugin) {
        this.plugin = plugin;
    }
    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {
        if (!(commandSender instanceof Player p)) return true;

        MessageManager.sendMessage(p, "Github: https://github.com/Sleepylux\n" +
                MessageManager.introMessage + ChatColor.RESET + "Issues: https://github.com/Sleepylux/HeadlessMC/issues");
        return true;
    }
}
