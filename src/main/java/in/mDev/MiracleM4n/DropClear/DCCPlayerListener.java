package in.mDev.MiracleM4n.DropClear;

import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.CreatureSpawnEvent;

import java.io.File;

public class DCCPlayerListener implements Listener {
    DropClear plugin;

    public DCCPlayerListener(DropClear plugin) {
        this.plugin = plugin;
    }

    YamlConfiguration dConfig = null;
    File dConfigF = null;

    @EventHandler
    public void onCreatureSpawn(CreatureSpawnEvent event) {
        dConfigF = new File(plugin.getDataFolder(), "chunks.yml");
        dConfig = YamlConfiguration.loadConfiguration(dConfigF);

        Integer x = event.getLocation().getChunk().getX();
        Integer y = event.getLocation().getChunk().getZ();

        if (dConfig.isSet(x.toString())
                && dConfig.get(x.toString()).equals(y.toString()))
            event.setCancelled(true);
    }
}
