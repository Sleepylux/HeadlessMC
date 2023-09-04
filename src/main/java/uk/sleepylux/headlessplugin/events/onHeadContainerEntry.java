package uk.sleepylux.headlessplugin.events;

import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.*;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;
import org.json.simple.JSONObject;
import uk.sleepylux.headlessplugin.HeadlessPlugin;
import static uk.sleepylux.headlessplugin.HeadlessPlugin.ConfigManager;

import java.util.Objects;

public class onHeadContainerEntry implements Listener {
    HeadlessPlugin plugin;
    public onHeadContainerEntry(HeadlessPlugin plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onEntry(InventoryClickEvent e) {
        if (!Boolean.parseBoolean(ConfigManager.get("preventHeadsInContainers")))
            return;

        if (e.getInventory().getType() == InventoryType.CRAFTING
                || e.getInventory().getType() == InventoryType.PLAYER) return;

        ItemStack item = e.getCurrentItem();
        if (item == null) return;

        if (item.getType() == Material.PLAYER_HEAD
                && item.hasItemMeta()
                && ((SkullMeta) item.getItemMeta()).hasOwner()) {
            e.setCancelled(true);
        }
    }

    @EventHandler
    public void onHopper(InventoryPickupItemEvent e) {
        if (!Boolean.parseBoolean(ConfigManager.get("preventHeadsInContainers")))
            return;

        if (e.getInventory().getType() == InventoryType.HOPPER) {
            ItemStack item = e.getItem().getItemStack();
            if (item.getType() == Material.PLAYER_HEAD
                    && item.hasItemMeta()
                    && ((SkullMeta) item.getItemMeta()).hasOwner()) {
                e.setCancelled(true);
            }
        }
    }
}
