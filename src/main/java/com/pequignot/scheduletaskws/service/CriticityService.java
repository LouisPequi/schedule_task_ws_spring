package com.pequignot.scheduletaskws.service;

import com.pequignot.scheduletaskws.model.TaskHistory;

import java.util.List;

public interface CriticityService {

    int[] getFrequencies(String frequencies);

    int calculateCriticity(List<TaskHistory> history, String frequencies, int occur);

}
