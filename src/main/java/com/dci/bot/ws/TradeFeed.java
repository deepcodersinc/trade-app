package com.dci.bot.ws;

import java.io.IOException;
import java.util.concurrent.ConcurrentHashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dci.bot.exception.ApplicationException;
import com.dci.bot.model.Position;
import com.dci.util.PropertyUtil;
import com.neovisionaries.ws.client.WebSocket;
import com.neovisionaries.ws.client.WebSocketException;
import com.neovisionaries.ws.client.WebSocketFactory;

public enum TradeFeed {
	INSTANCE;
	
	private final Logger logger = LoggerFactory.getLogger(TradeFeed.class);

	private static WebSocket ws;
	private static ConcurrentHashMap<String, Position> subscriptions = new ConcurrentHashMap<String, Position>();
	
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
		
		if(subscriptions.isEmpty()) {
			ws.addListener(new TradeQuoteListner());
		}
		subscriptions.put(position.getProductId(), position);
		ws.sendText(request);		
	}
	
	public ConcurrentHashMap<String, Position> getSubscriptions() {
		return subscriptions;
	}
}
