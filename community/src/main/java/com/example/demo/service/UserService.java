package com.example.demo.service;

import com.example.demo.dto.GithubUser;
import com.example.demo.mapper.UserMapper;
import com.example.demo.model.UserModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;
@Service
public class UserService {

    @Autowired
    private UserMapper userMapper;
    public UserModel addOrUpdateUser(GithubUser user) {
        UserModel userModel=userMapper.findByAccountId(user.getId());

        if (userModel==null){
            //用户不存在数据库内
            UserModel userModel1=new UserModel();
            String token= UUID.randomUUID().toString();
            userModel1.setToken(token);
            userModel1.setName(user.getName());
            userModel1.setAccountId(String.valueOf(user.getId()));
            userModel1.setGmtCreate(System.currentTimeMillis());
            userModel1.setGmtModified(userModel1.getGmtCreate());
            userModel1.setAvatarUrl(user.getAvatar_url());


            userMapper.insert(userModel1);

           return userModel1;



        }else{
            //用户已存在,更新
            String token= UUID.randomUUID().toString();
            userModel.setToken(token);
            userModel.setName(user.getName());
            userModel.setAvatarUrl(user.getAvatar_url());
            userMapper.updateUser(userModel);

            return  userModel;


        }
    }
}
