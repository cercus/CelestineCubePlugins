package fr.cercusmc.celestinecube.eventsaisonniercelestinecube.utils;

import org.bukkit.NamespacedKey;

public class Enchantment {
	
	private org.bukkit.enchantments.Enchantment enchantment;
	private int power;
	
	public Enchantment(String enchantment, int power) {
		if(enchantmentValid(enchantment)) {
			this.enchantment = org.bukkit.enchantments.Enchantment.getByKey(NamespacedKey.minecraft(enchantment.toLowerCase())) ;
			this.power = power;
		} else {
			this.enchantment = null;
			this.power = 0;
		
		}
	}
	
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return "{"+this.enchantment.getName() + ":"+this.power+"}";
	}
	
	public boolean enchantmentValid(String enchantments) {
		return NamespacedKey.minecraft(enchantments.toLowerCase()) != null;
	}
	
	public int getPower() {
		return power;
	}
	
	public org.bukkit.enchantments.Enchantment getEnchantment() {
		return enchantment;
	}

}
