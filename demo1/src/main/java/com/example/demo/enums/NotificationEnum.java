package com.example.demo.enums;

public enum NotificationEnum {
    REPLY_QUESTION(1,"回复了你的问题"),
    REPLY_COMMENT(2,"回复了你的评论");
    private int type;
    private String name;

    public int getType() {
        return type;
    }

    public String getName() {
        return name;
    }

    NotificationEnum(int type, String name) {
        this.type = type;
        this.name = name;
    }
}

