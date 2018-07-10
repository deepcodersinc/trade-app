package com.dci.bot.ws;

import java.io.IOException;

import com.dci.bot.http.TradeOrderManager;
import com.dci.util.TraderUtil;
import com.neovisionaries.ws.client.WebSocket;
import com.neovisionaries.ws.client.WebSocketAdapter;
import com.neovisionaries.ws.client.WebSocketException;
import com.neovisionaries.ws.client.WebSocketFrame;

public class FeedChannelListner extends WebSocketAdapter {

	private TradeOrderManager tradeOrder;
	
	private String productId;
	private String positionId;
	private String direction;
	private float buyPrice;
	private float sellPriceUpperLimit;
	private float sellPriceLowerLimit;
	private String profitLossAmount;
	
	FeedChannelListner() {
		super();
	}
		
	public FeedChannelListner(String productId, String direction, float buyPrice, float sellPriceUpperLimit,
			float sellPriceLowerLimit, TradeOrderManager tradeOrder) {
		super();
		this.productId = productId;
		this.direction = direction;
		this.buyPrice = buyPrice;
		this.sellPriceUpperLimit = sellPriceUpperLimit;
		this.sellPriceLowerLimit = sellPriceLowerLimit;
		this.tradeOrder = tradeOrder;
	}

	@Override
	public void onTextMessage(WebSocket websocket, String message) throws Exception {
		float price = Float.parseFloat(TraderUtil.getJsonNestedValue(message, "body", "currentPrice"));
		System.out.println(productId + ": " + price);
		
		try {
			if("BUY".equals(direction) && price <= buyPrice) {
				this.positionId = tradeOrder.openPosition(productId, price);
				System.out.println(">>>>>>>> Bought " + productId + " at "+ price);
				this.direction = "SELL"; 
			} else if("SELL".equals(direction) && price >= sellPriceUpperLimit) {			
				this.profitLossAmount = tradeOrder.closePosition(positionId);
				System.out.println(">>>>>>>> Sold " + productId + " at "+ price + "at profit of " + profitLossAmount);
				websocket.removeListener(this);
			} else if("SELL".equals(direction) && price <= sellPriceLowerLimit) {
				this.profitLossAmount = tradeOrder.closePosition(positionId);
				System.out.println(">>>>>>>> Sold " + productId + " at "+ price + " at loss of " + profitLossAmount);			
				websocket.removeListener(this);
			}
		} catch(Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void onDisconnected(WebSocket websocket, WebSocketFrame serverCloseFrame, WebSocketFrame clientCloseFrame,
			boolean closedByServer) {
		if (closedByServer) {
			try {
				websocket.recreate().connect();

			} catch (WebSocketException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}