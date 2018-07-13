package com.dci.bot.http;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnit;

import com.dci.util.PropertyUtil;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;


public class RestTradeOrderManagerTest {
	@Mock
	private OkHttpClient client;
	
	@Mock
	private Response response;

	@InjectMocks 
	private RestTradeOrderClient orderManager;

	@Before public void setup() {
		PropertyUtil.INSTANCE.setPropertyFile("environment-test.properties");
		MockitoAnnotations.initMocks(this);
	}


	//@Test
	public void testBuyTrade() {
		String responseJson = "{\\r\\n\\\"id\\\": \\\"9bc887eb-23d2-4293-a139-1da9c4eb5f1c\\\",\\r\\n\\\"positionId\\\": \\\"655ddda5-fd6d-48a9-800d-b7b93eb041af\\\",\\r\\n\\\"product\\\": {\\r\\n\\\"securityId\\\": \\\"26618\\\",\\r\\n\\\"symbol\\\": \\\"EUR/USD\\\",\\r\\n\\\"displayName\\\": \\\"EUR/USD\\\"\\r\\n},\\r\\n\\\"investingAmount\\\": {\\r\\n\\\"currency\\\": \\\"BUX\\\",\\r\\n\\\"decimals\\\": 2,\\r\\n\\\"amount\\\": \\\"10.00\\\"\\r\\n},\\r\\n\\\"price\\\": {\\r\\n\\\"currency\\\": \\\"USD\\\",\\r\\n\\\"decimals\\\": 5,\\r\\n\\\"amount\\\": \\\"1.07250\\\"\\r\\n},\\r\\n\\\"leverage\\\": 2,\\r\\n\\\"direction\\\": \\\"BUY\\\",\\r\\n\\\"type\\\": \\\"OPEN\\\",\\r\\n\\\"dateCreated\\\": 1492601296549\\r\\n}";
		//Response response = Response.error(200, ResponseBody.create(MediaType.parse("application/json") ,responseJson));	
		
		
		
		try {
			when(response.body().toString()).thenReturn(responseJson);
			when(client.newCall(any(Request.class)).execute()).thenReturn(response);
			
			assertThat(orderManager.openPosition("ss", 10.0F), is("655ddda5-fd6d-48a9-800d-b7b93eb041af"));
						
		} catch (Exception e) {
			fail();
		}		
	}

}
