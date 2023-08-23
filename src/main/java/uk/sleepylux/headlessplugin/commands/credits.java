package uk.sleepylux.headlessplugin.commands;

import net.kyori.adventure.text.Component;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import uk.sleepylux.headlessplugin.HeadlessPlugin;
import uk.sleepylux.headlessplugin.utility.MessageManager;

public class credits implements CommandExecutor {
    HeadlessPlugin plugin;
    public credits(HeadlessPlugin plugin) {
        this.plugin = plugin;
    }
    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {
        if (!(commandSender instanceof Player p)) return true;

        MessageManager.sendMessage(p, "Created by Lux\n" +
                MessageManager.introMessage + "Github: https://github.com/Sleepylux" +
                MessageManager.introMessage + "Issues: https://github.com/Sleepylux/HeadlessMC/issues");
        return true;
    }
}
