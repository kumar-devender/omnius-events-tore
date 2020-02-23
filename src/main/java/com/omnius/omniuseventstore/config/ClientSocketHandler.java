package com.omnius.omniuseventstore.config;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.omnius.omniuseventstore.dto.TaskDTO;
import com.omnius.omniuseventstore.service.TaskService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

@Component
@RequiredArgsConstructor
@Slf4j
public class ClientSocketHandler extends TextWebSocketHandler {
    private static List<WebSocketSession> sessions = new CopyOnWriteArrayList<>();
    private final TaskService taskService;
    private final ObjectMapper objectMapper;

    public void sendMessage() {
        try {
            String taskListAsString = getTasksAsString();
            for (WebSocketSession webSocketSession : sessions) {
                if (webSocketSession.isOpen()) {
                    webSocketSession.sendMessage(new TextMessage(taskListAsString));
                } else {
                    sessions.remove(webSocketSession);
                }
            }
        } catch (IOException e) {
            log.warn("could not convert task list to string [{}]", e);
        }
    }

    private String getTasksAsString() throws JsonProcessingException {
        List<TaskDTO> taskDTOS = taskService.getTasks();
        return objectMapper.writeValueAsString(taskDTOS);
    }

    @Override
    public void afterConnectionEstablished(WebSocketSession session) {
        sessions.add(session);
    }

}