package com.zwy.shiro;

import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.realm.Realm;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.servlet.Filter;
import java.util.*;

@Configuration
public class ShiroConfig {

   /* @Autowired
    private RoleMapMapper roleMapMapper;*/

    @Bean
    public Realm shiroRealm() {
        ShiroRealm myRealm = new ShiroRealm();
        // 修改凭证校验匹配器
        // 设置realm使用hash凭证
        HashedCredentialsMatcher credentialsMatcher = new HashedCredentialsMatcher();
        // 设置哈希算法的名字
        credentialsMatcher.setHashAlgorithmName("md5");
        // 设置散列的次数
        credentialsMatcher.setHashIterations(1024);
        myRealm.setCredentialsMatcher(credentialsMatcher);
        return myRealm;
    }

    @Bean
    public SecurityManager securityManager() {
        DefaultWebSecurityManager manager = new DefaultWebSecurityManager();
        Set<Realm> set= new HashSet<>();
        set.add(shiroRealm());
        manager.setRealms(set);
        return manager;
    }
    @Bean
    public ShiroFilterFactoryBean shiroFilterFactoryBean() {
        ShiroFilterFactoryBean filter = new ShiroFilterFactoryBean();
        filter.setSecurityManager(securityManager());
        //加入自定义的filter
        Map<String, Filter> filterMap = filter.getFilters();
        filterMap.put("authc", new ClientShiroThree());
        filter.setFilters(filterMap);
        //定义相关路径
        filter.setLoginUrl("/login");
//        filter.setUnauthorizedUrl("/noAuthorize");
        //定义拦截路径,记得将静态资源也排除过滤
        //进行权限的控制,必须使用LinkHashMap,shrio要按照顺序进行设置
        Map<String, String> authMap = new LinkedHashMap<>();
        authMap.put("/static/**/**", "anon");
//        authMap.put("/static/css/**", "anon");
//        authMap.put("/static/js/file/**", "anon");
//        authMap.put("/user/**", "client,roles[user]");
//        authMap.put("/admin/**", "client,roles[admin]");
        authMap.put("/login", "anon");
//        authMap.put("/**", "client");
        filter.setFilterChainDefinitionMap(authMap);
        //配置完成
        System.out.println("---------------shirofactory创建成功");
        return filter;
    }
}
