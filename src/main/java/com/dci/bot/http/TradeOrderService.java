package com.dci.bot.http;

import com.dci.bot.model.TradeRequest;
import com.dci.bot.model.TradeResponse;
import com.dci.util.PropertyUtil;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface TradeOrderService {
	String AUTH = "Bearer eyJhbGciOiJIUzI1NiJ9.eyJyZWZyZXNoYWJsZSI6ZmFsc2UsInN1YiI6ImJiMGNkYTJiLWExMGUtNGVkMy1hZDVhLTBmODJiNGMxNTJjNCIsImF1ZCI6ImJldGEuZ2V0YnV4LmNvbSIsInNjcCI6WyJhcHA6bG9naW4iLCJydGY6bG9naW4iXSwiZXhwIjoxODIwODQ5Mjc5LCJpYXQiOjE1MDU0ODkyNzksImp0aSI6ImI3MzlmYjgwLTM1NzUtNGIwMS04NzUxLTMzZDFhNGRjOGY5MiIsImNpZCI6Ijg0NzM2MjI5MzkifQ.M5oANIi2nBtSfIfhyUMqJnex-JYg6Sm92KPYaUL9GKg";
			
	Retrofit CONNECTION = new Retrofit.Builder()
			  .baseUrl(PropertyUtil.getValue("trade.http.host"))
			  .addConverterFactory(GsonConverterFactory.create())			  
			  .build();
	
	@Headers({"Authorization: " + AUTH,
			  "Accept-Language: nl-NL,en;q=0.8",
			  "Accept: application/json",
			  "Content-Type: application/json"})		
	@POST("/core/21/users/me/trades")
	Call<TradeResponse> buy(@Body TradeRequest request);
	
	@Headers({"Authorization: " + AUTH,
		  "Accept-Language: nl-NL,en;q=0.8",
		  "Accept: application/json",
		  "Content-Type: application/json"})	
	@DELETE("/core/21/users/me/portfolio/positions/{positionId}")
	Call<TradeResponse> sell(@Path("positionId") String positionId);  

}
