package uk.sleepylux.headlessplugin.events;

import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;
import uk.sleepylux.headlessplugin.HeadlessPlugin;

public class onHeadPlace implements Listener {
    HeadlessPlugin plugin;
    public onHeadPlace(HeadlessPlugin plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onPlace(BlockPlaceEvent e) {
        ItemStack item = e.getItemInHand();
        if (item.getType() == Material.PLAYER_HEAD
                && item.hasItemMeta()
                && ((SkullMeta) item.getItemMeta()).hasOwner())
            e.setCancelled(true);
    }
}
