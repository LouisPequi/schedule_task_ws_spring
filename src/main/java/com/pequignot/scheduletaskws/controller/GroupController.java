package com.pequignot.scheduletaskws.controller;

import com.pequignot.scheduletaskws.model.Group;
import com.pequignot.scheduletaskws.model.TaskHistory;
import com.pequignot.scheduletaskws.service.GroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/groups")
public class GroupController {

    @Autowired
    private GroupService service;

    @GetMapping("/")
    public Iterable<Group> getGroups() {
        return this.service.getGroups();
    }

    @GetMapping("/{id}/criticity")
    public long getCriticity(@PathVariable Integer id) {
        return this.service.getCriticity(id);
    }

    @GetMapping("/{id}/history")
    public Page<TaskHistory> getHistory(@PathVariable Integer id) {
        return this.service.getHistory(id);
    }
}
