package com.miraclem4n.dropclear;

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

        editOption(config, "NotNumb", "message.notNumber");
        editOption(config, "NegValue", "message.negativeValue");
        editOption(config, "TooFar", "message.tooFar");
        editOption(config, "CantFind", "message.cantFind");
        editOption(config, "NoPerms", "message.noPerm");
        editOption(config, "ItemKill", "message.itemCleared");

        editOption(config, "Max_Kill_Radius", "int.maxKill");
        editOption(config, "Max_Admin_Kill_Radius", "int.maxAdminKill");

        checkOption(config, "message.notNumber", plugin.notNumber);
        checkOption(config, "message.negativeValue", plugin.negativeInterger);
        checkOption(config, "message.tooFar", plugin.farAway);
        checkOption(config, "message.cantFind", plugin.cantFind);
        checkOption(config, "message.noPerm", plugin.noPermissions);
        checkOption(config, "message.itemCleared", plugin.itemKill);

        checkOption(config, "int.maxKill", plugin.maxKillRadius);
        checkOption(config, "int.maxAdminKill", plugin.maxAdminKillRadius);

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
        plugin.notNumber  = config.getString("message.notNumber", plugin.notNumber);
        plugin.negativeInterger  = config.getString("message.negativeValue", plugin.negativeInterger);
        plugin.farAway  = config.getString("message.tooFar", plugin.farAway);
        plugin.cantFind  = config.getString("message.cantFind", plugin.cantFind);
        plugin.noPermissions  = config.getString("message.noPerm", plugin.noPermissions);
        plugin.itemKill  = config.getString("message.itemCleared", plugin.itemKill);

        //Intergers
        plugin.maxKillRadius = config.getInt("int.maxKill", plugin.maxKillRadius);
        plugin.maxAdminKillRadius = config.getInt("int.maxAdminKill", plugin.maxAdminKillRadius);
    }

    void checkOption(YamlConfiguration config, String option, Object defValue) {
        if (!config.isSet(option)) {
            config.set(option, defValue);
            hasChanged = true;
        }
    }

    void editOption(YamlConfiguration config, String oldOption, String newOption) {
        if (config.isSet(oldOption)) {
            config.set(newOption, config.get(oldOption));
            config.set(oldOption, null);
            hasChanged = true;
        }
    }
}
