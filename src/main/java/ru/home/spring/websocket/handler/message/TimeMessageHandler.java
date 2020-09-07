package ru.home.spring.websocket.handler.message;

import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import java.time.Instant;

@Component
//@Order(1)
public class TimeMessageHandler implements MessageHandler {
    @Override
    public boolean handle(WebSocketSession session, String message) throws Exception {

        boolean result = false;
        if (message.startsWith("time")) {

            Instant currentTime = Instant.now();
            session.sendMessage(new TextMessage(currentTime.toString()));
            result = true;
        }

        return result;
    }
}
