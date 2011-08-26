package net.D3GN.MiracleM4n.DropClear;

import java.io.File;

import org.anjocaido.groupmanager.permissions.AnjoPermissionsHandler;
import org.bukkit.craftbukkit.CraftServer;
import org.bukkit.craftbukkit.command.ColouredConsoleSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.java.JavaPlugin;

import com.nijiko.permissions.PermissionHandler;
import com.nijikokun.bukkit.Permissions.Permissions;
import org.bukkit.util.config.Configuration;

public class DropClear extends JavaPlugin {

	DCCommandExecutor cExecutor = null;
    DCConfigListener cListener = null;

	ColouredConsoleSender console = null;
    Configuration config = null;
	
	//Strings
	 String notNumber = "That is not a number";
	 String negativeInterger = "Cant Use Negative Values";
	 String farAway = "Too far away";
	 String cantFind = "No Items Found";
	 String noPermissions = "You don't have permissions to use this";
	 String itemKill = "Items Cleared";
	 String pInfo = "[DropClear] ";
	
	//Integers
	 Integer maxKillRadius = 10;
	 Integer maxAdminKillRadius = 30;
	
	//Booleans
	 Boolean messageFix = true;
	 Boolean messFix = true;
  	 Boolean bukkitPermission = false;
	
	// Permissions
    public PermissionHandler permissions;
    Boolean permissions3;
    Boolean permissionsB = false;

    // GroupManager
    public AnjoPermissionsHandler gmPermissions;
    Boolean gmPermissionsB = false;
	
	public void onEnable() {
		PluginDescriptionFile pdfFile = getDescription();
		console = new ColouredConsoleSender((CraftServer)getServer());
        config = new Configuration(new File(getDataFolder(), "config.yml"));

        setupPermissions();

	    cExecutor = new DCCommandExecutor(this);
        cListener = new DCConfigListener(this);

        cListener.checkConfig();
		cListener.readConfig();

        getCommand("dropclear").setExecutor(cExecutor);
		
		console.sendMessage("[" + (pdfFile.getName()) + "]" + " version " + 
			pdfFile.getVersion() + " is enabled!");
	}
	    
	public void onDisable() {
		PluginDescriptionFile pdfFile = getDescription();
		console.sendMessage("[InvinciWolf]" + " version " + 
				pdfFile.getVersion() + " is disabled!");
	}
	   	
	private void setupPermissions() {
        Plugin permTest = this.getServer().getPluginManager().getPlugin("Permissions");
        PluginDescriptionFile pdfFile = getDescription();

        if(permissions != null)
            return;

        if(permTest != null) {
            permissions = ((Permissions) permTest).getHandler();
            permissionsB = true;

            System.out.println("[" + pdfFile.getName() + "]" + " Permissions " + (permTest.getDescription().getVersion()) + " found hooking in.");
        } else {
            permissionsB = false;
            System.out.println("[" + pdfFile.getName() + "]" + " Permissions not found, Checking for GroupManager.");
            setupGroupManager();
        }
    }

    private void setupGroupManager() {
        Plugin permTest = this.getServer().getPluginManager().getPlugin("GroupManager");
        PluginDescriptionFile pdfFile = getDescription();

        if(gmPermissions != null)
            return;

        if (permTest != null) {
            gmPermissionsB = true;
            System.out.println("[" + pdfFile.getName() + "]" + " GroupManager " + (permTest.getDescription().getVersion()) + " found hooking in.");
        } else {
            gmPermissionsB = false;
            System.out.println("[" + pdfFile.getName() + "]" + " GroupManager not found, Using SuperPerms.");
        }
    }

    public Boolean checkPermissions(Player player, String node) {
        if (permissionsB) {
            if (permissions.has(player, node))
                return true;
        } else if (gmPermissionsB) {
            if (gmPermissions.has(player, node))
                return true;
        } else if (player.hasPermission(node)) {
             return true;
        } else if (player.isOp()) {
             return true;
        }
        return false;
    }
}

