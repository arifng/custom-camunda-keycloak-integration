package com.arifng.camunda.dto;

import lombok.Getter;
import lombok.Setter;
import org.camunda.bpm.engine.rest.dto.history.HistoricActivityInstanceDto;
import org.camunda.bpm.engine.rest.dto.task.CommentDto;

import java.util.List;

/**
 * Created by Rana on 07/12/2021.
 */
@Getter
@Setter
public class ActivityInstanceDto extends HistoricActivityInstanceDto {
    private List<CommentDto> comments;
}
