package fr.cercusmc.celestinecube.eventsaisonniercelestinecube.commands;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.bukkit.entity.Villager.Profession;
import org.bukkit.util.StringUtil;

import fr.cercusmc.celestinecube.eventsaisonniercelestinecube.Main;
import fr.cercusmc.celestinecube.eventsaisonniercelestinecube.models.Config;
import fr.cercusmc.celestinecube.eventsaisonniercelestinecube.models.Drops;
import fr.cercusmc.celestinecube.eventsaisonniercelestinecube.models.Messages;
import fr.cercusmc.celestinecube.eventsaisonniercelestinecube.utils.ToolFunctions;
import fr.cercusmc.celestinecube.eventsaisonniercelestinecube.utils.Villager;
import net.md_5.bungee.api.ChatColor;

public class EventSaisonnierCommand implements CommandExecutor, TabCompleter {
	
	private static int MIN_XZ = -29999984;
	private static int MAX_XZ = 29999984;

	@Override
	public List<String> onTabComplete(CommandSender sender, Command cmd, String label, String[] args) {
		List<String> list = new ArrayList<>();
		if(args.length == 1) {	
			list = StringUtil.copyPartialMatches(args[0], Arrays.asList("reload", "spawn"), list);
		} else if(args.length == 2 && args[0].equalsIgnoreCase("spawn")) {
			list = StringUtil.copyPartialMatches(args[1], convertWorldsToString(), list);
		} else if(args.length == 3 && args[0].equalsIgnoreCase("spawn")) {
			if(sender instanceof Player) {
				Player p = (Player) sender;
				list = StringUtil.copyPartialMatches(args[2], Arrays.asList(Double.toString(p.getLocation().getX())), list);
			}
				
		} else if(args.length == 4 && args[0].equalsIgnoreCase("spawn")) {
			if(sender instanceof Player) {
				Player p = (Player) sender;
				list = StringUtil.copyPartialMatches(args[3], Arrays.asList(Double.toString(p.getLocation().getY())), list);
			}
				
		} else if(args.length == 5 && args[0].equalsIgnoreCase("spawn")) {
			if(sender instanceof Player) {
				Player p = (Player) sender;
				list = StringUtil.copyPartialMatches(args[4], Arrays.asList(Double.toString(p.getLocation().getZ())), list);
			}
				
		}
			
		return list;
	}
	
	public List<String> convertWorldsToString() {
		List<String> res = new ArrayList<>();
		for(World w : Bukkit.getWorlds())
			res.add(w.getName());
		return res;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		// /eventsaisonnier spawn <world> <x> <y> <z> 
		// /eventsaisonnier reload
		if(!(sender instanceof Player))
			ToolFunctions.sendMessage(sender, Main.getInstance().getConfig().getString("messages.not_a_player"));
		
		Player p = (Player) sender;
		
		if(!p.isOp())
			ToolFunctions.sendMessage(p, Main.getInstance().getConfig().getString("messages.no_permission"));
		
		if(args.length == 1) {
			if(args[0].equalsIgnoreCase("reload")) {
				Main.getInstance().reloadConfig();
				Main.villager = new Villager(null);
				Main.setConfigEvent(new Config(new Messages(Main.getInstance()), ChatColor.translateAlternateColorCodes('&', Main.getInstance().getConfig().getString("event_name")), null, new Drops()));
				ToolFunctions.sendMessage(p, Main.getInstance().getConfig().getString("messages.config_reloaded"));
			}
		} else if(args.length == 5) {
			if(args[0].equalsIgnoreCase("spawn")) {
				
				if(!containsWorld(Bukkit.getWorld(args[1])))
					ToolFunctions.sendMessage(p, Main.getInstance().getConfig().getString("messages.world_unknown"));
				if(!coordsValid(Bukkit.getWorld(args[1]), args[2], args[3], args[4]))
					ToolFunctions.sendMessage(p, Main.getInstance().getConfig().getString("messages.coordonnees_invalid"));
				
				Villager villager = new Villager(new Location(Bukkit.getWorld(args[1]), Double.parseDouble(args[2]), Double.parseDouble(args[3]), Double.parseDouble(args[4])));
				Main.villager = villager;
				villager.spawnVillager();
				System.out.println(villager);
				ToolFunctions.sendMessage(p, Main.getInstance().getConfig().getString("messages.success_summon_villager"));
				
			}
		}
		return false;
	}
	
	
	public boolean containsWorld(World world) {
		if(world == null)
			return false;
		for(World w : Bukkit.getWorlds())
			if(world.getName() == w.getName())
				return true;
		
		return false;
	}
	
	public boolean coordsValid(World world, String x, String y, String z) {
		try {
			double xD = Double.parseDouble(x);
			double yD = Double.parseDouble(y);
			double zD = Double.parseDouble(z);
			return (yD > world.getMinHeight() && yD < world.getMaxHeight()) &&
					(xD > MIN_XZ && xD < MAX_XZ) && (zD > MIN_XZ && zD < MAX_XZ) ;
		} catch(NullPointerException | NumberFormatException e) {
			return false;
		}
	}

}
