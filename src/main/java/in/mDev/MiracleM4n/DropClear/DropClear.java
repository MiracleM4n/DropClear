package in.mDev.MiracleM4n.DropClear;

import java.io.File;

import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;

import com.nijiko.permissions.PermissionHandler;
import com.nijikokun.bukkit.Permissions.Permissions;

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

    // Permissions
    public PermissionHandler permissions;
    Boolean permissionsB = false;

    public void onEnable() {
        pm = getServer().getPluginManager();
        pdfFile = getDescription();

        dConfigF = new File(getDataFolder(), "config.yml");
        dConfig = YamlConfiguration.loadConfiguration(dConfigF);

        setupPermissions();

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

    protected void setupPermissions() {
        Plugin permTest = pm.getPlugin("Permissions");

        if(permTest != null) {
            permissions = ((Permissions) permTest).getHandler();
            permissionsB = true;
            log("[" + pdfFile.getName() + "] Permissions " + (permTest.getDescription().getVersion()) + " found hooking in.");
        } else {
            permissionsB = false;
        }
    }

    public void log(Object loggedObject) {
        try {
           getServer().getConsoleSender().sendMessage(loggedObject.toString());
        } catch (IncompatibleClassChangeError ignored) {
            System.out.println(loggedObject);
        }
    }

    @SuppressWarnings("unused")
    public Boolean checkPermissions(Player player, String node, Boolean useOp) {
        if (permissionsB)
            if (permissions.has(player, node))
                return true;

        if (useOp)
            return player.isOp();

        return player.hasPermission(node);

    }
}

