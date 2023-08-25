package uk.sleepylux.heartlessplugin;

import org.bukkit.plugin.java.JavaPlugin;
import org.json.simple.JSONObject;
import uk.sleepylux.heartlessplugin.commands.credits;
import uk.sleepylux.heartlessplugin.commands.getHeart;
import uk.sleepylux.heartlessplugin.commands.revive;
import uk.sleepylux.heartlessplugin.events.*;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Objects;

public final class HeartlessPlugin extends JavaPlugin {

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
        Objects.requireNonNull(this.getCommand("getHead")).setExecutor(new getHeart(this));

        getServer().getPluginManager().registerEvents(new onJoin(this), this);
        getServer().getPluginManager().registerEvents(new onDeath(this), this);
        getServer().getPluginManager().registerEvents(new onInventoryClick(this), this);

        getServer().getPluginManager().registerEvents(new onHeartDestory(this), this);
        getServer().getPluginManager().registerEvents(new onHeartPlace(this), this);

        System.out.println("Loaded NoLifeSMPPlugin");

    }

    @Override
    public void onDisable() {
        System.out.println("Disabled NoLifeSMPPlugin");
    }
}
