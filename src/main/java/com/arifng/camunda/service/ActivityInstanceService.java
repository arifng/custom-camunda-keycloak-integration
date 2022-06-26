package com.arifng.camunda.service;

import com.arifng.camunda.dto.ActivityInstanceDto;

import java.util.List;

/**
 * Created by Rana on 07/12/2021.
 */
public interface ActivityInstanceService {
    List<ActivityInstanceDto> getActivityInstancesByProcessId(String processInstanceId);
}
