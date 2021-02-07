package com.zwy.controller;

import com.zwy.dto.AccessTokenDTO;
import com.zwy.dto.GithubUser;
import com.zwy.model.User;
import com.zwy.provider.GithubProvider;
import com.zwy.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.UUID;

/**
 * @Author ：zwy
 * @Date ：2021/1/19 12:44
 * @Version ：1.0
 * @Description ：GitHub 登录验证
 **/
@Controller
@Slf4j
public class AuthorizeController {

    @Autowired
    private GithubProvider githubProvider;

    @Autowired
    private UserService userService;

    @Value("${github.client.id}")
    private String clientId;
    @Value("${github.client.secret}")
    private String clientSecret;
    @Value("${github.redirect.uri}")
    private String redirectUri;


    @GetMapping("/callback")
    public String callback(@RequestParam(name = "code") String code,
                           @RequestParam(name = "state") String state,
                           HttpServletRequest request,
                           HttpServletResponse response) {
        AccessTokenDTO accessTokenDTO = new AccessTokenDTO();
        accessTokenDTO.setClient_id(clientId);
        accessTokenDTO.setClient_secret(clientSecret);
        accessTokenDTO.setCode(code);
        accessTokenDTO.setRedirect_uri(redirectUri);
        accessTokenDTO.setState(state);
        String accessToken = githubProvider.getAccessToken(accessTokenDTO);
        GithubUser githubUser = githubProvider.getUser(accessToken);
        if (githubUser == null) {
            log.error("callback get github error {}",githubUser );
            //登录失败，重新登录
            return "redirect:/";
        }
        User user = new User();
        User pastU = userService.findByAccoundId(String.valueOf(githubUser.getId()));
        String token;
        if (pastU != null){
            token = pastU.getToken();
        }else{
            token = UUID.randomUUID().toString();
        }
        user.setToken(token);
        user.setName(githubUser.getName());
        user.setAccountId(String.valueOf(githubUser.getId()));
        user.setGmtCreate(System.currentTimeMillis());
        user.setGmtModified(user.getGmtCreate());
        user.setBio(githubUser.getBio());
        user.setAvatarUrl(githubUser.getAvatar_url());
        User u = userService.insertOrUpdate(user);
        request.getSession().setAttribute("user", u);
        response.addCookie(new Cookie("token", token));
        return "redirect:/";
    }

    @GetMapping("/login")
    public String login(HttpServletRequest request,
                           HttpServletResponse response) {
        return "login";
    }

    @GetMapping("/logout")
    public String logout(HttpServletRequest request,
                           HttpServletResponse response) {
    request.getSession().removeAttribute("user");
    Cookie c = new Cookie("token",null);
    c.setMaxAge(0);
    response.addCookie(c);
    return "redirect:/";
    }
}