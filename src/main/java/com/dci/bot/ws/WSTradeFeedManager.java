package com.dci.bot.ws;

import java.io.IOException;

import javax.net.ssl.SNIHostName;
import javax.net.ssl.SSLContext;

import com.dci.bot.exception.ApplicationException;
import com.neovisionaries.ws.client.ProxySettings;
import com.neovisionaries.ws.client.WebSocket;
import com.neovisionaries.ws.client.WebSocketException;
import com.neovisionaries.ws.client.WebSocketFactory;

public class WSTradeFeedManager {
	
	private static WebSocket ws;

	private static String authHeader = "Bearer "
			+ "eyJhbGciOiJIUzI1NiJ9.eyJyZWZyZXNoYWJsZSI6ZmFsc2UsInN1YiI6ImJiMGNkYTJiLWE"
			+ "xMGUtNGVkMy1hZDVhLTBmODJiNGMxNTJjNCIsImF1ZCI6ImJldGEuZ2V0YnV4LmNvbSIsInN"
			+ "jcCI6WyJhcHA6bG9naW4iLCJydGY6bG9naW4iXSwiZXhwIjoxODIwODQ5Mjc5LCJpYXQiOjE"
			+ "1MDU0ODkyNzksImp0aSI6ImI3MzlmYjgwLTM1NzUtNGIwMS04NzUxLTMzZDFhNGRjOGY5MiI"
			+ "sImNpZCI6Ijg0NzM2MjI5MzkifQ.M5oANIi2nBtSfIfhyUMqJnex-JYg6Sm92KPYaUL9GKg";

	private static final String WS_URL = "ws://localhost:8080/subscriptions/me";
	//private static final String WS_URL = "https://rtf.beta.getbux.com/subscriptions/me";
	
	private static WSTradeFeedManager instance;
		
	private WSTradeFeedManager() {
		
	}
	
	public static WSTradeFeedManager getWebSocketConnection(ConnectionStatusListner listner) throws ApplicationException {
		
				
		if(instance == null) {
			instance = new WSTradeFeedManager();
			try {	
				
				WebSocketFactory factory = new WebSocketFactory();
				factory.getProxySettings().setServer(WS_URL);
								
				ws = factory
						//.setServerName("rtf.beta.getbux.com")	
						//.setVerifyHostname(false)
						.createSocket(WS_URL)						
						.addHeader("Accept-Language", "nl-NL,en;q=0.8")
						.addHeader("Authorization", authHeader)
						.addListener(listner)
						.connect();
				
				while(!listner.isConnected()) {					
					System.out.print("");
				}				
			} catch (WebSocketException e) {
				throw new ApplicationException(e.getMessage(), "0000");
			} catch (IOException e) {
				throw new ApplicationException(e.getMessage(), "0001");
			} finally {
				//ws.removeListener(listner);
			}
		}		
		
		return instance;
	}	
	
	public void subscribe(String json, FeedChannelListner listner) throws Exception {
		System.out.println("----Subscribe");
		ws.addListener(listner);
		ws.sendText(json);		
	}
	
	public void unsubscribe() {
		if(ws != null) {
			ws.disconnect();
		}
	}	
}
