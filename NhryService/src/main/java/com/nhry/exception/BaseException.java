package com.nhry.exception;

public class BaseException extends RuntimeException {

	private String code;

    private Object[] values;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Object[] getValues() {
        return values;
    }

    public void setValues(Object[] values) {
        this.values = values;
    }

    public BaseException(String message, Throwable cause, String code, Object[] values) {
        super(message, cause);
        this.code = code;
        this.values = values;
    }
}
