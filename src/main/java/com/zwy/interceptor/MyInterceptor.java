package com.zwy.interceptor;


import com.zwy.mapper.UserMapper;
import com.zwy.model.User;
import com.zwy.service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @Author ：zwy
 * @Date ：2021/1/25 20:56
 * @Version ：1.0
 * @Description ：TODO
 **/
public class MyInterceptor implements HandlerInterceptor {

    @Autowired
    private UserMapper userMapper;
    @Autowired
    private NotificationService notificationService;

    @Override
    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response, Object handler) throws Exception {
        Cookie[] cookies = request.getCookies();
        if (cookies != null && cookies.length != 0)
            for (Cookie cookie : cookies) {
                if ("token".equals(cookie.getName())) {
                    String token = cookie.getValue();
                    User user = userMapper.findByToken(token);
                    if (user != null) {
                        request.getSession().setAttribute("user", user);
                        Integer unReadCount = notificationService.countUnRead(user.getId());
                        request.getSession().setAttribute("unReadCount",unReadCount);
                        return true;
                    }
                }
            }
        return true;
    }
}
