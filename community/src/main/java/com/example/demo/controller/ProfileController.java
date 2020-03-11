package com.example.demo.controller;

import com.example.demo.dto.PaginationDTO;
import com.example.demo.model.UserModel;
//import com.example.demo.service.NotificationService;
import com.example.demo.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;

@Controller
public class ProfileController {

    @Autowired
    private QuestionService questionService;
//    @Autowired
//    private NotificationService notificationService;
    @GetMapping("/profile/{action}")
    public String profile(@PathVariable(name = "action") String action,
                          HttpServletRequest request,
                          @RequestParam(value = "page",defaultValue = "1") Integer page,
                          @RequestParam(value = "size",defaultValue = "5") Integer size,
                          Model model){
        UserModel userModel= (UserModel) request.getSession().getAttribute("user");

        if(userModel==null) return "redirect:/";


        if ("question".equals(action)){
            model.addAttribute("section","question");
            model.addAttribute("sectionName","我的提问");
            PaginationDTO paginationDTO= questionService.userList(userModel.getId(),page,size);
            model.addAttribute("pagination",paginationDTO);
        }else if ("replies".equals(action)){
          //  PaginationDTO paginationDTO= notificationService.getNotifyList(userModel.getId(),page,size);
           // model.addAttribute("pagination",paginationDTO);
            model.addAttribute("section","replies");
            model.addAttribute("sectionName","最新回复");
        }



        return "profile";
    }

}
