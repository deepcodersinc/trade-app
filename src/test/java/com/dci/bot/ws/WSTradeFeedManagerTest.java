package com.dci.bot.ws;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;

import java.io.IOException;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.dci.bot.model.Position;
import com.dci.bot.ws.listner.TradeQuoteListner;
import com.dci.util.PropertyUtil;
import com.neovisionaries.ws.client.WebSocket;
import com.neovisionaries.ws.client.WebSocketAdapter;
import com.neovisionaries.ws.client.WebSocketFactory;

public class WSTradeFeedManagerTest {	
	
	//@Mock
	WebSocket ws;
	@InjectMocks
	WSTradeFeedManager wsManager;

	@Before
	public void setUp() throws IOException {
		ws = new WebSocketFactory().createSocket("http://localhost:8080/");
		MockitoAnnotations.initMocks(this);
	}
	
	@After
	public void tearDown() {
		SubscriptionMap.getInstance().clear();
	}
	
	//@Test
	public void testSuccessfulSuscription() {
		String message1 = "{\"body\":{\"securityId\":\"producctId1\",\"currentPrice\":\"10.0\",\"timeStamp\":1531497234597},\"t\":\"trading.quote\"}";
		String message2 = "{\"body\":{\"securityId\":\"producctId2\",\"currentPrice\":\"10.0\",\"timeStamp\":1531497234597},\"t\":\"trading.quote\"}";
		Position p1 = new Position("producctId1", 10.0F, 8.0F, 12.0F);
		Position p2 = new Position("producctId2", 10.0F, 8.0F, 12.0F);
			
		try {
			when(ws.addListener(any(TradeQuoteListner.class))).thenReturn(ws);
			wsManager.subscribe(p1);
			wsManager.subscribe(p2);
			assertEquals(SubscriptionMap.getInstance().size(), 2);			

		} catch (Exception e) {
			e.printStackTrace();
			fail(e.getMessage());
			
		}
	}

}
