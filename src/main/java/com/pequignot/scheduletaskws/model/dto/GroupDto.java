package com.pequignot.scheduletaskws.model.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Getter
@Setter
@Builder
public class GroupDto {

    private long id;
    private String name;
    private String description;
    private int occur;
    private String freq;
    private int order;
    private Date dateCrea;
    private  Date dateModif;
    private String util;
    private int criticity;
    private List<TaskDto> tasks;
}
