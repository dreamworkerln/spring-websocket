package ru.home.spring.websocket.handler.message;

import org.springframework.core.annotation.Order;
import org.springframework.web.socket.WebSocketSession;

/**
 * can close WebSocketSession
 */
@Order(0) // - do not remove , or else @Order(40000) on bean BroadcastMessage won't work
public interface MessageHandler {
    /**
     * Handle message from client
     * @param session
     * @param message
     * @return do interrupt further handling
     * @throws Exception
     */
    boolean handle(WebSocketSession session, String message) throws Exception;
}
