package com.dci.bot;

import java.io.IOException;

import com.dci.bot.http.RestTradeOrderManager;
import com.dci.bot.http.TradeOrderManager;
import com.dci.bot.ws.ConnectionStatusListner;
import com.dci.bot.ws.FeedChannelListner;
import com.dci.bot.ws.WSTradeFeedManager;

public class AppBoot {

	public static void main(String[] args) {

		String json1 = "{\n" + "\"subscribeTo\": [\n" + "\"trading.product." + "sb26493" + "\"\n" + "]}";
		String json2 = "{\n" + "\"subscribeTo\": [\n" + "\"trading.product." + "sb26500" + "\"\n" + "]}";

		try {
			//WSTradeFeedManager app = WSTradeFeedManager.getWebSocketConnection(new ConnectionStatusListner());

			//app.subscribe(json1, new FeedChannelListner("sb26493", "BUY", 200.0F, 12300.0F, 12100.0F, new RestTradeOrderManager()));
			//app.subscribe(json2, new FeedChannelListner("sb26500", "BUY", 200.0F, 14000.0F, 11990.0F, new RestTradeOrderManager()));

		} catch (Exception e) {
			e.printStackTrace();
		}

		
		  TradeOrderManager order = new RestTradeOrderManager();
		  
		  try { 
			  String positionId = order.openPosition("sb26493", 1F);			  
			  order.closePosition(positionId); 
		  } catch (Exception e) {  
			  e.printStackTrace(); 
		  }
		 

	}
}
