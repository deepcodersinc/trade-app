package com.dci.bot.model;

public class Source {
	private String sourceType;
	private String sourceId;
	
	public Source(String sourceType, String sourceId) {
		super();
		this.sourceType = sourceType;
		this.sourceId = sourceId;
	}
	
	@Override
	public String toString() {
		return "Source [sourceType=" + sourceType + ", sourceId=" + sourceId + "]";
	}	
	
	public String getSourceType() {
		return sourceType;
	}
	public void setSourceType(String sourceType) {
		this.sourceType = sourceType;
	}
	public String getSourceId() {
		return sourceId;
	}
	public void setSourceId(String sourceId) {
		this.sourceId = sourceId;
	}	
}
