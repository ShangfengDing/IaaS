/**
 * File: ErrorCode.java
 * Author: weed
 * Create Time: 2012-11-26
 */
package appcloud.common.errorcode;

import appcloud.common.annotation.ErrorAnnotation;

/**
 * @author weed
 *
 */
public class ErrorCode{
    /** 
     * return error message
     */  
    public static String getMessage(Enum<? extends Enum<?>> errorCode){  
    	ErrorAnnotation error = null;  
        try {  
            error = errorCode.getClass().getField(errorCode.name()).getAnnotation(ErrorAnnotation.class);  
        } catch (Exception e) {  
            return null;  
        }  
        return error.msg();  
    }  
}
