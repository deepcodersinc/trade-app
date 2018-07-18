package com.dci.bot.http;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.anyFloat;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.when;

import java.util.concurrent.ConcurrentLinkedQueue;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.dci.bot.exception.ApplicationException;
import com.dci.bot.exception.BuyOrderException;
import com.dci.bot.model.Position;

public class TradePollingProcessorTest {
	@Mock
	TradeOrderClient client;

	ConcurrentLinkedQueue<String> messageQueue;

	@InjectMocks
	TradePollingProcessor processor;

	@Before
	public void setup() throws BuyOrderException, ApplicationException {
		MockitoAnnotations.initMocks(this);
		messageQueue = new ConcurrentLinkedQueue<String>();
		when(client.openPosition(anyString(), anyFloat())).thenReturn("posi");
	}

	@Test
	public void pollingTest() {
		createInput(10, 10, 12, 8);

		assertEquals(messageQueue.size(), 1);
		processor.processQuote();
		assertEquals(messageQueue.size(), 0);
	}

	@Test
	public void successfulBuyAtSameQuotePriceTest() {
		createInput(10, 10, 12, 8);

		assertTrue(processor.getPosition().getOrderStatus().isOpen());
		processor.processQuote();
		assertTrue(processor.getPosition().getOrderStatus().isBought());
	}

	@Test
	public void unsuccessfulBuyAtLessQuotePriceTest() {
		createInput(8, 10, 12, 8);

		assertTrue(processor.getPosition().getOrderStatus().isOpen());
		processor.processQuote();
		assertFalse(processor.getPosition().getOrderStatus().isBought());
	}

	@Test
	public void successfulSellAtProfitTest() {
		createInput(10, 10, 12, 8);
		processor.processQuote();
		assertTrue(processor.getPosition().getOrderStatus().isBought());

		messageQueue.add(generateQuote(14));
		processor.processQuote();
		assertTrue(processor.getPosition().getOrderStatus().isSold());
	}

	@Test
	public void successfulSellAtLossTest() {
		createInput(10, 10, 12, 8);
		processor.processQuote();
		assertTrue(processor.getPosition().getOrderStatus().isBought());

		messageQueue.add(generateQuote(8));
		processor.processQuote();
		assertTrue(processor.getPosition().getOrderStatus().isSold());
	}

	@Test
	public void unsuccessfulSellAtLossTest() {
		createInput(10, 10, 12, 8);
		processor.processQuote();
		assertTrue(processor.getPosition().getOrderStatus().isBought());

		messageQueue.add(generateQuote(8.5F));
		processor.processQuote();
		assertFalse(processor.getPosition().getOrderStatus().isSold());
	}
	
	private void createInput(float quotePrice, float buy, float sellUpper, float sellLower) {
		messageQueue.clear();
		messageQueue.add(generateQuote(quotePrice));
		processor = new TradePollingProcessor(new Position("", buy, sellUpper, sellLower), messageQueue);
		processor.setTradeOrderClient(client);
	}
	
	

	private String generateQuote(float quotePrice) {
		return "{\"body\":{\"securityId\":\"sb26493\",\"currentPrice\":\"" + quotePrice
				+ "\",\"timeStamp\":1531497234597},\"t\":\"trading.quote\"}";
	}

}
