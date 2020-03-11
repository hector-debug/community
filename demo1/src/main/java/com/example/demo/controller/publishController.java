package com.example.demo.controller;

import com.example.demo.cache.TagCache;
import com.example.demo.dto.QuestionDTO;
import com.example.demo.model.Question;
import com.example.demo.model.UserModel;
import com.example.demo.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.thymeleaf.util.StringUtils;


import javax.servlet.http.HttpServletRequest;

@Controller
public class publishController {


    @Autowired
    private QuestionService questionService;

    //编辑问题
    @GetMapping("/publish/{id}")
    public String  updateQuestion(@PathVariable("id") Integer id,
                          Model model){
       QuestionDTO question = questionService.getQuestionByID(id);
        model.addAttribute("question" ,question);

        model.addAttribute("tags", TagCache.getTags());

        return "publish";
    }

    //发布问题
    @GetMapping("/publish")
    public String publish(Model model){
        model.addAttribute("question",new Question());
        model.addAttribute("tags", TagCache.getTags());
        return "publish";
    }

//提交问题
    @PostMapping("/publish")
    public String doPublish(
            @RequestParam("title") String title,
            @RequestParam("description") String description,
            @RequestParam("tag") String tag,
            @RequestParam(name = "id",required = false) Integer id,
            HttpServletRequest request,
            Model model){

        Question question = new Question();
        question.setTitle(title);
        question.setDescription(description);
        question.setTag(tag);
        question.setId(id);
        //回显
        model.addAttribute("title" ,title);
        model.addAttribute("description" ,description);
        model.addAttribute("tag" ,tag);
        model.addAttribute("tags", TagCache.getTags());
        model.addAttribute("question",question);

        //判断用户是否登录
        UserModel userModel =(UserModel) request.getSession().getAttribute("user");
        if(userModel==null){
            model.addAttribute("error","用户未登录" );
            return "publish";
        }
        //判断输入合法性
        if(title==null|| title==""){
            model.addAttribute("error","标题不能为空");
                    return "publish";
        }
        if(description==null|| description==""){
            model.addAttribute("error","问题补充不能为空");
            return "publish";
        }
        if(tag==null|| tag==""){
            model.addAttribute("error","标签不能为空");
            return "publish";
        }

        String invalid=TagCache.isValid(tag);
        if(!StringUtils.isEmpty(invalid)){
            model.addAttribute("error","输入无效标签"+invalid);
            return "publish";
        }

        //可能要改为用cookie取
        //获取用户id,注入问题
        question.setCreator(userModel.getId());
        question.setGmtCreate(System.currentTimeMillis());

        questionService.createOrUpdate(question);
        return "redirect:/";
    }
}
