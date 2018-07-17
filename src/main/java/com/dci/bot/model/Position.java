package com.dci.bot.model;

public class Position {
	private String productId;
	private float buyPrice;
	private float sellPriceUpperLimit;
	private float sellPriceLowerLimit;
	
	private String positionId;
	private OrderStatus orderStatus;
	
	public Position() {
		this.orderStatus = OrderStatus.OPEN;
	}

	public Position(String productId, float buyPrice, float sellPriceUpperLimit, float sellPriceLowerLimit) {
		super();
		this.productId = productId;
		this.buyPrice = buyPrice;
		this.sellPriceUpperLimit = sellPriceUpperLimit;
		this.sellPriceLowerLimit = sellPriceLowerLimit;
		orderStatus = OrderStatus.OPEN;
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

	public String getPositionId() {
		return positionId;
	}

	public void setPositionId(String positionId) {
		this.positionId = positionId;
	}	
	
	public OrderStatus getOrderStatus() {
		return orderStatus;
	}

	public void setOrderStatus(OrderStatus orderStatus) {
		this.orderStatus = orderStatus;
	}



	public enum OrderStatus {
		OPEN,
		BOUGHT,
		SOLD,
		ERROR;
		
		public boolean isOpen() {
			return this.ordinal() == 0;
		}
		public boolean isBought() {
			return this.ordinal() == 1;
		}
		public boolean isSold() {
			return this.ordinal() == 2;
		}
		public boolean isError() {
			return this.ordinal() == 3;
		}
	}

	public String getSubscriptionMessage() {
		return "{\n" + "\"subscribeTo\": [\n" + "\"trading.product." + this.productId + "\"\n" + "]}";
	}
	
	public String getUnsubscriptionMessage() {
		return "{\n" + "\"unsubscribeFrom\": [\n" + "\"trading.product." + this.productId + "\"\n" + "]}";
	}

	@Override
	public String toString() {
		return "Position [productId=" + productId + ", buyPrice=" + buyPrice + ", sellPriceUpperLimit="
				+ sellPriceUpperLimit + ", sellPriceLowerLimit=" + sellPriceLowerLimit + ", positionId=" + positionId
				+ ", orderStatus=" + orderStatus + "]";
	}
	
	
}
