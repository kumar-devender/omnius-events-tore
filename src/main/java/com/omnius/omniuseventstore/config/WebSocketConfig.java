package com.omnius.omniuseventstore.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.omnius.omniuseventstore.service.TaskService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

@Configuration
@EnableWebSocket
@RequiredArgsConstructor
public class WebSocketConfig implements WebSocketConfigurer {

    private final TaskService taskService;
    private final ObjectMapper objectMapper;

    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry.addHandler(getClientSocketHandler(), "/register");
    }

    @Bean
    public ClientSocketHandler getClientSocketHandler() {
        return new ClientSocketHandler(taskService, objectMapper);
    }
}