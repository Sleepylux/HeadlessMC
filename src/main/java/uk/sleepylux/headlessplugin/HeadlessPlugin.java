package uk.sleepylux.headlessplugin;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import org.json.simple.JSONObject;
import uk.sleepylux.headlessplugin.commands.credits;
import uk.sleepylux.headlessplugin.commands.getHead;
import uk.sleepylux.headlessplugin.commands.revive;
import uk.sleepylux.headlessplugin.events.*;
import uk.sleepylux.headlessplugin.utility.JSONManager;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Objects;
import java.util.logging.Level;

public final class HeadlessPlugin extends JavaPlugin {
    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_YELLOW = "\u001B[33m";
    public static final String ANSI_PURPLE = "\u001B[35m";

    public static JSONManager ConfigManager;
    public static JSONManager PlayerManager;


    @Override
    public void onEnable() {

        File dataFolder = new File(this.getDataFolder().toURI());
        if (!dataFolder.exists()) dataFolder.mkdir();

        JSONObject defaultConfig = new JSONObject();
        defaultConfig.put("enabled", true);
        defaultConfig.put("preventHeadsInContainers", true);
        defaultConfig.put("invulnerableHeads", true);
        defaultConfig.put("loseLifeOnSelfKill", false);
        defaultConfig.put("regainLifeOnHeadUse", true);
        defaultConfig.put("lostLifeBuffs", true);

        ConfigManager = new JSONManager(this, this.getDataFolder() + "/Config.json", defaultConfig);
        PlayerManager = new JSONManager(this, this.getDataFolder() + "/Players.json", new JSONObject());

        if (!Boolean.parseBoolean(ConfigManager.get("enabled"))) {
            this.getLogger().log(Level.SEVERE, ANSI_PURPLE + "Plugin is disabled in config" + ANSI_RESET);
            Bukkit.getPluginManager().disablePlugin(this);
        }

        Objects.requireNonNull(this.getCommand("hrevive")).setExecutor(new revive(this));
        Objects.requireNonNull(this.getCommand("hcredits")).setExecutor(new credits(this));
        Objects.requireNonNull(this.getCommand("hgetHead")).setExecutor(new getHead(this));

        getServer().getPluginManager().registerEvents(new onJoin(this), this);
        getServer().getPluginManager().registerEvents(new onLeave(this), this);
        getServer().getPluginManager().registerEvents(new onDeath(this), this);
        getServer().getPluginManager().registerEvents(new onRespawn(this), this);
        getServer().getPluginManager().registerEvents(new onInventoryClick(this), this);
        getServer().getPluginManager().registerEvents(new EntityEffectEvent(this), this);

        getServer().getPluginManager().registerEvents(new onHeadDestory(this), this);
        getServer().getPluginManager().registerEvents(new onHeadPlace(this), this);
        getServer().getPluginManager().registerEvents(new onHeadContainerEntry(this), this);
        getServer().getPluginManager().registerEvents(new onHeadUse(this), this);

        System.out.println(ANSI_PURPLE + "┏━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┓" + ANSI_RESET);
        System.out.println(ANSI_PURPLE + "┃                                           ┃" + ANSI_RESET);
        System.out.println(ANSI_PURPLE + "┃          " + ANSI_YELLOW + "Loaded Headless Plugin" + ANSI_PURPLE + "           ┃" + ANSI_RESET);
        System.out.println(ANSI_PURPLE + "┃                                           ┃" + ANSI_RESET);
        System.out.println(ANSI_PURPLE + "┃  " + ANSI_YELLOW + "https://github.com/SleepyLux/HeadlessMC" + ANSI_PURPLE + "  ┃" + ANSI_RESET);
        System.out.println(ANSI_PURPLE + "┃                                           ┃" + ANSI_RESET);
        System.out.println(ANSI_PURPLE + "┗━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┛" + ANSI_RESET);

    }

    @Override
    public void onDisable() {
        System.out.println(ANSI_PURPLE + "┏━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┓" + ANSI_RESET);
        System.out.println(ANSI_PURPLE + "┃                                           ┃" + ANSI_RESET);
        System.out.println(ANSI_PURPLE + "┃          " + ANSI_RED + "Disabled Headless Plugin" + ANSI_PURPLE + "           ┃" + ANSI_RESET);
        System.out.println(ANSI_PURPLE + "┃                                           ┃" + ANSI_RESET);
        System.out.println(ANSI_PURPLE + "┃  " + ANSI_RED + "https://github.com/SleepyLux/HeadlessMC" + ANSI_PURPLE + "  ┃" + ANSI_RESET);
        System.out.println(ANSI_PURPLE + "┃                                           ┃" + ANSI_RESET);
        System.out.println(ANSI_PURPLE + "┗━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┛" + ANSI_RESET);
    }
}
