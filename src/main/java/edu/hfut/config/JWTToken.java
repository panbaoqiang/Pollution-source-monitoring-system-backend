package edu.hfut.config;

import org.apache.shiro.authc.AuthenticationToken;

/**
 * @author panbaoqiang
 * @Description
 * @create 2020-05-05 11:02
 */

public class JWTToken implements AuthenticationToken {

    // 密钥
    private String token;

    public JWTToken(String token) {
        this.token = token;
    }

    @Override
    public Object getPrincipal() {
        return token;
    }

    @Override
    public Object getCredentials() {
        return token;
    }
}
