package com.omnius.omniuseventstore.converter;

import com.omnius.omniuseventstore.domain.Task;
import com.omnius.omniuseventstore.dto.TaskDTO;
import com.omnius.omniuseventstore.util.FormatterUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class DtoToEntityConverter implements Converter<TaskDTO, Task> {
    @Override
    public Task convert(TaskDTO taskDTO) {
        Task task = new Task();
        BeanUtils.copyProperties(taskDTO, task, "id", "createdAt", "updatedAt");
        FormatterUtil.parseFromString(taskDTO.getDueDate())
                .ifPresent(task::setDueDate);
        FormatterUtil.parseFromString(taskDTO.getResolvedAt())
                .ifPresent(task::setResolvedAt);
        return task;
    }
}
