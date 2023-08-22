package uk.sleepylux.nolifesmpplugin;

import org.bukkit.plugin.java.JavaPlugin;
import org.json.simple.JSONObject;
import uk.sleepylux.nolifesmpplugin.commands.revive;
import uk.sleepylux.nolifesmpplugin.events.onJoin;

import javax.sound.midi.SysexMessage;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Objects;

public final class NoLifeSMPPlugin extends JavaPlugin {

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
                System.out.print("Wrote new \"Players.json\"");
            };
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        Objects.requireNonNull(this.getCommand("revive")).setExecutor(new revive(this));

        getServer().getPluginManager().registerEvents(new onJoin(this), this);

        System.out.println("Loaded NoLifeSMPPlugin");

    }

    @Override
    public void onDisable() {
        System.out.println("Disabled NoLifeSMPPlugin");
    }
}
