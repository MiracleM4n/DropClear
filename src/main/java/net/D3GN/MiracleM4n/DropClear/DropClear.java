package net.D3GN.MiracleM4n.DropClear;

import java.io.File;

import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;

import com.nijiko.permissions.PermissionHandler;
import com.nijikokun.bukkit.Permissions.Permissions;

import org.anjocaido.groupmanager.permissions.AnjoPermissionsHandler;

import ru.tehkode.permissions.PermissionManager;
import ru.tehkode.permissions.bukkit.PermissionsEx;


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
    Boolean permissions3;
    Boolean permissionsB = false;

    // GroupManager
    public AnjoPermissionsHandler gmPermissions;
    Boolean gmPermissionsB = false;

    // PermissionsEX
    public PermissionManager pexPermissions;
    Boolean PEXB = false;

    public void onEnable() {
        pm = getServer().getPluginManager();
        pdfFile = getDescription();

        dConfigF = new File(getDataFolder(), "config.yml");
        dConfig = YamlConfiguration.loadConfiguration(dConfigF);

        setupPEX();

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
        log("[InvinciWolf]" + " version " +
                pdfFile.getVersion() + " is disabled!");
    }

    protected void setupPEX() {
        Plugin pexTest = pm.getPlugin("PermissionsEx");

        if (pexTest != null) {
            pexPermissions = PermissionsEx.getPermissionManager();
            PEXB = true;
            log("[" + pdfFile.getName() + "] PermissionsEx " + (pexTest.getDescription().getVersion()) + " found hooking in.");
        } else {
            PEXB = false;
            setupPermissions();
        }
    }

    protected void setupPermissions() {
        Plugin permTest = pm.getPlugin("Permissions");

        if(permTest != null) {
            permissions = ((Permissions) permTest).getHandler();
            permissionsB = true;
            permissions3 = permTest.getDescription().getVersion().startsWith("3");
            log("[" + pdfFile.getName() + "] Permissions " + (permTest.getDescription().getVersion()) + " found hooking in.");
        } else {
            permissionsB = false;
            permissions3 = false;
            setupGroupManager();
        }
    }

    protected void setupGroupManager() {
        Plugin permTest = pm.getPlugin("GroupManager");

        if (permTest != null) {
            gmPermissionsB = true;
            log("[" + pdfFile.getName() + "] GroupManager " + (permTest.getDescription().getVersion()) + " found hooking in.");
        } else {
            gmPermissionsB = false;
            log("[" + pdfFile.getName() + "] No Legacy Permissions plugins were found defaulting to SuperPerms.");
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

        if (gmPermissionsB)
            if (gmPermissions.has(player, node))
                return true;

        if (PEXB)
            if (pexPermissions.has(player, node))
                return true;

        if (useOp)
            return player.isOp();

        return player.hasPermission(node);

    }
}

