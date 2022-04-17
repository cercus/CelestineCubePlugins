package fr.cercusmc.celestinecube.contestminage.recompenses;

import fr.cercusmc.celestinecube.contestminage.Main;
import net.milkbowl.vault.economy.EconomyResponse;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.List;

public class Recompense {

    private List<ItemStack> items;
    private int levels;
    private int money;


    public Recompense(List<ItemStack> items, int levels, int money) {
        this.items = items;
        this.levels = levels;
        this.money = money;
    }

    public List<ItemStack> getItems() {
        return items;
    }

    public int getLevels() {
        return levels;
    }

    public int getMoney() {
        return money;
    }

    public void giveRecompense(Player p) {
        for(ItemStack item : this.items) {
            p.getInventory().addItem(item);
            //System.out.println(item.getType() + "/" + item.getEnchantments());
        }

        EconomyResponse r = Main.getEcon().depositPlayer(p, this.money);
        if(!r.transactionSuccess())
            p.sendMessage("§cUne erreur est apparu !" + r.errorMessage);

        p.setLevel(p.getLevel() + this.levels);
    }
}
