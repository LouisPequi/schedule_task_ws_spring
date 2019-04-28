package com.pequignot.scheduletaskws.service;

import com.pequignot.scheduletaskws.model.Group;
import com.pequignot.scheduletaskws.model.TaskHistory;
import org.springframework.data.domain.Page;

public interface GroupService {
    Iterable<Group> getGroups();

    long getCriticity(Integer id);

    Page<TaskHistory> getHistory(Integer id);
}
