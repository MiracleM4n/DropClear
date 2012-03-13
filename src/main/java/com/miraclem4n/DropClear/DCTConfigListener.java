package com.miraclem4n.dropclear;

import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.configuration.file.YamlConfigurationOptions;
import org.bukkit.plugin.PluginDescriptionFile;

import java.io.IOException;

public class DCTConfigListener {
    DropClear plugin;

    public DCTConfigListener(DropClear plugin) {
        this.plugin = plugin;
    }

    Boolean hasChanged = false;

    public void checkConfig() {
        PluginDescriptionFile pdfFile = plugin.pdfFile;
        YamlConfiguration config = plugin.tConfig;
        YamlConfigurationOptions configO = config.options();

        checkOption(config, "0|1|2", "World");
        checkOption(config, "0|2|2", "World2");
        checkOption(config, "1|1|2", "World7");
        checkOption(config, "0|1|3", "World0");

        if (hasChanged) {
            configO.header("Tasks File.");

            plugin.log("[" + pdfFile.getName() + "]" + " tasks.yml has been updated.");

            try {
                config.save(plugin.dConfigF);
            } catch (IOException ignored) {}
        }
    }

    public void loadConfig() {
        plugin.dConfig = YamlConfiguration.loadConfiguration(plugin.tConfigF);
    }

    void checkOption(YamlConfiguration config, String option, Object defValue) {
        if (!config.isSet(option)) {
            config.set(option, defValue);
            hasChanged = true;
        }
    }
}
