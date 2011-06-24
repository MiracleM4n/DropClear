package net.D3GN.MiracleM4n.DropClear;


import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;

public class DCCommandExecutor implements CommandExecutor {
	
	private final DropClear plugin;
	
    public DCCommandExecutor(DropClear callbackPlugin) {
        plugin = callbackPlugin;
    }
    public Integer noTeleportRadius = 0;
    
    public boolean onCommand (CommandSender sender, Command command, String label, String[] args) {
    	if (!(sender instanceof Player)) {
			return true;
		}
    	Player player = ((Player) sender);
    	if ((label.equalsIgnoreCase("dropclear")) || (label.equalsIgnoreCase("dc"))) {
			if(args.length == 0) {
				return false;
			}
			if(args.length > 0) {
				int radius;
				try {
				    radius = new Integer(args[0]);
				} catch (NumberFormatException e) {
					player.sendMessage(ChatColor.RED + (plugin.pInfo) + (plugin.notNumber) + ".");
				    return true;
				}
				if ((DropClear.Permissions == null && player.isOp()) || 
						(DropClear.Permissions != null && DropClear.Permissions.has(player, "dropclear.killadmin"))) {
					if (radius < noTeleportRadius) {
						player.sendMessage(ChatColor.RED + (plugin.pInfo) + (plugin.negativeInterger) + ".");
						return true; 
					}
					if(radius <= plugin.maxAdminKillRadius) {
						plugin.messageFix = true;
						plugin.messFix = true;
						for(Entity entity : (player.getNearbyEntities(radius,radius,radius))) {
							if ((entity instanceof Entity) && !(entity instanceof LivingEntity)) {
								entity.remove();
							} else {
								plugin.messageFix = false;
							}
							if (plugin.messageFix) {
								player.sendMessage(ChatColor.RED + (plugin.pInfo) + (plugin.itemKill) + ".");
								plugin.messageFix = false;
								plugin.messFix = false;
							}
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
				} else if ((DropClear.Permissions == null && (player.isOp() == false)) || 
						(DropClear.Permissions != null && DropClear.Permissions.has(player, "dropclear.kill"))) {
					if (radius < noTeleportRadius) {
						player.sendMessage(ChatColor.RED + (plugin.pInfo) + (plugin.negativeInterger) + ".");
						return true; 
					}
					if(radius <= plugin.maxKillRadius) {
						plugin.messageFix = true;
						plugin.messFix = true;
						for(Entity entity : (player.getNearbyEntities(radius,radius,radius))) {
							if ((entity instanceof Entity) && !(entity instanceof LivingEntity)) {
								entity.remove();
							} else {
								plugin.messageFix = false;
							}
							if (plugin.messageFix) {
								player.sendMessage(ChatColor.RED + (plugin.pInfo) + (plugin.itemKill) + ".");
								plugin.messageFix = false;
								plugin.messFix = false;
							}
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
			} else {
				return true;
			}
		}
    	return true; 
	}
}
