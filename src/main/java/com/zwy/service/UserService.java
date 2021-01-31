package com.zwy.service;

import com.zwy.mapper.UserMapper;
import com.zwy.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Author ：zwy
 * @Date ：2021/1/25 17:34
 * @Version ：1.0
 * @Description ：TODO
 **/
@Service
public class UserService {

    @Autowired
    private UserMapper userMapper;

    public User insertOrUpdate(User user){
        User u = userMapper.findByAccount(user.getAccountId());
        if (u == null){
            userMapper.insert(user);
            return user;
        }else {
//            System.out.println(u.getGmtModified());
//            System.out.println(u.getToken());
            u.setGmtModified(System.currentTimeMillis());
            u.setToken(user.getToken());
            userMapper.update(u);
            return u;
        }
    }

    public User findById(Long commentator) {
        return userMapper.findById(commentator);
    }
}
