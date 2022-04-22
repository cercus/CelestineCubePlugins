package fr.cercusmc.celestinecube.eventsaisonniercelestinecube.utils;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nullable;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.entity.Villager.Profession;
import org.bukkit.inventory.Merchant;
import org.bukkit.inventory.MerchantRecipe;

import fr.cercusmc.celestinecube.eventsaisonniercelestinecube.Main;
import fr.cercusmc.celestinecube.eventsaisonniercelestinecube.models.Trade;
import net.md_5.bungee.api.ChatColor;

public class Villager {


	private String name;
	private Profession profession;
	private boolean invulnerable;
	private Location location;
	private org.bukkit.entity.Villager villager;
	private String titleMenu;
	private List<Trade> trades;
	
	public Villager(Location location) {
		this.name = ChatColor.translateAlternateColorCodes('&', Main.getInstance().getConfig().getString("villager.name"));
		this.profession = Profession.valueOf(Main.getInstance().getConfig().getString("villager.profession"));
		this.invulnerable = Main.getInstance().getConfig().getBoolean("villager.invulnerable");
		this.location = location;
		this.trades = buildTrades();
		this.titleMenu = Main.getInstance().getConfig().getString("villager.title_menu");
	}
	
	private List<Trade> buildTrades() {
		List<Trade> trades = new ArrayList<>();
		for(String i : Main.getInstance().getConfig().getConfigurationSection("villager.trades").getKeys(false)) {
			ConfigurationSection section = Main.getInstance().getConfig().getConfigurationSection("villager.trades."+i);
			List<Enchantment> enchSellItem = new ArrayList<>();
			for(String ench : section.getStringList("sellItem.enchantments"))
				enchSellItem.add(new Enchantment(ench.split(" ")[0], Integer.parseInt(ench.split(" ")[1])));
			
			Items sellItem = new Items(Material.valueOf(section.getString("sellItem.material")), section.getInt("sellItem.count"),
					section.getString("sellItem.name"), 
					section.getStringList("sellItem.lore"), 
					section.getBoolean("sellItem.unbreakable"), 
					enchSellItem, 
					section.getBoolean("sellItem.hide_enchantment"));
			
			List<Enchantment> enchBuy1Item = new ArrayList<>();
			for(String ench : section.getStringList("buyItem1.enchantments"))
				enchBuy1Item.add(new Enchantment(ench.split(" ")[0], Integer.parseInt(ench.split(" ")[1])));
			
			Items buyItem1 = new Items(Material.valueOf(section.getString("buyItem1.material")),  section.getInt("buyItem1.count"),
					section.getString("buyItem1.name"), 
					section.getStringList("buyItem1.lore"), 
					section.getBoolean("buyItem1.unbreakable"), 
					enchBuy1Item, 
					section.getBoolean("buyItem1.hide_enchantment"));
			Items buyItem2;
			if(section.getConfigurationSection("buyItem2") == null) {
				buyItem2 = null;
			} else {
				List<Enchantment> enchBuy2Item = new ArrayList<>();
				for(String ench : section.getStringList("buyItem2.enchantments"))
					enchBuy2Item.add(new Enchantment(ench.split(" ")[0], Integer.parseInt(ench.split(" ")[1])));
				
				buyItem2 = new Items(Material.valueOf(section.getString("buyItem2.material")), section.getInt("buyItem2.count"), 
						section.getString("buyItem2.name"), 
						section.getStringList("buyItem2.lore"), 
						section.getBoolean("buyItem2.unbreakable"), 
						enchBuy2Item, 
						section.getBoolean("buyItem2.hide_enchantment"));
				System.out.println("buyItem2 : " + buyItem2);
			}
			trades.add(new Trade(sellItem, buyItem1, buyItem2));
		}
		return trades;
	}

	public org.bukkit.entity.Villager spawnVillager(@Nullable Location location, @Nullable String name, @Nullable Profession profession) {
		if(location != null)
			this.location = location;
		if(name != null) 
			this.name = name;
		if(profession != null)
			this.profession = profession;
		this.villager = spawnVillager();
		
		return this.villager;
	}
	
	
	public org.bukkit.entity.Villager spawnVillager() {
		this.villager = (org.bukkit.entity.Villager) this.location.getWorld().spawnEntity(location, EntityType.VILLAGER);
		villager.setCustomName(ChatColor.translateAlternateColorCodes('&', this.name));
		villager.setCustomNameVisible(true);
		villager.setInvulnerable(invulnerable);
		villager.setProfession(profession);
		return villager;
	}
	
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return "Name : " + this.name + "\nProfession : " + this.profession.name() + "\nLocation : " + this.location;
	}
	

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
		this.villager.remove();
		this.villager = spawnVillager(null, this.name, null);
	}

	public Profession getProfession() {
		return profession;
	}

	public void setProfession(Profession profession) {
		this.villager.remove();
		this.profession = profession;
		this.villager = spawnVillager(null, null, this.profession);
		
	}

	public boolean isInvulnerable() {
		return invulnerable;
	}

	public void setInvulnerable(boolean invulnerable) {
		this.invulnerable = invulnerable;
	}

	public Location getLocation() {
		return location;
	}

	public void setLocation(Location location) {
		this.villager.remove();
		this.location = location;
		this.villager = spawnVillager(this.location, null, null);
	}
	
	public MerchantRecipe buildRecipe(Items sellItem, Items buyItem1, Items buyItem2) {
		MerchantRecipe recipe = new MerchantRecipe(sellItem.build(), 99999);
		if(buyItem1 != null)
			recipe.addIngredient(buyItem1.build());
		if(buyItem2 != null)
			recipe.addIngredient(buyItem2.build());
		return recipe;
	}
	
	public void openMerchant(Player player) {
		Merchant merchant = Bukkit.createMerchant(ChatColor.translateAlternateColorCodes('&', this.titleMenu));
		List<MerchantRecipe> recipes = new ArrayList<>();
		for(Trade t : this.trades) {
			recipes.add(buildRecipe(t.getSellItem(), t.getBuyItem1(), t.getBuyItem2()));
		}
		merchant.setRecipes(recipes);
		player.openMerchant(merchant, true);
	}
	
	public List<Trade> getTrades() {
		return trades;
	}
	
	public String getTitleMenu() {
		return titleMenu;
	}
}
