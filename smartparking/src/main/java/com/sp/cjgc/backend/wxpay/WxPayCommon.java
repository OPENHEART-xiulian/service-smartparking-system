package com.sp.cjgc.backend.wxpay;

import com.google.gson.Gson;
import com.sp.cjgc.MyConstants;
import com.sp.cjgc.backend.domain.WxPayReqParam;
import com.sp.cjgc.backend.utils.WeChatPayUtil;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * @Author: Zoey
 * @Since: 2024-09-05 13:33:06
 * @Description:
 */
public class WxPayCommon {

    /**
     * 封装基础通用请求数据
     *
     * @param basePayData 微信支付基础请求数据
     * @return 封装后的map对象
     */
    public static Map<String, Object> getBasePayParams(WxPayReqParam basePayData) {
        Map<String, Object> paramsMap = new HashMap<>();
        paramsMap.put("appid", WeChatPayUtil.getAppId());
        paramsMap.put("mchid", WeChatPayUtil.getMacId());
        // 如果商品名称
        paramsMap.put("description", "临停付费");
        // 订单号
        paramsMap.put("out_trade_no", basePayData.getOrderNumber());
        // 回调地址
        paramsMap.put("notify_url", MyConstants.WX_PAY_NOTIFY_URL);
        Map<String, Object> amountMap = new HashMap<>();
        // 支付金额
        double totalPrice = Double.parseDouble(basePayData.getTotalPrice());
        int totalCents = (int) (totalPrice * 100);
        amountMap.put("total", totalCents);
        amountMap.put("currency", WeChatPayUtil.getCurrency());
        paramsMap.put("amount", amountMap);
        return paramsMap;
    }

    /**
     * 获取请求对象（Post请求）
     *
     * @param paramsMap 请求参数
     * @return Post请求对象
     */
    public static HttpPost getHttpPost(String type, Map<String, Object> paramsMap) {

        // 1.设置请求地址
        HttpPost httpPost = new HttpPost(type);

        // 2.设置请求数据
        Gson gson = new Gson();
        String jsonParams = gson.toJson(paramsMap);

        // 3.设置请求信息
        StringEntity entity = new StringEntity(jsonParams, MyConstants.CHARSET);
        entity.setContentType("application/json");
        httpPost.setEntity(entity);
        httpPost.setHeader("Accept", "application/json");
        return httpPost;
    }

    /**
     * 解析响应数据
     *
     * @param response 发送请求成功后，返回的数据
     * @return 微信返回的参数
     */
    public static HashMap<String, String> resolverResponse(CloseableHttpResponse response) {
        try {
            // 1.获取请求码
            int statusCode = response.getStatusLine().getStatusCode();
            // 2.获取返回值 String 格式
            final String bodyAsString = EntityUtils.toString(response.getEntity());

            Gson gson = new Gson();
            if (statusCode == 200) {
                // 3.如果请求成功则解析成Map对象返回
                HashMap<String, String> resultMap = gson.fromJson(bodyAsString, HashMap.class);
                return resultMap;
            } else {
                if (StringUtils.isNoneBlank(bodyAsString)) {
                    // 4.请求码显示失败，则尝试获取提示信息
                    HashMap<String, String> resultMap = gson.fromJson(bodyAsString, HashMap.class);
                    throw new RuntimeException(resultMap.get("message"));
                }
                // 其他异常，微信也没有返回数据，这就需要具体排查了
                throw new IOException("request failed");
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e.getMessage());
        } finally {
            try {
                response.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 创建微信支付订单-jsapi方式
     *
     * @param basePayData 基础请求信息，商品标题、商家订单id、订单价格
     * @param openId      通过微信小程序或者公众号获取到用户的openId
     * @param wxPayClient 微信请求客户端（）
     * @return 微信支付二维码地址
     */
    public static String wxJsApiPay(WxPayReqParam basePayData, String openId, CloseableHttpClient wxPayClient) {

        // 1.获取请求参数的Map格式
        Map<String, Object> paramsMap = getBasePayParams(basePayData);

        // 1.1 添加支付者信息
        Map<String, String> payerMap = new HashMap<>();
        payerMap.put("openid", openId);
        paramsMap.put("payer", payerMap);

        // 2.获取请求对象
        HttpPost httpPost = getHttpPost(MyConstants.DOMAIN, paramsMap);

        // 3.完成签名并执行请求
        CloseableHttpResponse response = null;
        try {
            response = wxPayClient.execute(httpPost);
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("微信支付请求失败");
        }

        // 4.解析response对象
        HashMap<String, String> resultMap = resolverResponse(response);
        if (resultMap != null) {
            // native请求返回的是二维码链接，前端将链接转换成二维码即可
            return resultMap.get("prepay_id");
        }
        return null;
    }
}
