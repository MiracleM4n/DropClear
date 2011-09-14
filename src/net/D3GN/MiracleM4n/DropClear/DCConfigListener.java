package net.D3GN.MiracleM4n.DropClear;

import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.util.config.Configuration;

import java.util.logging.Level;

public class DCConfigListener {
    DropClear plugin;

    public DCConfigListener(DropClear plugin) {
        this.plugin = plugin;
    }

    Boolean hasChanged = false;

    public void checkConfig() {
		PluginDescriptionFile pdfFile = plugin.getDescription();
		Configuration config = plugin.config;
		config.load();

		if (config.getProperty("NotNumb") == null) {
			config.setProperty("NotNumb", plugin.notNumber);
			hasChanged = true;
		}
		if (config.getProperty("NegValue") == null) {
			config.setProperty("NegValue", plugin.negativeInterger);
			hasChanged = true;
		}
		if (config.getProperty("TooFar") == null) {
			config.setProperty("TooFar", plugin.farAway);
			hasChanged = true;
		}
		if (config.getProperty("CantFind") == null) {
			config.setProperty("CantFind", plugin.cantFind);
			hasChanged = true;
		}
		if (config.getProperty("NoPerms") == null) {
			config.setProperty("NoPerms", plugin.noPermissions);
			hasChanged = true;
		}
		if (config.getProperty("ItemKill") == null) {
			config.setProperty("ItemKill", plugin.itemKill);
			hasChanged = true;
		}
		if (config.getProperty("Max_Kill_Radius") == null) {
			config.setProperty("Max_Kill_Radius", plugin.maxKillRadius);
			hasChanged = true;
		}
		if (config.getProperty("Max_Admin_Kill_Radius") == null) {
			config.setProperty("Max_Admin_Kill_Radius", plugin.maxAdminKillRadius);
			hasChanged = true;
		}
		if (hasChanged) {
			config.setHeader("#DropClear  Configuration File, Enjoy!!");
			plugin.console.log(Level.INFO, "[" + pdfFile.getName() + "]" + " config.yml has been updated.");
			config.save();
		}
	}

	public void readConfig() {
		Configuration config = plugin.config;
		config.load();

		//Strings
		plugin.notNumber  = config.getString("NotNumb", plugin.notNumber);
		plugin.negativeInterger  = config.getString("NegValue", plugin.negativeInterger);
		plugin.farAway  = config.getString("TooFar", plugin.farAway);
		plugin.cantFind  = config.getString("CantFind", plugin.cantFind);
		plugin.noPermissions  = config.getString("NoPerms", plugin.noPermissions);
		plugin.itemKill  = config.getString("ItemKill", plugin.itemKill);

		//Intergers
		plugin.maxKillRadius = config.getInt("Max_Kill_Radius", plugin.maxKillRadius);
		plugin.maxAdminKillRadius = config.getInt("Max_Admin_Kill_Radius", plugin.maxAdminKillRadius);
	}
}
