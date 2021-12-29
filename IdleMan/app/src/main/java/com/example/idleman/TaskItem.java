package com.example.idleman;

public class TaskItem {
    private String text;
    private Long taskID;
    public TaskItem(String text,Long taskID) {
        this.text = text;
        this.taskID=taskID;

    }

    public void setTaskID(Long taskID) {
        this.taskID = taskID;
    }

    public Long getTaskID() {
        return taskID;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getText() {
        return text;
    }
}
