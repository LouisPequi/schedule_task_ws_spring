package com.pequignot.scheduletaskws.repository;

import com.pequignot.scheduletaskws.model.Task;
import com.pequignot.scheduletaskws.model.TaskHistory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;


public interface TaskRepository extends CrudRepository<Task, Integer> {

    @Query("SELECT h FROM TaskHistory h WHERE h.idTask = :idTask ORDER BY h.date DESC")
    Page<TaskHistory> findHistory(@Param("idTask") Integer idTask, Pageable page);
}
