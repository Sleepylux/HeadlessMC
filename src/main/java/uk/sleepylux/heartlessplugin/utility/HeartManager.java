package uk.sleepylux.heartlessplugin.utility;

import net.kyori.adventure.text.Component;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;
import uk.sleepylux.heartlessplugin.HeartlessPlugin;

import java.util.ArrayList;

public class HeartManager {
    public static ItemStack Create(HeartlessPlugin plugin, OfflinePlayer player, Integer id) {

        ItemStack skull = new ItemStack(Material.PLAYER_HEAD, 1);
        SkullMeta skullMeta = (SkullMeta) skull.getItemMeta();
        skullMeta.setOwningPlayer(player);
        skullMeta.displayName(Component.text(player.getName() + "'s head"));
        ArrayList<Component> lore = new ArrayList<>();
        lore.add(Component.text("Collect all of " + player.getName() + "'s heads to revive them"));
        skullMeta.lore(lore);
        skullMeta.setCustomModelData(id);
        skullMeta.setUnbreakable(true);
        skull.setItemMeta(skullMeta);
        return skull;
    }
}
