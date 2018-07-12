package com.dci.bot.http;

import java.io.IOException;

import com.dci.bot.exception.ApplicationException;
import com.dci.bot.exception.OrderException;
import com.dci.bot.model.ErrorResponse;
import com.dci.bot.model.Price;
import com.dci.bot.model.Source;
import com.dci.bot.model.TradeRequest;
import com.dci.util.JsonUtil;
import com.dci.util.PropertyUtil;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import okhttp3.Headers;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class RestTradeOrderManager implements TradeOrderManager {

	OkHttpClient client = new OkHttpClient();

	private static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");
	
	private Headers headers;
	
	public RestTradeOrderManager() throws ApplicationException {
		String[] namesAndValues = { "Authorization", PropertyUtil.INSTANCE.getValue("auth.token"),
									"Accept-Language", "nl-NL,en;q=0.8",
									"Accept", "application/json",
									"Content-Type", "application/json" };		
		headers = Headers.of(namesAndValues);		
	}

	public synchronized String openPosition(String productId, float price) throws OrderException, ApplicationException {
		Response response;
		String responseJson;
		
		TradeRequest buyRequest = new TradeRequest(productId, new Price("BUX", 2, Float.toString(price)), 2, "BUY",
				new Source("OTHER", ""));
		
		try {
			RequestBody body = RequestBody.create(JSON, (JsonUtil.INSTANCE.getJson(buyRequest)));
	
			Request request = new Request.Builder()
					.url(PropertyUtil.INSTANCE.getValue("trade.buy.url"))
					.post(body)
					.headers(headers)
					.build();
	
			response = client.newCall(request).execute();
			responseJson = response.body().string();	
			
			if (response.isSuccessful()) {
				return JsonUtil.INSTANCE.getJsonValue(responseJson, "positionId");
			} else {				
				ErrorResponse error = new ObjectMapper().readValue(responseJson, ErrorResponse.class);
				throw new OrderException(error.getMessage(), error.getDeveloperMessage(), error.getErrorCode(), response.code());
			}						
		} catch (ApplicationException ae) {			
			throw new ApplicationException(ae.getMessage());
		} catch (JsonParseException jpe) {
			throw new ApplicationException(jpe.getMessage());
		} catch (JsonMappingException jme) {
			throw new ApplicationException(jme.getMessage());
		} catch (IOException ioe) {
			throw new ApplicationException(ioe.getMessage());
		}		
	}

	public synchronized String closePosition(String positionId) throws OrderException, ApplicationException {
		
		try {
			Request request = new Request.Builder()
					.url(PropertyUtil.INSTANCE.getValue("trade.sell.url") + positionId)
					.delete()
					.headers(headers)
					.build();
	
			Response response = client.newCall(request).execute();
			String responseJson = response.body().string();
	
			if (response.isSuccessful()) {
				return JsonUtil.INSTANCE.getJsonValue(responseJson, "positionId");
			} else {				
				ErrorResponse error = new ObjectMapper().readValue(responseJson, ErrorResponse.class);
				throw new OrderException(error.getMessage(), error.getDeveloperMessage(), error.getErrorCode(), response.code());
			}						
		} catch (ApplicationException ae) {			
			throw new ApplicationException(ae.getMessage());
		} catch (JsonParseException jpe) {
			throw new ApplicationException(jpe.getMessage());
		} catch (JsonMappingException jme) {
			throw new ApplicationException(jme.getMessage());
		} catch (IOException ioe) {
			throw new ApplicationException(ioe.getMessage());
		}		
	}
}
