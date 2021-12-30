package com.example.idleman;

public class TaskItem {
    private String text;
    private Long taskID;
    private String username;
    public TaskItem(String text,Long taskID,String username) {
        this.text = text;
        this.taskID=taskID;
        this.username=username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
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
