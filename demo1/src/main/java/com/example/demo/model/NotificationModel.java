package com.example.demo.model;

import lombok.Data;

@Data
public class NotificationModel {
    private Integer id;
    private Integer notifier;
    private Integer receiver;
    private Integer outId;
    private Integer type;
    private Long gmt_create;
    private Integer status;
}
