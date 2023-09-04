package uk.sleepylux.headlessplugin.utility;

import org.bukkit.Bukkit;
import org.jetbrains.annotations.NotNull;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import uk.sleepylux.headlessplugin.HeadlessPlugin;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;
import java.util.logging.Level;

public class JSONManager {
    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_RED = "\u001B[31m";
    HeadlessPlugin plugin;
    File file;
    public JSONObject json;
    public JSONManager(@NotNull HeadlessPlugin plugin, @NotNull String path, JSONObject defaultValue) {
        this.plugin = plugin;
        try {
            File file = new File(path);

            if (file.isDirectory()) throw new ParseException(0);

            if (file.exists()) {
                this.file = file;
                this.json = getJSON();
                return;
            }

            if (file.createNewFile()) {
                FileWriter writer = new FileWriter(path);
                writer.write(defaultValue.toJSONString());
                writer.close();
            }

            this.json = getJSON();
        } catch (NullPointerException e) {
            plugin.getLogger().log(Level.SEVERE, ANSI_RED + "[JSONManager] No path given" + ANSI_RESET);
            Bukkit.getPluginManager().disablePlugin(plugin);
        } catch (IOException e) {
            plugin.getLogger().log(Level.SEVERE, ANSI_RED + "[JSONManager] Cannot edit: \"" + path + "\" Missing read or write permissions" + ANSI_RESET);
            Bukkit.getPluginManager().disablePlugin(plugin);
        } catch (ParseException e) {
            plugin.getLogger().log(Level.SEVERE, ANSI_RED + "[JSONManager] \"" + path + "\" is a directory" + ANSI_RESET);
            Bukkit.getPluginManager().disablePlugin(plugin);
        }
    }

    private JSONObject getJSON() {
        try {
            Scanner scanner = new Scanner(file);

            JSONParser parser = new JSONParser();
            String JSONStr = "";
            while (scanner.hasNextLine()) {
                JSONStr = JSONStr.concat(scanner.nextLine());
            }

            return (JSONObject) parser.parse(JSONStr);
        } catch (FileNotFoundException | ParseException e) {
            throw new RuntimeException(e);
        }
    }
    private void setJSON() {
        try {
            FileWriter writer = new FileWriter(this.file);
            writer.write(this.json.toJSONString());
            writer.close();
        } catch (IOException e) {
            plugin.getLogger().log(Level.SEVERE, ANSI_RED + "[JSONManager] Cannot edit: \"" + this.file.getAbsolutePath() + "\" Missing read or write permissions" + ANSI_RESET);
            Bukkit.getPluginManager().disablePlugin(plugin);
        }
    }

    public boolean set(String key, Object value) {
        this.json = getJSON();
        this.json.put(key, value);
        setJSON();
        return true;
    }

    public String get(String key) {
        try {
            return this.json.get(key).toString();
        } catch (Exception e) {
            return null;
        }
    }

    public JSONObject parse(String jsonStr) {
        JSONParser parser = new JSONParser();
        try {
            return (JSONObject) parser.parse(jsonStr);
        } catch (ParseException e) {
            plugin.getLogger().log(Level.SEVERE, ANSI_RED + "[JSONManager] Parse data is not valid JSON,\nPlease do not edit \"Config.json\" or \"Players.json\"" + ANSI_RESET);
            Bukkit.getPluginManager().disablePlugin(plugin);
            return new JSONObject();
        }
    }
}
