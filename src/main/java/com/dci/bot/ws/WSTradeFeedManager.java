package com.dci.bot.ws;

import java.io.IOException;

import com.dci.bot.exception.ApplicationException;
import com.dci.bot.model.Position;
import com.dci.bot.ws.listner.ConnectionStatusListner;
import com.dci.bot.ws.listner.RegisterSubscriptionListner;
import com.dci.bot.ws.listner.TradeQuoteListner;
import com.dci.util.PropertyUtil;
import com.neovisionaries.ws.client.WebSocket;
import com.neovisionaries.ws.client.WebSocketException;
import com.neovisionaries.ws.client.WebSocketFactory;

public class WSTradeFeedManager {
	
	private static WebSocket ws;
		
	private WSTradeFeedManager() {
		
	}
	
	public static void getWSConnection(ConnectionStatusListner connectListner, TradeQuoteListner tradeQuoteListner) throws ApplicationException {		
				
		if (ws!=null) return;
		
		try {								
				ws = new WebSocketFactory()
						.createSocket(PropertyUtil.INSTANCE.getValue("trade.feed.url"))						
						.addHeader("Accept-Language", "nl-NL,en;q=0.8")
						.addHeader("Authorization", PropertyUtil.INSTANCE.getValue("auth.token"))
						.addListener(connectListner)
						.connect();
				
				System.out.print("Connecting");
				
				while(!connectListner.isConnected()) {
					System.out.print("...");
				}
				ws.removeListener(connectListner);
				ws.addListener(tradeQuoteListner);
				
			} catch (WebSocketException e) {
				throw new ApplicationException(e.getMessage());
			} catch (IOException e) {
				throw new ApplicationException(e.getMessage());
			} 		
	}	
	
	public static void subscribe(Position position) throws Exception {
		String request = "{\n" + "\"subscribeTo\": [\n" + "\"trading.product." + position.getProductId() + "\"\n" + "]}";
		ws.addListener(new RegisterSubscriptionListner(position));
		ws.sendText(request);		
	}	
	
	/*public void printSub() {
		subscriptions.forEach((k, v) -> System.out.println(k + " size " + subscriptions.size()));
	}*/	
}
