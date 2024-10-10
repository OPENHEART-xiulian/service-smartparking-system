package com.sp.cjgc.common.utils;

import cn.hutool.core.util.IdUtil;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.sp.cjgc.RespEnum;
import com.sp.cjgc.common.exception.BizException;

import java.util.Date;

/**
 * @Author: Zoey
 * @Date: 2024/2/28
 * @Time: 上午11:17
 * @Describe:
 */
public class JwtUtil {

    /**
     * 生成签名
     *
     * @param account  用户名
     * @param password 密码
     * @return 加密的token
     */
    public static String sign(String account, String password) {
        // 获取当前时间
        Date currentDate = new Date();
        // 默认过期时间为 30天
        long EXPIRE_TIME = 30 * 24 * 60 * 60;
        // 设置token过期时间,将秒数转换为毫秒并添加到当前时间上
        Date newDate = new Date(currentDate.getTime() + (EXPIRE_TIME * 1000));
        Algorithm algorithm = Algorithm.HMAC256(password);
        String jwtId = IdUtil.fastSimpleUUID();
        // 附带username信息
        return JWT.create()
                .withJWTId(jwtId)
                .withClaim("account", account)
                .withExpiresAt(newDate)
                .withIssuedAt(currentDate)
                .sign(algorithm);
    }

    /**
     * 校验token是否正确
     *
     * @param token    token
     * @param account  登录账号
     * @param password 登录密码
     * @return 是否正确
     */
    public static boolean verify(String token, String account, String password) {
        try {
            //根据密码生成JWT效验器
            Algorithm algorithm = Algorithm.HMAC256(password);
            JWTVerifier verifier = JWT.require(algorithm)
                    .withClaim("account", account)
                    .build();
            // 效验TOKEN
            verifier.verify(token);
            return true;
        } catch (Exception e) {
            throw new BizException(RespEnum.FAILURE.getCode(), "登录失效，请重新登录");
        }
    }

    /**
     * 获得token中的信息无需secret解密也能获得
     *
     * @return token中包含的用户名
     */
    public static String getAccount(String token) {
        try {
            DecodedJWT jwt = JWT.decode(token);
            return jwt.getClaim("account").asString();
        } catch (JWTDecodeException e) {
            return null;
        }
    }
}
