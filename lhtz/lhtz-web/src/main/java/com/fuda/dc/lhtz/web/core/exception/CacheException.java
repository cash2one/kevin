package com.fuda.dc.lhtz.web.core.exception;

/** 
 * Cache操作的异常
 * 
 * @author liukai
 */
public class CacheException extends RuntimeException {
    private static final long serialVersionUID = -8582680672937004772L;

    /**
     * Cache操作的异常
     */
    public CacheException() {
        super();
    }

    /**
     * Cache操作的异常
     * 
     * @param message   异常消息
     * @param cause     异常堆栈
     */
    public CacheException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * Cache操作的异常
     * 
     * @param message   异常消息
     */
    public CacheException(String message) {
        super(message);
    }

    /**
     * Cache操作的异常
     * 
     * @param cause 异常堆栈
     */
    public CacheException(Throwable cause) {
        super(cause);
    }

}
