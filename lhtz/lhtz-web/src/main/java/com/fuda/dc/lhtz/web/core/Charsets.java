package com.fuda.dc.lhtz.web.core;

/**
 * 常用字符编码
 * 
 * @author liukai
 */
public enum Charsets {

    UTF8("UTF-8"), //
    GBK("GBK"), //
    ISO8859("ISO8859-1");

    public final String charset;

    /**
     * 编码枚举类
     * 
     * @param value	编码
     */
    private Charsets(String value) {
        this.charset = value;
    }
}
