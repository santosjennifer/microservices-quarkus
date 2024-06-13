package com.github.ifood.resource;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import jakarta.websocket.OnClose;
import jakarta.websocket.OnOpen;
import jakarta.websocket.Session;
import jakarta.websocket.server.ServerEndpoint;

@ServerEndpoint("/localizacoes")
public class LocalizacaoWebSocket {

    private static final Set<Session> sessions = new HashSet<>();

    @OnOpen
    public void onOpen(Session session) {
        sessions.add(session);
    }

    @OnClose
    public void onClose(Session session) {
        sessions.remove(session);
    }

    public static void enviarMensagem(String mensagem) throws IOException {
        for (Session session : sessions) {
            session.getBasicRemote().sendText(mensagem);
        }
    }
}