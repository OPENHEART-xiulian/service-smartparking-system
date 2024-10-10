package com.sp.cjgc.backend.service.impl;

import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.sp.cjgc.MyConstants;
import com.sp.cjgc.RespEnum;
import com.sp.cjgc.backend.domain.SystemWeChatJsapiPay;
import com.sp.cjgc.backend.domain.WxChatPayDto;
import com.sp.cjgc.backend.domain.WxPayReqParam;
import com.sp.cjgc.backend.service.OrderPaidCatOutboundService;
import com.sp.cjgc.backend.service.SystemWeChatJsapiPayService;
import com.sp.cjgc.backend.service.WxPayService;
import com.sp.cjgc.backend.utils.HttpRequestUtil;
import com.sp.cjgc.backend.utils.UploadUtils;
import com.sp.cjgc.backend.utils.WeChatPayUtil;
import com.sp.cjgc.backend.wxpay.WxPayCallbackUtil;
import com.sp.cjgc.backend.wxpay.WxPayCommon;
import com.sp.cjgc.common.exception.BizException;
import com.wechat.pay.contrib.apache.httpclient.util.PemUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.impl.client.CloseableHttpClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.security.Signature;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;

/**
 * @Author: Zoey
 * @Since: 2024-09-05 13:26:18
 * @Description: 微信支付服务实现类
 */
@Slf4j
@Service
public class WxPayServiceImpl implements WxPayService {

    @Resource
    private CloseableHttpClient wxPayClient;

    @Resource
    private WxPayCallbackUtil wxChatPayCallback;

    @Resource
    private OrderPaidCatOutboundService orderPaidCatOutboundService;

    @Value("${file.thirdParty}")
    private Boolean thirdParty;

    @Autowired
    private SystemWeChatJsapiPayService systemWeChatJsapiPayService;


    /**
     * 微信用户调用微信支付
     */
    @Override
    public WxChatPayDto wxPay(WxPayReqParam param) {
        // 获取用户的openId
        String openId = getWxOpenId(param.getCode());
        String prepayId = WxPayCommon.wxJsApiPay(param, openId, wxPayClient);
        WxChatPayDto wxChatPayDto = new WxChatPayDto();
        wxChatPayDto.setAppId(WeChatPayUtil.getAppId());
        wxChatPayDto.setTimeStamp(String.valueOf(System.currentTimeMillis() / 1000));
        wxChatPayDto.setNonceStr(UUID.randomUUID().toString().replaceAll("-", ""));
        wxChatPayDto.setPrepayId("prepay_id=" + prepayId);
        wxChatPayDto.setSignType("RSA");
        wxChatPayDto.setPaySign(getSign(wxChatPayDto.getNonceStr(), wxChatPayDto.getAppId(), wxChatPayDto.getPrepayId(), Long.parseLong(wxChatPayDto.getTimeStamp())));
        return wxChatPayDto;
    }

    /**
     * 微信支付成功回调
     *
     * @param request  request
     * @param response response
     * @return map
     */
    @Override
    public Map<String, String> wxOrderCallBack(HttpServletRequest request, HttpServletResponse response) {
        Map<String, String> map = new HashMap<>();
        try {
            Map<String, String> stringMap = wxChatPayCallback.wxChatPayCallback(request, response);
            //支付成功
            if (stringMap.get("trade_state").equals("SUCCESS")) {
                // 获取咱们自己生成的订单号 不是微信支付生成的
                String out_trade_no = stringMap.get("out_trade_no");
                // 写你自己的支付成功和后的逻辑 例如修改订单状态啥的。
                log.info("支付成功，订单号：{}", out_trade_no);
                orderPaidCatOutboundService.updateStatusByOrderNo(out_trade_no);
            }
            //响应微信
            map.put("code", "SUCCESS");
            map.put("message", "成功");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return map;
    }

    /**
     * 获取用户的openid
     *
     * @param code
     */
    public String getWxOpenId(String code) {
        try {
            // 调用微信小程序的获取session服务，从而获取当前用户的opendid
            String url =  MyConstants.sessionUrl
                    .replaceFirst("\\{\\}", WeChatPayUtil.getAppId())
                    .replaceFirst("\\{\\}", WeChatPayUtil.getSecret())
                    .replaceFirst("\\{\\}", code);
            String session = HttpRequestUtil.httpGet("获取微信session", url);
            // 判断是否有返回数据
            if (StringUtils.isNotBlank(session)) {
                // 转为json
                JSONObject obj = JSONUtil.parseObj(session);
                // 获取当前用户openid
                log.info("用户获取微信的openid成功");
                return obj.getStr("openid");
            } else {
                throw new BizException(RespEnum.FAILURE.getCode(), "获取用户openid失败");
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new BizException(RespEnum.FAILURE.getCode(), "获取用户openid失败");
        }
    }

    /**
     * 获取签名
     *
     * @param nonceStr  随机数
     * @param appId     微信公众号或者小程序等的appid
     * @param prepay_id 预支付交易会话ID
     * @param timestamp 时间戳 10位
     * @return String 新签名
     */
    String getSign(String nonceStr, String appId, String prepay_id, long timestamp) {
        //从下往上依次生成
        String message = buildMessage(appId, timestamp, nonceStr, prepay_id);
        //签名
        try {
            return sign(message.getBytes(MyConstants.CHARSET));
        } catch (IOException e) {
            throw new BizException("签名异常,请检查参数或商户私钥");
        }
    }

    String sign(byte[] message) {
        try {
            SystemWeChatJsapiPay entity = systemWeChatJsapiPayService.getWeChatJsapiPay();
            if (Objects.isNull(entity)) throw new BizException("微信JSAPI支付配置信息不存在");
            //签名方式
            Signature sign = Signature.getInstance("SHA256withRSA");
            // 获取商户私钥文件地址
            String privateKeyPath = entity.getPrivateKeyPath();
            if (thirdParty) {
                //从第三方服务器中读取私钥
                sign.initSign(PemUtil.loadPrivateKey(UploadUtils.readPrivateKeyFromServer(privateKeyPath)));
            } else {
                FileSystemResource resource = new FileSystemResource(privateKeyPath);
                InputStream fis = resource.getInputStream();
                //私钥，通过MyPrivateKey来获取，这是个静态类可以接调用方法 ，需要的是_key.pem文件的绝对路径配上文件名
                sign.initSign(PemUtil.loadPrivateKey(fis));
            }
            sign.update(message);
            return Base64.getEncoder().encodeToString(sign.sign());
        } catch (Exception e) {
            throw new BizException("签名异常,请检查参数或商户私钥");
        }
    }

    /**
     * 按照前端签名文档规范进行排序，\n是换行
     *
     * @param nonceStr  随机数
     * @param appId     微信公众号或者小程序等的appid
     * @param prepay_id 预支付交易会话ID
     * @param timestamp 时间戳 10位
     * @return String 新签名
     */
    String buildMessage(String appId, long timestamp, String nonceStr, String prepay_id) {
        return appId + "\n"
                + timestamp + "\n"
                + nonceStr + "\n"
                + prepay_id + "\n";
    }
}
