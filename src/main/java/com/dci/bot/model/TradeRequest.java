package com.dci.bot.model;

public class TradeRequest {
    private String productId;
    private Price investingAmount;
    private int leverage;
    private String direction;
    private Source source;
    
    public TradeRequest(String productId, Price investingAmount, int leverage, String direction, Source source) {
		super();
		this.productId = productId;
		this.investingAmount = investingAmount;
		this.leverage = leverage;
		this.direction = direction;
		this.source = source;
	}
    
    @Override
	public String toString() {
		return "TradeRequest [productId=" + productId + ", investingAmount=" + investingAmount.toString() + ", leverage="
				+ leverage + ", direction=" + direction + ", source=" + source.toString() +"]";
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
	public Source getSource() {
		return source;
	}
	public void setSource(Source source) {
		this.source = source;
	}
    
}
