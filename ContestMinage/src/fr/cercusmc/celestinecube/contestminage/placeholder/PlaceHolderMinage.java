package fr.cercusmc.celestinecube.contestminage.placeholder;

import org.bukkit.OfflinePlayer;

import fr.cercusmc.celestinecube.contestminage.Main;
import me.clip.placeholderapi.expansion.PlaceholderExpansion;

public class PlaceHolderMinage extends PlaceholderExpansion {

	@Override
	public String getAuthor() {
		// TODO Auto-generated method stub
		return "CercusMC";
	}

	@Override
	public String getIdentifier() {
		// TODO Auto-generated method stub
		return "contestminage";
	}

	@Override
	public String getVersion() {
		// TODO Auto-generated method stub
		return "1.0.0";
	}
	
	@Override
	public String onRequest(OfflinePlayer player, String params) {
		
		if(params.equalsIgnoreCase("first")) {
			return Main.getInstance().getConfig().getInt("history.1.count") == 0 ? "" : Main.getInstance().getConfig().getString("history.1.name") + " - §e" +Main.getInstance().getConfig().getInt("history.1.count"); 
		}
		if(params.equalsIgnoreCase("second")) {
			return Main.getInstance().getConfig().getInt("history.2.count") == 0 ? "" : Main.getInstance().getConfig().getString("history.2.name") + " - §e" +Main.getInstance().getConfig().getInt("history.2.count"); 
		}
		if(params.equalsIgnoreCase("third")) {
			return Main.getInstance().getConfig().getInt("history.3.count") == 0 ? "" : Main.getInstance().getConfig().getString("history.3.name") + " - §e" +Main.getInstance().getConfig().getInt("history.3.count"); 
		}
		if(params.equalsIgnoreCase("fourth")) {
			return Main.getInstance().getConfig().getInt("history.4.count") == 0 ? "" : Main.getInstance().getConfig().getString("history.4.name") + " - §e" +Main.getInstance().getConfig().getInt("history.4.count"); 
		}
		if(params.equalsIgnoreCase("fifth")) {
			return Main.getInstance().getConfig().getInt("history.5.count") == 0 ? "" : Main.getInstance().getConfig().getString("history.5.name") + " - §e" +Main.getInstance().getConfig().getInt("history.5.count"); 
		}
		
		return null;
	}

}
