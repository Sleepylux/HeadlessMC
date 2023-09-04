package uk.sleepylux.headlessplugin.events;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityPotionEffectEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import uk.sleepylux.headlessplugin.HeadlessPlugin;

import java.util.logging.Level;

import static uk.sleepylux.headlessplugin.HeadlessPlugin.PlayerManager;

public class EntityEffectEvent implements Listener {
    HeadlessPlugin plugin;
    public EntityEffectEvent(HeadlessPlugin plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onEffect(EntityPotionEffectEvent e) {
        if (!(e.getEntity() instanceof Player p)) return;

        if (e.getAction() != EntityPotionEffectEvent.Action.CHANGED
            && e.getAction() != EntityPotionEffectEvent.Action.REMOVED) return;

        boolean Cleared = e.getAction() == EntityPotionEffectEvent.Action.CLEARED;

        int lives = Integer.parseInt(PlayerManager.parse(PlayerManager.get(p.getUniqueId().toString())).get("lives").toString());
        if (lives <= 3 && (Cleared || e.getOldEffect().getType() == PotionEffectType.FIRE_RESISTANCE))
            p.addPotionEffect(new PotionEffect(PotionEffectType.FIRE_RESISTANCE, -1, 1), true);

        if (lives <= 2 && (Cleared || e.getOldEffect().getType() == PotionEffectType.SPEED))
            p.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, -1, 1), true);

        if (lives <= 2 && (Cleared || e.getOldEffect().getType() == PotionEffectType.INCREASE_DAMAGE))
            p.addPotionEffect(new PotionEffect(PotionEffectType.INCREASE_DAMAGE, -1, 1), true);
    }
}
