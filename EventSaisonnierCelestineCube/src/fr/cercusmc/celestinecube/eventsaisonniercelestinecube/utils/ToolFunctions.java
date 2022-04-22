package fr.cercusmc.celestinecube.eventsaisonniercelestinecube.utils;

import org.bukkit.command.CommandSender;

import net.md_5.bungee.api.ChatColor;

public class ToolFunctions {
	
	public static void sendMessage(CommandSender p, String message) {
		p.sendMessage(ChatColor.translateAlternateColorCodes('&', message));
	}

}
