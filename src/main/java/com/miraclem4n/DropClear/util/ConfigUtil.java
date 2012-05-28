package com.miraclem4n.dropclear.util;

import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;

public class ConfigUtil {
    static YamlConfiguration config;
    static File file;

    public static void initialize() {
        load();
    }

    public static void load() {
        file = new File("plugins/DropClear/config.yml");

        config = YamlConfiguration.loadConfiguration(file);

        config.options().indent(4);
        config.options().header("DropClear Config");

        loadDefaults();
    }

    private static void loadDefaults() {
        editOption("NotNumb", "message.notNumber");
        editOption("NegValue", "message.negativeValue");
        editOption("TooFar", "message.tooFar");
        editOption("CantFind", "message.cantFind");
        editOption("NoPerms", "message.noPerm");
        editOption("ItemKill", "message.itemCleared");

        editOption("Max_Kill_Radius", "int.maxKill");
        editOption("Max_Admin_Kill_Radius", "int.maxAdminKill");

        checkOption("message.notNumber", "That is not a number.");
        checkOption("message.negativeValue", "You can't use negative values.");
        checkOption("message.tooFar", "Too far away.");
        checkOption("message.cantFind", "No items found.");
        checkOption("message.noPerm", "You don't have permissions to use this.");
        checkOption("message.itemCleared", "%items% items cleared.");

        checkOption("int.maxKill", 10);
        checkOption("int.maxAdminKill", 30);
    }

    public static void set(String key, Object obj) {
        config.set(key, obj);

        save();
    }

    public static Boolean save() {
        try {
            config.save(file);
            return true;
        } catch (Exception ignored) {
            return false;
        }
    }

    public static YamlConfiguration getConfig() {
        return config;
    }

    private static void checkOption(String option, Object defValue) {
        if (!config.isSet(option))
            set(option, defValue);
    }

    private static void editOption(String oldOption, String newOption) {
        if (config.isSet(oldOption)) {
            set(newOption, config.get(oldOption));
            set(oldOption, null);
        }
    }
}
