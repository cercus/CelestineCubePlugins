package fr.cercusmc.celestinecube.contestminage;

import fr.cercusmc.celestinecube.contestminage.commands.ContestMinage;
import fr.cercusmc.celestinecube.contestminage.events.BreakEvent;
import fr.cercusmc.celestinecube.contestminage.placeholder.PlaceHolderMinage;
import fr.cercusmc.celestinecube.contestminage.recompenses.Recompense;
import fr.cercusmc.celestinecube.contestminage.recompenses.RecompenseItem;
import fr.cercusmc.celestinecube.contestminage.utils.Point;
import fr.cercusmc.celestinecube.contestminage.utils.State;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Logger;

public final class Main extends JavaPlugin {

    private static Main instance;

    private static HashMap<Material, Integer> points;

    private static ArrayList<Point> players;

    private static State etat;

    private static int timer;

    private static Economy econ = null;

    private static HashMap<Integer, Recompense> recompenses;



    @Override
    public void onEnable() {
        saveDefaultConfig();
        instance = this;
        players = new ArrayList<>();
        loadPoints();
        loadRecompenses();
        changeState(State.NOT_IN_CONTEST);
        setTimer(getConfig().getInt("time_contest"));

        Bukkit.getPluginManager().registerEvents(new BreakEvent(), this);

        getCommand("contestminage").setExecutor(new ContestMinage());

        getCommand("contestminage").setTabCompleter(new ContestMinage());

        if(!setupEconomy()) {
            Logger.getLogger("ContestMinage").severe("Vault n'est pas installé sur le serveur !");
            getServer().getPluginManager().disablePlugin(Main.getInstance());
            return;
        }
        
        if(Bukkit.getPluginManager().getPlugin("PlaceholderAPI") != null)
        	new PlaceHolderMinage().register();

    }

    public static void loadPoints() {
        points = new HashMap<>();
        for(String i : Main.getInstance().getConfig().getConfigurationSection("points").getKeys(false))
            points.put(Material.valueOf(i), Main.getInstance().getConfig().getInt("points."+i));

    }

    public static void loadRecompenses() {

        recompenses = new HashMap<>();
        for(String i : Main.getInstance().getConfig().getConfigurationSection("recompense").getKeys(false)) {
            List<ItemStack> items = new ArrayList<>();
            for(String j : Main.getInstance().getConfig().getConfigurationSection("recompense."+i+".items").getKeys(false)) {
                ConfigurationSection section = Main.getInstance().getConfig().getConfigurationSection("recompense."+i+".items");
                List<String> enchStrings = Main.getInstance().getConfig().getStringList("recompense."+i+".items."+j+".enchantments");

                HashMap<Enchantment, Integer> enchs = new HashMap<>();
                if(enchStrings.size() > 0) {

                    for(String k : enchStrings) {
                        if(null != NamespacedKey.minecraft(k.split(" ")[0].toLowerCase()))
                            enchs.put(Enchantment.getByKey(NamespacedKey.minecraft(k.split(" ")[0].toLowerCase())), Integer.parseInt(k.split(" ")[1]));
                        else
                            System.out.println("Attention enchantment non valide !!!");
                    }
                }
                RecompenseItem it = new RecompenseItem(section.getString(j+".name"), section.getStringList(j+".lore"), Material.valueOf(section.getString(j+".material")), section.getInt(j+".count"),
                        section.getBoolean(j+".item_enchantable"),
                        enchs);
                ItemStack itRes = it.buildItem();
                items.add(itRes);

            }
            recompenses.put(Integer.valueOf(i), new Recompense(items, Main.getInstance().getConfig().getInt("recompense."+i+".level_xp"), Main.getInstance().getConfig().getInt("recompense."+i+".money")));
        }
    }

    public boolean setupEconomy() {
        //System.out.println(getServer().getPluginManager().getPlugin("Vault"));
        if(null == getServer().getPluginManager().getPlugin("Vault")) return false;
        RegisteredServiceProvider<Economy> rsp = getServer().getServicesManager().getRegistration(Economy.class);
        System.out.println(rsp);
        if(null == rsp)
            return false;
        econ = rsp.getProvider();
        return null != econ;
    }

    public static Main getInstance() {
        return instance;
    }

    public static HashMap<Material, Integer> getPoints() {
        return points;
    }

    public static ArrayList<Point> getPlayers() {
        return players;
    }

    public static void changeState(State state) {
        etat = state;
    }

    public static boolean isState(State state) {
        return etat == state;
    }

    public static void setTimer(int seconds) {
        timer = seconds;
    }

    public static int getTimer() {
        return timer;
    }

    public static Economy getEcon() {
        return econ;
    }

    public static HashMap<Integer, Recompense> getRecompenses() {
        return recompenses;
    }
}
