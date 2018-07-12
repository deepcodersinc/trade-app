package com.dci.bot.model;

import com.google.gson.annotations.SerializedName;

public class Price {
	@SerializedName("currency")
    private String currency;
	
	@SerializedName("decimals")
    private int decimals;
	
	@SerializedName("amount")
    private String amount;
    
    public Price(String currency, int decimals, String amount) {
		super();
		this.currency = currency;
		this.decimals = decimals;
		this.amount = amount;
	}

	@Override
	public String toString() {
		return "InvestingAmount [currency=" + currency + ", decimals=" + decimals + ", amount=" + amount + "]";
	}
	
	public String getCurrency() {
		return currency;
	}
	public void setCurrency(String currency) {
		this.currency = currency;
	}
	public int getDecimals() {
		return decimals;
	}
	public void setDecimals(int decimals) {
		this.decimals = decimals;
	}
	public String getAmount() {
		return amount;
	}
	public void setAmount(String amount) {
		this.amount = amount;
	}
    
    
}
