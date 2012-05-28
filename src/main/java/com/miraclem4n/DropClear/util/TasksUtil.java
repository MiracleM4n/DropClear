package com.miraclem4n.dropclear.util;

import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;

public class TasksUtil {
    static YamlConfiguration config;
    static File file;

    public static void initialize() {
        load();
    }

    public static void load() {
        file = new File("plugins/DropClear/tasks.yml");

        config = YamlConfiguration.loadConfiguration(file);

        config.options().indent(4);
        config.options().header("DropClear Config");

        loadDefaults();
    }

    private static void loadDefaults() {
        checkOption("0|1|2", "World");
        checkOption("0|2|2", "World2");
        checkOption("1|1|2", "World7");
        checkOption("0|1|3", "World0");
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
}
