package edu.hfut.config;

import edu.hfut.pojo.entity.User;
import edu.hfut.service.IUserService;
import edu.hfut.util.comon.JwtUtil;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author panbaoqiang
 * @Description
 * @create 2020-05-05 11:03
 */
@Component
public class CustomRealm extends AuthorizingRealm  {

    @Autowired
    private IUserService userService;

    @Override
    public boolean supports(AuthenticationToken token) {
        return token instanceof JWTToken;
    }

    /**
     * 默认使用此方法进行用户名正确与否验证，错误抛出异常即可。
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        System.out.println("————身份认证方法————");
        String token = (String) authenticationToken.getCredentials();
        String username = JwtUtil.getUsername(token);
        if(username == null){
            System.out.println("用户为空");
            throw new AuthenticationException();
        }
        User user = userService.queryUserByUserName(username);
        if(user == null){
            System.out.println("无用户");
            throw new AuthenticationException();
        }
        boolean verify = JwtUtil.verify(token, username, user.getPassword());
        if(!verify){
            throw new AuthenticationException();
        }
        return new SimpleAuthenticationInfo(token, token, "MyRealm");
    }

    /**
     * 只有当需要检测用户权限的时候才会调用此方法，例如checkRole,checkPermission之类的
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        System.out.println("————权限认证————");
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        return info;
    }
}

