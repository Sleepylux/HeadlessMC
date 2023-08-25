package uk.sleepylux.heartlessplugin.events;

import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.inventory.ItemStack;
import org.json.simple.JSONObject;
import uk.sleepylux.heartlessplugin.HeartlessPlugin;
import uk.sleepylux.heartlessplugin.utility.HeartManager;
import uk.sleepylux.heartlessplugin.utility.MessageManager;
import uk.sleepylux.heartlessplugin.utility.PlayersManager;

public class onDeath implements Listener {
    HeartlessPlugin plugin;
    public onDeath(HeartlessPlugin plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent e) {
        if (e.getPlayer().getKiller() == null
             || e.getPlayer().equals(e.getPlayer().getKiller())) return;

        Player player = e.getPlayer();
        OfflinePlayer offlinePlayer = plugin.getServer().getOfflinePlayer(player.getUniqueId());

        JSONObject playerJSON = PlayersManager.getPlayerJSON(plugin, player.getUniqueId());

        ItemStack skull = HeartManager.Create(plugin, offlinePlayer, Integer.parseInt(playerJSON.get("id").toString()));

        Integer lives = Integer.parseInt((playerJSON.get("lives").toString()))-1;
        playerJSON.put("lives", lives);

        if (lives == 0) {
            player.banPlayer("You have run out of lives... goodluck");
            player.kick();
            playerJSON.put("dead", true);
            MessageManager.broadcastMessage(plugin.getServer(), ChatColor.LIGHT_PURPLE + player.getName() + " has been eliminated...");
            for (Player p : plugin.getServer().getOnlinePlayers()) {
                p.playSound(p.getLocation(), Sound.BLOCK_ANVIL_PLACE, 1F, 1F);
            }
        }

        player.getWorld().dropItem(player.getLocation(), skull);
        PlayersManager.setPlayerJSON(plugin, player.getUniqueId(), playerJSON);

    }
}
