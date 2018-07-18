package com.dci.bot.http;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dci.bot.exception.ApplicationException;
import com.dci.bot.exception.BuyOrderException;
import com.dci.bot.model.Price;
import com.dci.bot.model.TradeRequest;
import com.dci.bot.model.TradeResponse;

import retrofit2.Response;

public class TradeOrderClient {
	
	private final Logger logger = LoggerFactory.getLogger(TradeOrderClient.class);
		
	public String openPosition(String productId, float price) throws BuyOrderException, ApplicationException {		
	
		TradeRequest request = new TradeRequest(productId, new Price("BUX", 2, Float.toString(price)), 2);
		
		try {
			TradeOrderService service = TradeOrderService.CONNECTION.create(TradeOrderService.class);
			Response<TradeResponse> httpResponse = service.buy(request).execute();
			
			if (httpResponse.isSuccessful()) {
				logger.info("---- Bought " + productId + " at " + httpResponse.body().getPrice().getAmount() + " ----");
				return httpResponse.body().getPositionId();
			} else {
				throw new BuyOrderException(httpResponse.errorBody().string());
			}		
		} catch (IOException ioe) {
			throw new ApplicationException(ioe.getMessage());
		}		
	}

	public String closePosition(String positionId) throws ApplicationException {
		
		try {
			TradeOrderService service = TradeOrderService.CONNECTION.create(TradeOrderService.class);		
			Response<TradeResponse> httpResponse = service.sell(positionId).execute();			
	
			if (httpResponse.isSuccessful()) {
				String profitLoss = httpResponse.body().getProfitAndLoss().getAmount();
				logger.info("---- Sold at profit/loss of " + profitLoss + " ----");
				return profitLoss;
			} else {				
				throw new ApplicationException(httpResponse.errorBody().string());
			}						
		} catch (IOException ioe) {
			throw new ApplicationException(ioe.getMessage());
		}		
	}
}
