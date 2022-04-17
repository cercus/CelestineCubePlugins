package fr.cercusmc.celestinecube.contestminage.recompenses;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.HashMap;
import java.util.List;

public class RecompenseItem {
    private String name;
    private List<String> lore;
    private Material material;
    private int count;
    private boolean item_enchantable;
    private HashMap<Enchantment, Integer> enchantments;


    public RecompenseItem(String name, List<String> lore, Material material, int count, boolean item_enchantable,
                          HashMap<Enchantment, Integer> enchantments) {
        this.name = name;
        this.lore = lore;
        this.material = material;
        this.count = count;
        this.item_enchantable = item_enchantable;
        this.enchantments = enchantments;
    }


    public String getName() {
        return name;
    }

    public List<String> getLore() {
        return lore;
    }

    public Material getMaterial() {
        return material;
    }

    public int getCount() {
        return count;
    }

    public boolean isItem_enchantable() {
        return item_enchantable;
    }

    public HashMap<Enchantment, Integer> getEnchantments() {
        return enchantments;
    }

    public ItemStack buildItem() {
        ItemStack it = new ItemStack(this.material, this.count);
        ItemMeta meta = it.getItemMeta();
        meta.setDisplayName(this.name);
        meta.setLore(this.lore);
        if(this.item_enchantable) {
            this.enchantments.entrySet().forEach(entry -> {
                meta.addEnchant(entry.getKey(), entry.getValue(), true);
            });
        }
        it.setItemMeta(meta);
        return it;
    }

    @Override
    public String toString() {
        // TODO Auto-generated method stub
        return "name : " + this.name + "\n lore : " + this.lore + "\n enchantement : " + this.enchantments + "\n has_enchant : " + this.item_enchantable;
    }
}
