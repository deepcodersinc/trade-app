package com.dci.bot.ws;

import static org.junit.Assert.fail;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.dci.bot.model.Position;
import com.neovisionaries.ws.client.WebSocket;

public class FeedManagerTest {
	
	@Mock
	WebSocket ws;
	
	@InjectMocks
	FeedManager feedManager;
	
	@Before 
	public void setup() {
		MockitoAnnotations.initMocks(this);
	}
	
	@Test
	public void successfulSubscriptionTest() {
		String message = "{\n" + "\"subscribeTo\": [\n" + "\"trading.product.something\"\n" + "]}";
		try {
			feedManager.subscribe(new Position("something", 10, 12, 8));
			verify(ws, times(1)).sendText(message);
			
		} catch (Exception e) {
			fail();
			e.printStackTrace();
		}
	}
	
	@Test
	public void successfulUnsubscriptionTest() {
		String message = "{\n" + "\"unsubscribeFrom\": [\n" + "\"trading.product.something\"\n" + "]}";
		try {
			feedManager.unSubscribe(new Position("something", 10, 12, 8));
			verify(ws, times(1)).sendText(message);
			
		} catch (Exception e) {
			fail();
			e.printStackTrace();
		}
	}
}
