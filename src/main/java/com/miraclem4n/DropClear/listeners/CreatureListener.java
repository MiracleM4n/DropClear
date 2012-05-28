package com.miraclem4n.dropclear.listeners;

import com.miraclem4n.dropclear.DropClear;
import com.miraclem4n.dropclear.util.TasksUtil;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.CreatureSpawnEvent;

import java.util.Map;

public class CreatureListener implements Listener {
    DropClear plugin;

    public CreatureListener(DropClear instance) {
        plugin = instance;
    }

    @EventHandler
    public void onCreatureSpawn(CreatureSpawnEvent event) {
        TasksUtil.load();

        for (Map.Entry<String, Object> set : TasksUtil.getConfig().getValues(true).entrySet()) {
            World world = plugin.getServer().getWorld(set.getValue().toString());

            if (world == null)
                continue;

            if (world != event.getLocation().getWorld())
                continue;

            String value = set.getKey();

            Double x;
            Double y;
            Double z;

            try {
                x = Double.valueOf(value.split("\\|")[0]);
                y = Double.valueOf(value.split("\\|")[1]);
                z = Double.valueOf(value.split("\\|")[2]);
            } catch (Exception ignored) {
                continue;
            }

            if (event.getLocation() == new Location(event.getLocation().getWorld(), x,y,z))
                event.setCancelled(true);
        }
    }
}
