package com.zwy.controller;

import com.zwy.config.SaltUtils;
import com.zwy.dto.AccessTokenDTO;
import com.zwy.dto.GithubUser;
import com.zwy.exception.CustomizeErrorCode;
import com.zwy.exception.CustomizeException;
import com.zwy.model.User;
import com.zwy.provider.GithubProvider;
import com.zwy.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
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
                           HttpServletResponse response) throws Exception {
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
        user.setLoginType(User.LOGIN_TYPE_GITHUB);
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
    @PostMapping("/doLogin")
    public String doLogin(HttpServletRequest request,
                           HttpServletResponse response,Model model,
                          @RequestParam(name = "name") String name,
                          @RequestParam(name = "password") String password) {
        //使用 shiro 登录验证
        //1 认证的核心组件：获取 Subject 对象
        Subject subject = SecurityUtils.getSubject();
        //2 将登陆表单封装成 token 对象
        UsernamePasswordToken token = new UsernamePasswordToken(name, password);
        try {
            //3 让 shiro 框架进行登录验证：
            subject.login(token);
        } catch (IncorrectCredentialsException e) {
            e.printStackTrace();
            model.addAttribute("error","用户名或密码错误！");
            return "/login";
//            throw new CustomizeException(CustomizeErrorCode.LOGIN_FAIL);
        }
        User u = userService.findByName(name);
        request.getSession().setAttribute("user", u);
        response.addCookie(new Cookie("token", u.getToken()));
        return "redirect:/";
    }
    @PostMapping("/doRegister")
    public String doRegister(HttpServletRequest request,
                           HttpServletResponse response, Model model,
                           @RequestParam(name = "name") String name,
                           @RequestParam(name = "password") String password,
                           @RequestParam(name = "pwd") String pwd,
                           @RequestParam(name = "bio") String bio) {
        User user = new User();
        user.setName(name);
        user.setBio(bio);
        user.setToken(UUID.randomUUID().toString());
        user.setGmtCreate(System.currentTimeMillis());
        user.setGmtModified(System.currentTimeMillis());
        user.setAvatarUrl("http://zwyz.oss-cn-beijing.aliyuncs.com/community/publish/img/1624236910835.jpg?Expires=1939596902&OSSAccessKeyId=LTAI4GK95AhfKyxui78C1KkS&Signature=L%2F8aCyV1oFzK93KH6bDWr%2BN6p3o%3D");
        user.setAccountId("111");
        user.setLoginType(User.LOGIN_TYPE_USER);
        user.setPassword(SaltUtils.getPwdBySalt(password,user.getGmtCreate()+""));
        User u = userService.findByName(name);
        model.addAttribute("name",name);
        model.addAttribute("password",password);
        model.addAttribute("pwd",pwd);
        model.addAttribute("bio",bio);
        if (u != null) {
            model.addAttribute("error","该用户名已注册");
            return "/register";
        }
        if (StringUtils.isBlank(name) || StringUtils.isBlank(password) || StringUtils.isBlank(pwd)) {
            model.addAttribute("error","输入信息不完整，请检查后提交！");
            return "/register";
        }
        if (!password.equals(pwd)) {
            model.addAttribute("error","两次密码不一样！");
            return "/register";
        }
        userService.insert(user);
        return "/login";
    }
    @GetMapping("/register")
    public String register(HttpServletRequest request,
                           HttpServletResponse response) {
        return "register";
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
