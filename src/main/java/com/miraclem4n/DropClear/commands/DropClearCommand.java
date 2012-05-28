package com.miraclem4n.dropclear.commands;

import com.miraclem4n.dropclear.DropClear;
import com.miraclem4n.dropclear.types.ConfigType;
import com.miraclem4n.dropclear.util.MessageUtil;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.craftbukkit.entity.CraftItem;
import org.bukkit.entity.*;

public class DropClearCommand implements CommandExecutor {
    DropClear plugin;

    public DropClearCommand(DropClear instance) {
        plugin = instance;
    }

    public Integer noTeleportRadius = 0;

    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        String commandName = command.getName();

        if (sender instanceof Player) {
            Player player = ((Player) sender);

            if (commandName.equalsIgnoreCase("dropclear")) {
                if (args.length == 2) {
                    Integer entityVal = 0;
                    String entityVar = args[0];
                    Integer radius;

                    try {
                        radius = new Integer(args[1]);
                    } catch (NumberFormatException e) {
                        MessageUtil.sendMessage(player, ConfigType.MESSAGE_NOT_NUMBER.getObject().toString());
                        return true;
                    }

                    if (!entityVar.equalsIgnoreCase("mob") ||
                            !entityVar.equalsIgnoreCase("drop"))
                        return false;

                    if (!player.hasPermission("dropclear.killadmin") ||
                            !player.hasPermission("dropclear.kill")) {
                        MessageUtil.sendMessage(player, ConfigType.MESSAGE_NO_PERM.getObject().toString());
                        return true;
                    }

                    Integer killRadius = ConfigType.INT_MAX_KILL.getObject().toInteger();

                    if (player.hasPermission("dropclear.killadmin"))
                        killRadius = ConfigType.INT_MAX_ADMIN_KILL.getObject().toInteger();

                    if (radius < noTeleportRadius) {
                        MessageUtil.sendMessage(player, ConfigType.MESSAGE_NEGATIVE_VALUE.getObject().toString());
                        return true;
                    } else if (radius <= killRadius) {
                        Boolean hasFound = false;

                        if (entityVar.equalsIgnoreCase("mob")) {
                            for (Entity entity : player.getNearbyEntities(radius,radius,radius)) {
                                if (entity instanceof Player || entity instanceof Wolf)
                                    continue;

                                if (entity instanceof LivingEntity) {
                                    entityVal++;
                                    entity.remove();
                                    hasFound = true;
                                }
                            }
                        } else if (entityVar.equalsIgnoreCase("drop")) {
                            for (Entity entity : player.getNearbyEntities(radius,radius,radius)) {
                                if (entity instanceof Item) {
                                    entityVal++;
                                    entity.remove();
                                    hasFound = true;
                                } else if (entity instanceof CraftItem) {
                                    entityVal++;
                                    entity.remove();
                                    hasFound = true;
                                }
                            }
                        }

                        if (hasFound) {
                            MessageUtil.sendMessage(player, ConfigType.MESSAGE_ITEMS_CLEARED.getObject().toString().replace("%items%", entityVal.toString()));
                            return true;
                        } else {
                            MessageUtil.sendMessage(player, ConfigType.MESSAGE_CANT_FIND.getObject().toString());
                            return true;
                        }
                    } else if (radius > killRadius) {
                        MessageUtil.sendMessage(player, ConfigType.MESSAGE_TOO_FAR.getObject().toString());
                        return true;
                    }
                } else {
                    if (commandName.equalsIgnoreCase("dropclear")) {
                        if(args.length == 2) {
                            Integer entityVal = 0;
                            String entityVar = args[0];
                            World world = plugin.getServer().getWorld(args[0]);

                            if (world == null) {
                                MessageUtil.sendMessage(Bukkit.getConsoleSender(), " World " + args[0] + " not found.");
                                return true;
                            }

                            if (!entityVar.equalsIgnoreCase("mob") ||
                                    !entityVar.equalsIgnoreCase("drop"))
                                return false;


                            Boolean hasFound = false;
                            if (entityVar.equalsIgnoreCase("mob")) {
                                for(Entity entity : (world.getEntities())) {
                                    if (entity instanceof Player || entity instanceof Wolf)
                                        continue;

                                    if (entity instanceof LivingEntity) {
                                        entityVal++;
                                        entity.remove();
                                        hasFound = true;
                                    }
                                }
                            } else if (entityVar.equalsIgnoreCase("drop")) {
                                for (Entity entity : world.getEntities()) {
                                    if (entity instanceof Item) {
                                        entityVal++;
                                        entity.remove();
                                        hasFound = true;
                                    } else if (entity instanceof CraftItem) {
                                        entityVal++;
                                        entity.remove();
                                        hasFound = true;
                                    }
                                }
                            }

                            if (hasFound) {
                                MessageUtil.sendMessage(player, ConfigType.MESSAGE_ITEMS_CLEARED.getObject().toString().replace("%items%", entityVal.toString()));
                                return true;
                            } else {
                                MessageUtil.sendMessage(Bukkit.getConsoleSender(), ConfigType.MESSAGE_CANT_FIND.getObject().toString());
                                return true;
                            }
                        }
                    } else {
                        MessageUtil.sendMessage(Bukkit.getConsoleSender(), " To use DropClear from console use /dropclear *WORLD* *MOB/DROP*");
                        return true;
                    }
                }
            }
        }

        return false;
    }
}
