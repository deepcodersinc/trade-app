package com.dci.bot.model;

public class Quote {
	String t;
	Body body;
	
	
	public String getT() {
		return t;
	}
	public void setT(String t) {
		this.t = t;
	}
	public Body getBody() {
		return body;
	}
	public void setBody(Body body) {
		this.body = body;
	}


	public class Body {
		String securityId;
		float currentPrice;
		public String getSecurityId() {
			return securityId;
		}
		public void setSecurityId(String securityId) {
			this.securityId = securityId;
		}
		public float getCurrentPrice() {
			return currentPrice;
		}
		public void setCurrentPrice(float currentPrice) {
			this.currentPrice = currentPrice;
		}		
	}		
}
