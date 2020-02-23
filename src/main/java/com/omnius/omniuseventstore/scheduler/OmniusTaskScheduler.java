package com.omnius.omniuseventstore.scheduler;

import com.omnius.omniuseventstore.domain.Priority;
import com.omnius.omniuseventstore.domain.RandomEnum;
import com.omnius.omniuseventstore.domain.Status;
import com.omnius.omniuseventstore.domain.Task;
import com.omnius.omniuseventstore.publisher.TaskPublisher;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

@Component
@RequiredArgsConstructor
@Slf4j
public class OmniusTaskScheduler {
    private final TaskPublisher taskPublisher;
    private static final RandomEnum<Priority> PRIORITY = new RandomEnum<>(Priority.class);
    private static final Random RANDOM = new Random();
    private static final AtomicInteger COUNTER = new AtomicInteger();

    @Scheduled(fixedDelayString = "${fixedDelay.in.milliseconds}")
    public void scheduleFixedDelayTask() {
        log.info("Creating new task at [{}]", LocalDateTime.now().toString());
        Task task = buildTask();
        taskPublisher.publishTask(task);
    }

    private Task buildTask() {
        int current = COUNTER.getAndIncrement();
        return Task.builder()
                .description(String.format("%s%s", "Task", current))
                .status(Status.NEW)
                .priority(PRIORITY.random())
                .dueDate(LocalDateTime.now().plusMinutes(15))
                .title(String.format("%s%s", "Title", current))
                .build();
    }

}
