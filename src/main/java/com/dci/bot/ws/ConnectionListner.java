package com.dci.bot.ws;

import com.dci.bot.exception.ApplicationException;
import com.dci.bot.model.Quote;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.neovisionaries.ws.client.WebSocket;
import com.neovisionaries.ws.client.WebSocketAdapter;
import com.neovisionaries.ws.client.WebSocketException;

public class ConnectionListner extends WebSocketAdapter {

	private static final String CONNECTED_SUCCESS = "connect.connected";
	private boolean connected = false;

	@Override
	public void onTextMessage(WebSocket websocket, String message) throws Exception {
		connected = CONNECTED_SUCCESS.equals(new GsonBuilder().create().fromJson(message, Quote.class).getT());

		if (!connected) {					
			throw new Gson().fromJson(message, ApplicationException.class);
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
