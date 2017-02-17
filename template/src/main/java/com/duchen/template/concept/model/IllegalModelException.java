package com.duchen.template.concept.model;

/**
 * 数据模型错误异常
 *
 * @author hzsunjianshun
 */
public class IllegalModelException extends RuntimeException {

    private static final long serialVersionUID = -8340903566440709502L;

    public IllegalModelException() {
    }

    public IllegalModelException(String detailMessage) {
        super(detailMessage);
    }

    public IllegalModelException(String message, Throwable cause) {
        super(message, cause);
    }

    public IllegalModelException(Throwable cause) {
        super((cause == null ? null : cause.toString()), cause);
    }
}
