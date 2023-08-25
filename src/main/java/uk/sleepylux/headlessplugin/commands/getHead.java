package uk.sleepylux.headlessplugin.commands;

import net.kyori.adventure.text.Component;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.json.simple.JSONObject;
import uk.sleepylux.headlessplugin.HeadlessPlugin;
import uk.sleepylux.headlessplugin.utility.HeadManager;
import uk.sleepylux.headlessplugin.utility.MessageManager;
import uk.sleepylux.headlessplugin.utility.PlayersManager;

public class getHead implements CommandExecutor {
    HeadlessPlugin plugin;
    public getHead(HeadlessPlugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {
        if (!(commandSender instanceof Player player)) return true;

        if (strings.length == 0) {
            MessageManager.sendMessage(player, Component.text("Please input a user to get the head of"));
            return true;
        }
        OfflinePlayer p = plugin.getServer().getOfflinePlayer(strings[0]);
        JSONObject PJSON = PlayersManager.getPlayerJSON(plugin, p.getUniqueId());
        if (PJSON == null) {
            MessageManager.sendMessage(player, Component.text("User \"" + strings[0] + "\" has not joined the server before"));
            return true;
        }

        ItemStack Skull = HeadManager.Create(plugin, p, Integer.parseInt(PJSON.get("id").toString()));
        player.getInventory().addItem(Skull);
        MessageManager.sendMessage(player, Component.text("Gave you " + strings[0] + "'s head"));
        return true;
    }
}
