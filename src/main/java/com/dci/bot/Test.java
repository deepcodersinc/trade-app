package com.dci.bot;

import com.dci.bot.model.Position;
import com.dci.bot.ws.WSTradeFeedManager;
import com.dci.bot.ws.listner.ConnectionStatusListner;
import com.dci.util.PropertyUtil;

public class Test {
	
	public static void main(String[] args)  {
		PropertyUtil.INSTANCE.setPropertyFile("environment.properties");
		
			
		//TradeRequest buyRequest = new TradeRequest(productId, new Price("BUX", 2, Float.toString(price)), 2, "BUY", new Source("OTHER", ""));
		
		try {
			WSTradeFeedManager wsManager = new WSTradeFeedManager();
			
			wsManager.createWSConnection(new ConnectionStatusListner());
			
			wsManager.subscribe(new Position("sb26493", 12800.0F, 12420.0F, 12300.0F));
			wsManager.subscribe(new Position("sb26502", 1.19F, 1.8F, 1.2F));
			
			
		} catch(Exception e) {
			e.printStackTrace();
		}
		
	}

}
