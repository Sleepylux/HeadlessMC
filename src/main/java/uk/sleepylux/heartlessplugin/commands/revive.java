package uk.sleepylux.heartlessplugin.commands;

import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.json.simple.JSONObject;
import uk.sleepylux.heartlessplugin.HeartlessPlugin;
import uk.sleepylux.heartlessplugin.utility.HeartManager;
import uk.sleepylux.heartlessplugin.utility.PlayersManager;

import java.util.UUID;

public class revive implements CommandExecutor {
    HeartlessPlugin plugin;
    public revive(HeartlessPlugin plugin) {
        this.plugin = plugin;
    }

    public static Inventory inv;
    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {
        if (!(commandSender instanceof Player)) return true;
        Player sender = (Player) commandSender;

        Inventory inv = Bukkit.createInventory(null, 36, Component.text("Revive a dead player."));

        JSONObject json = PlayersManager.getJSON(plugin);

        json.forEach((k, v) -> {
            JSONObject player;
            try {
                player = (JSONObject) v;
            } catch (Exception e) {
                return;
            }

            if ((Boolean) player.get("dead")) {
                OfflinePlayer offlinePlayer = plugin.getServer().getOfflinePlayer(UUID.fromString(k.toString()));

                JSONObject playerJSON = PlayersManager.getPlayerJSON(plugin, offlinePlayer.getUniqueId());
                ItemStack skull = HeartManager.Create(plugin, offlinePlayer, Integer.parseInt(playerJSON.get("id").toString()));
                inv.addItem(skull);
            }
        });

        revive.inv = inv;
        sender.openInventory(inv);
        return true;
    }
}
