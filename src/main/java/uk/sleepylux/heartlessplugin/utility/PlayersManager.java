package uk.sleepylux.heartlessplugin.utility;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import uk.sleepylux.heartlessplugin.HeartlessPlugin;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class PlayersManager {
    public static JSONObject getJSON(HeartlessPlugin plugin) {
        File deadplayers = new File(plugin.getDataFolder() + "/Players.json");
        try {
            Scanner playersScanner = new Scanner(deadplayers);

            JSONParser parser = new JSONParser();
            String jsonStr = "";
            while (playersScanner.hasNextLine()) {
                jsonStr = jsonStr.concat(playersScanner.nextLine());
            }

            return (JSONObject) parser.parse(jsonStr);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static JSONObject getPlayerJSON(HeartlessPlugin plugin, UUID uuid) {
        JSONObject json = getJSON(plugin);
        JSONObject playerJson = (JSONObject) json.get(uuid.toString());

        return playerJson;
    }

    public static boolean setJSON(HeartlessPlugin plugin, JSONObject json) {
        try {
            FileWriter writer = new FileWriter(plugin.getDataFolder() + "/Players.json");
            writer.write(json.toJSONString());
            writer.close();
            return true;
        } catch (IOException e) {
            return false;
        }
    }

    public static boolean setPlayerJSON(HeartlessPlugin plugin, UUID playerUUID, JSONObject playerJson) {
        JSONObject json = getJSON(plugin);
        json.put(playerUUID.toString(), playerJson);
        setJSON(plugin, json);
        return true;
    }
}
