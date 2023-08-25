package uk.sleepylux.headlessplugin.events;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.format.TextColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import uk.sleepylux.headlessplugin.HeadlessPlugin;
import uk.sleepylux.headlessplugin.utility.NametagManager;
import uk.sleepylux.headlessplugin.utility.PlayersManager;

import java.util.*;

public class onJoin implements Listener {
    HeadlessPlugin plugin;
    public onJoin(HeadlessPlugin plugin) {
        this.plugin = plugin;
    }
    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        JSONObject json = PlayersManager.getJSON(plugin);


        boolean contains = json.containsKey(event.getPlayer().getUniqueId().toString());
        if (contains) {
            updateJoinmessage(plugin, event);
            NametagManager.updateVisibleLifeCount(plugin, event.getPlayer(), NametagManager.TeamAction.CREATE);
            return;
        };

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

        updateJoinmessage(plugin, event);
        NametagManager.updateVisibleLifeCount(plugin, event.getPlayer(), NametagManager.TeamAction.CREATE);
    }

    public static void updateJoinmessage(HeadlessPlugin plugin, PlayerJoinEvent event) {
        Component joinMessage = event.joinMessage();
        if (joinMessage != null) {
            String lifeCount = (PlayersManager.getPlayerJSON(plugin, event.getPlayer().getUniqueId())).get("lives").toString();
            TextComponent message = Component.text("[").color(TextColor.fromHexString("#FF55FF"))
                    .append(Component.text(lifeCount).color(TextColor.fromHexString("#FFAA00")))
                    .append(Component.text("] ").color(TextColor.fromHexString("#FF55FF")))
                    .append(Component.text(event.getPlayer().getName() + " joined the game").color(TextColor.fromHexString("#FFFF55")));

            event.joinMessage(message);
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
