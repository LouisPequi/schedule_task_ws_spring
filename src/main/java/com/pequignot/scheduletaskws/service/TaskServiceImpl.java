package com.pequignot.scheduletaskws.service;


import com.pequignot.scheduletaskws.model.Task;
import com.pequignot.scheduletaskws.model.TaskHistory;
import com.pequignot.scheduletaskws.repository.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service("TaskService")
public class TaskServiceImpl implements TaskService {

    @Autowired
    private TaskRepository repository;
    @Autowired
    private CriticityService criticityService;

    @Override
    public Iterable<Task> getTasks() {
        return this.repository.findAll();
    }

    @Override
    public Integer getCriticity(Integer id) {
        Optional<Task> task = this.repository.findById(id);
        Page<TaskHistory> taskHistory = this.getHistory(id);
        if (task.isPresent()) {
            return this.criticityService.calculateCriticity(taskHistory.getContent(), task.get().getFreq(), task.get().getOccur());
        } else {
            // 0 is a non defined value
            // It means the task does not exist
            return 0;
        }
    }

    @Override
    public Page<TaskHistory> getHistory(Integer id) {
        Optional<Task> task = this.repository.findById(id);
        if (task.isPresent()) {
            return this.repository.findHistory(task.get().getId(), PageRequest.of(0, task.get().getOccur()));
        } else {
            return Page.empty();
        }
    }
}
