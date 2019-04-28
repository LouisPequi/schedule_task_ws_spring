package com.pequignot.scheduletaskws.model.dto;

import java.util.Date;
import java.util.List;

public class GroupDto {

    public long id;
    public String name;
    public String description;
    public int occur;
    public String freq;
    public int order;
    public Date dateCrea;
    public  Date dateModif;
    public String user;
    public int criticity;
    public List<TaskDto> tasks;
}
