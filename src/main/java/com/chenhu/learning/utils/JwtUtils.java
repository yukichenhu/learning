package com.chenhu.learning.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.util.ObjectUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

/**
 * @author chenhu
 * @since 2022/06/23
 */
public class JwtUtils {

    private JwtUtils(){

    }

    public static final long EXPIRE = 1000 * 60 * 60 * 12;//token过期时间   12小时
    public static final String APP_SECRET = "chen1234abcd";//密钥


    public static String getJwtToken(Long userId, String username){

        return Jwts.builder()
                .setHeaderParam("typ", "JWT")
                .setHeaderParam("alg", "HS256")
                .setSubject("my-user")
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRE))
                .claim("userId", userId)
                .claim("username", username)
                .signWith(SignatureAlgorithm.HS256, APP_SECRET)
                .compact();
    }

    /**
     * 根据token，判断token是否存在与有效
     * @param jwtToken token
     * @return 是否有效
     */
    public static boolean checkToken(String jwtToken) {
        if(ObjectUtils.isEmpty(jwtToken)) return false;
        try {
            Jwts.parser().setSigningKey(APP_SECRET).parseClaimsJws(jwtToken);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    /**
     * 根据request判断token是否存在与有效（也就是把token取出来罢了）
     * @param request 请求
     * @return 是否有效
     */
    public static boolean checkToken(HttpServletRequest request) {
        String jwtToken = request.getHeader("Token");
        return checkToken(jwtToken);
    }

    /**
     * 根据request获取用户id
     * @param request 请求
     * @return 用户id
     */
    public static Long getUserId(HttpServletRequest request) {
        String jwtToken = request.getHeader("Token");
        if(ObjectUtils.isEmpty(jwtToken)){
            return null;
        }
        try{
            Jws<Claims> claimsJws = Jwts.parser().setSigningKey(APP_SECRET).parseClaimsJws(jwtToken);
            Claims claims = claimsJws.getBody();
            return (Long) claims.get("userId");
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }


    /**
     * 根据token获取用户的username
     * @param request 请求
     * @return 用户id
     */
    public static String getUsername(HttpServletRequest request) {
        String jwtToken = request.getHeader("Token");
        if(ObjectUtils.isEmpty(jwtToken)){
            return null;
        }
        try{
            Jws<Claims> claimsJws = Jwts.parser().setSigningKey(APP_SECRET).parseClaimsJws(jwtToken);
            Claims claims = claimsJws.getBody();
            return (String) claims.get("username");
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }
}
