package com.dci.bot.http;

import java.io.IOException;

import com.dci.bot.model.Price;
import com.dci.bot.model.Source;
import com.dci.bot.model.TradeRequest;
import com.dci.bot.model.TradeResponse;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;

public class RestTradeOrderManager implements TradeOrderManager {

	static OkHttpClient client = new OkHttpClient();
	public static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");
	
	//private static String POST_URI = "http://localhost:8080/core/21/users/me/trades";
	//private static String DELETE_URI = "http://localhost:8080/core/21/users/me/portfolio/positions/";
	private static String POST_URI = "https://api.beta.getbux.com/core/21/users/me/trades";
	private static String DELETE_URI = "https://api.beta.getbux.com/core/21/users/me/portfolio/positions/";
	
	
	private static String auth = "Bearer " + "eyJhbGciOiJIUzI1NiJ9.eyJyZWZyZXNoYWJsZSI6ZmFsc2UsInN1YiI6ImJiMGNkYTJiLWE"
			+ "xMGUtNGVkMy1hZDVhLTBmODJiNGMxNTJjNCIsImF1ZCI6ImJldGEuZ2V0YnV4LmNvbSIsInN"
			+ "jcCI6WyJhcHA6bG9naW4iLCJydGY6bG9naW4iXSwiZXhwIjoxODIwODQ5Mjc5LCJpYXQiOjE"
			+ "1MDU0ODkyNzksImp0aSI6ImI3MzlmYjgwLTM1NzUtNGIwMS04NzUxLTMzZDFhNGRjOGY5MiI"
			+ "sImNpZCI6Ijg0NzM2MjI5MzkifQ.M5oANIi2nBtSfIfhyUMqJnex-JYg6Sm92KPYaUL9GKg";

	public String openPosition(String productId, float price) throws IOException {
		TradeRequest buyRequest = new TradeRequest(productId, new Price("BUX", 2, Float.toString(price)), 2, "BUY",
				new Source("OTHER", ""));

		RequestBody body = RequestBody.create(JSON, (new ObjectMapper()).writeValueAsString(buyRequest));
		
		Request request = new Request.Builder()
				.url(POST_URI)
				.post(body)
				.header("Authorization", auth)
				.header("Accept-Language", "nl-NL,en;q=0.8")
				.header("Accept", "application/json")
				.header("Content-Type", "application/json")
				.build();
		
		Response response = client.newCall(request).execute();
		String res = response.body().string();

		TradeResponse tr = (new ObjectMapper()).readValue(res, TradeResponse.class);
		
		System.out.println(tr);

		return tr.getPositionId();
		
	}

	public String closePosition(String positionId) throws IOException {
		
		System.out.println("----positionId: " +positionId);
		
		Request request = new Request.Builder()
				.url(DELETE_URI + positionId)
				.delete()
				.header("Authorization", auth)
				.header("Accept-Language", "nl-NL,en;q=0.8")
				.header("Accept", "application/json")
				.header("Content-Type", "application/json")				
				.build();
		
		Response response = client.newCall(request).execute();
		String res = response.body().string();
		
		System.out.println(res);
		
		TradeResponse tr = (new ObjectMapper()).readValue(res, TradeResponse.class);

		return tr.getProfitAndLoss().getAmount();
	}	
}
