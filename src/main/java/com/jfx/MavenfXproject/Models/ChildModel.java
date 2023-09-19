package com.jfx.MavenfXproject.Models;

public class ChildModel 
{
    private int childID;
    private int childCountdown;
    private boolean childPaused;

    // 默认构造函数
    public ChildModel() {
        // 这里可以设置默认值
        childID = 0;
        childCountdown = 15;
        childPaused = false;
    }

    // 带参数的构造函数
    public ChildModel(int childID, int childCountdown, boolean childPaused) {
        this.childID = childID;
        this.childCountdown = childCountdown;
        this.childPaused = childPaused;
    }

    // Getter和Setter方法
    public int getChildID() {
        return childID;
    }

    public void setChildID(int childID) {
        this.childID = childID;
    }

    public int getChildCountdown() {
        return childCountdown;
    }

    public void setChildCountdown(int childCountdown) {
        this.childCountdown = childCountdown;
    }

    public boolean isChildPaused() {
        return childPaused;
    }

    public void setChildPaused(boolean childPaused) {
        this.childPaused = childPaused;
    }
    
}
