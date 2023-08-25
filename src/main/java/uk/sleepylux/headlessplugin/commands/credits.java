package uk.sleepylux.headlessplugin.commands;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.event.ClickEvent;
import net.kyori.adventure.text.format.TextColor;
import net.kyori.adventure.text.format.TextDecoration;
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

        TextComponent textComp = Component.text("My Github page: ").color(TextColor.fromHexString("#FFFFFF"))
                .append(Component.text("SleepyLux\n").color(TextColor.fromHexString("#FF55FF")).decorate(TextDecoration.UNDERLINED)
                        .clickEvent(ClickEvent.clickEvent(ClickEvent.Action.OPEN_URL, "https://github.com/SleepyLux")))
                .append(MessageManager.introMessage)
                .append(Component.text("Found a bug? Report it: ").color(TextColor.fromHexString("#FFFFFF")))
                .append(Component.text("here").color(TextColor.fromHexString(("#FF55FF"))).decorate(TextDecoration.UNDERLINED)
                        .clickEvent(ClickEvent.clickEvent(ClickEvent.Action.OPEN_URL, "https://github.com/SleepyLux/HeadlessMC/issues")));

        MessageManager.sendMessage(p, textComp);
        return true;
    }
}
