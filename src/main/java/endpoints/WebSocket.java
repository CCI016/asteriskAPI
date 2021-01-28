package endpoints;

import io.quarkus.scheduler.Scheduled;
import org.slf4j.Logger;

import javax.enterprise.context.ApplicationScoped;
import javax.websocket.OnClose;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@ServerEndpoint("/websocket")
@ApplicationScoped
public class WebSocket {
    private final Map<String, Session> sessions = new ConcurrentHashMap<>();

    @OnOpen
    public void onOpen(Session session) {
        sessions.put(session.getId(), session);
    }

    @OnClose
    public void onClose(Session session) {
        sessions.remove(session.getId());
    }

    @Scheduled(every = "1s")
    public void updateTime() {
        sessions.values().forEach(session -> sendMessage(session, String.format("{\"time\":\"%s\"}",
                LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")))));
    }

    private void sendMessage(Session session, String format) {
        try {
            session.getBasicRemote().sendText(format);
        } catch (IOException e) {
            onClose(session);
        }
    }
}
