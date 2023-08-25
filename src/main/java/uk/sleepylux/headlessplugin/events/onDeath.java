package uk.sleepylux.headlessplugin.events;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.format.TextColor;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.inventory.ItemStack;
import org.json.simple.JSONObject;
import org.w3c.dom.Text;
import uk.sleepylux.headlessplugin.HeadlessPlugin;
import uk.sleepylux.headlessplugin.utility.HeadManager;
import uk.sleepylux.headlessplugin.utility.MessageManager;
import uk.sleepylux.headlessplugin.utility.NametagManager;
import uk.sleepylux.headlessplugin.utility.PlayersManager;

public class onDeath implements Listener {
    HeadlessPlugin plugin;
    public onDeath(HeadlessPlugin plugin) {
        this.plugin = plugin;
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onPlayerDeath(PlayerDeathEvent e) {
        Player killer = e.getPlayer().getKiller();
        if (killer == null
             || e.getPlayer().equals(killer)) return;

        Player player = e.getPlayer();
        OfflinePlayer offlinePlayer = plugin.getServer().getOfflinePlayer(player.getUniqueId());

        JSONObject playerJSON = PlayersManager.getPlayerJSON(plugin, player.getUniqueId());

        ItemStack skull = HeadManager.Create(plugin, offlinePlayer, Integer.parseInt(playerJSON.get("id").toString()));

        int lifeCount = Integer.parseInt((playerJSON.get("lives").toString()))-1;
        playerJSON.put("lives", lifeCount);
        if (lifeCount == 0) playerJSON.put("dead", true);

        int killerLifeCount = Integer.parseInt((PlayersManager.getPlayerJSON(plugin, killer.getUniqueId()).get("lives").toString()))-1;

        e.deathMessage(
                Component.text("[").color(TextColor.fromHexString("#FF55FF"))
                        .append(Component.text(lifeCount).color(TextColor.fromHexString("#FFAA00")))
                        .append(Component.text("] ").color(TextColor.fromHexString("#FF55FF")))
                        .append(Component.text(player.getName() + " has lost a life due to ").color(TextColor.fromHexString("#FFFF55")))
                        .append(Component.text("[").color(TextColor.fromHexString("#FF55FF")))
                        .append(Component.text(killerLifeCount).color(TextColor.fromHexString("#FFAA00")))
                        .append(Component.text("] ").color(TextColor.fromHexString("#FF55FF")))
                        .append(Component.text(killer.getName()).color(TextColor.fromHexString("#FFFF55")))
        );

        player.getWorld().dropItem(player.getLocation(), skull);
        PlayersManager.setPlayerJSON(plugin, player.getUniqueId(), playerJSON);
        NametagManager.updateVisibleLifeCount(plugin, player, NametagManager.TeamAction.UPDATE);

        if (lifeCount == 0) {
            player.getInventory().clear();
            player.banPlayer("You have run out of lives... goodluck");
            player.kick();
            for (Player p : plugin.getServer().getOnlinePlayers()) {
                p.playSound(p.getLocation(), Sound.BLOCK_ANVIL_PLACE, 1F, 1F);
            }
            e.deathMessage(
                    Component.text("[").color(TextColor.fromHexString("#FF55FF"))
                            .append(Component.text(lifeCount).color(TextColor.fromHexString("#FFAA00")))
                            .append(Component.text("] ").color(TextColor.fromHexString("#FF55FF")))
                            .append(Component.text(player.getName() + " has ran out of lives due to ").color(TextColor.fromHexString("#FFFF55")))
                            .append(Component.text("[").color(TextColor.fromHexString("#FF55FF")))
                            .append(Component.text(killerLifeCount).color(TextColor.fromHexString("#FFAA00")))
                            .append(Component.text("] ").color(TextColor.fromHexString("#FF55FF")))
                            .append(Component.text(killer.getName()).color(TextColor.fromHexString("#FFFF55")))
            );
        }
    }
}
