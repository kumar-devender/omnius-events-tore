package com.omnius.omniuseventstore.service;

import com.omnius.omniuseventstore.converter.ConverterService;
import com.omnius.omniuseventstore.domain.Task;
import com.omnius.omniuseventstore.dto.TaskDTO;
import com.omnius.omniuseventstore.errors.NotFoundException;
import com.omnius.omniuseventstore.repository.TaskRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.UUID;

import static java.util.stream.Collectors.toList;

@Service
@RequiredArgsConstructor
@Slf4j
public class TaskService {
    private final TaskRepository taskRepository;
    private final ConverterService converterService;

    public TaskDTO saveTask(Task task) {
        log.info("Saving new task [{}]", task);
        TaskDTO taskDTO = converterService.convert(taskRepository.save(task), TaskDTO.class);
        return taskDTO;
    }

    public TaskDTO updateTask(UUID taskId, TaskDTO taskDTO) {
        log.info("Updating task [{}]", taskId);
        taskRepository.findById(taskId)
                .orElseThrow(() -> new NotFoundException(String.format("No task found with id %s", taskId)));
        Task entity = converterService.convert(taskDTO, Task.class);
        entity.setId(taskId);
        taskRepository.save(entity);
        log.info("Updated task [{}]", entity);
        return converterService.convert(entity, TaskDTO.class);
    }

    public void deleteTask(UUID taskId) {
        log.info("Delete task with id [{}]", taskId);
        taskRepository.deleteById(taskId);
    }

    public TaskDTO getTask(UUID taskId) {
        log.info("Get task with id [{}]", taskId);
        return taskRepository.findById(taskId)
                .map(task -> converterService.convert(task, TaskDTO.class))
                .orElseThrow(() -> new NotFoundException(String.format("No task found with id %s", taskId)));
    }

    public List<TaskDTO> getTasks() {
        return taskRepository.findAll().stream()
                .sorted(Comparator.comparing(Task::getDueDate)
                        .thenComparing(Task::getPriority)
                        .reversed())
                .map(task -> converterService.convert(task, TaskDTO.class))
                .collect(toList());
    }
}
