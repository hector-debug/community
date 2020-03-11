package com.example.demo.dto;

import com.example.demo.model.UserModel;
import lombok.Data;

@Data
public class CommentShowDTO {
    private Long id;
    private Long parentId;
    private Integer type;
    private Integer commentator;
    private Long gmtCreate;
    private Long gmtModified;
    private Long likeCount;
    private String content;
    private UserModel userModel;
}
