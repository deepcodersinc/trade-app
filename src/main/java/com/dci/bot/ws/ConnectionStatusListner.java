package com.dci.bot.ws;

import com.dci.bot.exception.ApplicationException;
import com.dci.util.JsonUtil;
import com.neovisionaries.ws.client.WebSocket;
import com.neovisionaries.ws.client.WebSocketAdapter;
import com.neovisionaries.ws.client.WebSocketException;

public class ConnectionStatusListner extends WebSocketAdapter {

	private static final String CONNECTED_SUCCESS = "connect.connected";
	private boolean connected = false;

	@Override
	public void onTextMessage(WebSocket websocket, String message) throws Exception {
		connected = CONNECTED_SUCCESS.equals(JsonUtil.INSTANCE.getJsonValue(message, "t"));

		if (!connected) {
			throw new ApplicationException(JsonUtil.INSTANCE.getJsonNestedValue(message, "body", "developerMessage"),
					JsonUtil.INSTANCE.getJsonNestedValue(message, "body", "errorCode"));
		}
		websocket.removeListener(this);
	}

	@Override
	public void onConnectError(WebSocket websocket, WebSocketException exception) throws Exception {
		throw new ApplicationException(exception.getMessage());
	}

	@Override
	public void onError(WebSocket websocket, WebSocketException cause) throws Exception {
		throw new ApplicationException(cause.getMessage());
	}

	public boolean isConnected() {
		return this.connected;
	}
}
