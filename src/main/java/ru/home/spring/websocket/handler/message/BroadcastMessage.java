package ru.home.spring.websocket.handler.message;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import ru.home.spring.websocket.handler.ConnectionList;

import java.util.Map;

@Component
@Order(40000)
public class BroadcastMessage implements MessageHandler {

    private final ConnectionList connectionList;

    @Autowired
    public BroadcastMessage(ConnectionList connectionList) {
        this.connectionList = connectionList;
    }

    @Override
    public boolean handle(WebSocketSession session, String message) throws Exception {

        long id = (long) session.getAttributes().get("id");
        TextMessage broadcastMessage = new TextMessage(id + ": " + message);
        for (Map.Entry<String, WebSocketSession> entry : connectionList) {
            entry.getValue().sendMessage(broadcastMessage);
        }

        return true;
    }
}
