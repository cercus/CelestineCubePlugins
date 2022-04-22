package fr.cercusmc.celestinecube.eventsaisonniercelestinecube.utils;


import java.util.ArrayList;
import java.util.List;

import org.bukkit.Material;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import net.md_5.bungee.api.ChatColor;

public class Items {
	
	private Material material;
	private String name;
	private int count;
	private List<String> lore;
	private boolean unbreakable;
	private List<Enchantment> enchantments;
	private boolean hideEnchantment;
	
	
	public Items(Material material, int count, String name, List<String> lore, boolean unbreakable, List<Enchantment> enchantments,
			boolean hideEnchantment) {
		this.material = material;
		this.count = count;
		this.name = ChatColor.translateAlternateColorCodes('&', name);
		this.lore = lore;
		this.unbreakable = unbreakable;
		this.enchantments = enchantments;
		this.hideEnchantment = hideEnchantment;
	}
	
	
	public ItemStack build() {
		ItemStack it = new ItemStack(this.material, this.count);
		ItemMeta meta = it.getItemMeta();
		if(this.name != "")
			meta.setDisplayName(ChatColor.translateAlternateColorCodes('&', this.name));
		if(!this.lore.isEmpty())
			meta.setLore(translateColorLore());
		if(unbreakable)
			meta.setUnbreakable(true);
		for(Enchantment ench : this.enchantments)
			if(ench.getEnchantment() != null && ench.getPower() > 0)
				meta.addEnchant(ench.getEnchantment(), ench.getPower(), true);
		
		if(!this.enchantments.isEmpty())
			if(hideEnchantment)
				meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
		
		it.setItemMeta(meta);
		return it;
	}
	
	private List<String> translateColorLore() {
		List<String> res = new ArrayList<>();
		for(String i : this.lore) {
			res.add(ChatColor.translateAlternateColorCodes('&', i));
		}
		return res;
	}
	
	@Override
	public String toString() {
		return "Material : " +this.material.name() + "\nCount : " 
				+ this.count + "\nName : " + this.name+"\nLore : " 
				+ this.lore + "\nEnchantements : " + this.enchantments.toString();
	
	}
	
	
	public Material getMaterial() {
		return material;
	}
	public String getName() {
		return name;
	}
	public List<String> getLore() {
		return lore;
	}
	public boolean isUnbreakable() {
		return unbreakable;
	}
	public List<Enchantment> getEnchantments() {
		return enchantments;
	}
	public boolean isHideEnchantment() {
		return hideEnchantment;
	}
	
	public int getCount() {
		return count;
	}
	
	

}
