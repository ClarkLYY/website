package com.clarklyy.website.config;

import com.clarklyy.website.service.tools.JwtFilter;
import com.clarklyy.website.service.tools.UserRealm;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.filter.DelegatingFilterProxy;

import javax.annotation.Resource;
import javax.servlet.Filter;
import java.util.HashMap;
import java.util.Map;

@Configuration
public class ShiroConfig {


    //将自己的验证方式Realm加入到容器中
    @Bean
    public UserRealm userRealm(){
        UserRealm userRealm = new UserRealm();
//        userRealm.setCredentialsMatcher(hashedCredentialsMatcher());
        return userRealm;
    }

    //权限管理，配置realm的管理认证
    @Bean
    public SecurityManager securityManager(){
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        securityManager.setRealm(userRealm());
        return securityManager;
    }


    //Filter工厂，设置对应的过滤条件和跳转条件
    @Bean
    public ShiroFilterFactoryBean shiroFilterFactoryBean(SecurityManager securityManager) {
        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
        shiroFilterFactoryBean.setSecurityManager(securityManager);
        Map<String, String> map = new HashMap<String, String>();
        Map<String, Filter> filters = new HashMap<>();
        filters.put("jwt",new JwtFilter());
        shiroFilterFactoryBean.setFilters(filters);
        //登录注册不拦截
//        map.put("/user/register","anon");
//        map.put("/user/verify","anon");
        map.put("/user/login","anon");
////        map.put("/user/getUser","anon");
//        //登出
//        map.put("/logout","logout");
        //对所有用户认证
        System.out.println("放入 jwt");
        map.put("/**","jwt");
        //登录
//        shiroFilterFactoryBean.setLoginUrl("/user/unauth");
        //首页
//        shiroFilterFactoryBean.setSuccessUrl("/index");
        //错误页面，认证不通过跳转
//        shiroFilterFactoryBean.setUnauthorizedUrl("/error");
        shiroFilterFactoryBean.setFilterChainDefinitionMap(map);
        System.out.println("放入 jwt");
        return shiroFilterFactoryBean;
    }

//    //自定义sessionManager
//    @Bean
//    public SessionManager sessionManager() {
//        return new MySessionManager();
//    }

    //解决org.apache.shiro.UnavailableSecurityManagerException
//    @Bean
//    public FilterRegistrationBean delegatingFilterProxy(){
//        FilterRegistrationBean filterRegistrationBean = new FilterRegistrationBean();
//        DelegatingFilterProxy proxy = new DelegatingFilterProxy();
//        proxy.setTargetFilterLifecycle(true);
//        proxy.setTargetBeanName("shiroFilterFactoryBean");
//        filterRegistrationBean.setFilter(proxy);
//        return filterRegistrationBean;
//    }

//    @Bean
//    public JwtFilter jwtFilter(){
//        System.out.println("token jwtFilter");
//        return new JwtFilter();
//    }
}
