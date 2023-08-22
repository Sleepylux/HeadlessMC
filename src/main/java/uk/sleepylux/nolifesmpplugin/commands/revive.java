package uk.sleepylux.nolifesmpplugin.commands;

import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;
import org.jetbrains.annotations.NotNull;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import uk.sleepylux.nolifesmpplugin.NoLifeSMPPlugin;

import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;

public class revive implements CommandExecutor {
    NoLifeSMPPlugin plugin;
    public revive(NoLifeSMPPlugin plugin) {
        this.plugin = plugin;
    }

    Inventory inv;
    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {
        if (!(commandSender instanceof Player)) return true;
        Player sender = (Player) commandSender;

        Inventory inv = Bukkit.createInventory(null, 36, Component.text("Revive a dead player."));

        File deadplayers = new File(plugin.getDataFolder() + "/Players.json");
        try {
            Scanner deadplayersScanner = new Scanner(deadplayers);

            JSONParser parser = new JSONParser();
            String jsonStr = "";
            while (deadplayersScanner.hasNextLine()) {
                jsonStr = jsonStr.concat(deadplayersScanner.nextLine());
            }

            System.out.println(jsonStr);
            JSONObject json = (JSONObject) parser.parse(jsonStr);

            json.forEach((k, v) -> {
               JSONObject player = (JSONObject) v;
               OfflinePlayer offlinePlayer = plugin.getServer().getOfflinePlayer(UUID.fromString(k.toString()));

               if ((Boolean) player.get("dead")) {
                   ItemStack skull = new ItemStack(Material.PLAYER_HEAD, 1);
                   SkullMeta skullMeta = (SkullMeta) skull.getItemMeta();
                   skullMeta.setOwningPlayer(offlinePlayer);
                   skullMeta.displayName(Component.text(offlinePlayer.getPlayer().getName() + "'s Head"));
                   ArrayList<Component> lore = new ArrayList<>();
                   lore.add(Component.text("Collect all of " + offlinePlayer.getPlayer().getName() + "'s heads to revive them"));
                   skullMeta.lore(lore);
                   skull.setItemMeta(skullMeta);
                   inv.addItem(skull);
               }
            });

            this.inv = inv;
            sender.openInventory(inv);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return true;
    }

    @EventHandler
    public void OnInventoryClick(InventoryClickEvent e) {
        if (!e.getInventory().equals(inv)) return;
        e.setCancelled(true);

        ItemStack clickedItem = e.getCurrentItem();
        if (clickedItem == null || clickedItem.getType().isAir()) return;
        Player revivee = plugin.getServer().getPlayer(String.valueOf(clickedItem.displayName()).split("'s")[0]);

        Player player = (Player) e.getWhoClicked();

        if (revivee == null) {
            player.sendMessage(Component.text("[NoLife] Could not find user you are trying to revive"));
            return;
        }

        File players = new File(plugin.getDataFolder() + "/Players.json");
        try {
            Scanner deadplayersScanner = new Scanner(players);

            JSONParser parser = new JSONParser();
            String jsonStr = "";
            while (deadplayersScanner.hasNextLine()) {
                jsonStr = jsonStr.concat(deadplayersScanner.nextLine());
            }

            System.out.println(jsonStr);
            JSONObject json = (JSONObject) parser.parse(jsonStr);

            AtomicInteger HeadCount = new AtomicInteger();
            player.getInventory().forEach((itemStack) -> {
                if (itemStack.getType() != Material.PLAYER_HEAD) return;

                ItemMeta meta = itemStack.getItemMeta();

                if (!meta.hasCustomModelData()) return;

                if (meta.getCustomModelData() != Integer.parseInt((String) ((JSONObject) json.get(revivee.getUniqueId())).get("id"))) return;
                HeadCount.getAndIncrement();
            });

            if (HeadCount.get() < 4) {
                player.sendMessage(Component.text("[NoLife] You do not have all of " + clickedItem.displayName() + "s"));
                return;
            }

            JSONObject reviveeJson = (JSONObject) json.get(revivee.getUniqueId());

            reviveeJson.put("dead", false);
            reviveeJson.put("lives", 4);

            json.put(revivee.getUniqueId(), reviveeJson);

            FileWriter writer = new FileWriter(plugin.getDataFolder() + "/Players.json");
            writer.write(json.toJSONString());
            writer.close();
        } catch (Exception ex) { player.sendMessage(Component.text("[NoLife] Something went wrong with /revive, please retry")); };



    }
}
