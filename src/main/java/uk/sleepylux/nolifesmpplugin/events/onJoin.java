package uk.sleepylux.nolifesmpplugin.events;

import net.kyori.adventure.text.Component;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import uk.sleepylux.nolifesmpplugin.NoLifeSMPPlugin;

import java.io.File;
import java.io.FileWriter;
import java.util.*;

public class onJoin implements Listener {
    NoLifeSMPPlugin plugin;
    public onJoin(NoLifeSMPPlugin plugin) {
        this.plugin = plugin;
    }
    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        File deadplayers = new File(plugin.getDataFolder() + "/Players.json");
        try {
            Scanner deadplayersScanner = new Scanner(deadplayers);

            JSONParser parser = new JSONParser();
            String jsonStr = "";
            while (deadplayersScanner.hasNextLine()) {
                jsonStr = jsonStr.concat(deadplayersScanner.nextLine());
            }

            JSONObject json = (JSONObject) parser.parse(jsonStr);

            boolean contains = json.containsKey(event.getPlayer().getUniqueId());
            if (contains) return;

            JSONArray takenids = (JSONArray) json.get("takenids");
            if (takenids == null) takenids = new JSONArray();

            JSONObject value = new JSONObject();
            value.put("id", generateRandom(12, Arrays.asList(takenids.toArray())));
            value.put("dead", false);
            value.put("lives", 4);

            json.put("takenids", takenids);
            json.put(event.getPlayer().getUniqueId().toString(), value);

            FileWriter writer = new FileWriter(plugin.getDataFolder() + "/Players.json");
            writer.write(json.toJSONString());
            writer.close();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static String generateRandom(int length, List<Object> taken) {
        Random random = new Random();
        char[] digits = new char[length];
        digits[0] = (char) (random.nextInt(9) + '1');
        for (int i = 1; i < length; i++) {
            digits[i] = (char) (random.nextInt(10) + '0');
        }
        String output = String.valueOf(Long.parseLong(new String(digits)));
        if (taken.contains(output)) generateRandom(length, taken);
        return output;
    }
}
