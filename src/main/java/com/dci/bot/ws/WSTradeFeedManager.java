package com.dci.bot.ws;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dci.bot.exception.ApplicationException;
import com.dci.bot.model.Position;
import com.dci.bot.ws.listner.ConnectionStatusListner;
import com.dci.bot.ws.listner.TradeQuoteListner;
import com.dci.util.PropertyUtil;
import com.neovisionaries.ws.client.WebSocket;
import com.neovisionaries.ws.client.WebSocketException;
import com.neovisionaries.ws.client.WebSocketFactory;

public class WSTradeFeedManager {
	
	private static WebSocket ws;
	private static Logger logger = LoggerFactory.getLogger(WSTradeFeedManager.class);
	
	public void createWSConnection(ConnectionStatusListner listner) throws ApplicationException {		
				
		if (ws!=null) return;
		
		try {								
				ws = new WebSocketFactory()
						.createSocket(PropertyUtil.INSTANCE.getValue("trade.feed.url"))						
						.addHeader("Accept-Language", "nl-NL,en;q=0.8")
						.addHeader("Authorization", PropertyUtil.INSTANCE.getValue("auth.token"))
						.addListener(listner)
						.connect();
				
				logger.info("Connecting...");
				
				while(!listner.isConnected()) {
					
				}
				logger.info("Connected");
								
			} catch (WebSocketException e) {
				throw new ApplicationException(e.getMessage());
			} catch (IOException e) {
				throw new ApplicationException(e.getMessage());
			} 		
	}	
	
	public void subscribe(Position position) throws Exception {
		String request = "{\n" + "\"subscribeTo\": [\n" + "\"trading.product." + position.getProductId() + "\"\n" + "]}";
		
		if(SubscriptionMap.getInstance().isEmpty()) {
			ws.addListener(new TradeQuoteListner());
		}
		SubscriptionMap.getInstance().put(position.getProductId(), position);
		ws.sendText(request);		
	}
}
