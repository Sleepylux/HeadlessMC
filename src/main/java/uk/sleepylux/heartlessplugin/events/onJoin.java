package uk.sleepylux.heartlessplugin.events;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import uk.sleepylux.heartlessplugin.HeartlessPlugin;
import uk.sleepylux.heartlessplugin.utility.PlayersManager;

import java.util.*;

public class onJoin implements Listener {
    HeartlessPlugin plugin;
    public onJoin(HeartlessPlugin plugin) {
        this.plugin = plugin;
    }
    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        JSONObject json = PlayersManager.getJSON(plugin);

        boolean contains = json.containsKey(event.getPlayer().getUniqueId().toString());
        if (contains) return;

        JSONArray takenids = (JSONArray) json.get("takenids");
        if (takenids == null) takenids = new JSONArray();

        JSONObject value = new JSONObject();
        String id = generateRandom(9, Arrays.asList(takenids.toArray()));
        value.put("id", id);
        value.put("dead", false);
        value.put("lives", 4);

        takenids.add(id);
        json.put("takenids", takenids);
        json.put(event.getPlayer().getUniqueId().toString(), value);

        PlayersManager.setJSON(plugin, json);
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
