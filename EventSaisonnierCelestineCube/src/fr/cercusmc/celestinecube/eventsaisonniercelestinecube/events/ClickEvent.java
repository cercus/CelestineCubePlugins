package fr.cercusmc.celestinecube.eventsaisonniercelestinecube.events;

import org.bukkit.entity.Villager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractAtEntityEvent;

import fr.cercusmc.celestinecube.eventsaisonniercelestinecube.Main;
import net.md_5.bungee.api.ChatColor;


public class ClickEvent implements Listener {
	
	@EventHandler
	public void onClick(PlayerInteractAtEntityEvent e) {
		if(e.getRightClicked() instanceof Villager) {
			Villager villager = (Villager) e.getRightClicked();
			if(villager.getName().equalsIgnoreCase(ChatColor.translateAlternateColorCodes('&', Main.getInstance().getConfig().getString("villager.name")))) {
				Main.villager.openMerchant(e.getPlayer());
			}
		}
	}
}
