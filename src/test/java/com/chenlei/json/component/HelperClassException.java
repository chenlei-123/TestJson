package com.chenlei.json.component;

/**
 * Created by Administrator on 2016/4/20.
 */
public class HelperClassException extends Exception {
    public HelperClassException() {
    }

    public HelperClassException(String message) {
        super(message);
    }

    public HelperClassException(String message, Throwable cause) {
        super(message, cause);
    }

    public HelperClassException(Throwable cause) {
        super(cause);
    }

    public HelperClassException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
