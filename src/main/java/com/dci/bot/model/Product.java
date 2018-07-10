package com.dci.bot.model;

public class Product {
	private String securityId;
	private String symbol;
	private String displayName;
	private Price currentPrice;
	private Price closingPrice;
	
	public Product() {
		
	}
	
	public Product(String securityId, String symbol, String displayName, Price currentPrice, Price closingPrice) {
		super();
		this.securityId = securityId;
		this.symbol = symbol;
		this.displayName = displayName;
		this.currentPrice = currentPrice;
		this.closingPrice = closingPrice;
	}
	
	@Override
	public String toString() {
		return "Product [securityId=" + securityId + ", symbol=" + symbol + ", displayName=" + displayName
				+ ", currentPrice=" + currentPrice.toString() + ", closingPrice=" + closingPrice.toString() + "]";
	}
	
	public String getSecurityId() {
		return securityId;
	}
	public void setSecurityId(String securityId) {
		this.securityId = securityId;
	}
	public String getSymbol() {
		return symbol;
	}
	public void setSymbol(String symbol) {
		this.symbol = symbol;
	}
	public String getDisplayName() {
		return displayName;
	}
	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}
	public Price getCurrentPrice() {
		return currentPrice;
	}
	public void setCurrentPrice(Price currentPrice) {
		this.currentPrice = currentPrice;
	}
	public Price getClosingPrice() {
		return closingPrice;
	}
	public void setClosingPrice(Price closingPrice) {
		this.closingPrice = closingPrice;
	}
	
	
	
	
}
