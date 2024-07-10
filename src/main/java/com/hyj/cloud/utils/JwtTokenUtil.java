package com.hyj.cloud.utils;


import com.hyj.cloud.common.constants.JwtConstants;
import com.hyj.cloud.model.dto.UserToken;
import com.hyj.cloud.pojo.AuthException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import lombok.Data;
import org.springframework.util.StringUtils;

import java.time.Duration;
import java.util.Date;
import java.util.UUID;

import static com.hyj.cloud.common.constants.JwtConstants.JWT_TOKEN_TTL;
import static io.jsonwebtoken.SignatureAlgorithm.HS256;

/**
 * @author: wangjing
 * @createTime: 2022-11-23 14:55
 * @version: 1.0.0
 * @Description: JwtToken 工具类
 */
@Data
public class JwtTokenUtil {



    /**
     * 生成token
     *
     * @param
     * @return
     */
    public static String createToken(String userId) {
        return Jwts.builder()
                .setHeaderParam("type","JWT")
                .setHeaderParam("alg",HS256)
                .claim(JwtConstants.PAYLOAD_USERID_KEY,userId)
                .setExpiration(new Date(System.currentTimeMillis() + JWT_TOKEN_TTL.toMillis()))
                .setId(UUID.randomUUID().toString())
                .signWith(HS256,JwtConstants.SECRET)
                .compact();
    }



    /**
     * 获取 token 中注册信息
     *
     * @param token
     * @return
     */
    public static Claims getTokenClaim(String token) {
        try {
            return Jwts.parser().setSigningKey(JwtConstants.SECRET).parseClaimsJws(token).getBody();
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * 验证 token 是否过期失效
     *
     * @param token
     * @return true 过期 false 未过期
     */
    public static Boolean isTokenExpired(String token) {
        return getExpirationDate(token).before(new Date());
    }

    /**
     * 获取 token 失效时间
     *
     * @param token
     * @return
     */
    public static Date getExpirationDate(String token) {
        return getTokenClaim(token).getExpiration();
    }


    /**
     * 获取 token 发布时间
     *
     * @param token
     * @return
     */
    public static Date getIssuedAtDate(String token) {
        return getTokenClaim(token).getIssuedAt();
    }


    /**
     * 获取用户Id
     *
     * @param token
     * @return
     */
    public static String getUserIdByToken(String token) {
        Claims claims = getTokenClaim(token);
        String userId = claims.get(JwtConstants.PAYLOAD_USERID_KEY).toString();
        return userId;
    }

    public static void main(String[] args) {
        JwtTokenUtil jwtTokenUtil = new JwtTokenUtil();
        String token = jwtTokenUtil.createToken("12345");
        String userId = jwtTokenUtil.getUserIdByToken(token);
        System.out.println(userId);
    }


}
