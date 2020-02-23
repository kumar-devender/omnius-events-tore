package com.omnius.omniuseventstore;

import com.omnius.omniuseventstore.dto.TaskDTO;
import com.omnius.omniuseventstore.service.TaskService;
import io.swagger.annotations.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping({"", "/v1"})
@RequiredArgsConstructor
@Api(value = "Omnius Event Store REST API")
public class TaskController {
    private final TaskService taskService;

    @ApiOperation(value = "View a list of available employees", response = List.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully retrieved task with given id"),
            @ApiResponse(code = 404, message = "The resource you were trying to reach is not found")
    })
    @RequestMapping(
            method = RequestMethod.GET,
            value = "/omnius-event-store/task/{task_id}",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public TaskDTO getTask(@PathVariable("task_id") UUID taskId) {
        return taskService.getTask(taskId);
    }

    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully retrieved all tasks in the event store"),
            @ApiResponse(code = 404, message = "The resource you were trying to reach is not found")
    })
    @ApiOperation(value = "Get a list of available task in the event store", response = List.class)
    @RequestMapping(
            method = RequestMethod.GET,
            value = "/omnius-event-store/task",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public List<TaskDTO> getAllTask() {
        return taskService.getTasks();
    }

    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully delete the task from the event store"),
            @ApiResponse(code = 404, message = "The resource you were trying to delete is not found")
    })
    @ApiOperation(value = "Delete the task with given id from the event store")
    @RequestMapping(
            method = RequestMethod.DELETE,
            value = "/omnius-event-store/task/{task_id}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void deleteTask(@ApiParam(value = "Task Id to delete task object", required = true)
                           @PathVariable("task_id") UUID taskId) {
        taskService.deleteTask(taskId);
    }

    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully updated the task in the event store"),
            @ApiResponse(code = 404, message = "The resource you were trying to reach is not found")
    })
    @ApiOperation(value = "Update the task with given id in the event store", response = TaskDTO.class)
    @RequestMapping(
            method = RequestMethod.PUT,
            value = "/omnius-event-store/task/{task_id}",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public TaskDTO updateTask(@ApiParam(value = "Task Id to update task object", required = true)
                              @PathVariable("task_id") UUID taskId, @RequestBody TaskDTO taskDTO) {
        return taskService.updateTask(taskId, taskDTO);
    }

}
