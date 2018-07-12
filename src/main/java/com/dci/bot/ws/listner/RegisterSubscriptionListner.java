package com.dci.bot.ws.listner;

import com.dci.bot.exception.ApplicationException;
import com.dci.bot.model.Position;
import com.dci.bot.ws.SubscriptionMap;
import com.dci.util.JsonUtil;
import com.neovisionaries.ws.client.WebSocket;
import com.neovisionaries.ws.client.WebSocketAdapter;

public class RegisterSubscriptionListner extends WebSocketAdapter {
	
	Position position;
	SubscriptionMap<String, Position> subscriptions = SubscriptionMap.getInstance();
	
	public RegisterSubscriptionListner(Position position) {
		this.position = position;
	}
	
	@Override
	public void onTextMessage(WebSocket websocket, String message) throws ApplicationException {						
		boolean isTradeQuote = JsonUtil.INSTANCE.getJsonValue(message, "t").equals("trading.quote");
		
		if(isTradeQuote) {									
			subscriptions.put(position.getProductId(), position);
		}
		websocket.removeListener(this);
	}
}
