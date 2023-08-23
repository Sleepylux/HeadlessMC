package uk.sleepylux.headlessplugin.utility;

import net.kyori.adventure.text.Component;
import org.bukkit.ChatColor;
import org.bukkit.Server;
import org.bukkit.entity.Player;

public class MessageManager {
    public static String introMessage = ChatColor.GOLD + "[Headless] ";
    public static void sendMessage(Player player, String text) {
        player.sendMessage(Component.text( introMessage + ChatColor.RESET + text));
    }

    public static void broadcastMessage(Server server, String text) {
        server.broadcast(Component.text(introMessage + ChatColor.RESET + text));
    }
}
