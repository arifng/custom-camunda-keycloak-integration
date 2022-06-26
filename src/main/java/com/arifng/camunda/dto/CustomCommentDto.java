package com.arifng.camunda.dto;

import lombok.Getter;
import lombok.Setter;
import org.camunda.bpm.engine.rest.dto.task.CommentDto;
import org.camunda.bpm.engine.rest.dto.task.TaskDto;

import java.util.List;

/**
 * Created by Rana on 06/12/2021.
 */
@Getter
@Setter
public class CustomCommentDto extends TaskDto {
    private List<CommentDto> comments;
}
