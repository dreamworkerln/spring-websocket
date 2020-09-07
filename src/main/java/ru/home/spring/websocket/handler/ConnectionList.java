package ru.home.spring.websocket.handler;

import org.springframework.stereotype.Component;
import org.springframework.web.socket.WebSocketSession;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

@Component
public class ConnectionList implements Iterable<Map.Entry<String, WebSocketSession>> {



    private static AtomicLong idGen = new AtomicLong(1L);

    private final static String ID_NAME = "id";

    private Map<String, WebSocketSession> connections = new HashMap<>();

    private Map<Long, WebSocketSession> longIdx = new HashMap<>();

    public void add(WebSocketSession session) {

        long id = idGen.getAndIncrement();
        session.getAttributes().put(ID_NAME, id);

        //long id = idGen.getAndIncrement();
        connections.put(session.getId(), session);
        longIdx.put(id, session);
    }

    public WebSocketSession get(String id) {
        return connections.get(id);
    }

    public WebSocketSession get(long id) {
        return longIdx.get(id);
    }

    public void remove(String id) {

        long longId = getLongIdByStringId(id);
        connections.remove(id);
        longIdx.remove(longId);
    }

    public void remove(long id) {
        String stringId = longIdx.get(id).getId();
        connections.remove(stringId);
    }

    // ------------------------------------------------------------------

    private long getLongIdByStringId(String id) {
        return (long) connections.get(id).getAttributes().get(ID_NAME);
    }


    @Override
    public Iterator<Map.Entry<String, WebSocketSession>> iterator() {
        return connections.entrySet().iterator();
    }
}
