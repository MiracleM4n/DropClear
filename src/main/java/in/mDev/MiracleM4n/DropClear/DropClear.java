package in.mDev.MiracleM4n.DropClear;

import java.io.File;

import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.configuration.file.YamlConfiguration;

public class DropClear extends JavaPlugin {
    PluginManager pm;
    PluginDescriptionFile pdfFile;

    // Listeners
    DCCommandExecutor cExecutor = null;
    DCConfigListener cListener = null;

    // Configuration
    YamlConfiguration dConfig = null;
    File dConfigF = null;

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
        dConfig = YamlConfiguration.loadConfiguration(dConfigF);

        cExecutor = new DCCommandExecutor(this);
        cListener = new DCConfigListener(this);

        cListener.checkConfig();
        cListener.loadConfig();

        getCommand("dropclear").setExecutor(cExecutor);

        log("[" + (pdfFile.getName()) + "]" + " version " +
            pdfFile.getVersion() + " is enabled!");
    }

    public void onDisable() {
        PluginDescriptionFile pdfFile = getDescription();
        log("[" + (pdfFile.getName()) + "]" + " version " +
                pdfFile.getVersion() + " is disabled!");
    }

    public void log(Object loggedObject) {
        System.out.println(loggedObject);
    }

    @SuppressWarnings("unused")
    public Boolean checkPermissions(Player player, String node, Boolean useOp) {
        if (useOp)
            return player.isOp();

        return player.hasPermission(node);

    }
}

