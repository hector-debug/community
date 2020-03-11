package com.example.demo.controller;
import com.example.demo.dto.AccessTokenDTO;
import com.example.demo.dto.GithubUser;
import com.example.demo.model.UserModel;
import com.example.demo.provider.GithubProvider;
import com.example.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
public class AuthorizeController {
    @Autowired
    private GithubProvider githubProvider;

    @Value("${github.client.id}")
    private String clientId;
    @Value("${github.client.secret}")
    private String clientSecret;
    @Value("${github.redirect}")
    private String redirect;

    @Autowired
    private UserService userService;

   @GetMapping("/callback")
    public String callback(@RequestParam(name="code") String code,
                           @RequestParam(name="state") String state,
                           HttpServletResponse response){
       AccessTokenDTO accessTokenDTO = new AccessTokenDTO();
       accessTokenDTO.setCode(code);
       accessTokenDTO.setState(state);
       accessTokenDTO.setRedirect_uri(redirect);
       accessTokenDTO.setClient_id(clientId);
       accessTokenDTO.setClient_secret(clientSecret);
       String accessToken = githubProvider.getAccessToken(accessTokenDTO);
       System.out.println(accessToken);
       GithubUser user = githubProvider.getUSer(accessToken);



       if(user==null){
           //登录失败
           return "redirect:/";
       }else {
           //登录成功

           UserModel userModel=userService.addOrUpdateUser(user);
           Cookie tokenCookie=new Cookie("id",userModel.getAccountId());

           response.addCookie(tokenCookie);
       }
        return "redirect:/";

    }

    @GetMapping("/logout")
    public String logout(HttpServletRequest request,
                         HttpServletResponse response){
       request.getSession().removeAttribute("user");
        Cookie id = new Cookie("id", null);
        id.setMaxAge(0);
        response.addCookie(id);
       return "redirect:/";
    }
}
