package com.omnius.omniuseventstore.dto;

import com.omnius.omniuseventstore.domain.Priority;
import com.omnius.omniuseventstore.domain.Status;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(description = "All details about the task")
public class TaskDTO {
    @NotNull
    @ApiModelProperty(notes = "The database generated task ID")
    private UUID id;
    @NotNull
    @ApiModelProperty(notes = "Title of task")
    private String title;
    @ApiModelProperty(notes = "Long description of task")
    private String description;
    @NotNull
    @ApiModelProperty(notes = "Status of task")
    private Status status;
    @NotNull
    @ApiModelProperty(notes = "Priority of task")
    private Priority priority;
    @NotNull
    @ApiModelProperty(notes = "Due date of the task")
    private String dueDate;
    @ApiModelProperty(notes = "Resolve date of the task")
    private String resolvedAt;
}
