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

import com.dci.bot.http.TradeOrderClient;
import com.dci.bot.model.Position;
import com.neovisionaries.ws.client.WebSocket;

public class TradeQuoteListnerTest {

	@Mock
	TradeOrderClient tradeOrder;
	@Mock
	WebSocket ws;
	@InjectMocks
	TradeQuoteListner listner;	

	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);
	}
	
	@After
	public void tearDown() {
		TradeFeed.INSTANCE.getSubscriptions().clear();
	}
	

	@Test
	public void testSuccessfulBuyAtEqualPrice() {
		String message = "{\"body\":{\"securityId\":\"producctId1\",\"currentPrice\":\"10.0\",\"timeStamp\":1531497234597},\"t\":\"trading.quote\"}";
		Position p1 = new Position("producctId1", 10.0F, 8.0F, 12.0F);
		TradeFeed.INSTANCE.getSubscriptions().put(p1.getProductId(), p1);

		try {			
			assertFalse(TradeFeed.INSTANCE.getSubscriptions().get("producctId1").isBought());
			listner.onTextMessage(ws, message);
			assertTrue(TradeFeed.INSTANCE.getSubscriptions().get("producctId1").isBought());

		} catch (Exception e) {
			fail(e.getMessage());
		}
	}

	@Test
	public void testSuccessfulBuyAtLessPrice() {
		String message = "{\"body\":{\"securityId\":\"producctId1\",\"currentPrice\":\"9.0\",\"timeStamp\":1531497234597},\"t\":\"trading.quote\"}";
		Position p1 = new Position("producctId1", 10.0F, 8.0F, 12.0F);
		TradeFeed.INSTANCE.getSubscriptions().put(p1.getProductId(), p1);

		try {
			assertFalse(TradeFeed.INSTANCE.getSubscriptions().get("producctId1").isBought());
			listner.onTextMessage(ws, message);
			assertTrue(TradeFeed.INSTANCE.getSubscriptions().get("producctId1").isBought());

		} catch (Exception e) {
			fail(e.getMessage());
		}
	}

	@Test
	public void testUnsuccessfulBuyAtHigherPrice() {
		String message = "{\"body\":{\"securityId\":\"producctId1\",\"currentPrice\":\"12.0\",\"timeStamp\":1531497234597},\"t\":\"trading.quote\"}";
		Position p1 = new Position("producctId1", 10.0F, 8.0F, 12.0F);
		TradeFeed.INSTANCE.getSubscriptions().put(p1.getProductId(), p1);

		try {
			assertFalse(TradeFeed.INSTANCE.getSubscriptions().get("producctId1").isBought());
			listner.onTextMessage(ws, message);
			assertFalse(TradeFeed.INSTANCE.getSubscriptions().get("producctId1").isBought());

		} catch (Exception e) {
			fail(e.getMessage());
		}
	}

	@Test
	public void testSuccessfulSellAtLoss() {
		String message = "{\"body\":{\"securityId\":\"producctId1\",\"currentPrice\":\"10.0\",\"timeStamp\":1531497234597},\"t\":\"trading.quote\"}";
		Position p1 = new Position("producctId1", 10.0F, 8.0F, 12.0F);
		TradeFeed.INSTANCE.getSubscriptions().put(p1.getProductId(), p1);

		try {
			assertFalse(TradeFeed.INSTANCE.getSubscriptions().get("producctId1").isBought());
			listner.onTextMessage(ws, message);
			assertTrue(TradeFeed.INSTANCE.getSubscriptions().get("producctId1").isBought());

			message = "{\"body\":{\"securityId\":\"producctId1\",\"currentPrice\":\"7.0\",\"timeStamp\":1531497234597},\"t\":\"trading.quote\"}";
			listner.onTextMessage(ws, message);
			assertEquals(TradeFeed.INSTANCE.getSubscriptions().size(), 0);
			
			assertFalse(ws.isOpen());

		} catch (Exception e) {
			fail(e.getMessage());
		}
	}

	@Test
	public void testSuccessfulSellAtProfit() {
		String message = "{\"body\":{\"securityId\":\"producctId1\",\"currentPrice\":\"10.0\",\"timeStamp\":1531497234597},\"t\":\"trading.quote\"}";
		Position p1 = new Position("producctId1", 10.0F, 8.0F, 12.0F);
		TradeFeed.INSTANCE.getSubscriptions().put(p1.getProductId(), p1);

		try {			
			assertFalse(TradeFeed.INSTANCE.getSubscriptions().get("producctId1").isBought());
			listner.onTextMessage(ws, message);
			assertTrue(TradeFeed.INSTANCE.getSubscriptions().get("producctId1").isBought());

			message = "{\"body\":{\"securityId\":\"producctId1\",\"currentPrice\":\"14.0\",\"timeStamp\":1531497234597},\"t\":\"trading.quote\"}";
			listner.onTextMessage(ws, message);
			assertEquals(TradeFeed.INSTANCE.getSubscriptions().size(), 0);
			
			assertFalse(ws.isOpen());

		} catch (Exception e) {
			e.printStackTrace();
			fail(e.getMessage());
		}
	}

}
