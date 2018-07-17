package com.dci.bot.ws;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dci.bot.exception.ApplicationException;
import com.dci.util.PropertyUtil;
import com.neovisionaries.ws.client.WebSocket;
import com.neovisionaries.ws.client.WebSocketException;
import com.neovisionaries.ws.client.WebSocketFactory;

public class WSConnector {
	
	private final Logger logger = LoggerFactory.getLogger(WSConnector.class);
	private final static String AUTH = "Bearer eyJhbGciOiJIUzI1NiJ9.eyJyZWZyZXNoYWJsZSI6ZmFsc2UsInN1YiI6ImJiMGNkYTJiLWExMGUtNGVkMy1hZDVhLTBmODJiNGMxNTJjNCIsImF1ZCI6ImJldGEuZ2V0YnV4LmNvbSIsInNjcCI6WyJhcHA6bG9naW4iLCJydGY6bG9naW4iXSwiZXhwIjoxODIwODQ5Mjc5LCJpYXQiOjE1MDU0ODkyNzksImp0aSI6ImI3MzlmYjgwLTM1NzUtNGIwMS04NzUxLTMzZDFhNGRjOGY5MiIsImNpZCI6Ijg0NzM2MjI5MzkifQ.M5oANIi2nBtSfIfhyUMqJnex-JYg6Sm92KPYaUL9GKg";
	
	public WebSocket getConnection() throws ApplicationException {
		
		WebSocket ws;
		ConnectionListner listner = new ConnectionListner();

		try {
			ws = new WebSocketFactory()
					.createSocket(PropertyUtil.getValue("trade.ws.url"))
					.addHeader("Accept-Language", "nl-NL,en;q=0.8")
					.addHeader("Authorization", AUTH)
					.addListener(listner)
					.connect();

			logger.info("Connecting...");

			while (!listner.isConnected()) {
				logger.info(".");
			}
			logger.info("Connected");
			return ws;

		} catch (WebSocketException e) {
			e.printStackTrace();
			throw new ApplicationException(e.getMessage());
		} catch (IOException e) {
			throw new ApplicationException(e.getMessage());
		}
	}
}
