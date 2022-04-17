package fr.cercusmc.celestinecube.contestminage.utils;

import fr.cercusmc.celestinecube.contestminage.Main;
import fr.cercusmc.celestinecube.contestminage.events.BreakEvent;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.Collections;
import java.util.TreeMap;

public class TimerTask extends BukkitRunnable {

    private int index = 1;

    @Override
    public void run() {
        if(Main.getTimer() == 0) {
            Bukkit.broadcastMessage(Main.getInstance().getConfig().getString("messages.event_finished"));
            Main.changeState(State.NOT_IN_CONTEST);
            cancel();

            /**
             * Comptabilisation des points et distribution des récompenses
             */
            Bukkit.broadcastMessage(Main.getInstance().getConfig().getString("messages.display_result_header"));
            TreeMap<Integer, Player> map = new TreeMap<>(Collections.reverseOrder());
            for(Point p : Main.getPlayers()) {
                map.put(p.getValue(), p.getP());
            }
            map.entrySet().forEach(entry -> {
                if(index <= 5) {
                    Bukkit.broadcastMessage("§e"+index+". " + entry.getValue().getName() +" : §f" + entry.getKey());
                    Main.getRecompenses().get(index).giveRecompense(entry.getValue());
                    entry.getValue().playSound(entry.getValue().getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1.0f, 1.0f);
                    Main.getInstance().getConfig().set("history."+index+".name", entry.getValue().getDisplayName());
                    Main.getInstance().getConfig().set("history."+index+".count", entry.getKey());
                    Main.getInstance().saveConfig();
                    index++;
                }
            });
            index = 1;

            Main.getPlayers().clear();
            BreakEvent.removePickaxe();

        }
        Main.setTimer(Main.getTimer()-1);
    }
}
