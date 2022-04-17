package fr.cercusmc.celestinecube.contestminage.commands;

import fr.cercusmc.celestinecube.contestminage.Main;
import fr.cercusmc.celestinecube.contestminage.events.BreakEvent;
import fr.cercusmc.celestinecube.contestminage.utils.State;
import fr.cercusmc.celestinecube.contestminage.utils.TimerTask;
import org.bukkit.Bukkit;
import org.bukkit.command.*;
import org.bukkit.entity.Player;
import org.bukkit.util.StringUtil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ContestMinage implements CommandExecutor, TabCompleter {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if(sender instanceof Player || sender instanceof ConsoleCommandSender) {
            //Player p = (Player) sender;
        	if(sender.isOp()) {
	            if(args.length == 1) {
	                if(args[0].equalsIgnoreCase("reload"))
	                    subCommandReload(sender);
	                else if(args[0].equalsIgnoreCase("start"))
	                    subCommandStart();
	                else if(args[0].equalsIgnoreCase("testplaceholder"))
	                	sender.sendMessage("%contestminage_first%");
	            }
        	}
        }
        return false;
    }

	private void subCommandReload(CommandSender sender) {
		Main.getInstance().reloadConfig();
		Main.loadPoints();
		Main.loadRecompenses();
		sender.sendMessage("§eLe plugin ContestMinage a bien été rechargé !");
	}

	private void subCommandStart() {
		Main.changeState(State.IN_CONTEST);
		Main.setTimer(Main.getInstance().getConfig().getInt("time_contest"));
		Bukkit.broadcastMessage(Main.getInstance().getConfig().getString("messages.event_begin").replaceAll("%time%", Integer.toString(Main.getInstance().getConfig().getInt("time_contest"))));
		BreakEvent.givePickaxe();
		TimerTask task = new TimerTask();
		task.runTaskTimer(Main.getInstance(), 0, 20);
	}

    @Override
    public List<String> onTabComplete(CommandSender sender, Command cmd, String label, String[] args) {
        ArrayList<String> list = new ArrayList<>();
        if(args.length == 1) {
            list = StringUtil.copyPartialMatches(args[0], Arrays.asList("reload", "start", "testplaceholder"), list);
        }
        return list;
    }
}
