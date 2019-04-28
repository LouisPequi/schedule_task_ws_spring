package com.pequignot.scheduletaskws.controller;

import com.pequignot.scheduletaskws.model.Task;
import com.pequignot.scheduletaskws.model.TaskHistory;
import com.pequignot.scheduletaskws.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/tasks")
public class TaskController {

    @Autowired
    private TaskService service;

    @GetMapping("/")
    public Iterable<Task> getTasks() {
        return this.service.getTasks();
    }

    @GetMapping("/{id}/criticity")
    public Integer getCriticity(@PathVariable Integer id) {
        return this.service.getCriticity(id);
    }

    @GetMapping("/{id}/history")
    public Page<TaskHistory> getHistory(@PathVariable Integer id) {
        return this.service.getHistory(id);
    }
}
