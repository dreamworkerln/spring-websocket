package ru.home.spring.websocket.handler.message;

import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

@Component
//@Order(1)
public class HelloMessageHandler implements MessageHandler {
    @Override
    public boolean handle(WebSocketSession session, String message) throws Exception {

        boolean result = false;

        if (message.startsWith("hello") || message.startsWith("greet") || message.startsWith("hi")) {

            session.sendMessage(new TextMessage("Hello there!"));
            result = true;
        }

        return result;
    }
}
