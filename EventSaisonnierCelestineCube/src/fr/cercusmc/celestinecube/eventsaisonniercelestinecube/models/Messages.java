package fr.cercusmc.celestinecube.eventsaisonniercelestinecube.models;

import fr.cercusmc.celestinecube.eventsaisonniercelestinecube.Main;

public class Messages {
	
	private String notAPplayer;
	private String noPermission;
	private String configReload;
	private String worldUnknown;
	private String coordonneesInvalid;
	private String successSummonVillager;
	
	
	public Messages(Main main) {
		super();
		this.notAPplayer = main.getConfig().getString("messages.not_a_player");
		this.noPermission = main.getConfig().getString("messages.no_permission");;
		this.configReload = main.getConfig().getString("messages.config_reloaded");;
		this.worldUnknown = main.getConfig().getString("messages.world_unknown");;
		this.coordonneesInvalid = main.getConfig().getString("messages.coordonnees_invalid");;
		this.successSummonVillager = main.getConfig().getString("messages.success_summon_villager");;
	}


	public String getNotAPplayer() {
		return notAPplayer;
	}


	public String getNoPermission() {
		return noPermission;
	}


	public String getConfigReload() {
		return configReload;
	}


	public String getWorldUnknown() {
		return worldUnknown;
	}


	public String getCoordonneesInvalid() {
		return coordonneesInvalid;
	}


	public String getSuccessSummonVillager() {
		return successSummonVillager;
	}
	
	
	
	

}
