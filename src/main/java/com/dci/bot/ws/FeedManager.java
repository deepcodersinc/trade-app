package com.dci.bot.ws;

import java.util.concurrent.ConcurrentLinkedQueue;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dci.bot.http.TradeOrderClient;
import com.dci.bot.http.TradePollingProcessor;
import com.dci.bot.model.Position;
import com.neovisionaries.ws.client.WebSocket;
import com.neovisionaries.ws.client.WebSocketAdapter;

public class FeedManager {

	private final Logger logger = LoggerFactory.getLogger(FeedManager.class);
	private WebSocket ws;
	
	public FeedManager(WebSocket websocket) {
		this.ws = websocket;
	}
	
	private void init(Position position) {
		ConcurrentLinkedQueue<String> messageQueue = new ConcurrentLinkedQueue<String>();;
		TradePollingProcessor processor = new TradePollingProcessor(position, messageQueue);
		processor.setTradeOrderClient(new TradeOrderClient());
		
		new Thread(() -> {
			while(!processor.isTerminatePolling()) {
				processor.processQuote();
			}
			unSubscribe(position);
		}).start();

		ws.addListener(new WebSocketAdapter() {

			@Override
			public void onTextMessage(WebSocket websocket, String message) {
				messageQueue.add(message);
				logger.debug(message);
			}			
		});
	}

	public void subscribe(Position position) throws Exception {		
		init(position);
		ws.sendText(position.getSubscriptionMessage());
	}

	public void unSubscribe(Position position) {
		ws.sendText(position.getUnsubscriptionMessage());
		ws.disconnect();
	}	
}
