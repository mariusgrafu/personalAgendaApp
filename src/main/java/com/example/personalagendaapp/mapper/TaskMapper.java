package com.example.personalagendaapp.mapper;

import com.example.personalagendaapp.dto.TaskRequest;
import com.example.personalagendaapp.model.Task;
import org.springframework.stereotype.Component;

@Component
public class TaskMapper {
    public Task taskRequestToTask(TaskRequest taskRequest) {
        return new Task(
                taskRequest.getName(),
                taskRequest.getStartDate(),
                taskRequest.getEndDate(),
                taskRequest.getDescription(),
                false,
                taskRequest.getAuthorId(),
                taskRequest.getCategoryId()
        );
    }
}
