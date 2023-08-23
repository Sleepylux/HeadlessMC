package uk.sleepylux.headlessplugin.events;

import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Item;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityCombustEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.ItemDespawnEvent;
import org.bukkit.inventory.ItemStack;
import uk.sleepylux.headlessplugin.HeadlessPlugin;

public class onHeadDestory implements Listener {
    HeadlessPlugin plugin;
    public onHeadDestory(HeadlessPlugin plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onEntityDamage(EntityDamageEvent e) {
        if (e.getEntity().getType() == EntityType.DROPPED_ITEM) {
            ItemStack item = ((Item) e.getEntity()).getItemStack();
            if (item.getType() == Material.PLAYER_HEAD
                    && item.hasItemMeta()
                    && item.getItemMeta().hasCustomModelData()) {
                e.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void onFireDamage(EntityCombustEvent e) {
        if (e.getEntity().getType() == EntityType.DROPPED_ITEM) {
            ItemStack item = ((Item) e.getEntity()).getItemStack();
            if (item.getType() == Material.PLAYER_HEAD
                    && item.hasItemMeta()
                    && item.getItemMeta().hasCustomModelData()) {
                e.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void onDespawn(ItemDespawnEvent e) {
        if (e.getEntity().getItemStack().getType() == Material.PLAYER_HEAD
                && e.getEntity().getItemStack().hasItemMeta()
                && e.getEntity().getItemStack().getItemMeta().hasCustomModelData())
            e.setCancelled(true);
    }
}
