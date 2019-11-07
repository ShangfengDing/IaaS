package appcloud.openapi.manager.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import appcloud.openapi.constant.Constants;

public class StringUtil {
	private static final Pattern numericPattern = Pattern.compile("^[0-9]+$");
	
	/**
     * 判断是否数字表示  
     *   
     * @param src  
     *            源字符串  
     * @return 是否数字的标志  
     */ 
    public static boolean isNumeric(String src) {  
        boolean return_value = false;  
        if (src != null && src.length() > 0) {  
            Matcher m = numericPattern.matcher(src);  
            if (m.find()) {  
                return_value = true;  
            }  
        }  
        return return_value;  
    }  
    
    /**
     * 判断是否是空字符串 null和"" 都返回 true  
     *   
     * @author Robin Chang  
     * @param s  
     * @return  
     */ 
    public static boolean isEmpty(String s) {  
        if (s != null && !s.equals("")) {  
            return false;  
        }  
        return true;  
    }  
    
    /**
     * 判断是否数字或空表示  
     *   
     * @param src  
     *            源字符串  
     * @return 是否数字或空的标志  
     */ 
    public static boolean isNullOrNumeric(String src) {  
    	if (isEmpty(src))
    		return true;
        return isNumeric(src);  
    }  
    
    /**
     * 检查字符串
     * @param src
     * @param minL
     * @param maxL
     * @return
     */
    public static boolean isValidInSizeAndChinese(String src, Integer minL, Integer maxL) {  
    	if (src == null) return false;
        return Pattern.matches("(.|[\u4e00-\u9fa5]){"+ minL + ","+
        		maxL+"}",src);
    }  
    
    /**
     * 检查字符串是否是UUID，UUID由32位字符构成
     * @param uuid
     * @return
     */
    public static boolean isUUID (String uuid) {
    	if (uuid == null) return false;
    	return Pattern.matches("\\w{32}", uuid);
    }
    
    public static void main(String[] args) {
		System.out.println(isValidInSizeAndChinese("api-test-volume", 1, 20));
	}
}
