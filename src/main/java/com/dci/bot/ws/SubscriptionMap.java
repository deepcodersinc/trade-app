package com.dci.bot.ws;

import java.util.concurrent.ConcurrentHashMap;

public class SubscriptionMap<String, Position> extends ConcurrentHashMap<String, Position> {
	
	private static SubscriptionMap instance;
	
	private SubscriptionMap() {
		
	}
	
	public static SubscriptionMap getInstance() {
		if(instance == null) {
			instance = new SubscriptionMap();
		} 
		return instance;		
	}
}
