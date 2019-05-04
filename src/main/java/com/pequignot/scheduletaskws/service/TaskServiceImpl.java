package com.pequignot.scheduletaskws.service;


import com.pequignot.scheduletaskws.model.Group;
import com.pequignot.scheduletaskws.model.Task;
import com.pequignot.scheduletaskws.model.TaskHistory;
import com.pequignot.scheduletaskws.model.dto.GroupDto;
import com.pequignot.scheduletaskws.model.dto.TaskDto;
import com.pequignot.scheduletaskws.repository.TaskHistoryRepository;
import com.pequignot.scheduletaskws.repository.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service("TaskService")
public class TaskServiceImpl implements TaskService {

    @Autowired
    private TaskRepository repository;
    @Autowired
    private TaskHistoryRepository historyRepository;
    @Autowired
    private CriticityService criticityService;

    @Override
    public List<TaskDto> getTasks() {
        Iterable<Task> tasks = this.repository.findAll();
        List<TaskDto> taskDtoList = new ArrayList<>();
            for (Task task : tasks) {
                taskDtoList.add(buildTaskDto(task));
            }
        return taskDtoList;
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

    @Override
    public TaskDto doTask(Integer id, String util) {
        TaskHistory history = new TaskHistory();
        Optional<Task> task = this.repository.findById(id);
        history.setIdTask(id);
        history.setDate(new Date());
        history.setUtil(util);
        this.historyRepository.save(history);
        if (task.isPresent()) {
            return buildTaskDto(task.get());
        } else {
            return null;
        }
    }

    private TaskDto buildTaskDto(Task task) {
        return TaskDto.builder()
                .id(task.getId())
                .id_group(task.getId())
                .name(task.getName())
                .description(task.getDescription())
                .occur(task.getOccur())
                .freq(task.getFreq())
                .order(task.getOrder())
                .dateCrea(task.getDateCrea())
                .dateModif(task.getDateModif())
                .util(task.getUtil())
                .criticity(this.getCriticity(task.getId()))
                .build();
    }
}
