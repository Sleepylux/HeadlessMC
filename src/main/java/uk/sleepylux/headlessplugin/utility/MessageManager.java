package uk.sleepylux.headlessplugin.utility;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.format.TextColor;
import org.bukkit.Server;
import org.bukkit.entity.Player;

public class MessageManager {
    public static TextComponent introMessage = Component.text("[Headless] ").color(TextColor.fromHexString("#FFAA00"));
    public static void sendMessage(Player player, TextComponent text) {
        player.sendMessage(introMessage.append(text));
    }

    public static void broadcastMessage(Server server, TextComponent text) {
        server.broadcast(introMessage.append(text));
    }
}
