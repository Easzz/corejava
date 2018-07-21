package com.easzz.corejava.utils.extensions;

/**
 * 工具类异常
 * Created by 李溪林 on 16-8-11.
 */
public class UtilsException extends RuntimeException {

    public UtilsException(String message){
        super(message);
    }

    public UtilsException(Throwable cause){
        super(cause);
    }

    public UtilsException(String message, Throwable cause){
        super(message,cause);
    }
}
