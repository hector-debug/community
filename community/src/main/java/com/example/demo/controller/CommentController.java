package com.example.demo.controller;

import com.example.demo.dto.CommentDTO;
import com.example.demo.dto.ResultDTO;
import com.example.demo.mapper.CommentMapper;
import com.example.demo.model.CommentModel;
import com.example.demo.model.UserModel;
import com.example.demo.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;


@Controller
public class CommentController {
    @Autowired
    private CommentMapper commentMapper;
    @Autowired
    private CommentService commentService;

    @ResponseBody
    @PostMapping("/comment")
    public  Object post(@RequestBody CommentDTO commentDTO,
                        HttpServletRequest request){
        UserModel userModel =(UserModel) request.getSession().getAttribute("user");
        if(userModel==null){
            return ResultDTO.errirOf(201,"先登录再评论");
        }
        CommentModel commentModel =new CommentModel();
        commentModel.setContent(commentDTO.getContent());
        commentModel.setGmtCreate(System.currentTimeMillis());
        commentModel.setGmtModified(commentModel.getGmtCreate());
        commentModel.setCommentator(userModel.getId());
        commentModel.setType(commentDTO.getType());
        commentModel.setParentId(commentDTO.getParentId());
        commentModel.setLikeCount(0L);
        commentService.addComment(commentModel);
        Map<Object,Object> objectObjectHashMap = new HashMap<>();

        objectObjectHashMap.put("message","评论成功");
        return ResultDTO.okOf();

    }
}
