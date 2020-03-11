package com.example.demo.controller;

import com.example.demo.dto.PaginationDTO;

import com.example.demo.model.Question;
import com.example.demo.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import javax.servlet.http.HttpServletRequest;
import java.util.List;


@Controller
public class indexController {
    @Autowired
    private QuestionService questionService;

    @GetMapping("/" )
    public String index(HttpServletRequest request,
                        Model model,
                        @RequestParam(name = "page",defaultValue = "1") Integer page,
                        @RequestParam(name = "size",defaultValue = "5") Integer size,
                        @RequestParam(name = "search",required = false) String search){



        //得到首页元素，并放入，paginationDTO封装有questionList
        PaginationDTO paginationDTO = questionService.getList(search,page, size);
        //根据阅读量选出热门话题
        List<Question> hots=questionService.getHots();

        model.addAttribute("pagination",paginationDTO);
        model.addAttribute("hots",hots);
        return "/index";
    }
}
