package com.pequignot.scheduletaskws.service;

import com.pequignot.scheduletaskws.model.Task;
import com.pequignot.scheduletaskws.model.TaskHistory;
import org.springframework.data.domain.Page;

public interface TaskService {
    Iterable<Task> getTasks();

    Integer getCriticity(Integer id);

    Page<TaskHistory> getHistory(Integer id);
}
