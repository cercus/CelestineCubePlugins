package fr.cercusmc.celestinecube.eventsaisonniercelestinecube.models;

import fr.cercusmc.celestinecube.eventsaisonniercelestinecube.utils.Villager;

public class Config {
	
	private Messages message;
	private String eventName;
	private Villager villager;
	private Drops drop;
	
	public Config(Messages message, String eventName, Villager villager, Drops drop) {
		super();
		this.message = message;
		this.eventName = eventName;
		this.villager = villager;
		this.drop = drop;
	}

	public Messages getMessage() {
		return message;
	}

	public String getEventName() {
		return eventName;
	}

	public Villager getVillager() {
		return villager;
	}
	
	public Drops getDrop() {
		return drop;
	}
	
	
	
	

}
