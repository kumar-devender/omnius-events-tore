package com.omnius.omniuseventstore.listener;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.omnius.omniuseventstore.config.ClientSocketHandler;
import com.omnius.omniuseventstore.domain.Task;
import com.omnius.omniuseventstore.service.TaskService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.TextMessage;

@Component
@RequiredArgsConstructor
@Slf4j
public class TaskListener {
    private final TaskService taskService;
    private final ObjectMapper objectMapper;
    private final ClientSocketHandler clientSocketHandler;

    @JmsListener(destination = "omnius_task_queue", containerFactory = "taskListenerFactory")
    public void receiveMessage(Message message) {
        log.info("Received {{}}", message);
        if (message instanceof TextMessage) {
            TextMessage textMessage = (TextMessage) message;
            try {
                String messageData = textMessage.getText();
                Task task = objectMapper.readValue(messageData, Task.class);
                taskService.saveTask(task);
                clientSocketHandler.sendMessage();
            } catch (JMSException | JsonProcessingException e) {
                log.warn("Can not convert message to task {}", e);
            }
        }
    }
}
