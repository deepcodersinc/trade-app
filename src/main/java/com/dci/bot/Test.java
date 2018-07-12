package com.dci.bot;

import com.dci.bot.http.RestTradeOrderClient;
import com.dci.bot.http.TradeOrderClient;
import com.dci.bot.model.Position;
import com.dci.bot.ws.WSTradeFeedManager;
import com.dci.bot.ws.listner.ConnectionStatusListner;
import com.dci.bot.ws.listner.TradeQuoteListner;
import com.dci.util.PropertyUtil;

public class Test {
	
	public static void main(String[] args)  {
		PropertyUtil.INSTANCE.setPropertyFile("environment.properties");
		
			
		//TradeRequest buyRequest = new TradeRequest(productId, new Price("BUX", 2, Float.toString(price)), 2, "BUY", new Source("OTHER", ""));
		
		try {	
			TradeOrderClient orderManager = new RestTradeOrderClient();
			WSTradeFeedManager.getWSConnection(new ConnectionStatusListner(), new TradeQuoteListner(orderManager));
			
			WSTradeFeedManager.subscribe(new Position("sb26493", 12800.0F, 12420.0F, 12300.0F));
			WSTradeFeedManager.subscribe(new Position("sb26502", 1.0F, 1.2F, 1.2F));
			//orderManager.openPosition("sb26493", 120.0F);
			
		} catch(Exception e) {
			e.printStackTrace();
		}
		
	}

}
