package com.clarklyy.website.service.tools;

import cn.hutool.core.bean.BeanUtil;
import com.clarklyy.website.common.utils.JwtUtils;
import com.clarklyy.website.domain.entity.AccountProfile;
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


    public boolean supports(AuthenticationToken authenticationToken) {
        return authenticationToken instanceof JwtToken;
    }

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
        JwtToken jwtToken = (JwtToken) authenticationToken;
        JwtUtils jwtUtils = new JwtUtils();
        String userId = jwtUtils.getClaimsByToken((String) jwtToken.getPrincipal()).getSubject();
        User user = userMapper.selectByPrimaryKey(Integer.valueOf(userId));//获取用户
        if (user==null){
            throw new UnknownAccountException("用户不存在");
        }

        AccountProfile profile = new AccountProfile();

        profile.setId(user.getUserId());
        profile.setEmail(user.getUserEmail());
        profile.setUsername(user.getUserEmail());
//        //返回构建好的AuthInfo，第一个参数为用户主体，第二个为用户密码，第三个getName()即可
        return new SimpleAuthenticationInfo(profile,jwtToken.getCredentials(), getName());
    }
}
