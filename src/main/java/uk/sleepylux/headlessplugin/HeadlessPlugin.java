package uk.sleepylux.headlessplugin;

import org.bukkit.plugin.java.JavaPlugin;
import org.json.simple.JSONObject;
import uk.sleepylux.headlessplugin.commands.credits;
import uk.sleepylux.headlessplugin.commands.revive;
import uk.sleepylux.headlessplugin.events.*;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Objects;

public final class HeadlessPlugin extends JavaPlugin {

    @Override
    public void onEnable() {
        System.out.println("Loading NoLifeSMPPlugin" + this.getDataFolder() + "/Players.json");

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

        Objects.requireNonNull(this.getCommand("revive")).setExecutor(new revive(this));
        Objects.requireNonNull(this.getCommand("credits")).setExecutor(new credits(this));

        getServer().getPluginManager().registerEvents(new onJoin(this), this);
        getServer().getPluginManager().registerEvents(new onDeath(this), this);
        getServer().getPluginManager().registerEvents(new onInventoryClick(this), this);

        getServer().getPluginManager().registerEvents(new onHeadDestory(this), this);
        getServer().getPluginManager().registerEvents(new onHeadPlace(this), this);

        System.out.println("Loaded NoLifeSMPPlugin");

    }

    @Override
    public void onDisable() {
        System.out.println("Disabled NoLifeSMPPlugin");
    }
}
