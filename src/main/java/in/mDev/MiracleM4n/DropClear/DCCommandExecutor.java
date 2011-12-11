package in.mDev.MiracleM4n.DropClear;

import org.bukkit.ChatColor;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.craftbukkit.entity.CraftItem;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Item;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Wolf;

public class DCCommandExecutor implements CommandExecutor {
    DropClear plugin;

    public DCCommandExecutor(DropClear plugin) {
        this.plugin = plugin;
    }
    
    public Integer noTeleportRadius = 0;
    
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        String commandName = command.getName();
        if (sender instanceof Player) {
            Player player = ((Player) sender);
            if (commandName.equalsIgnoreCase("dropclear")) {
                if(args.length == 2) {
                    Integer entityVal = 0;
                    Integer dropVal = 0;
                    String entityVar;
                    Integer radius;
                    entityVar = args[0];
                    try {
                        radius = new Integer(args[1]);
                    } catch (NumberFormatException e) {
                        player.sendMessage(ChatColor.RED + (plugin.pInfo) + (plugin.notNumber) + ".");
                      return true;
                    }
                    if (entityVar.equalsIgnoreCase("mob")) {
                        if (plugin.checkPermissions(player, "dropclear.killadmin", false)) {
                            if (radius < noTeleportRadius) {
                                player.sendMessage(ChatColor.RED + (plugin.pInfo) + (plugin.negativeInterger) + ".");
                                    return true;
                                }
                                if(radius <= plugin.maxAdminKillRadius) {
                                    plugin.messageFix = false;
                                    plugin.messFix = true;
                                    for(Entity entity : (player.getNearbyEntities(radius,radius,radius))) {
                                        if (!(entity instanceof Player)) {
                                            if (!(entity instanceof Wolf)) {
                                                if (entity instanceof LivingEntity) {
                                                    entityVal++;
                                                    entity.remove();
                                                    plugin.messageFix = true;
                                                }
                                            }
                                        }
                                        }
                                    if (plugin.messageFix) {
                                        player.sendMessage(ChatColor.RED + (plugin.pInfo) + entityVal + " " + (plugin.itemKill) + ".");
                                        plugin.messFix = false;
                                        return true;
                                    }
                                    if (plugin.messFix) {
                                        player.sendMessage(ChatColor.RED + (plugin.pInfo) + (plugin.cantFind) + ".");
                                        return true;
                                    }
                                }
                                if(radius > plugin.maxAdminKillRadius) {
                                    player.sendMessage(ChatColor.RED + (plugin.pInfo) + (plugin.farAway) + ".");
                                    return true;
                                }
                        } else if (plugin.checkPermissions(player, "dropclear.kill", false)) {
                            if (radius < noTeleportRadius) {
                                player.sendMessage(ChatColor.RED + (plugin.pInfo) + (plugin.negativeInterger) + ".");
                                return true;
                            }
                               if(radius <= plugin.maxKillRadius) {
                                plugin.messageFix = false;
                                plugin.messFix = true;
                                for(Entity entity : (player.getNearbyEntities(radius,radius,radius))) {
                                    if (!(entity instanceof Player)) {
                                        if (!(entity instanceof Wolf)) {
                                            if (entity instanceof LivingEntity) {
                                                entityVal++;
                                                entity.remove();
                                                plugin.messageFix = true;
                                            }
                                        }
                                    }
                                }
                                if (plugin.messageFix) {
                                    player.sendMessage(ChatColor.RED + (plugin.pInfo) + entityVal + " " + (plugin.itemKill) + ".");
                                    plugin.messFix = false;
                                    return true;
                                }
                                if (plugin.messFix) {
                                    player.sendMessage(ChatColor.RED + (plugin.pInfo) + (plugin.cantFind) + ".");
                                    return true;
                                }
                            }
                            if(radius > plugin.maxKillRadius) {
                                player.sendMessage(ChatColor.RED + (plugin.pInfo) + (plugin.farAway) + ".");
                                return true;
                            }
                        } else {
                            player.sendMessage(ChatColor.RED + (plugin.pInfo) + (plugin.noPermissions) + ".");
                            return true;
                        }
                    } else if (entityVar.equalsIgnoreCase("drop")) {
                        if (plugin.checkPermissions(player, "dropclear.killadmin", false)) {
                            if (radius < noTeleportRadius) {
                                player.sendMessage(ChatColor.RED + (plugin.pInfo) + (plugin.negativeInterger) + ".");
                                return true;
                            }
                            if(radius <= plugin.maxAdminKillRadius) {
                                plugin.messageFix = false;
                                plugin.messFix = true;
                                for(Entity entity : (player.getNearbyEntities(radius,radius,radius))) {
                                    if (entity instanceof Item) {
                                        dropVal++;
                                        entity.remove();
                                           plugin.messageFix = true;
                                    }
                                    if (entity instanceof CraftItem) {
                                        dropVal++;
                                        entity.remove();
                                        plugin.messageFix = true;
                                    }
                                }
                                if (plugin.messageFix) {
                                    player.sendMessage(ChatColor.RED + (plugin.pInfo) + dropVal + " " + (plugin.itemKill) + ".");
                                    plugin.messFix = false;
                                    return true;
                                }
                                   if (plugin.messFix) {
                                    player.sendMessage(ChatColor.RED + (plugin.pInfo) + (plugin.cantFind) + ".");
                                    return true;
                                }
                            }
                               if(radius > plugin.maxAdminKillRadius) {
                                player.sendMessage(ChatColor.RED + (plugin.pInfo) + (plugin.farAway) + ".");
                                return true;
                            }
                           } else if (plugin.checkPermissions(player, "dropclear.kill", false)) {
                            if (radius < noTeleportRadius) {
                                player.sendMessage(ChatColor.RED + (plugin.pInfo) + (plugin.negativeInterger) + ".");
                                return true;
                            }
                            if(radius <= plugin.maxKillRadius) {
                                plugin.messageFix = false;
                                plugin.messFix = true;
                                for(Entity entity : (player.getNearbyEntities(radius,radius,radius))) {
                                    if (entity instanceof Item) {
                                        dropVal++;
                                           entity.remove();
                                        plugin.messageFix = true;
                                    }
                                    if (entity instanceof CraftItem) {
                                        dropVal++;
                                        entity.remove();
                                           plugin.messageFix = true;
                                    }
                                }
                                if (plugin.messageFix) {
                                       player.sendMessage(ChatColor.RED + (plugin.pInfo) + dropVal + " " + (plugin.itemKill) + ".");
                                    plugin.messFix = false;
                                    return true;
                                }
                                   if (plugin.messFix) {
                                    player.sendMessage(ChatColor.RED + (plugin.pInfo) + (plugin.cantFind) + ".");
                                    return true;
                                }
                            }
                               if(radius > plugin.maxKillRadius) {
                                player.sendMessage(ChatColor.RED + (plugin.pInfo) + (plugin.farAway) + ".");
                                return true;
                            }
                           } else {
                            player.sendMessage(ChatColor.RED + (plugin.pInfo) + (plugin.noPermissions) + ".");
                            return true;
                        }
                    }
                }
            }
        } else {
            if (commandName.equalsIgnoreCase("dropclear")) {
                if(args.length == 2) {
                    Integer entityVal = 0;
                    Integer dropVal = 0;
                    String entityVar;
                    World world;
                    entityVar = args[1];
                    world = plugin.getServer().getWorld(args[0]);
                    if (world == null) {
                        plugin.log(ChatColor.RED + (plugin.pInfo) + " World " + args[0] + " not found.");
                        return true;
                    } else if (entityVar.compareToIgnoreCase("mob") == 0) {
                        plugin.messageFix = false;
                        plugin.messFix = true;
                        for(Entity entity : (world.getEntities())) {
                            if (!(entity instanceof Player)) {
                                if (!(entity instanceof Wolf)) {
                                    if (entity instanceof LivingEntity) {
                                        entityVal++;
                                        entity.remove();
                                        plugin.messageFix = true;
                                    }
                                }
                            }
                        }
                        if (plugin.messageFix) {
                            plugin.log(ChatColor.RED + (plugin.pInfo) + entityVal + " " + (plugin.itemKill) + ".");
                            plugin.messFix = false;
                            return true;
                        }
                        if (plugin.messFix) {
                            plugin.log(ChatColor.RED + (plugin.pInfo) + (plugin.cantFind) + ".");
                            return true;
                        }
                        return false;
                    } else if (entityVar.equalsIgnoreCase("drop")) {
                        plugin.messageFix = false;
                        plugin.messFix = true;
                        for(Entity entity : (world.getEntities())) {
                            if (entity instanceof Item) {
                                dropVal++;
                                entity.remove();
                                plugin.messageFix = true;
                            }
                            if (entity instanceof CraftItem) {
                                dropVal++;
                                entity.remove();
                                plugin.messageFix = true;
                            }
                        }
                        if (plugin.messageFix) {
                            plugin.log(ChatColor.RED + (plugin.pInfo) + dropVal + " " + (plugin.itemKill) + ".");
                            plugin.messFix = false;
                            return true;
                        }
                        if (plugin.messFix) {
                            plugin.log(ChatColor.RED + (plugin.pInfo) + (plugin.cantFind) + ".");
                            return true;
                        }
                        return false;
                    }
                } else {
                    plugin.log(ChatColor.RED + (plugin.pInfo) + " To use DropClear from console use /dropclear *WORLD* *MOB/DROP*");
                    return true;
                }
            }
        }
        return false;
    }
}
