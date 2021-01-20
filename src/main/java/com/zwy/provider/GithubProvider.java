package com.zwy.provider;

import com.alibaba.fastjson.JSON;
import com.zwy.dto.AccessTokenDTO;
import com.zwy.dto.GithubUser;
import okhttp3.*;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Objects;

/**
 * @Author ：zwy
 * @Date ：2021/1/19 13:03
 * @Version ：1.0
 * @Description ：TODO
 **/
@Component
public class GithubProvider {


    public String getAccessToken(AccessTokenDTO accessTokenDTO){
        MediaType mediaType  = MediaType.get("application/json; charset=utf-8");
        OkHttpClient client = new OkHttpClient();
        RequestBody body = RequestBody.create(mediaType, JSON.toJSONString(accessTokenDTO));
        Request request = new Request.Builder()
                .url("https://github.com/login/oauth/access_token?client_id="
                        +accessTokenDTO.getClient_id()+"&client_secret="+accessTokenDTO.getClient_secret()
                        +"&code="+accessTokenDTO.getCode()+"&redirect_uri="+accessTokenDTO.getRedirect_uri()
                        +"&state="+accessTokenDTO.getState())
                .post(body)
                .build();
        try (Response response = client.newCall(request).execute()) {
            String str = response.body().string();
            return str.split("&")[0].split("=")[1];
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    public GithubUser getUser(String accessToken){
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url("https://api.github.com/user")
                .header("Authorization","token "+accessToken)
                .build();
        try {
            Response response = client.newCall(request).execute();
            String str = response.body().string();
            return JSON.parseObject(str,GithubUser.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;

    }
}
