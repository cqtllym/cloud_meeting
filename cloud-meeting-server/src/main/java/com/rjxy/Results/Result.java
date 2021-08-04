package com.rjxy.Results;

import com.rjxy.Enum.ErrorEnum;

public class Result<T> {

    private int code;
    private String errMsg;
    private T data;

    public Result() {

    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getErrMsg() {
        return errMsg;
    }

    public void setErrMsg(String errMsg) {
        this.errMsg = errMsg;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    //正常处理
    public static <T> Result<T> success(T data) {
        return new Result<>(data);
    }

    //异常时处理
    public static <T> Result<T> error(int code, String errMsg) {
        return new Result<>(code, errMsg);
    }

    //异常时处理
    public static <T> Result<T> error(ErrorEnum errorEnum) {
        return new Result<>(errorEnum.getCode(), errorEnum.getErrMsg());
    }

    //success情况下调用的构造器
    public Result(T data) {
        this.code = 200;
        this.data = data;
    }

    //error情况下调用的构造器
    private Result(int code, String errMsg) {
        this.code = code;
        this.errMsg = errMsg;
    }

    //其他
    private Result(int code, String errMsg, T data) {
        this.code = code;
        this.errMsg = errMsg;
        this.data = data;
    }
}
