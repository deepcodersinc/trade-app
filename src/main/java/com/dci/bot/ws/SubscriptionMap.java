package com.dci.bot.ws;

import java.util.concurrent.ConcurrentHashMap;

public class SubscriptionMap<K, V> extends ConcurrentHashMap<K, V> {
	
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
