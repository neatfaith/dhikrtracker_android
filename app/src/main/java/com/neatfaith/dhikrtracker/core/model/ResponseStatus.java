package com.neatfaith.dhikrtracker.core.model;


import java.util.HashMap;
import java.util.Map;

/**
 * This class will be used to return error information from database calls etc.
 *
 */

public class ResponseStatus {

    private boolean success;
    private long code;
    private String message;

    public ResponseStatus() {
        this.success = false;
        this.code = 0;
        this.message = "";
    }

    public ResponseStatus(boolean success, long code, String message) {
        this.success = success;
        this.code = code;
        this.message = message;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public long getCode() {
        return code;
    }

    public void setCode(long code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {

        Map<String,String> map = new HashMap<>();

        map.put("success",""+this.isSuccess());
        map.put("code",""+this.getCode());
        map.put("message",""+this.getMessage());


        return map.toString();
    }
}
