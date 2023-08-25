package uk.sleepylux.headlessplugin.events;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.format.TextColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;
import org.json.simple.JSONObject;
import uk.sleepylux.headlessplugin.HeadlessPlugin;
import uk.sleepylux.headlessplugin.utility.NametagManager;
import uk.sleepylux.headlessplugin.utility.PlayersManager;

public class onLeave implements Listener {
    HeadlessPlugin plugin;
    public onLeave(HeadlessPlugin plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onPlayerLeave(PlayerQuitEvent e) {
        Component quitMessage = e.quitMessage();
        if (quitMessage != null) {
            String lifeCount = (PlayersManager.getPlayerJSON(plugin, e.getPlayer().getUniqueId())).get("lives").toString();
            TextComponent message = Component.text("[").color(TextColor.fromHexString("#FF55FF"))
                    .append(Component.text(lifeCount).color(TextColor.fromHexString("#FFAA00")))
                    .append(Component.text("] ").color(TextColor.fromHexString("#FF55FF")))
                    .append(Component.text(e.getPlayer().getName() + " left the game").color(TextColor.fromHexString("#FFFF55")));

            e.quitMessage(message);
        }

        NametagManager.updateVisibleLifeCount(plugin, e.getPlayer(), NametagManager.TeamAction.DESTROY);
    }
}
