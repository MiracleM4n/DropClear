package net.D3GN.MiracleM4n.DropClear;

import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.configuration.file.YamlConfigurationOptions;
import org.bukkit.plugin.PluginDescriptionFile;

import java.io.IOException;

public class DCConfigListener {
    DropClear plugin;

    public DCConfigListener(DropClear plugin) {
        this.plugin = plugin;
    }

    Boolean hasChanged = false;

    public void checkConfig() {
        PluginDescriptionFile pdfFile = plugin.pdfFile;
        YamlConfiguration config = plugin.dConfig;
        YamlConfigurationOptions configO = config.options();

        if (config.get("NotNumb") == null) {
            config.set("NotNumb", plugin.notNumber);
            hasChanged = true;
        }
        if (config.get("NegValue") == null) {
            config.set("NegValue", plugin.negativeInterger);
            hasChanged = true;
        }
        if (config.get("TooFar") == null) {
            config.set("TooFar", plugin.farAway);
            hasChanged = true;
        }
        if (config.get("CantFind") == null) {
            config.set("CantFind", plugin.cantFind);
            hasChanged = true;
        }
        if (config.get("NoPerms") == null) {
            config.set("NoPerms", plugin.noPermissions);
            hasChanged = true;
        }
        if (config.get("ItemKill") == null) {
            config.set("ItemKill", plugin.itemKill);
            hasChanged = true;
        }
        if (config.get("Max_Kill_Radius") == null) {
            config.set("Max_Kill_Radius", plugin.maxKillRadius);
            hasChanged = true;
        }
        if (config.get("Max_Admin_Kill_Radius") == null) {
            config.set("Max_Admin_Kill_Radius", plugin.maxAdminKillRadius);
            hasChanged = true;
        }
        if (hasChanged) {
            configO.header("DropClear  Configuration File, Enjoy!!");

            plugin.log("[" + pdfFile.getName() + "]" + " config.yml has been updated.");

            try {
                config.save(plugin.dConfigF);
            } catch (IOException ignored) {}
        }
    }

    public void loadConfig() {
        YamlConfiguration config = plugin.dConfig;

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
