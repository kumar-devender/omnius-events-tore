package com.omnius.omniuseventstore.converter;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.omnius.omniuseventstore.domain.Task;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jms.support.converter.MessageConversionException;
import org.springframework.jms.support.converter.MessageConverter;
import org.springframework.stereotype.Component;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;
import javax.jms.TextMessage;

@Component
@Slf4j
@RequiredArgsConstructor
public class TaskMessageConverter implements MessageConverter {
    private final ObjectMapper mapper;

    @Override
    public Message toMessage(Object object, Session session) throws JMSException, MessageConversionException {
        TextMessage message = session.createTextMessage();
        message.setText(object.toString());
        return message;
    }

    @Override
    public Object fromMessage(Message message) throws JMSException, MessageConversionException {
        TextMessage textMessage = (TextMessage) message;
        String payload = textMessage.getText();

        Task task = null;
        try {
            task = mapper.readValue(payload, Task.class);
        } catch (Exception e) {
            log.warn("Can not convert to Task [{}]", e);
        }
        return task;
    }
}