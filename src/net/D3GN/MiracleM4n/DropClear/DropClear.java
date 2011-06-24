package net.D3GN.MiracleM4n.DropClear;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.logging.Logger;

import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.util.config.Configuration;

import com.nijiko.permissions.PermissionHandler;
import com.nijikokun.bukkit.Permissions.Permissions;

public class DropClear extends JavaPlugin {

	protected final static Logger logger = Logger.getLogger("Minecraft");
	DCCommandExecutor commandexecutor = new DCCommandExecutor(this);
	
	
	//Strings
	public String notNumber = "That is not a number";
	public String negativeInterger = "Cant Use Negative Values";
	public String farAway = "Too far away";
	public String cantFind = "No Items Found";
	public String noPermissions = "You don't have permissions to use this";
	public String itemKill = "Items Cleared";
	public String pInfo = "[DropClear] ";
	
	//Integers
	public Integer maxKillRadius = 10;
	public Integer maxAdminKillRadius = 30;
	
	//Booleans
	public Boolean messageFix = true;
	public Boolean messFix = true;
	
	public static PermissionHandler Permissions;
	
	public void onEnable() {
		PluginDescriptionFile pdfFile = getDescription();
		getCommand("dropclear").setExecutor(commandexecutor);
		getCommand("dc").setExecutor(commandexecutor);
	        
		setupPermissions();
		
		System.out.println("[" + (pdfFile.getName()) + "]" + " version " + 
			pdfFile.getVersion() + " is enabled!");
		
		moveFiles();
		readConfig();
	}
	
	public void moveFiles(){
		getDataFolder().mkdir();
		getDataFolder().setWritable(true);
		getDataFolder().setExecutable(true);
		extractFile("config.yml");
	}
	
	private void extractFile(String name) {
		PluginDescriptionFile pdfFile = getDescription();
		File actual = new File(getDataFolder(), name);
			if (!actual.exists()) {
				InputStream input = getClass().getResourceAsStream("/Config/" + name);
				if (input != null) {
					FileOutputStream output = null;
					try
					{
						output = new FileOutputStream(actual);
						byte[] buf = new byte[8192];
						int length = 0;

						while ((length = input.read(buf)) > 0) {
							output.write(buf, 0, length);
						}
						System.out.println("[" + (pdfFile.getName()) + "]" + "Default file written: " + name);
					} catch (Exception e) {
						e.printStackTrace();
					} finally {
						try {
							if (input != null)
								input.close();
						} catch (Exception e) {
						} try {
							if (output != null)
								output.close();
						} catch (Exception e) {
						}
					}
				}
			}
		}
	
	public void readConfig() {
		Configuration config = new Configuration(new File(getDataFolder(), "config.yml"));
		config.load();
		
		//Strings
		notNumber  = config.getString("NotNumb", notNumber);
		negativeInterger  = config.getString("NegValue", negativeInterger);
		farAway  = config.getString("TooFar", farAway);
		cantFind  = config.getString("CantFind", cantFind);
		noPermissions  = config.getString("NoPerms", noPermissions);
		itemKill  = config.getString("ItemKill", itemKill);
		
		//Intergers
		maxKillRadius = config.getInt("Max_Kill_Radius", maxKillRadius);
		maxAdminKillRadius = config.getInt("Max_Admin_Kill_Radius", maxAdminKillRadius);
	}
	    
	public void onDisable() {
		PluginDescriptionFile pdfFile = getDescription();
		System.out.println("[InvinciWolf]" + " version " + 
				pdfFile.getVersion() + " is disabled!");
	}
	   	
	private void setupPermissions() 
	{
		Plugin test = this.getServer().getPluginManager().getPlugin("Permissions");
			
		if (DropClear.Permissions == null) {
			if (test != null) {
				DropClear.Permissions = ((Permissions)test).getHandler();
				System.out.println("[InvinciWolf] Permissions found hooking in.");
			} else {
				System.out.println("[InvinciWolf] Permissions plugin not found, defaulting to ops.txt.");
			}
		}
	}
}

