package com.miraclem4n.dropclear;

import com.miraclem4n.dropclear.commands.DropClearCommand;
import com.miraclem4n.dropclear.listeners.CreatureListener;
import com.miraclem4n.dropclear.util.ConfigUtil;
import com.miraclem4n.dropclear.util.MessageUtil;
import com.miraclem4n.dropclear.util.TasksUtil;
import org.bukkit.Chunk;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Wolf;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Map;

public class DropClear extends JavaPlugin {
    PluginManager pm;
    PluginDescriptionFile pdfFile;

    public void onEnable() {
        pm = getServer().getPluginManager();
        pdfFile = getDescription();

        ConfigUtil.initialize();
        TasksUtil.initialize();

        pm.registerEvents(new CreatureListener(this), this);

        getCommand("dropclear").setExecutor(new DropClearCommand(this));

        startTasks();

        MessageUtil.log("[" + pdfFile.getName() + "] version " + pdfFile.getVersion() + " is enabled!");
    }

    public void onDisable() {
        MessageUtil.log("[" + pdfFile.getName() + "] version " + pdfFile.getVersion() + " is disabled!");
    }

    private void startTasks() {
        getServer().getScheduler().scheduleAsyncRepeatingTask(this, new Runnable() {
            public void run() {
                TasksUtil.load();

                for (Map.Entry<String, Object> set : TasksUtil.getConfig().getValues(true).entrySet()) {
                    World world = getServer().getWorld(set.getValue().toString());

                    if (world == null)
                        continue;

                    String value = set.getKey();

                    Integer x;
                    Integer y;
                    Integer z;

                    try {
                        x = Integer.valueOf(value.split("\\|")[0]);
                        y = Integer.valueOf(value.split("\\|")[1]);
                        z = Integer.valueOf(value.split("\\|")[2]);
                    } catch (Exception ignored) {
                        continue;
                    }

                    Block block = world.getBlockAt(x,y,z);

                    if (block == null)
                        continue;

                    Chunk chunk = block.getChunk();
                    
                    for (Entity entity : chunk.getEntities()) {
                        if (entity instanceof Player)
                            continue;

                        if (entity instanceof Wolf)
                            continue;

                        entity.remove();
                    }
                }
            }
        }, 200L, 200L);
    }
}

