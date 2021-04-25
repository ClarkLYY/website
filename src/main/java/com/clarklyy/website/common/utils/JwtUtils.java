package com.clarklyy.website.common.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.apache.log4j.Logger;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.Date;
@Data
@Slf4j
@Component
@ConfigurationProperties(prefix = "clark.jwt")
public class JwtUtils {
    private String secret;
    private long expire;
    private String header;

    //生成token
    public String generateToken(int userId){
        Date nowDate = new Date();
        Date expireDate = new Date(nowDate.getTime() + expire * 1000);
        return Jwts.builder()
                .setHeaderParam("typ", "JWT")
                .setSubject(userId+"")
                .setIssuedAt(nowDate)
                .setExpiration(expireDate)
                .signWith(SignatureAlgorithm.HS512, secret)
                .compact();
    }

    //校验token
    public Claims getClaimsByToken(String token){
        try{
            log.debug("进行token验证");
            return Jwts.parser()
                    .setSigningKey(secret)
                    .parseClaimsJws(token)
                    .getBody();
        }catch (Exception e){
            log.debug("token 错误", e);
            return null;
        }
    }

    public boolean isTokenExpired(Date expiration){
        return expiration.before(new Date());
    }
}
