package com.dci.bot.http;

import com.dci.bot.exception.ApplicationException;
import com.dci.bot.exception.OrderException;

public interface TradeOrderClient {
	
	public String openPosition(String productId, float price) throws OrderException, ApplicationException;
	
	public String closePosition(String positionId) throws OrderException, ApplicationException;
}
