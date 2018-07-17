package com.dci.bot.http;

import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.function.Predicate;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dci.bot.exception.ApplicationException;
import com.dci.bot.exception.BuyOrderException;
import com.dci.bot.model.Position;
import com.dci.bot.model.Quote;
import com.google.gson.GsonBuilder;

public class TradePollingProcessor {

	private final Logger logger = LoggerFactory.getLogger(TradePollingProcessor.class);
	TradeOrderClient client;
	Position position;
	ConcurrentLinkedQueue<String> messageQueue;
	boolean terminatePolling;

	public TradePollingProcessor(Position position, ConcurrentLinkedQueue<String> messageQueue) {
		this.position = position;
		this.client = new TradeOrderClient();
		this.messageQueue = messageQueue;
		this.terminatePolling = false;
	}

	public void processQuote() {

		while (!messageQueue.isEmpty()) {
			try {
				String message = messageQueue.poll();
				Quote quote = new GsonBuilder().create().fromJson(message, Quote.class);

				if (!"trading.quote".equals(quote.getT())) {
					continue;
				}
				
				checkIfTradable(position.getSellPriceUpperLimit(),
						p -> (position.getOrderStatus().isBought() && p <= quote.getBody().getCurrentPrice()));
				checkIfTradable(position.getSellPriceLowerLimit(),
						p -> (position.getOrderStatus().isBought() && p >= quote.getBody().getCurrentPrice()));
				checkIfTradable(position.getBuyPrice(),
						p -> (position.getOrderStatus().isOpen() && p >= quote.getBody().getCurrentPrice()));
						
			} catch (ApplicationException ae) {
				logger.error(ae.getMessage());	
			} catch (Exception e) {
				logger.error(e.getMessage());
			}
		}
	}

	public void checkIfTradable(Float value, Predicate<Float> p) throws ApplicationException {
		if (p.test(value)) {
			executeTrade();
		}
	}
	
	private synchronized void executeTrade() throws ApplicationException {
		switch (position.getOrderStatus()) {

		case OPEN:
			logger.debug("Attempting to purchase...");
			try {
				String positionId = client.openPosition(position.getProductId(), position.getBuyPrice());
				position.setPositionId(positionId);
				position.setOrderStatus(Position.OrderStatus.BOUGHT);
				
			} catch (BuyOrderException oe) {
				logger.error(oe.getMessage());
				position.setOrderStatus(Position.OrderStatus.ERROR);
				terminatePolling = true;
				throw new ApplicationException(oe.getMessage());
			}	
			break;
			
		case BOUGHT:
			logger.debug("Attempting to sell...");
			client.closePosition(position.getPositionId());
			position.setOrderStatus(Position.OrderStatus.SOLD);
			terminatePolling = true;
			return;			
		default:
			break;
		}	
	}	

	public boolean isTerminatePolling() {
		return terminatePolling;
	}
	
	public Position getPosition() {
		return this.position;
	}
	
}
