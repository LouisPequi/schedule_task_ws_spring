package com.pequignot.scheduletaskws.repository;

import com.pequignot.scheduletaskws.model.Group;
import com.pequignot.scheduletaskws.model.Task;
import com.pequignot.scheduletaskws.model.TaskHistory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.Collection;

public interface GroupRepository extends CrudRepository<Group, Integer> {

    @Query("SELECT t FROM Task t WHERE t.idGroup = :idGroup")
    Iterable<Task> findTasksFromGroupId(@Param("idGroup") Integer idGroup);

    @Query("SELECT h FROM TaskHistory h WHERE h.idTask IN :idTasks ORDER BY h.date DESC")
    Page<TaskHistory> findHistory(@Param("idTasks") Collection<Integer> idTasks, Pageable page);
}
