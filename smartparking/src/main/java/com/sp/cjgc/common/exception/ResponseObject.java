package com.sp.cjgc.common.exception;

import com.sp.cjgc.RespEnum;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;

/**
 * @Author: Zoey
 * @Date: 2024/1/4
 * @Time: 上午9:44
 * @Describe:
 */
@Data
@ToString
@NoArgsConstructor
public class ResponseObject<T> implements Serializable {

    private static final long serialVersionUID = -8713837118340960775L;

    /**
     * 响应代码
     */
    @ApiModelProperty(value = "响应状态，200｜操作成功、非200｜操作失败", position = 1)
    private Integer code;

    /**
     * 响应消息
     */
    @ApiModelProperty(value = "消息说明", position = 2)
    private String message;

    /**
     * 响应结果
     */
    @ApiModelProperty(value = "响应结果", position = 3)
    private T data;

    public ResponseObject(BaseErrorInfoInterface errorInfo) {
        this.code = errorInfo.getResultCode();
        this.message = errorInfo.getResultMsg();
    }

    /**
     * 成功
     *
     * @return
     */
    public static <T> ResponseObject success() {
        ResponseObject<T> rb = new ResponseObject<T>();
        rb.setCode(RespEnum.SUCCESS.getCode());
        rb.setMessage(RespEnum.SUCCESS.getMessage());
        return rb;
    }

    /**
     * 成功
     *
     * @param data
     * @return
     */
    public static <T> ResponseObject<T> success(T data) {
        ResponseObject<T> rb = new ResponseObject<T>();
        rb.setCode(RespEnum.SUCCESS.getCode());
        rb.setMessage(RespEnum.SUCCESS.getMessage());
        rb.setData(data);
        return rb;
    }

    /**
     * 失败
     */
    public static <T> ResponseObject<T> error(BaseErrorInfoInterface errorInfo) {
        ResponseObject<T> rb = new ResponseObject<T>();
        rb.setCode(errorInfo.getResultCode());
        rb.setMessage(errorInfo.getResultMsg());
        return rb;
    }

    /**
     * 失败
     */
    public static <T> ResponseObject<T> error(Integer code, String message) {
        ResponseObject<T> rb = new ResponseObject<T>();
        rb.setCode(code);
        rb.setMessage(message);
        return rb;
    }
}