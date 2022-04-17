package fr.cercusmc.celestinecube.contestminage.events;

import fr.cercusmc.celestinecube.contestminage.Main;
import fr.cercusmc.celestinecube.contestminage.utils.Point;
import fr.cercusmc.celestinecube.contestminage.utils.State;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.block.Block;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;
import java.util.TreeMap;

public class BreakEvent implements Listener {
    @EventHandler
    public void onBreak(BlockBreakEvent e) {
        if(Main.isState(State.IN_CONTEST)) {
            Player p = e.getPlayer();
            ItemStack it = e.getPlayer().getInventory().getItemInMainHand();
            Block b = e.getBlock();
            if(Main.getInstance().getConfig().getBoolean("pioche_event.use")) {
                if(checkPickaxe(it) && checkWorld(p) && !it.getEnchantments().containsKey(Enchantment.SILK_TOUCH)) {
                    if(checkBlock(b)) {
                        if(Main.getPlayers().contains(new Point(p))) {
                            int val = Main.getPlayers().get(getPositionOfPlayer(p)).getValue();
                            Main.getPlayers().set(getPositionOfPlayer(p), new Point(p, val+Main.getPoints().get(b.getType())));
                        } else {
                            Main.getPlayers().add(new Point(p, Main.getPoints().get(b.getType())));
                        }
                    }
                }
            } else {
                if(checkWorld(p)&& checkBlock(b) && !it.getEnchantments().containsKey(Enchantment.SILK_TOUCH)) {
                    if(Main.getPlayers().contains(new Point(p))) {
                        int val = Main.getPlayers().get(getPositionOfPlayer(p)).getValue();
                        Main.getPlayers().set(getPositionOfPlayer(p), new Point(p, val+Main.getPoints().get(b.getType())));
                    } else {
                        Main.getPlayers().add(new Point(p, Main.getPoints().get(b.getType())));
                    }
                }
            }
        }
    }


    private boolean checkBlock(Block b) {

        return Main.getPoints().containsKey(b.getType());

    }

    private boolean checkWorld(Player p) {
        return Main.getInstance().getConfig().getStringList("worlds_contest").contains(p.getWorld().getName());
    }

    private boolean checkPickaxe(ItemStack it) {
        //System.out.println(it.getItemMeta().getDisplayName() + " /// " + Main.getInstance().getConfig().getString("pioche_event.name"));
        if(null == it || null == it.getItemMeta()) return false;
        boolean name = (it.getItemMeta().getDisplayName().equals(Main.getInstance().getConfig().getString("pioche_event.name")));
        boolean material = it.getType().equals(Material.valueOf(Main.getInstance().getConfig().getString("pioche_event.material")));
        boolean lore = it.getItemMeta().getLore().equals(Main.getInstance().getConfig().getStringList("pioche_event.lore"));
        return name && material && lore;
    }

    private int getPositionOfPlayer(Player player) {
        int index = 0;
        for(Point p : Main.getPlayers()) {
            if(p.equals(new Point(player)))
                return index;
            else
                index++;
        }
        return -1;
    }

    private static ItemStack buildPickaxe() {
        ItemStack it = new ItemStack(Material.valueOf(Main.getInstance().getConfig().getString("pioche_event.material")), 1);
        ItemMeta meta = it.getItemMeta();
        meta.setDisplayName(Main.getInstance().getConfig().getString("pioche_event.name"));
        meta.setLore(Main.getInstance().getConfig().getStringList("pioche_event.lore"));
        for(String i : Main.getInstance().getConfig().getStringList("pioche_event.enchantments")) {
            meta.addEnchant(Enchantment.getByKey(NamespacedKey.minecraft(i.split(" ")[0].toLowerCase())), Integer.parseInt(i.split(" ")[1]), true);
        }
        it.setItemMeta(meta);
        return it;
    }

    public static void givePickaxe() {
        if(Main.getInstance().getConfig().getBoolean("pioche_event.use")) {
            for(Player p : Bukkit.getOnlinePlayers()) {
                p.getInventory().addItem(buildPickaxe());
            }
        }
    }

    public static void removePickaxe(TreeMap<Integer, Player> map) {
        if(Main.getInstance().getConfig().getBoolean("pioche_event.use")) {
        	 map.entrySet().forEach(entry -> {
        		 if(Arrays.asList(entry.getValue().getInventory().getContents()).contains(buildPickaxe()))
                     entry.getValue().getInventory().remove(buildPickaxe());
        	 });
            for(Player p : Bukkit.getOnlinePlayers()) {
                if(Arrays.asList(p.getInventory().getContents()).contains(buildPickaxe()))
                    p.getInventory().remove(buildPickaxe());
            }
        }
    }

}
