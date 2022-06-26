package com.arifng.camunda.controller;

import com.arifng.camunda.dto.ActivityInstanceDto;
import com.arifng.camunda.service.ActivityInstanceService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.List;

/**
 * Created by Rana on 07/12/2021.
 */
@RestController
public class ActivityInstanceController {
    private ActivityInstanceService activityInstanceService;

    @Autowired
    public ActivityInstanceController(ActivityInstanceService activityInstanceService) {
        this.activityInstanceService = activityInstanceService;
    }

    @GetMapping("/api/process/{processId}/activity-instance")
    public List<ActivityInstanceDto> getActivityInstances(@PathVariable("processId") String processInstanceId) {
        if (StringUtils.isBlank(processInstanceId)) {
            return Collections.emptyList();
        }

        return activityInstanceService.getActivityInstancesByProcessId(processInstanceId);
    }
}
