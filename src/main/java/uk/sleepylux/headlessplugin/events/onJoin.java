package uk.sleepylux.headlessplugin.events;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.format.TextColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.json.simple.JSONObject;
import uk.sleepylux.headlessplugin.HeadlessPlugin;
import uk.sleepylux.headlessplugin.utility.NametagManager;
import static uk.sleepylux.headlessplugin.HeadlessPlugin.PlayerManager;

import java.util.*;

public class onJoin implements Listener {
    HeadlessPlugin plugin;
    public onJoin(HeadlessPlugin plugin) {
        this.plugin = plugin;
    }
    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        if (PlayerManager.get(event.getPlayer().getUniqueId().toString()) != null) {
            updateJoinmessage(plugin, event);
            NametagManager.updateVisibleLifeCount(plugin, event.getPlayer(), NametagManager.TeamAction.CREATE);
            return;
        }

        JSONObject value = new JSONObject();
        value.put("dead", false);
        value.put("lives", 4);

        PlayerManager.set(event.getPlayer().getUniqueId().toString(), value);
        updateJoinmessage(plugin, event);
        NametagManager.updateVisibleLifeCount(plugin, event.getPlayer(), NametagManager.TeamAction.CREATE);
    }

    public static void updateJoinmessage(HeadlessPlugin plugin, PlayerJoinEvent event) {
        Component joinMessage = event.joinMessage();
        if (joinMessage != null) {
            String lifeCount = PlayerManager.parse(PlayerManager.get(event.getPlayer().getUniqueId().toString())).get("lives").toString();
            TextComponent message = Component.text("[").color(TextColor.fromHexString("#FF55FF"))
                    .append(Component.text(lifeCount).color(TextColor.fromHexString("#FFAA00")))
                    .append(Component.text("] ").color(TextColor.fromHexString("#FF55FF")))
                    .append(Component.text(event.getPlayer().getName() + " joined the game").color(TextColor.fromHexString("#FFFF55")));

            event.joinMessage(message);
        }
    }
}
