package com.example.demo.provider;

import com.alibaba.fastjson.JSON;
import com.example.demo.dto.AccessTokenDTO;

import com.example.demo.dto.GithubUser;
import okhttp3.*;
import org.springframework.stereotype.Component;


import java.io.IOException;

@Component
public class GithubProvider {
    public String getAccessToken(AccessTokenDTO accessTokenDTO){
        MediaType mediaType
                = MediaType.get("application/json; charset=utf-8");

        OkHttpClient client = new OkHttpClient();
//.create(mediaType, JSON.toJSONString(accessTokenDTO))
        RequestBody body = RequestBody.create(mediaType, JSON.toJSONString(accessTokenDTO));
        Request request = new Request.Builder()
                .url("https://github.com/login/oauth/access_token")
                .post(body)
                .build();
        try (Response response = client.newCall(request).execute()) {
            String str=response.body().string();
            String token = str.split("&")[0].split("=")[1];

            return token;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return  null;
    }
    public GithubUser getUSer(String accessToken){
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url("https://api.github.com/user?access_token="+accessToken)
                .build();
        try {

            Response response = client.newCall(request).execute();

            String str=response.body().string();

            GithubUser githubUser = JSON.parseObject(str, GithubUser.class);
            return githubUser;
        } catch (IOException e) {
            e.printStackTrace();

        }
//        GithubUser githubUser=new GithubUser();
//        githubUser.setAvatar_url("https://avatars3.githubusercontent.com/u/52646482?v=4");
//        githubUser.setId((long) 52646482);
//        githubUser.setName("lihuihong");

          return null;

    }
}
