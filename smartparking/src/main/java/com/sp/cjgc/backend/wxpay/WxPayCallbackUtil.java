package com.sp.cjgc.backend.wxpay;

import com.alibaba.fastjson.JSONObject;
import com.sp.cjgc.backend.domain.SystemWeChatJsapiPay;
import com.sp.cjgc.backend.service.SystemWeChatJsapiPayService;
import com.sp.cjgc.common.exception.BizException;
import com.wechat.pay.contrib.apache.httpclient.auth.Verifier;
import com.wechat.pay.contrib.apache.httpclient.util.AesUtil;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.security.GeneralSecurityException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @Author: Zoey
 * @Since: 2024-09-05 13:54:00
 * @Description:
 */
@Component
public class WxPayCallbackUtil {

    @Resource
    private Verifier verifier;

    @Resource
    private SystemWeChatJsapiPayService systemWeChatJsapiPayService;

    /**
     * 获取回调数据
     * @param request
     * @param response
     * @return
     */
    public Map<String, String> wxChatPayCallback(HttpServletRequest request, HttpServletResponse response) {
        //获取报文
        String body = getRequestBody(request);

        //随机串
        String nonceStr = request.getHeader("Wechatpay-Nonce");

        //微信传递过来的签名
        String signature = request.getHeader("Wechatpay-Signature");

        //证书序列号（微信平台）
        String serialNo = request.getHeader("Wechatpay-Serial");

        //时间戳
        String timestamp = request.getHeader("Wechatpay-Timestamp");

        //构造签名串  应答时间戳\n,应答随机串\n,应答报文主体\n
        String signStr = Stream.of(timestamp, nonceStr, body).collect(Collectors.joining("\n", "", "\n"));

        Map<String, String> map = new HashMap<>(2);
        try {
            //验证签名是否通过
            boolean result = verifiedSign(serialNo, signStr, signature);
            if(result){
                //解密数据
                String plainBody = decryptBody(body);
                return convertWechatPayMsgToMap(plainBody);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return map;
    }

    /**
     * 转换body为map
     * @param plainBody
     * @return
     */
    public Map<String,String> convertWechatPayMsgToMap(String plainBody){

        Map<String,String> paramsMap = new HashMap<>(2);

        JSONObject jsonObject = JSONObject.parseObject(plainBody);
        //商户订单号
        paramsMap.put("out_trade_no",jsonObject.getString("out_trade_no"));
        //交易状态
        paramsMap.put("trade_state",jsonObject.getString("trade_state"));
        //附加数据
        paramsMap.put("attach",jsonObject.getString("attach"));
        if (jsonObject.getJSONObject("attach") != null && !jsonObject.getJSONObject("attach").equals("")){
            paramsMap.put("account_no",jsonObject.getJSONObject("attach").getString("accountNo"));
        }
        return paramsMap;

    }

    /**
     * 解密body的密文
     *
     * "resource": {
     *         "original_type": "transaction",
     *         "algorithm": "AEAD_AES_256_GCM",
     *         "ciphertext": "",
     *         "associated_data": "",
     *         "nonce": ""
     *     }
     *
     * @param body body
     * @return
     */
    public String decryptBody(String body) throws UnsupportedEncodingException, GeneralSecurityException {
        SystemWeChatJsapiPay entity = systemWeChatJsapiPayService.getWeChatJsapiPay();
        if (Objects.isNull(entity)) throw new BizException("微信JSAPI支付配置信息不存在");
        AesUtil aesUtil = new AesUtil(entity.getApiV3Key().getBytes("utf-8"));
        JSONObject object = JSONObject.parseObject(body);
        JSONObject resource = object.getJSONObject("resource");
        String ciphertext = resource.getString("ciphertext");
        String associatedData = resource.getString("associated_data");
        String nonce = resource.getString("nonce");
        return aesUtil.decryptToString(associatedData.getBytes("utf-8"),nonce.getBytes("utf-8"),ciphertext);
    }


    /**
     * 验证签名
     *
     * @param serialNo  微信平台-证书序列号
     * @param signStr   自己组装的签名串
     * @param signature 微信返回的签名
     * @return
     * @throws UnsupportedEncodingException
     */
    public boolean verifiedSign(String serialNo, String signStr, String signature) throws UnsupportedEncodingException {
        return verifier.verify(serialNo, signStr.getBytes("utf-8"), signature);
    }

    /**
     * 读取请求数据流
     *
     * @param request
     * @return
     */
    public String getRequestBody(HttpServletRequest request) {
        StringBuffer sb = new StringBuffer();
        try (ServletInputStream inputStream = request.getInputStream();
             BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
        ) {
            String line;
            while ((line = reader.readLine()) != null) {
                sb.append(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return sb.toString();

    }
}
