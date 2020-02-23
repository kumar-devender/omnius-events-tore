package com.omnius.omniuseventstore.converter;

import com.omnius.omniuseventstore.domain.Task;
import com.omnius.omniuseventstore.dto.TaskDTO;
import com.omnius.omniuseventstore.util.FormatterUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import static java.util.Optional.ofNullable;

@Component
public class EntityToDtoConverter implements Converter<Task, TaskDTO> {
    @Override
    public TaskDTO convert(Task task) {
        TaskDTO taskDTO = new TaskDTO();
        BeanUtils.copyProperties(task, taskDTO);
        ofNullable(task.getDueDate())
                .map(FormatterUtil::formatToString)
                .ifPresent(taskDTO::setDueDate);
        ofNullable(task.getResolvedAt())
                .map(FormatterUtil::formatToString)
                .ifPresent(taskDTO::setResolvedAt);
        return taskDTO;
    }
}
