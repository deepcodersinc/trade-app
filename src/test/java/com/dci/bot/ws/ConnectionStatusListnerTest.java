package com.dci.bot.ws;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.dci.bot.ws.listner.ConnectionStatusListner;
import com.dci.util.PropertyUtil;
import com.neovisionaries.ws.client.WebSocket;

public class ConnectionStatusListnerTest {

	@Mock
	WebSocket ws;
	@InjectMocks
	ConnectionStatusListner listner;

	@Before
	public void setup() {
		PropertyUtil.INSTANCE.setPropertyFile("environment-test.properties");
		MockitoAnnotations.initMocks(this);
	}

	@Test
	public void testSuccessfulConnection() {
		String message = "{\"body\":{\"userId\":\"bb0cda2b-a10e-4ed3-ad5a-0f82b4c152c4\",\"sessionId\":\"c541db59-3abe-4d79-8d98-66ba6dba8158\",\"time\":1531512340450},\"t\":\"connect.connected\"}";
		
		try {			
			listner.onTextMessage(ws, message);
			assertTrue(listner.isConnected());
			
		} catch (Exception e) {
			fail();
		}
	}

	@Test
	public void testFailedConnection() {
		String message = "{\"t\": \"connect.failed\",\"body\": {\"developerMessage\": \"Missing JWT Access Token in request\",\"errorCode\": \"RTF_002\"}}";
		
		try {			
			listner.onTextMessage(ws, message);
			assertFalse(listner.isConnected());
			
		} catch (Exception e) {
			assertTrue(true);
		}
	}
}
