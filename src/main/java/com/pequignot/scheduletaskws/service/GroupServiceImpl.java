package com.pequignot.scheduletaskws.service;

import com.pequignot.scheduletaskws.model.Group;
import com.pequignot.scheduletaskws.model.Task;
import com.pequignot.scheduletaskws.model.TaskHistory;
import com.pequignot.scheduletaskws.model.dto.GroupDto;
import com.pequignot.scheduletaskws.model.dto.TaskDto;
import com.pequignot.scheduletaskws.repository.GroupRepository;
import com.pequignot.scheduletaskws.repository.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.*;

@Service("GroupService")
public class GroupServiceImpl implements GroupService {

    @Autowired
    private GroupRepository repository;
    @Autowired
    private TaskRepository taskRepository;
    @Autowired
    private TaskService taskService;
    @Autowired
    private CriticityService criticityService;

    @Override
    public List<GroupDto> getGroups() {
        Iterable<Group> groups = this.repository.findAll();
        List<GroupDto> groupDtoList = new ArrayList<>();
        for (Group group : groups) {
            List<Task> tasks = this.taskRepository.findByIdGroup(group.getId());
            List<TaskDto> taskDtoList = new ArrayList<>();
            for (Task task : tasks) {
                TaskDto taskDto = TaskDto.builder()
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
                        .criticity(this.taskService.getCriticity(task.getId()))
                        .build();
                taskDtoList.add(taskDto);
            }
            GroupDto groupDto = GroupDto.builder()
                    .id(group.getId())
                    .name(group.getName())
                    .description(group.getDescription())
                    .occur(group.getOccur())
                    .freq(group.getFreq())
                    .order(group.getOrder())
                    .dateCrea(group.getDateCrea())
                    .dateModif(group.getDateModif())
                    .util(group.getUtil())
                    .criticity(this.getCriticity(group.getId()))
                    .tasks(taskDtoList)
                    .build();
            groupDtoList.add(groupDto);
        }
        return groupDtoList;
    }

    @Override
    public Integer getCriticity(Integer id) {
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
