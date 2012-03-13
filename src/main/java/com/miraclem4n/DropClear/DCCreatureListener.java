package com.miraclem4n.dropclear;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.CreatureSpawnEvent;

import java.util.Map;

public class DCCreatureListener implements Listener {
    DropClear plugin;

    public DCCreatureListener(DropClear plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onCreatureSpawn(CreatureSpawnEvent event) {
        new DCTConfigListener(plugin).loadConfig();

        for (Map.Entry<String, Object> set : plugin.tConfig.getValues(true).entrySet()) {
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
