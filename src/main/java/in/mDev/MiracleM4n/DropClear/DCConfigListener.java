package in.mDev.MiracleM4n.DropClear;

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

        checkOption(config, "NotNumb", plugin.notNumber);
        checkOption(config, "NegValue", plugin.negativeInterger);
        checkOption(config, "TooFar", plugin.farAway);
        checkOption(config, "CantFind", plugin.cantFind);
        checkOption(config, "NoPerms", plugin.noPermissions);
        checkOption(config, "ItemKill", plugin.itemKill);
        checkOption(config, "Max_Kill_Radius", plugin.maxKillRadius);
        checkOption(config, "Max_Admin_Kill_Radius", plugin.maxAdminKillRadius);

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

    void checkOption(YamlConfiguration config, String option, Object defValue) {
        if (!config.isSet(option)) {
            config.set(option, defValue);
            hasChanged = true;
        }
    }
}
