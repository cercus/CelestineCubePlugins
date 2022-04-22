package fr.cercusmc.celestinecube.eventsaisonniercelestinecube.models;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.EntityType;

import fr.cercusmc.celestinecube.eventsaisonniercelestinecube.Main;
import fr.cercusmc.celestinecube.eventsaisonniercelestinecube.utils.Enchantment;
import fr.cercusmc.celestinecube.eventsaisonniercelestinecube.utils.Items;

public class Drops {
	
	private boolean allMobs = true;
	private List<EntityType> mobs_available;
	private double chance;
	private Items items;
	public Drops() {
		this.allMobs = Main.getInstance().getConfig().getBoolean("drops.all_mobs");
		this.mobs_available = transformListStringToListEntity();
		this.chance = Main.getInstance().getConfig().getDouble("drops.chance");
		this.items = buildItem();
	}
	private Items buildItem() {
		ConfigurationSection section = Main.getInstance().getConfig().getConfigurationSection("drops.item");
		List<Enchantment> ench = new ArrayList<>();
		for(String e : section.getStringList("drops.item.enchantments"))
			ench.add(new Enchantment(e.split(" ")[0], Integer.parseInt(e.split(" ")[1])));
		
		Items it = new Items(Material.valueOf(section.getString("drops.material")), section.getInt("drops.count"),
				section.getString("drops.name"), 
				section.getStringList("drops.lore"), 
				section.getBoolean("drops.unbreakable"), 
				ench, 
				section.getBoolean("drops.hide_enchantment"));
		
		return it;
	}
	private List<EntityType> transformListStringToListEntity() {
		List<EntityType> res = new ArrayList<>();
		if(this.allMobs)
			return res;
		for(String i : Main.getInstance().getConfig().getStringList("drops.mobs_available"))
			try {
				res.add(EntityType.valueOf(i));
			} catch(IllegalArgumentException e) {
				continue;
			}
		return res;
	}
	public boolean isAllMobs() {
		return allMobs;
	}
	public List<EntityType> getMobs_available() {
		return mobs_available;
	}
	public double getChance() {
		return chance;
	}
	public Items getItems() {
		return items;
	}
	
	
	
	

}
