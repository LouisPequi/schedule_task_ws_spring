package com.pequignot.scheduletaskws.service;

import com.pequignot.scheduletaskws.model.Group;
import com.pequignot.scheduletaskws.model.TaskHistory;
import com.pequignot.scheduletaskws.model.dto.GroupDto;
import org.springframework.data.domain.Page;

import java.util.List;

public interface GroupService {
    List<GroupDto> getGroups();

    Integer getCriticity(Integer id);

    Page<TaskHistory> getHistory(Integer id);
}
