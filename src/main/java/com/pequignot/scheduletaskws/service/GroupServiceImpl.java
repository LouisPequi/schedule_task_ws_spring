package com.pequignot.scheduletaskws.service;

import com.pequignot.scheduletaskws.model.Group;
import com.pequignot.scheduletaskws.model.Task;
import com.pequignot.scheduletaskws.model.TaskHistory;
import com.pequignot.scheduletaskws.repository.GroupRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Set;
import java.util.TreeSet;

@Service("GroupService")
public class GroupServiceImpl implements GroupService {

    @Autowired
    private GroupRepository repository;
    @Autowired
    private CriticityService criticityService;

    @Override
    public Iterable<Group> getGroups() {
        return this.repository.findAll();
    }

    @Override
    public long getCriticity(Integer id) {
        Optional<Group> group = this.repository.findById(id);
        Page<TaskHistory> groupHistory = this.getHistory(id);
        if (group.isPresent()) {
            return this.criticityService.calculateCriticity(groupHistory.getContent(), group.get().getFreq(), group.get().getOccur());
        } else {
            // 0 is a non defined value
            // It means the task does not exist
            return 0;
        }
    }

    @Override
    public Page<TaskHistory> getHistory(Integer id) {
        Optional<Group> group = this.repository.findById(id);
        Iterable<Task> tasks = this.repository.findTasksFromGroupId(id);
        Set<Integer> taskIds = new TreeSet<>();
        tasks.forEach(task -> {
            taskIds.add(task.getId());
        });
        if (group.isPresent() && !taskIds.isEmpty()) {
            return this.repository.findHistory(taskIds, PageRequest.of(0, group.get().getOccur()));
        } else {
            return Page.empty();
        }
    }
}
