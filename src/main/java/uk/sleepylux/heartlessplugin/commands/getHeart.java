package uk.sleepylux.heartlessplugin.commands;

import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.json.simple.JSONObject;
import uk.sleepylux.heartlessplugin.HeartlessPlugin;
import uk.sleepylux.heartlessplugin.utility.HeartManager;
import uk.sleepylux.heartlessplugin.utility.MessageManager;
import uk.sleepylux.heartlessplugin.utility.PlayersManager;

public class getHeart implements CommandExecutor {
    HeartlessPlugin plugin;
    public getHeart(HeartlessPlugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {
        if (!(commandSender instanceof Player player)) return true;

        if (strings.length == 0) {
            MessageManager.sendMessage(player, "Please input a user to get the head of");
            return true;
        }
        OfflinePlayer p = plugin.getServer().getOfflinePlayer(strings[0]);
        JSONObject PJSON = PlayersManager.getPlayerJSON(plugin, p.getUniqueId());
        if (PJSON == null) {
            MessageManager.sendMessage(player, "User \"" + strings[0] + "\" has not joined the server before");
            return true;
        }

        ItemStack Skull = HeartManager.Create(plugin, p, Integer.parseInt(PJSON.get("id").toString()));
        player.getInventory().addItem(Skull);
        MessageManager.sendMessage(player, "Gave you " + strings[0] + "'s head");
        return true;
    }
}
