package com.dci.bot;

import com.dci.bot.model.Position;
import com.dci.bot.ws.ConnectionStatusListner;
import com.dci.bot.ws.WSTradeFeedManager;
import com.dci.util.PropertyUtil;

public class Test {

	public static void main(String[] args) {
		PropertyUtil.INSTANCE.setPropertyFile("environment-prod.properties");
		
		try {
			WSTradeFeedManager.getWSConnection(new ConnectionStatusListner());
			
			WSTradeFeedManager.subscribe(new Position("sb26502", 12422.0F, 12420.0F, 12422.5F));
			WSTradeFeedManager.subscribe(new Position("sb26493", 9270.0F, 9260.0F, 9280.0F));
			
			
		} catch(Exception e) {
			e.printStackTrace();
		}
	}

}
