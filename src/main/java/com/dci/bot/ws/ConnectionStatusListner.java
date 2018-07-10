package com.dci.bot.ws;

import com.dci.bot.exception.ApplicationException;
import com.dci.util.TraderUtil;
import com.neovisionaries.ws.client.WebSocket;
import com.neovisionaries.ws.client.WebSocketAdapter;

public class ConnectionStatusListner extends WebSocketAdapter {
	
	boolean connected = false;
	
	@Override
	public void onTextMessage(WebSocket websocket, String message) throws Exception {		
			String status = TraderUtil.getJsonValue(message, "t");
			this.connected = "connect.connected".equalsIgnoreCase(status);
			System.out.println(status);
			
			if("connect.failed".equalsIgnoreCase(status)) {
				throw new ApplicationException(
						TraderUtil.getJsonNestedValue(message, "body", "developerMessage"),
						TraderUtil.getJsonNestedValue(message, "body", "errorCode")
				);
			}			
	}
	
	public boolean isConnected() {
		return this.connected;
	}
}
