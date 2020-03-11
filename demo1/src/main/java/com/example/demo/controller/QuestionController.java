package com.example.demo.controller;

import com.example.demo.dto.CommentShowDTO;
import com.example.demo.dto.QuestionDTO;
import com.example.demo.service.CommentService;
import com.example.demo.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Controller
public class QuestionController {
    @Autowired
    private QuestionService questionService;
    @Autowired
    private CommentService commentService;
    @GetMapping("/question/{id}")
    public String question(@PathVariable(name = "id") Integer id,
                           Model model){
        //得到问题
        QuestionDTO questionDTO=questionService.getQuestionByID(id);
      //得到评论
       List<CommentShowDTO> comments=commentService.getCommentByQuestionId(questionDTO.getId());
       //得到相关问题
        List<QuestionDTO> relateQuestions=questionService.getRelated(questionDTO);
        System.out.println("bb"+relateQuestions);
        //累加阅读数
        questionService.incView(id);

        //将元素放入model
        model.addAttribute("question",questionDTO);
        model.addAttribute("comments",comments);
        model.addAttribute("relateQuestions", relateQuestions);
        return "question";
    }
}
