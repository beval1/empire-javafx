package com.beval.empirejavafx.utils;

public class ExceptionUtils {
    public static Throwable getCause(Throwable e) {
        Throwable cause = null;
        Throwable result = e;

        while (null != (cause = result.getCause()) && (result != cause)) {
            result = cause;
        }
        return result;
    }

    private ExceptionUtils(){}
}
