package com.dci.bot.model;

import java.io.Serializable;

public class TradeRequest implements Serializable {
	
	private static final long serialVersionUID = 5016944900935702603L;
	
	private String productId;	
	private Price investingAmount;
	private int leverage;	
    private String direction;
   
    
   public TradeRequest(String productId, Price investingAmount, int leverage) {
		super();
		this.productId = productId;
		this.investingAmount = investingAmount;
		this.leverage = leverage;
		this.direction = "BUY";		
	}
    
    @Override
	public String toString() {
		return "TradeRequest [productId=" + productId + ", investingAmount=" + investingAmount.toString() + ", leverage="
				+ leverage + ", direction=" + direction +"]";
	}

    public String getProductId() {
		return productId;
	}
	public void setProductId(String productId) {
		this.productId = productId;
	}
	public Price getInvestingAmount() {
		return investingAmount;
	}
	public void setInvestingAmount(Price investingAmount) {
		this.investingAmount = investingAmount;
	}
	public int getLeverage() {
		return leverage;
	}
	public void setLeverage(int leverage) {
		this.leverage = leverage;
	}
	public String getDirection() {
		return direction;
	}
	public void setDirection(String direction) {
		this.direction = direction;
	}
}
