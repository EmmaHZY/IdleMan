package com.example.idleman;

public class TaskItem {
    private String text;
    private Long taskID;
    private String username;
    private String tag;
    public TaskItem(String text,Long taskID,String username,String tag) {
        this.text = text;
        this.taskID=taskID;
        this.username=username;
        this.tag=tag;
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

    public void setTag(String tag) {
        this.tag = tag;
    }

    public String getTag() {
        return tag;
    }
}
