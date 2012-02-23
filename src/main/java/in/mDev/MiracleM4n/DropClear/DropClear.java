package in.mDev.MiracleM4n.DropClear;

import java.io.File;
import java.util.Map;

import org.bukkit.Chunk;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Wolf;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.configuration.file.YamlConfiguration;

public class DropClear extends JavaPlugin {
    PluginManager pm;
    PluginDescriptionFile pdfFile;

    // Configuration
    YamlConfiguration dConfig = null;
    YamlConfiguration tConfig = null;
    File dConfigF = null;
    File tConfigF = null;

    // Strings
    String notNumber = "That is not a number";
    String negativeInterger = "Cant Use Negative Values";
    String farAway = "Too far away";
    String cantFind = "No Items Found";
    String noPermissions = "You don't have permissions to use this";
    String itemKill = "Items Cleared";
    String pInfo = "[DropClear] ";

    // Integers
    Integer maxKillRadius = 10;
    Integer maxAdminKillRadius = 30;

    // Booleans
    Boolean messageFix = true;
    Boolean messFix = true;

    public void onEnable() {
        pm = getServer().getPluginManager();
        pdfFile = getDescription();

        dConfigF = new File(getDataFolder(), "config.yml");
        tConfigF = new File(getDataFolder(), "tasks.yml");

        dConfig = YamlConfiguration.loadConfiguration(dConfigF);
        tConfig = YamlConfiguration.loadConfiguration(tConfigF);

        new DCConfigListener(this).checkConfig();
        new DCConfigListener(this).loadConfig();

        new DCTConfigListener(this).checkConfig();
        new DCTConfigListener(this).loadConfig();

        pm.registerEvents(new DCCreatureListener(this), this);

        getCommand("dropclear").setExecutor(new DCCommandExecutor(this));

        startTasks();

        log("[" + (pdfFile.getName()) + "]" + " version " + pdfFile.getVersion() + " is enabled!");
    }

    public void onDisable() {
        PluginDescriptionFile pdfFile = getDescription();
        log("[" + (pdfFile.getName()) + "]" + " version " + pdfFile.getVersion() + " is disabled!");
    }

    public void log(Object loggedObject) {
        System.out.println(loggedObject);
    }

    void startTasks() {
        getServer().getScheduler().scheduleAsyncRepeatingTask(this, new Runnable() {
            public void run() {
                new DCTConfigListener((DropClear) pm.getPlugin("DropClear")).loadConfig();

                for (Map.Entry<String, Object> set : tConfig.getValues(true).entrySet()) {
                    World world = getServer().getWorld(set.getValue().toString());

                    if (world == null)
                        continue;

                    String value = set.getKey();

                    Integer x;
                    Integer y;
                    Integer z;

                    try {
                        x = Integer.valueOf(value.split("\\|")[0]);
                        y = Integer.valueOf(value.split("\\|")[1]);
                        z = Integer.valueOf(value.split("\\|")[2]);
                    } catch (Exception ignored) {
                        continue;
                    }

                    Block block = world.getBlockAt(x,y,z);

                    if (block == null)
                        continue;

                    Chunk chunk = block.getChunk();
                    
                    for (Entity entity : chunk.getEntities()) {
                        if (entity instanceof Player)
                            continue;

                        if (entity instanceof Wolf)
                            continue;

                        entity.remove();
                    }
                }
            }
        }, 10*20L, 10*20L);
    }

    public Boolean checkPermissions(Player player, String node, Boolean useOp) {
        if (useOp)
            return player.isOp();

        return player.hasPermission(node);

    }
}

