package com.zwy.shiro;

import io.jsonwebtoken.ExpiredJwtException;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.web.filter.authc.AuthenticationFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@Component
public class ClientShiroThree extends AuthenticationFilter {

    /*@Autowired
    private UserMapper userMapper;
    @Autowired
    private StudentMapper studentMapper;
    @Autowired
    private ISysPageService sysPageService;*/

    public static ClientShiroThree clientShiroThree;

    @PostConstruct
    public void init(){
        clientShiroThree = this;
    }


    @Override
    protected boolean onAccessDenied(ServletRequest servletRequest, ServletResponse response1) throws Exception {
        HttpServletResponse response = (HttpServletResponse) response1;
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        response.sendRedirect("/login");
        System.out.println("onAccessDenied");
        return false;
    }
    @Override
    protected boolean isAccessAllowed(ServletRequest servletRequest, ServletResponse response, Object mappedValue) {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
//        request.getSession().setAttribute("user", u);
//        response.addCookie(new Cookie("token", token));
        Cookie[] cookies = request.getCookies();
        /*LoginDTO loginDTO = new LoginDTO();
        String token = null;
        for (Cookie c : cookies){
            if (c.getName().equals("userToken")) {
//                System.out.println(c.getValue());
                token = c.getValue();
                User user = clientShiroThree.userMapper.selectOne(new QueryWrapper<User>().eq("token", c.getValue()));
                loginDTO.setId(user.getId());
                loginDTO.setUsername(user.getUsername());
                loginDTO.setRole(user.getRole());
                SecurityUtils.getSubject().getSession().setAttribute("login", loginDTO);
            }else if (c.getName().equals("stuToken")) {
                token = c.getValue();
                Student student = clientShiroThree.studentMapper.selectOne(new QueryWrapper<Student>().eq("token", c.getValue()));
                loginDTO.setId(student.getId());
                loginDTO.setUsername(student.getStuNo());
                loginDTO.setRole(student.getRole());
                SecurityUtils.getSubject().getSession().setAttribute("login", loginDTO);
            }
        }*/
        /*if (null == token||"".equals(token)) {
            System.out.println("-------------------token为空");
            return false;
        }
        //验证token的真实性
        try {
            TokenUtil.getTokenBody(token);
        } catch (ExpiredJwtException e) {
            e.getClaims();
            e.printStackTrace();
            System.out.println("----------------token有问题");
            return false;
        } catch (Exception e){
            e.printStackTrace();
        }
        List<SysPage> pageList = clientShiroThree.sysPageService.listByRole(loginDTO.getRole());
        SecurityUtils.getSubject().getSession().setAttribute("pageList", pageList);*/
        return true;
    }
}
