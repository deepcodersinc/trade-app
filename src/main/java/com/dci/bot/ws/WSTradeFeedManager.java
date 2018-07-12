package com.dci.bot.ws;

import java.io.IOException;
import java.util.Optional;

import org.apache.commons.cli.Options;

import com.dci.bot.exception.ApplicationException;
import com.dci.bot.http.RestTradeOrderManager;
import com.dci.bot.model.Position;
import com.dci.util.JsonUtil;
import com.dci.util.PropertyUtil;
import com.neovisionaries.ws.client.WebSocket;
import com.neovisionaries.ws.client.WebSocketAdapter;
import com.neovisionaries.ws.client.WebSocketException;
import com.neovisionaries.ws.client.WebSocketFactory;

public class WSTradeFeedManager {
	
	private static WebSocket ws;
		
	private WSTradeFeedManager() {
		
	}
	
	public static void getWSConnection(ConnectionStatusListner listner) throws ApplicationException {		
				
		if (ws!=null) return;
		
		try {								
				ws = new WebSocketFactory()
						.createSocket(PropertyUtil.INSTANCE.getValue("trade.feed.url"))						
						.addHeader("Accept-Language", "nl-NL,en;q=0.8")
						.addHeader("Authorization", PropertyUtil.INSTANCE.getValue("auth.token"))
						.addListener(listner)
						.connect();
				
				System.out.print("Connecting");
				
				while(!listner.isConnected()) {
					System.out.print("...");
				}
				ws.removeListener(listner);
				ws.addListener(new TradeQuoteListner(new RestTradeOrderManager()));
				
			} catch (WebSocketException e) {
				throw new ApplicationException(e.getMessage());
			} catch (IOException e) {
				throw new ApplicationException(e.getMessage());
			} 		
	}	
	
	public static void subscribe(Position position) throws Exception {
		String request = "{\n" + "\"subscribeTo\": [\n" + "\"trading.product." + position.getProductId() + "\"\n" + "]}";
		ws.addListener(new WebSocketAdapter() {
			
			@Override
			public void onTextMessage(WebSocket websocket, String message) throws ApplicationException {				
				SubscriptionMap<String, Position> subscriptions = SubscriptionMap.getInstance();				
				boolean isTradeQuote = JsonUtil.INSTANCE.getJsonValue(message, "t").equals("trading.quote");
				
				if(isTradeQuote) {									
					subscriptions.put(position.getProductId(), position);
				}
				websocket.removeListener(this);
			}
		});
		ws.sendText(request);		
	}	
	
	/*public void printSub() {
		subscriptions.forEach((k, v) -> System.out.println(k + " size " + subscriptions.size()));
	}*/	
}
