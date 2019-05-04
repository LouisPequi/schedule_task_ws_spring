package com.pequignot.scheduletaskws.model.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class DoTaskDto {

    private Integer id;
    private String util;
}
