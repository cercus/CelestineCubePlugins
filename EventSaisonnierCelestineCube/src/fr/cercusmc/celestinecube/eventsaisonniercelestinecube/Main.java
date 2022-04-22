package fr.cercusmc.celestinecube.eventsaisonniercelestinecube;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import fr.cercusmc.celestinecube.eventsaisonniercelestinecube.commands.EventSaisonnierCommand;
import fr.cercusmc.celestinecube.eventsaisonniercelestinecube.events.ClickEvent;
import fr.cercusmc.celestinecube.eventsaisonniercelestinecube.events.DeathEvent;
import fr.cercusmc.celestinecube.eventsaisonniercelestinecube.models.Config;
import fr.cercusmc.celestinecube.eventsaisonniercelestinecube.models.Drops;
import fr.cercusmc.celestinecube.eventsaisonniercelestinecube.models.Messages;
import fr.cercusmc.celestinecube.eventsaisonniercelestinecube.utils.Villager;
import net.md_5.bungee.api.ChatColor;

public class Main extends JavaPlugin {
	
	private static Main instance;
	private static Config configEvent;
	
	public static Villager villager;
	
	@Override
	public void onEnable() {
		// TODO Auto-generated method stub
		instance = this;
		saveDefaultConfig();
		Bukkit.getPluginManager().registerEvents(new ClickEvent(), this);
		Bukkit.getPluginManager().registerEvents(new DeathEvent(), this);
		configEvent = new Config(new Messages(this), ChatColor.translateAlternateColorCodes('&', this.getConfig().getString("event_name")), null, new Drops());
		villager = new Villager(null);
		getCommand("eventsaisonnier").setExecutor(new EventSaisonnierCommand());
		getCommand("eventsaisonnier").setTabCompleter(new EventSaisonnierCommand());
	}
	
	public static Main getInstance() {
		return instance;
	}
	
	public static Config getConfigEvent() {
		return configEvent;
	}
	
	public static void setConfigEvent(Config configEvent) {
		Main.configEvent = configEvent;
	}
	
	public Villager getVillager() {
		return villager;
	}
	
	public void setVillager(Villager villager) {
		Main.villager = villager;
	}
}
