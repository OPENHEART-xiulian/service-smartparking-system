package com.sp.cjgc.common.section;

import com.sp.cjgc.RespEnum;
import com.sp.cjgc.backend.mapper.SystemUsersMapper;
import com.sp.cjgc.common.annotation.PassToken;
import com.sp.cjgc.common.exception.BizException;
import com.sp.cjgc.common.utils.LoginUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;

/**
 * @Author: Zoey
 * @Date: 2024/2/28
 * @Time: 上午11:16
 * @Describe:
 */
@Slf4j
public class JwtAuthInterceptor implements HandlerInterceptor {

    // 判断是否开启token验证 true|开启  false｜关闭
    @Value("${global.token}")
    private boolean openOrNotToken;

    @Value("${global.authorization}")
    private String authorization;

    @Autowired
    private SystemUsersMapper spUsersMapper;

    @Override
    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object object) throws Exception {
        // 判断是否开启了token验证
        if (!openOrNotToken) return true;
        // 如果不是映射到方法直接通过
        if (!(object instanceof HandlerMethod)) return true;
        // 获取方法上的注解
        HandlerMethod handlerMethod = (HandlerMethod) object;
        Method method = handlerMethod.getMethod();
        // 判断方法上是否有 @PassToken 注解
        if (method.isAnnotationPresent(PassToken.class)) {
            PassToken passToken = method.getAnnotation(PassToken.class);
            // 判断是否需要token验证
            if (passToken.required()) return true;
            // 获取token
            String token = httpServletRequest.getHeader(authorization);
            // 判断是否存在token
            if (StringUtils.isBlank(token))
                throw new BizException(RespEnum.FAILURE.getCode(), "请输入token");
            // token验证
            LoginUtil.loginInspect(token, passToken.verifyPermissions(), passToken.authority(), spUsersMapper);
            return true;
        }
        // 如果没有 PassToken 则说明也不需要token，直接免登录
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    /**
     * 整个请求结束之后被调用，也就是在DispatchServlet渲染了对应的视图之后执行（主要用于进行资源清理工作）
     *
     * @param httpServletRequest
     * @param httpServletResponse
     * @param o
     * @param e
     * @throws Exception
     */
    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {
        log.info("请求结束 执行了 afterCompletion 方法...");
    }
}
