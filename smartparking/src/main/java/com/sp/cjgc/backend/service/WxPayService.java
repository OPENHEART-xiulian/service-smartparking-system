package com.sp.cjgc.backend.service;

import com.sp.cjgc.backend.domain.WxChatPayDto;
import com.sp.cjgc.backend.domain.WxPayReqParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * @Author: Zoey
 * @Since: 2024-09-05 13:58:20
 * @Description:
 */
public interface WxPayService {
    WxChatPayDto wxPay(WxPayReqParam param);

    Map<String, String> wxOrderCallBack(HttpServletRequest request, HttpServletResponse response);
}
