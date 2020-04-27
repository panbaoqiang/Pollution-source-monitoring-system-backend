package edu.hfut.util.comon;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
/**
 * @author panbaoqiang
 * @Description jwt令牌生成工具
 * @create 2020-04-27 22:48
 */
public class JwtUtil {
    /**
     * 过期时间，默认一分钟
     */
    private static final long EXPIRE_TIME = 30*60*1000;
    /**
     * 私钥
     */
    private static final String TOKEN_SECRET = "privateKey";
    /**
     * 生成签名
     * @param operatorId
     * @return
     */
    public static String sign(String operatorId){
        try{
            // 设置过期时间
            Date date = new Date(System.currentTimeMillis()+EXPIRE_TIME);

            // 私钥和加密算法
            Algorithm algorithm = Algorithm.HMAC256(TOKEN_SECRET);

            // 设置头部信息
            Map<String,Object> header = new HashMap<String,Object>(2);
            header.put("Type","Jwt");
            header.put("alg","HS256");
            return JWT.create()
                    .withHeader(header)
                    .withClaim("operatorId",operatorId)
                    .withExpiresAt(date)
                    .sign(algorithm);
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    public static String verify(String token){
        try{
            Algorithm algorithm = Algorithm.HMAC256(TOKEN_SECRET);
            JWTVerifier verifier = JWT.require(algorithm).build();
            DecodedJWT jwt = verifier.verify(token);
            String operatorId = jwt.getClaim("operatorId").asString();
            return operatorId;
        }catch (Exception e){
            return null;
        }
    }

    public static void main(String[] args) {
        String token = JwtUtil.sign("1");
        System.out.println(token);
        String id = JwtUtil.verify(token);
        System.out.println(id);
    }
}
