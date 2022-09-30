package com.hg.model;

public class IDVerifyMessage {
    private int code;
    private int status;
    private String message;
    private String serialNo;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getSerialNo() {
        return serialNo;
    }

    public void setSerialNo(String serialNo) {
        this.serialNo = serialNo;
    }

    @Override
    public String toString() {
        return "IDVerifyMessage{" +
                "code=" + code +
                ", status=" + status +
                ", message='" + message + '\'' +
                ", serialNo='" + serialNo + '\'' +
                '}';
    }
}
