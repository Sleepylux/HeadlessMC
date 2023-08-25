package uk.sleepylux.headlessplugin.events;

import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.*;
import org.bukkit.inventory.ItemStack;
import uk.sleepylux.headlessplugin.HeadlessPlugin;

import java.util.Objects;

public class onHeadContainerEntry implements Listener {
    HeadlessPlugin plugin;
    public onHeadContainerEntry(HeadlessPlugin plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onEntry(InventoryClickEvent e) {
        if (e.getInventory().getType() == InventoryType.CRAFTING
                || e.getInventory().getType() == InventoryType.PLAYER) return;

        ItemStack item = e.getCurrentItem();
        if (item == null) return;

        if (item.getType() == Material.PLAYER_HEAD
                && item.hasItemMeta()
                && item.getItemMeta().hasCustomModelData()) {
            e.setCancelled(true);
        }
    }

    @EventHandler
    public void onHopper(InventoryPickupItemEvent e) {
        if (e.getInventory().getType() == InventoryType.HOPPER) {
            ItemStack item = e.getItem().getItemStack();
            if (item.getType() == Material.PLAYER_HEAD
                    && item.hasItemMeta()
                    && item.getItemMeta().hasCustomModelData()) {
                e.setCancelled(true);
            }
        }
    }
}
