package com.dci.bot.model;

public class TradeResponse {
	private String id;
	private String positionId;
	private Product product;
	private Price profitAndLoss;
	private Price investingAmount;
	private Price price;
	private int leverage;
	private String direction;
	private String type;
	private long dateCreated;
	
	public TradeResponse() {
		
	}
	
	public TradeResponse(String id, String positionId, int leverage, String direction, String type, long dateCreated,
			Product product, Price profitAndLoss, Price investingAmount, Price price) {
		super();
		this.id = id;
		this.positionId = positionId;
		this.leverage = leverage;
		this.direction = direction;
		this.type = type;
		this.dateCreated = dateCreated;
		this.product = product;
		this.profitAndLoss = profitAndLoss;
		this.investingAmount = investingAmount;
		this.price = price;
	}
	
	@Override
	public String toString() {
		return "TradeResponse [id=" + id + ", positionId=" + positionId + ", leverage=" + leverage + ", direction="
				+ direction + ", type=" + type + ", dateCreated=" + dateCreated + ", product=" + product
				+ ", profitAndLoss=" + profitAndLoss + ", investingAmount=" + investingAmount + ", price=" + price
				+ "]";
	}
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getPositionId() {
		return positionId;
	}
	public void setPositionId(String positionId) {
		this.positionId = positionId;
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
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public long getDateCreated() {
		return dateCreated;
	}
	public void setDateCreated(long dateCreated) {
		this.dateCreated = dateCreated;
	}
	public Product getProduct() {
		return product;
	}
	public void setProduct(Product product) {
		this.product = product;
	}
	public Price getProfitAndLoss() {
		return profitAndLoss;
	}
	public void setProfitAndLoss(Price profitAndLoss) {
		this.profitAndLoss = profitAndLoss;
	}
	public Price getInvestingAmount() {
		return investingAmount;
	}
	public void setInvestingAmount(Price investingAmount) {
		this.investingAmount = investingAmount;
	}
	public Price getPrice() {
		return price;
	}
	public void setPrice(Price price) {
		this.price = price;
	}
	
	
	
}
