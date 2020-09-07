package ru.home.spring.websocket.handler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;
import ru.home.spring.websocket.handler.message.MessageHandler;

import java.time.Instant;
import java.util.List;
import java.util.Map;

@Component
@Slf4j
public class CustomWebSocketHandler extends TextWebSocketHandler {

    private final ConnectionList connectionList;
    private final List<MessageHandler> handlerList;

    public CustomWebSocketHandler(ConnectionList connectionList, List<MessageHandler> handlerList) {
        this.connectionList = connectionList;
        this.handlerList = handlerList;
    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {

        String clientMessage = message.getPayload();

        // handling incoming message
        for (MessageHandler messageHandler : handlerList) {

            boolean interrupt = messageHandler.handle(session, clientMessage);
            if(interrupt) {
                break;
            }
        }
    }


    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {

        connectionList.add(session);
        log.info(session.getId());

        super.afterConnectionEstablished(session);
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {

        connectionList.remove(session.getId());
        super.afterConnectionClosed(session, status);
    }
}
