package com.arifng.camunda.controller;

import com.arifng.camunda.form.CommentForm;
import com.arifng.camunda.service.TaskListService;
import com.arifng.camunda.dto.CustomCommentDto;
import org.camunda.bpm.engine.TaskService;
import org.camunda.bpm.engine.task.Comment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by Rana on 21/11/2021.
 */
@RestController
public class CommentController {
    @Autowired
    private TaskService taskService;

    @Autowired
    private TaskListService taskListService;

    @GetMapping("/api/process/{processId}/comments")
    public List<Comment> getComments(@PathVariable("processId") String processInstanceId) {

        if(processInstanceId != null) {
            List<Comment> comments = taskService.getProcessInstanceComments(processInstanceId);
            comments.sort(Comparator.comparing(Comment::getTime));
            String collect = comments.stream().map(Comment::getFullMessage).collect(Collectors.joining("\n"));
            System.out.println(collect);
            return comments;
        }

        return null;
    }

    @GetMapping("/api/process/{processId}/comments-pretty")
    public List<CustomCommentDto> getCommentsPerTask(@PathVariable("processId") String processInstanceId) {
        return taskListService.getCommentsPerProcess("", processInstanceId);
    }

    @PostMapping("/api/comment")
    public Comment createComment(@RequestBody CommentForm form) {
        if(form == null) {
            return null;
        }

        System.out.println(form.getTaskId() + " - " + form.getProcessInstanceId() + " - " + form.getComment());
        if(form.isValid()) {
            return taskService.createComment(form.getTaskId(), form.getProcessInstanceId(), form.getComment());
        }
        return null;
    }
}
