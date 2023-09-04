package uk.sleepylux.headlessplugin.events;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;
import uk.sleepylux.headlessplugin.HeadlessPlugin;
import static uk.sleepylux.headlessplugin.HeadlessPlugin.PlayerManager;

public class onRespawn implements Listener {
    HeadlessPlugin plugin;
    public onRespawn(HeadlessPlugin plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onSpawn(PlayerRespawnEvent e) {
        Player p = e.getPlayer();

        int lives = Integer.parseInt(PlayerManager.parse(PlayerManager.get(p.getUniqueId().toString())).get("lives").toString());
        System.out.println("lives: " + lives);


        new BukkitRunnable() {
            public void run() {
                if (lives <= 3)
                    p.addPotionEffect(new PotionEffect(PotionEffectType.FIRE_RESISTANCE, -1, 0));

                if (lives <= 2)
                    p.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, -1, 0));

                if (lives <= 1)
                    p.addPotionEffect(new PotionEffect(PotionEffectType.INCREASE_DAMAGE, -1, 0));
            }
        }.runTaskLater(plugin, 3);

    }
}
