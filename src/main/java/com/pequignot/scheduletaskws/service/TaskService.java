package com.pequignot.scheduletaskws.service;

import com.pequignot.scheduletaskws.model.Task;
import com.pequignot.scheduletaskws.model.TaskHistory;
import com.pequignot.scheduletaskws.model.dto.TaskDto;
import org.springframework.data.domain.Page;

import java.util.List;

public interface TaskService {
    List<TaskDto> getTasks();

    Integer getCriticity(Integer id);

    Page<TaskHistory> getHistory(Integer id);

    TaskDto doTask(Integer id, String util);
}
