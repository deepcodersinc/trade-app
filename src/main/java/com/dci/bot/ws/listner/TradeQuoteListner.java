package com.dci.bot.ws.listner;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dci.bot.exception.ApplicationException;
import com.dci.bot.exception.OrderException;
import com.dci.bot.http.RestTradeOrderClient;
import com.dci.bot.http.TradeOrderClient;
import com.dci.bot.model.Position;
import com.dci.bot.ws.SubscriptionMap;
import com.dci.bot.ws.WSTradeFeedManager;
import com.dci.util.JsonUtil;
import com.neovisionaries.ws.client.WebSocket;
import com.neovisionaries.ws.client.WebSocketAdapter;

public class TradeQuoteListner extends WebSocketAdapter {	
	private static Logger logger = LoggerFactory.getLogger(TradeQuoteListner.class);	
	TradeOrderClient tradeOrder;
	
	public TradeQuoteListner() {
		super();
		this.tradeOrder = new RestTradeOrderClient();
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
				logger.debug(productId +" - "+ price);				
								
				position = (Position) SubscriptionMap.getInstance().get(productId);
								
				if(position != null) {	
					if (!position.isBought() && price <= position.getBuyPrice()) {

						String positionId = tradeOrder.openPosition(position.getProductId(), position.getBuyPrice());
						logger.info("---- Bought " + position.getProductId() + " at " + price);
						position.setBought(true);
						position.setPositionId(positionId);
						SubscriptionMap.getInstance().replace(productId, position);						

					} else if (position.isBought() && price >= position.getSellPriceUpperLimit()) {
						tradeOrder.closePosition(position.getPositionId());
						logger.debug("---- Sold " + position.getProductId() + " at " + price);
						unSubscribe(websocket, productId);

					} else if (position.isBought() && price <= position.getSellPriceLowerLimit()) {
						tradeOrder.closePosition(position.getPositionId());
						logger.debug("---- Sold " + position.getProductId() + " at " + price);
						unSubscribe(websocket, productId);
					}
				}	
			}

		} catch (OrderException oe) {
			logger.debug("Couldnot make the purchase. Closing position " + oe.getMessage());
			if(position!=null && !position.isBought()) unSubscribe(websocket, productId);
			oe.printStackTrace();
		} catch(ApplicationException ae) {
			ae.printStackTrace();
		}
	}
	
	public void unSubscribe(WebSocket websocket, String productId) {
		String request = "{\n" + "\"unsubscribeFrom\": [\n" + "\"trading.product." + productId + "\"\n" + "]}";
		websocket.sendText(request);
		SubscriptionMap.getInstance().remove(productId);
		
		if(SubscriptionMap.getInstance().size() == 0) websocket.disconnect();
	}
	
	public void setTradeOrderClient(TradeOrderClient tradeOrder) {
		this.tradeOrder = tradeOrder;
	}
}
