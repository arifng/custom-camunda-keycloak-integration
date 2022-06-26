package com.arifng.camunda.form;

import static java.util.Objects.nonNull;

/**
 * Created by Rana on 22/11/2021.
 */
public class CommentForm {
    private String taskId;
    private String processInstanceId;
    private String comment;

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    public void setProcessInstanceId(String processInstanceId) {
        this.processInstanceId = processInstanceId;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getTaskId() {
        return taskId;
    }

    public String getProcessInstanceId() {
        return processInstanceId;
    }

    public String getComment() {
        return comment;
    }

    public boolean isValid() {
        return nonNull(comment) && nonNull(taskId) && nonNull(processInstanceId);
    }
}
