package net.D3GN.MiracleM4n.DropClear;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.logging.Logger;

import org.bukkit.craftbukkit.CraftServer;
import org.bukkit.craftbukkit.command.ColouredConsoleSender;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.util.config.Configuration;

import com.nijiko.permissions.PermissionHandler;
import com.nijikokun.bukkit.Permissions.Permissions;

public class DropClear extends JavaPlugin {

	protected final static Logger logger = Logger.getLogger("Minecraft");
	public ColouredConsoleSender console = null;
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
	public Boolean hasChanged = false;
  	public Boolean bukkitPermission = false;
	
	public static PermissionHandler permissions;
	
	public void onEnable() {
		PluginDescriptionFile pdfFile = getDescription();
		console = new ColouredConsoleSender((CraftServer)getServer());
		getCommand("dropclear").setExecutor(commandexecutor);
		getCommand("dc").setExecutor(commandexecutor);
	        
		setupPermissions();
		
		console.sendMessage("[" + (pdfFile.getName()) + "]" + " version " + 
			pdfFile.getVersion() + " is enabled!");
		
		moveFiles();
		checkConfig();
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
					console.sendMessage("[" + (pdfFile.getName()) + "]" + " Default file written: " + name);
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
	
	public void checkConfig() {
		PluginDescriptionFile pdfFile = getDescription();
		Configuration config = new Configuration(new File(getDataFolder(), "config.yml"));
		config.load();
		
		if (config.getProperty("NotNumb") == null) {
			config.setProperty("NotNumb", notNumber);
			hasChanged = true;
		}
		if (config.getProperty("NegValue") == null) {
			config.setProperty("NegValue", negativeInterger);
			hasChanged = true;
		}
		if (config.getProperty("TooFar") == null) {
			config.setProperty("TooFar", farAway);
			hasChanged = true;
		}
		if (config.getProperty("CantFind") == null) {
			config.setProperty("CantFind", cantFind);
			hasChanged = true;
		}
		if (config.getProperty("NoPerms") == null) {
			config.setProperty("NoPerms", noPermissions);
			hasChanged = true;
		}
		if (config.getProperty("ItemKill") == null) {
			config.setProperty("ItemKill", itemKill);
			hasChanged = true;
		}
		if (config.getProperty("Max_Kill_Radius") == null) {
			config.setProperty("Max_Kill_Radius", maxKillRadius);
			hasChanged = true;
		}
		if (config.getProperty("Max_Admin_Kill_Radius") == null) {
			config.setProperty("Max_Admin_Kill_Radius", maxAdminKillRadius);
			hasChanged = true;
		}
		if (hasChanged) {
			config.setHeader("#DropClear  Configuration File, Enjoy!!");
			console.sendMessage("[" + pdfFile.getName() + "]" + " config.yml has been updated.");
			config.save();
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
		console.sendMessage("[InvinciWolf]" + " version " + 
				pdfFile.getVersion() + " is disabled!");
	}
	   	
	private void setupPermissions() {
		PluginDescriptionFile pdfFile = getDescription();
		Plugin permTest = this.getServer().getPluginManager().getPlugin("Permissions");	
		Plugin bukkitPermTest = this.getServer().getPluginManager().getPlugin("PermissionsBukkit");
		if (bukkitPermTest != null) {
			bukkitPermission = true;
			console.sendMessage("[" + (pdfFile.getName()) + "]" + " PermissionsBukkit " + (bukkitPermTest.getDescription().getVersion()) + " found hooking in.");
		} else if (permissions == null) {
			if (permTest != null) {
				permissions = ((Permissions)permTest).getHandler();
				console.sendMessage("[" + (pdfFile.getName()) + "]" + " Permissions "  + (permTest.getDescription().getVersion()) + " found hooking in.");
			} 
		} else {
			bukkitPermission = true;
			console.sendMessage("[" + (pdfFile.getName()) + "]" + " Permissions plugin not found, Defaulting to Bukkit Methods.");
		}
	}
}

