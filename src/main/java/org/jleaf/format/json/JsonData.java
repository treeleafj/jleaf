package org.jleaf.format.json;

import java.io.Serializable;

@SuppressWarnings("serial")
public class JsonData implements Serializable {

    /**
     * 是否成功
     */
    private boolean success = true;

    /**
     * 信息(一般放错误信息)
     */
    private String msg;

    /**
     * 返回结果
     */
    private Object result;

    public JsonData() {
    }

    public JsonData(boolean success, String msg) {
        super();
        this.success = success;
        this.msg = msg;
    }

    public JsonData(String msg, Object result) {
        super();
        this.msg = msg;
        this.result = result;
    }

    public JsonData(boolean success, String msg, Object result) {
        this.success = success;
        this.msg = msg;
        this.result = result;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Object getResult() {
        return result;
    }

    public void setResult(Object result) {
        this.result = result;
    }
}
