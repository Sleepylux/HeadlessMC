package uk.sleepylux.headlessplugin.events;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;
import org.json.simple.JSONObject;
import uk.sleepylux.headlessplugin.HeadlessPlugin;
import uk.sleepylux.headlessplugin.utility.MessageManager;
import uk.sleepylux.headlessplugin.utility.NametagManager;
import static uk.sleepylux.headlessplugin.HeadlessPlugin.PlayerManager;
import static uk.sleepylux.headlessplugin.HeadlessPlugin.ConfigManager;

public class onHeadUse implements Listener {
    HeadlessPlugin plugin;
    public onHeadUse(HeadlessPlugin plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onUse(PlayerInteractEvent e) {
        if (!Boolean.parseBoolean(ConfigManager.get("regainLifeOnHeadUse")))
            return;

        if (e.getAction() != Action.RIGHT_CLICK_AIR) return;

        Player player = e.getPlayer();
        ItemStack item = e.getItem();

        if (item == null) return;

        if (item.getType() == Material.PLAYER_HEAD
                && item.hasItemMeta()
                && ((SkullMeta) item.getItemMeta()).getOwningPlayer() != null
                && ((SkullMeta) item.getItemMeta()).getOwningPlayer().getUniqueId() == player.getUniqueId()) {

            JSONObject playerJSON = PlayerManager.parse(PlayerManager.get(e.getPlayer().getUniqueId().toString()));
            int lives = Integer.parseInt(playerJSON.get("lives").toString());
            item.setAmount(item.getAmount()-1);
            playerJSON.put("lives", lives+1);
            player.clearActivePotionEffects();
            if ((lives+1) <= 3)
                player.addPotionEffect(new PotionEffect(PotionEffectType.FIRE_RESISTANCE, -1, 0));

            if ((lives+1) <= 2)
                player.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, -1, 0));

            if ((lives+1) <= 1)
                player.addPotionEffect(new PotionEffect(PotionEffectType.INCREASE_DAMAGE, -1, 0));
            PlayerManager.set(e.getPlayer().getUniqueId().toString(), playerJSON);
            NametagManager.updateVisibleLifeCount(plugin, player, NametagManager.TeamAction.UPDATE);
            MessageManager.sendMessage(player, Component.text("Used head!, You now have ").color(TextColor.fromHexString("#FF55FF"))
                    .append(Component.text(lives+1).color(TextColor.fromHexString("#FFAA00")))
                    .append(Component.text(" lives").color(TextColor.fromHexString("#FF55FF")))
            );
        }
    }
}
