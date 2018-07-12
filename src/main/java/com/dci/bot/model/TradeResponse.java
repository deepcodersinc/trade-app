package com.dci.bot.model;

import com.google.gson.annotations.SerializedName;

public class TradeResponse {
	@SerializedName("positionId")
	private String positionId;
	
	@SerializedName("profitAndLoss")
	private Price profitAndLoss;

	public String getPositionId() {
		return positionId;
	}

	public void setPositionId(String positionId) {
		this.positionId = positionId;
	}

	public Price getProfitAndLoss() {
		return profitAndLoss;
	}

	public void setProfitAndLoss(Price profitAndLoss) {
		this.profitAndLoss = profitAndLoss;
	}
	
	
}
