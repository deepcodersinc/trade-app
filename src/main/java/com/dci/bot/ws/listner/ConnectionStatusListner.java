package com.dci.bot.ws.listner;

import com.dci.bot.exception.ApplicationException;
import com.dci.util.JsonUtil;
import com.neovisionaries.ws.client.WebSocket;
import com.neovisionaries.ws.client.WebSocketAdapter;

public class ConnectionStatusListner extends WebSocketAdapter {
	
	private static final String CONNECTED_SUCCESS = "connect.connected";
	private boolean connected = false;
	
	@Override
	public void onTextMessage(WebSocket websocket, String message) throws Exception {							
		connected = CONNECTED_SUCCESS.equals(JsonUtil.INSTANCE.getJsonValue(message, "t"));
		
		if(!connected) {
			throw new ApplicationException(
					JsonUtil.INSTANCE.getJsonNestedValue(message, "body", "developerMessage"),
					JsonUtil.INSTANCE.getJsonNestedValue(message, "body", "errorCode")
			);
		}
		websocket.removeListener(this);
	}
	
	public boolean isConnected() {
		return this.connected;
	}
}
