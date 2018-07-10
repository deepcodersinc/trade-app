package com.dci.bot.http;

public interface TradeOrderManager {
	
	public String openPosition(String productId, float price) throws Exception;
	
	public String closePosition(String positionId) throws Exception;
}
