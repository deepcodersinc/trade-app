package com.dci.bot.http;

import java.io.IOException;

import com.dci.bot.exception.ApplicationException;
import com.dci.bot.exception.OrderException;
import com.dci.bot.model.ErrorResponse;
import com.dci.bot.model.Price;
import com.dci.bot.model.TradeRequest;
import com.dci.bot.model.TradeResponse;
import com.dci.util.PropertyUtil;
import com.google.gson.Gson;

import retrofit2.Response;

public class RestTradeOrderClient implements TradeOrderClient {

	public synchronized String openPosition(String productId, float price) throws OrderException, ApplicationException {		
	
		TradeRequest request = new TradeRequest(productId, new Price(PropertyUtil.INSTANCE.getValue("trade.currency"), 2, Float.toString(price)), 2);
		
		try {
			TradeService service = TradeService.retrofit.create(TradeService.class);
			Response<TradeResponse> httpResponse = service.buy(request).execute();
			
			if (httpResponse.isSuccessful()) {
				return httpResponse.body().getPositionId();
			} else {	
				ErrorResponse error = new Gson().fromJson(httpResponse.errorBody().string(), ErrorResponse.class);
				throw new OrderException(error.getMessage(), error.getDeveloperMessage(), error.getErrorCode(), httpResponse.code());
			}		
		} catch (IOException ioe) {
			throw new ApplicationException(ioe.getMessage());
		}		
	}

	public synchronized String closePosition(String positionId) throws OrderException, ApplicationException {
		
		try {
			TradeService service = TradeService.retrofit.create(TradeService.class);		
			Response<TradeResponse> httpResponse = service.sell(positionId).execute();			
	
			if (httpResponse.isSuccessful()) {
				return httpResponse.body().getProfitAndLoss().getAmount();
			} else {				
				ErrorResponse error = new Gson().fromJson(httpResponse.errorBody().string(), ErrorResponse.class);
				throw new OrderException(error.getMessage(), error.getDeveloperMessage(), error.getErrorCode(), httpResponse.code());
			}						
		} catch (IOException ioe) {
			throw new ApplicationException(ioe.getMessage());
		}		
	}
}
