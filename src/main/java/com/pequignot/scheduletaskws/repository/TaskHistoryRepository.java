package com.pequignot.scheduletaskws.repository;

import com.pequignot.scheduletaskws.model.TaskHistory;
import org.springframework.data.repository.CrudRepository;

public interface TaskHistoryRepository extends CrudRepository<TaskHistory, Integer> {
}
