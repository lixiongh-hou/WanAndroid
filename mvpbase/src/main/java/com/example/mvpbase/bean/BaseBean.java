package com.example.mvpbase.bean;

/**
 * @author: 雄厚
 * Date: 2020/8/6
 * Time: 15:11
 * 接收响应的数据 可根据自己需求进行修改
 */
public class BaseBean<T> {
    /**
     * code : 200           (接口状态码)
     * ----------------200  业务处理成功
     * ----------------0    业务处理失败
     * ----------------1    业务调用错误
     * ----------------500	服务器处理异常
     * ----------------403	拒绝请求(常见于签名错误)
     * ----------------401	未认证(常见于未登录)
     * ----------------404	服务未找到
     * workCode : 1         (业务码先不管)
     * msg : SUCCESS        (业务结果描述)
     * data : T             (业务结果)
     * time : 1534745411903 (服务器时间)
     */
    private int errorCode;
    private String errorMsg;
    private T data;
    public T getData() {
        return data;
    }
    public void setData(T data) {
        this.data = data;
    }

    public int getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }

    /**
     * @return true:成功
     */
    public boolean isSuccess() {
        return (0 == errorCode);
    }
}
