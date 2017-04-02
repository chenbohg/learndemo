package com.paul.marcro;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.AbstractWebSocketHandler;

public class MarcroHandler extends AbstractWebSocketHandler{
	
	private static final Logger logger = LoggerFactory.getLogger(MarcroHandler.class) ;
	
	@Override
	protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
		logger.info("recevied message:" + message.getPayload());
		Thread.sleep(2000);
		session.sendMessage(new TextMessage("send message"));
	}
}
