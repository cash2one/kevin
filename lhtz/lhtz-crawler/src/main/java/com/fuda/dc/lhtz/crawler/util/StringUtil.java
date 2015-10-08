package com.fuda.dc.lhtz.crawler.util;

public class StringUtil {
	
	public static String implode(String... values) {
		StringBuilder sb = new StringBuilder();
		for (String value : values) {
			sb.append(value);
		}
		return sb.toString();
	}
	
	public static float strToFloat(String field) {
		return strToFloat(field, false);
	}
	
	/**
	 * 将爬取的数字字符串转成float类型
	 * 
	 * @param field
	 * @param isRatio
	 * @return
	 */
	public static float strToFloat(String field, boolean isRatio) {
        if (field == null || "".equals(field)) {
        	return (float)0.0;
        } else {
        	if (isRatio) {
        		if (field.endsWith("%")) {
        			field = field.substring(0, field.length() - 1);
        		}
        	}
        	try {
        		return Float.valueOf(field);
        	} catch (Exception e) {
        		return (float)0.0;
        	}
		}
	}
}
