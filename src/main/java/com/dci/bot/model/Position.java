package com.dci.bot.model;

public class Position {
	private String productId;
	private float buyPrice;
	private float sellPriceUpperLimit;
	private float sellPriceLowerLimit;
	
	private String positionId;
	private boolean bought;
	
	

	public Position(String productId, float buyPrice, float sellPriceUpperLimit, float sellPriceLowerLimit) {
		super();
		this.productId = productId;
		this.buyPrice = buyPrice;
		this.sellPriceUpperLimit = sellPriceUpperLimit;
		this.sellPriceLowerLimit = sellPriceLowerLimit;
		this.bought = false;
	}

	public String getProductId() {
		return productId;
	}

	public void setProductId(String productId) {
		this.productId = productId;
	}

	public float getBuyPrice() {
		return buyPrice;
	}

	public void setBuyPrice(float buyPrice) {
		this.buyPrice = buyPrice;
	}

	public float getSellPriceUpperLimit() {
		return sellPriceUpperLimit;
	}

	public void setSellPriceUpperLimit(float sellPriceUpperLimit) {
		this.sellPriceUpperLimit = sellPriceUpperLimit;
	}

	public float getSellPriceLowerLimit() {
		return sellPriceLowerLimit;
	}

	public void setSellPriceLowerLimit(float sellPriceLowerLimit) {
		this.sellPriceLowerLimit = sellPriceLowerLimit;
	}

	public boolean isBought() {
		return bought;
	}

	public void setBought(boolean bought) {
		this.bought = bought;
	}

	public String getPositionId() {
		return positionId;
	}

	public void setPositionId(String positionId) {
		this.positionId = positionId;
	}
	
	
	
}
