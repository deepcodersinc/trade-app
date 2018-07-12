package com.dci.bot.ws;

import com.dci.bot.exception.ApplicationException;
import com.dci.bot.exception.OrderException;
import com.dci.bot.http.RestTradeOrderManager;
import com.dci.bot.http.TradeOrderManager;
import com.dci.bot.model.Position;
import com.dci.util.JsonUtil;
import com.neovisionaries.ws.client.WebSocket;
import com.neovisionaries.ws.client.WebSocketAdapter;

public class TradeQuoteListner extends WebSocketAdapter {	
	
	SubscriptionMap<String, Position> subscriptions = SubscriptionMap.getInstance();
	TradeOrderManager tradeOrder;
	
	public TradeQuoteListner() {
		
	}
	
	public TradeQuoteListner(TradeOrderManager tradeOrder) {
		this.tradeOrder = tradeOrder;
	}

	@Override
	public void onTextMessage(WebSocket websocket, String message) {
		Position position = null;
		String productId = null;
		
		try {	
			
			boolean isTradeQuote = JsonUtil.INSTANCE.getJsonValue(message, "t").equals("trading.quote");
			
			if(isTradeQuote) {	
				
				float price = Float.parseFloat(JsonUtil.INSTANCE.getJsonNestedValue(message, "body", "currentPrice"));
				productId = JsonUtil.INSTANCE.getJsonNestedValue(message, "body", "securityId");
				System.out.println(productId +" - "+ price);
				
				position = subscriptions.get(productId);
				
				if(position != null) {	
					if (!position.isBought() && price <= position.getBuyPrice()) {

						String positionId = tradeOrder.openPosition(position.getProductId(), position.getBuyPrice());
						System.out.println(">>>>>>>> Bought " + position.getProductId() + " at " + price);
						position.setBought(true);
						position.setPositionId(positionId);
						subscriptions.replace(productId, position);

					} else if (position.isBought() && price >= position.getSellPriceUpperLimit()) {
						tradeOrder.closePosition(position.getPositionId());
						System.out.println(">>>>>>>> Sold " + position.getProductId() + " at " + price);
						unSubscribe(websocket, productId);

					} else if (position.isBought() && price <= position.getSellPriceLowerLimit()) {
						tradeOrder.closePosition(position.getPositionId());
						System.out.println(">>>>>>>> Sold " + position.getProductId() + " at " + price);
						unSubscribe(websocket, productId);
					}
				}	
			}

		} catch (OrderException oe) {
			System.out.println(oe.getHttpErrorCode() + ": " + oe.getMessage());
			if(position!=null && !position.isBought()) unSubscribe(websocket, productId);
			oe.printStackTrace();
		} catch(ApplicationException ae) {
			ae.printStackTrace();
		}
	}
	
	public void unSubscribe(WebSocket websocket, String productId) {
		String request = "{\n" + "\"unsubscribeFrom\": [\n" + "\"trading.product." + productId + "\"\n" + "]}";
		websocket.sendText(request);
		subscriptions.remove(productId);
	}
}
