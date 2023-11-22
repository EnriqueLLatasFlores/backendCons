package com.construction.systems.constech.task.domain.service;

import com.construction.systems.constech.task.domain.model.entities.Task;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

public interface TaskService {
    Task save(Task task);
    List<Task> fetchAll();

    Task fetchByTitle(String title);

    Task fetchByAssigned(String assigned);
}
