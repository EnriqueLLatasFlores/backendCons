package com.construction.systems.constech.task.service;

import com.construction.systems.constech.shared.exception.FetchIdNotFoundException;
import com.construction.systems.constech.shared.exception.ResourceNotFoundException;
import com.construction.systems.constech.shared.exception.ResourceValidationException;
import com.construction.systems.constech.task.domain.model.entities.Task;
import com.construction.systems.constech.task.domain.persistence.TaskRepository;
import com.construction.systems.constech.task.domain.service.TaskService;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.List;
import java.util.Optional;
import java.util.Set;

@AllArgsConstructor
@Service
public class TaskServiceImpl implements TaskService {

    private final TaskRepository taskRepository;

    private final Validator validator;

    @Transactional
    @Override
    public Task save(Task task) {
        Set<ConstraintViolation<Task>> violations = validator.validate(task);
        if (violations.isEmpty()){
            return taskRepository.save(task);
        }
        throw new ResourceValidationException("Task", violations);
    }

    @Transactional
    @Override
    public Task update(Task student) {
        return null;
    }

    @Transactional
    @Override
    public boolean deleteById(Integer id) {
        if (taskRepository.existsById(id)) { // cuando la respuesta de busqueda es un solo elemento
            taskRepository.deleteById(id);
            if (taskRepository.existsById(id)) // Validar que se elimino
                return false;
            return true;
        }
        throw new FetchIdNotFoundException("Task", id);
    }

    @Transactional(readOnly = true)
    @Override
    public List<Task> fetchAll() { return taskRepository.findAll(); }


    @Transactional(readOnly = true)
    @Override
    public Task fetchByTitle(String title) {
        Optional<Task> optionalTask = taskRepository.findByTitle(title);
        if(optionalTask.isPresent()){
            return optionalTask.get();
        }
        throw  new ResourceNotFoundException("Task", "title", title);
    }

    @Transactional(readOnly = true)
    @Override
    public Task fetchByAssigned(String assigned) {
        Optional<Task> optionalTask = taskRepository.findByAssigned(assigned);
        if(optionalTask.isPresent()){
            return optionalTask.get();
        }
        throw  new RuntimeException("No esta el asignado a buscar");
    }
}

