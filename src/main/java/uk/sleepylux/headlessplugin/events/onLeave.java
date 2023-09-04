package uk.sleepylux.headlessplugin.events;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.format.TextColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;
import uk.sleepylux.headlessplugin.HeadlessPlugin;
import uk.sleepylux.headlessplugin.utility.NametagManager;
import static uk.sleepylux.headlessplugin.HeadlessPlugin.PlayerManager;

public class onLeave implements Listener {
    HeadlessPlugin plugin;
    public onLeave(HeadlessPlugin plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onPlayerLeave(PlayerQuitEvent e) {
        Component quitMessage = e.quitMessage();
        if (quitMessage != null) {
            String lifeCount = PlayerManager.parse(PlayerManager.get(e.getPlayer().getUniqueId().toString())).get("lives").toString();
            TextComponent message = Component.text("[").color(TextColor.fromHexString("#FF55FF"))
                    .append(Component.text(lifeCount).color(TextColor.fromHexString("#FFAA00")))
                    .append(Component.text("] ").color(TextColor.fromHexString("#FF55FF")))
                    .append(Component.text(e.getPlayer().getName() + " left the game").color(TextColor.fromHexString("#FFFF55")));

            e.quitMessage(message);
        }

        NametagManager.updateVisibleLifeCount(plugin, e.getPlayer(), NametagManager.TeamAction.DESTROY);
    }
}
