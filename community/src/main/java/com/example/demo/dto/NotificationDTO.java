package com.example.demo.dto;

import com.example.demo.model.UserModel;
import lombok.Data;

@Data
public class NotificationDTO {
    private Integer id;
    private Long gmt_create;
    private Integer status;
    private UserModel notifier;
    private String outerTitle;
    private String type;

}
