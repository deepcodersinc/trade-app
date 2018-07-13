package com.dci.bot.ws;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.dci.bot.http.RestTradeOrderClient;
import com.dci.bot.model.Position;
import com.dci.bot.ws.listner.TradeQuoteListner;
import com.neovisionaries.ws.client.WebSocket;

public class TradeQuoteListnerTest {

	@Mock
	RestTradeOrderClient tradeOrder;
	@Mock
	WebSocket ws;
	@InjectMocks
	TradeQuoteListner listner;	

	SubscriptionMap<String, Position> subscription;

	@Before
	public void setUp() {
		subscription = SubscriptionMap.getInstance();
		MockitoAnnotations.initMocks(this);
	}
	
	@After
	public void tearDown() {
		subscription.clear();
	}
	

	@Test
	public void testSuccessfulBuyAtEqualPrice() {
		String message = "{\"body\":{\"securityId\":\"producctId1\",\"currentPrice\":\"10.0\",\"timeStamp\":1531497234597},\"t\":\"trading.quote\"}";
		Position p1 = new Position("producctId1", 10.0F, 8.0F, 12.0F);
		subscription.put(p1.getProductId(), p1);

		try {			
			assertFalse(subscription.get("producctId1").isBought());
			listner.onTextMessage(ws, message);
			assertTrue(subscription.get("producctId1").isBought());

		} catch (Exception e) {
			fail(e.getMessage());
		}
	}

	@Test
	public void testSuccessfulBuyAtLessPrice() {
		String message = "{\"body\":{\"securityId\":\"producctId1\",\"currentPrice\":\"9.0\",\"timeStamp\":1531497234597},\"t\":\"trading.quote\"}";
		Position p1 = new Position("producctId1", 10.0F, 8.0F, 12.0F);
		subscription.put(p1.getProductId(), p1);

		try {
			assertFalse(subscription.get("producctId1").isBought());
			listner.onTextMessage(ws, message);
			assertTrue(subscription.get("producctId1").isBought());

		} catch (Exception e) {
			fail(e.getMessage());
		}
	}

	@Test
	public void testUnsuccessfulBuyAtHigherPrice() {
		String message = "{\"body\":{\"securityId\":\"producctId1\",\"currentPrice\":\"12.0\",\"timeStamp\":1531497234597},\"t\":\"trading.quote\"}";
		Position p1 = new Position("producctId1", 10.0F, 8.0F, 12.0F);
		subscription.put(p1.getProductId(), p1);

		try {
			assertFalse(subscription.get("producctId1").isBought());
			listner.onTextMessage(ws, message);
			assertFalse(subscription.get("producctId1").isBought());

		} catch (Exception e) {
			fail(e.getMessage());
		}
	}

	@Test
	public void testSuccessfulSellAtLoss() {
		String message = "{\"body\":{\"securityId\":\"producctId1\",\"currentPrice\":\"10.0\",\"timeStamp\":1531497234597},\"t\":\"trading.quote\"}";
		Position p1 = new Position("producctId1", 10.0F, 8.0F, 12.0F);
		subscription.put(p1.getProductId(), p1);

		try {
			assertFalse(subscription.get("producctId1").isBought());
			listner.onTextMessage(ws, message);
			assertTrue(subscription.get("producctId1").isBought());

			message = "{\"body\":{\"securityId\":\"producctId1\",\"currentPrice\":\"7.0\",\"timeStamp\":1531497234597},\"t\":\"trading.quote\"}";
			listner.onTextMessage(ws, message);
			assertEquals(subscription.size(), 0);
			
			assertFalse(ws.isOpen());

		} catch (Exception e) {
			fail(e.getMessage());
		}
	}

	@Test
	public void testSuccessfulSellAtProfit() {
		String message = "{\"body\":{\"securityId\":\"producctId1\",\"currentPrice\":\"10.0\",\"timeStamp\":1531497234597},\"t\":\"trading.quote\"}";
		Position p1 = new Position("producctId1", 10.0F, 8.0F, 12.0F);
		subscription.put(p1.getProductId(), p1);

		try {			
			assertFalse(subscription.get("producctId1").isBought());
			listner.onTextMessage(ws, message);
			assertTrue(subscription.get("producctId1").isBought());

			message = "{\"body\":{\"securityId\":\"producctId1\",\"currentPrice\":\"14.0\",\"timeStamp\":1531497234597},\"t\":\"trading.quote\"}";
			listner.onTextMessage(ws, message);
			assertEquals(subscription.size(), 0);
			
			assertFalse(ws.isOpen());

		} catch (Exception e) {
			fail(e.getMessage());
		}
	}

}
