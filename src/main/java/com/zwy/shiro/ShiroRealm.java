package com.zwy.shiro;

import com.zwy.mapper.UserMapper;
import com.zwy.model.User;
import org.apache.shiro.authc.*;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.realm.AuthenticatingRealm;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @Author zwy
 * @Date 2020/9/13 15:58
 * @Version 1.0
 * @Deacription TODO
 **/

public class ShiroRealm extends AuthenticatingRealm {

    @Autowired
    private UserMapper userMapper;
    /**
     *  登录的验证实现方法
     * @param token
     * @return
     * @throws AuthenticationException
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        UsernamePasswordToken token2 = (UsernamePasswordToken) token;
        String username = token2.getUsername();
        User user = userMapper.getUserByUsername(username);
        if(user == null) {
            throw new UnknownAccountException("用户名或密码有误！");
        }
        /*if(user.getState() == User.STATE_1) {
            throw new UnknownAccountException("用户名已被禁用，请联系系统管理员！");
        }*/
        /**
         * principals: 可以使用户名，或d登录用户的对象
         * hashedCredentials: 从数据库中获取的密码
         * credentialsSalt：密码加密的盐值
         * RealmName:  类名（ShiroRealm）
         */
        ByteSource salt = ByteSource.Util.bytes(user.getGmtCreate()+"");
        AuthenticationInfo info = new SimpleAuthenticationInfo(user, user.getPassword(), salt, getName());
        return info; //框架完成验证
    }


    public static void main(String[] args) {
        //加密方式
        String hashAlgorithmName = "MD5";
        //盐：为了即使相同的密码不同的盐加密后的结果也不同
        ByteSource byteSalt = ByteSource.Util.bytes("1111");
        //密码
        Object source = "0000";
        //加密次数
        int hashIterations = 1024;
        SimpleHash result = new SimpleHash(hashAlgorithmName, source, byteSalt, hashIterations);
        System.out.println(result.toString());
    }


}
