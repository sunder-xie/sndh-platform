package com.nhry.webService.client;

/**
 * Created by cbz on 7/6/2016.
 */
public class PISuccessMessage {
    private boolean success;
    private String data;
    private String message;

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
