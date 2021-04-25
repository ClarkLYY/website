package com.clarklyy.website.service.tools;

import com.clarklyy.website.domain.entity.User;
import com.clarklyy.website.repository.mapper.UserMapper;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;

import javax.annotation.Resource;

public class UserRealm extends AuthorizingRealm {

    @Resource
    UserMapper userMapper;

    //权限管理
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        String userEmail = (String) principalCollection.getPrimaryPrincipal();
        User user = userMapper.selectByUserEmail(userEmail);
        SimpleAuthorizationInfo simpleAuthorizationInfo = new SimpleAuthorizationInfo();
        simpleAuthorizationInfo.addRole("admin");
        simpleAuthorizationInfo.addStringPermission("all");
        return simpleAuthorizationInfo;
    }

    //用户认证
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        UsernamePasswordToken token = (UsernamePasswordToken)authenticationToken;
        String userEmail = token.getPrincipal().toString();//获取用户名
        String password = String.valueOf((char[]) token.getCredentials());//获取密码
        User user = userMapper.selectByUserEmail(userEmail);//获取用户
        System.out.println("AuthenticationToken:");
        System.out.println("hashCode:" + authenticationToken.hashCode());
        System.out.println("Principal:" + authenticationToken.getPrincipal());
        System.out.println("Credentials:" + authenticationToken.getCredentials().toString());
        System.out.println("host: "+token.getHost());
        System.out.print("password: ");
        for(char i :token.getPassword()){
            System.out.print(i);
        }

        if (user==null){
            throw new UnknownAccountException("用户不存在");
        }
        if (user.getActiStatus()==0){
            throw new AccountException("该邮箱未确认注册！");
        }
        //返回构建好的AuthInfo，第一个参数为用户主体，第二个为用户密码，第三个getName()即可
        return new SimpleAuthenticationInfo(user,user.getUserPassword(), ByteSource.Util.bytes(user.getSalt()), getName());
    }
}
