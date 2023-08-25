package uk.sleepylux.heartlessplugin.events;

import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;
import org.json.simple.JSONObject;
import uk.sleepylux.heartlessplugin.HeartlessPlugin;
import uk.sleepylux.heartlessplugin.utility.HeartManager;
import uk.sleepylux.heartlessplugin.utility.MessageManager;
import uk.sleepylux.heartlessplugin.utility.PlayersManager;

import java.util.concurrent.atomic.AtomicBoolean;

import static uk.sleepylux.heartlessplugin.commands.revive.inv;

public class onInventoryClick implements Listener {
    HeartlessPlugin plugin;
    public onInventoryClick(HeartlessPlugin plugin) {
        this.plugin = plugin;
    }
    @EventHandler
    public void OnInventoryClick(InventoryClickEvent e) {
        if (!e.getInventory().equals(inv)) return;
        e.setCancelled(true);

        ItemStack clickedItem = e.getCurrentItem();
        if (clickedItem == null || clickedItem.getType().isAir()) return;

        OfflinePlayer revivee = ((SkullMeta) clickedItem.getItemMeta()).getOwningPlayer();
        Player player = (Player) e.getWhoClicked();

        if (revivee == null) {
            MessageManager.sendMessage(player, " Could not find user you are trying to revive");
            return;
        }

        JSONObject reviveeJson = PlayersManager.getPlayerJSON(plugin, revivee.getUniqueId());

        JSONObject playerJSON = PlayersManager.getPlayerJSON(plugin, revivee.getUniqueId());
        ItemStack skull = HeartManager.Create(plugin, revivee, Integer.parseInt(playerJSON.get("id").toString()));
        skull.setAmount(4);

        AtomicBoolean hasHeads = new AtomicBoolean(false);
        player.getInventory().forEach(item -> {
            if (item != null && item.isSimilar(clickedItem) && item.getAmount() >= 4) {
                hasHeads.set(true);
                player.getInventory().removeItem(item);
            }
        });
        if (!hasHeads.get()) {
            MessageManager.sendMessage(player, "You must have 4 of " + revivee.getName() + "'s heads to revive them");
            return;
        }

        reviveeJson.put("dead", false);
        reviveeJson.put("lives", 4);

        inv.close();
        PlayersManager.setPlayerJSON(plugin, revivee.getUniqueId(), reviveeJson);
        MessageManager.broadcastMessage(plugin.getServer(), ChatColor.LIGHT_PURPLE + revivee.getName() + " has been revived!");
        plugin.getServer().getBanList(BanList.Type.NAME).pardon(revivee.getName());
        for (Player p : plugin.getServer().getOnlinePlayers()) {
            p.playSound(p.getLocation(), Sound.BLOCK_BEACON_POWER_SELECT, 1F, 1F);
        }
    }
}
