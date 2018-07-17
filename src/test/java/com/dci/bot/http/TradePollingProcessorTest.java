package com.dci.bot.http;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.concurrent.ConcurrentLinkedQueue;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.dci.bot.model.Position;
import com.dci.util.PropertyUtil;

public class TradePollingProcessorTest {
	@Mock
	TradeOrderClient client;
	@InjectMocks
	TradePollingProcessor processor;
	
	ConcurrentLinkedQueue<String> messageQueue;
	
	
	@Before 
	public void setup() {
		new PropertyUtil().loadProperties("environment-test.properties");
		messageQueue = new ConcurrentLinkedQueue<String>();
		MockitoAnnotations.initMocks(this);
	}
	
	@After
	public void teardown() {
		messageQueue.clear();
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
	public void successfulBuyAtLessQuotePriceTest() {
		createInput(8, 10, 12, 8);		
					
		assertTrue(processor.getPosition().getOrderStatus().isOpen());
		processor.processQuote();
		assertTrue(processor.getPosition().getOrderStatus().isBought());
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
	
	private void createInput(float quotePrice, float buyPrice, float sellUpper, float sellLower) {
		String message = generateQuote(quotePrice);
		Position position = new Position("sb26493", buyPrice, sellUpper, sellLower);
		messageQueue.clear();
		messageQueue.add(message);
		processor = new TradePollingProcessor(position, messageQueue);
	}
	
	private String generateQuote(float quotePrice) {
		return "{\"body\":{\"securityId\":\"sb26493\",\"currentPrice\":\""+quotePrice+"\",\"timeStamp\":1531497234597},\"t\":\"trading.quote\"}";
	}
}
