package uk.sleepylux.headlessplugin;

import org.bukkit.plugin.java.JavaPlugin;
import org.json.simple.JSONObject;
import uk.sleepylux.headlessplugin.commands.credits;
import uk.sleepylux.headlessplugin.commands.getHead;
import uk.sleepylux.headlessplugin.commands.revive;
import uk.sleepylux.headlessplugin.events.*;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Objects;

public final class HeadlessPlugin extends JavaPlugin {

    @Override
    public void onEnable() {

        File dataFolder = new File(this.getDataFolder().toURI());
        if (!dataFolder.exists()) dataFolder.mkdir();

        File deadPlayers = new File(this.getDataFolder() + "/Players.json");
        if (deadPlayers.isDirectory()) deadPlayers.delete();
        try {
            if (deadPlayers.createNewFile()) {
                FileWriter writer = new FileWriter(this.getDataFolder() + "/Players.json");
                writer.write(new JSONObject().toString());
                writer.close();
            };
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        Objects.requireNonNull(this.getCommand("hrevive")).setExecutor(new revive(this));
        Objects.requireNonNull(this.getCommand("hcredits")).setExecutor(new credits(this));
        Objects.requireNonNull(this.getCommand("hgetHead")).setExecutor(new getHead(this));

        getServer().getPluginManager().registerEvents(new onJoin(this), this);
        getServer().getPluginManager().registerEvents(new onLeave(this), this);
        getServer().getPluginManager().registerEvents(new onDeath(this), this);
        getServer().getPluginManager().registerEvents(new onInventoryClick(this), this);

        getServer().getPluginManager().registerEvents(new onHeadDestory(this), this);
        getServer().getPluginManager().registerEvents(new onHeadPlace(this), this);
        getServer().getPluginManager().registerEvents(new onHeadContainerEntry(this), this);

        System.out.println("┏━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┓");
        System.out.println("┃                                           ┃");
        System.out.println("┃          Loaded Headless Plugin           ┃");
        System.out.println("┃                                           ┃");
        System.out.println("┃  https://github.com/SleepyLux/HeadlessMC  ┃");
        System.out.println("┃                                           ┃");
        System.out.println("┗━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┛");

    }

    @Override
    public void onDisable() {
        System.out.println("Disabled Headless");
    }
}
