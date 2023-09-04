package uk.sleepylux.headlessplugin.commands;

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
import uk.sleepylux.headlessplugin.HeadlessPlugin;
import uk.sleepylux.headlessplugin.utility.HeadManager;
import static uk.sleepylux.headlessplugin.HeadlessPlugin.PlayerManager;

import java.util.UUID;

public class revive implements CommandExecutor {
    HeadlessPlugin plugin;
    public revive(HeadlessPlugin plugin) {
        this.plugin = plugin;
    }

    public static Inventory inv;
    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {
        if (!(commandSender instanceof Player)) return true;
        Player sender = (Player) commandSender;

        Inventory inv = Bukkit.createInventory(null, 36, Component.text("Revive a dead player."));

        JSONObject json = PlayerManager.json;

        json.forEach((k, v) -> {
            JSONObject player;
            try {
                player = (JSONObject) v;
            } catch (Exception e) {
                return;
            }

            if ((Boolean) player.get("dead")) {
                OfflinePlayer offlinePlayer = plugin.getServer().getOfflinePlayer(UUID.fromString(k.toString()));
                ItemStack skull = HeadManager.Create(plugin, offlinePlayer);
                inv.addItem(skull);
            }
        });

        revive.inv = inv;
        sender.openInventory(inv);
        return true;
    }
}
