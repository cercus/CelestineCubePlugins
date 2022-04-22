package fr.cercusmc.celestinecube.eventsaisonniercelestinecube.models;

import fr.cercusmc.celestinecube.eventsaisonniercelestinecube.utils.Items;

public class Trade {
	
	private Items sellItem;
	private Items buyItem1;
	private Items buyItem2;
	
	public Trade(Items sellItem, Items buyItem1, Items buyItem2) {
		super();
		this.sellItem = sellItem;
		this.buyItem1 = buyItem1;
		this.buyItem2 = buyItem2;
	}

	public Items getSellItem() {
		return sellItem;
	}

	public Items getBuyItem1() {
		return buyItem1;
	}

	public Items getBuyItem2() {
		return buyItem2;
	}
	
	
	
	

}
