package edu.hfut.websocket;

import org.springframework.stereotype.Component;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author panbaoqiang
 * @Description
 * @create 2020-04-30 9:02
 */
@Component
@ServerEndpoint("/websocket/{username}")
public class WebSocket {
    private static int onlineCount = 0;
    private static Map<String, WebSocket> clients = new ConcurrentHashMap<String, WebSocket>();
    private Session session;
    private String username;

    @OnOpen
    public void onOpen(@PathParam("username") String username, Session session) throws IOException {
        this.username = username;
        this.session = session;
        System.out.println(session);
        addOnlineCount();
        clients.put(username, this);
        System.out.println("已连接" + username);
    }

    @OnClose
    public void onClose() throws IOException {
        System.out.println("链接关闭");
        clients.remove(username);
        subOnlineCount();
    }

    @OnMessage
    public void onMessage(String message) throws IOException {

        System.out.println("message:" + message);
//		System.out.println("item:" + req.getJSONObject("item"));
//		System.out.println("item:" + req.getInteger("code"));

        // 发送数据给服务端
        sendMessageAll(message);
    }

    @OnError
    public void onError(Session session, Throwable error) {
        error.printStackTrace();
    }

    public void sendMessageTo(String message, String To) throws IOException {
        // session.getBasicRemote().sendText(message);
        // session.getAsyncRemote().sendText(message);
        for (WebSocket item : clients.values()) {
            if (item.username.equals(To))
                item.session.getAsyncRemote().sendText(message);
        }
    }

    public void sendMessageAll(String message) throws IOException {
        for (WebSocket item : clients.values()) {
            item.session.getAsyncRemote().sendText(message+"ans");
        }
    }

    public static synchronized int getOnlineCount() {
        return onlineCount;
    }

    public static synchronized void addOnlineCount() {
        WebSocket.onlineCount++;
    }

    public static synchronized void subOnlineCount() {
        WebSocket.onlineCount--;
    }

    public static synchronized Map<String, WebSocket> getClients() {
        return clients;
    }
}
