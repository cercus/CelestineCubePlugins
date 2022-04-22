package fr.cercusmc.celestinecube.eventsaisonniercelestinecube.events;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;

import fr.cercusmc.celestinecube.eventsaisonniercelestinecube.Main;

public class DeathEvent implements Listener {
	
	@EventHandler
	public void onDeath(EntityDeathEvent e) {
		if(e.getEntity().getKiller() == null || e.getEntity() == null)
			return;
		if(Main.getConfigEvent().getDrop().isAllMobs()) {
			if(Main.getConfigEvent().getDrop().getChance() <= Math.random()) {
				e.getEntity().getWorld().dropItemNaturally(e.getEntity().getLocation(), Main.getConfigEvent().getDrop().getItems().build());
			}
		} else {
			if(Main.getConfigEvent().getDrop().getMobs_available().contains(e.getEntityType())) {
				if(Main.getConfigEvent().getDrop().getChance() <= Math.random()) {
					e.getEntity().getWorld().dropItemNaturally(e.getEntity().getLocation(), Main.getConfigEvent().getDrop().getItems().build());
				}
			}
		}
	}

}
