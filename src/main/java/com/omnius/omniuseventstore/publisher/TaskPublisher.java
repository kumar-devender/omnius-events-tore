package com.omnius.omniuseventstore.publisher;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.omnius.omniuseventstore.domain.Task;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class TaskPublisher {
    private final JmsTemplate jmsTemplate;
    private final ObjectMapper objectMapper;

    public void publishTask(Task task) {
        log.info("Publishing new task [{}]" + task);
        try {
            jmsTemplate.convertAndSend("omnius_task_queue", objectMapper.writeValueAsString(task));
        } catch (JsonProcessingException e) {
            log.warn("Error in publishing message [{}]", e);
        }
    }
}
