/**
 * File: ErrorMsgAnnotation.java
 * Author: weed
 * Create Time: 2012-11-26
 */
package appcloud.common.annotation;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * @author weed
 *
 */
@Retention(RetentionPolicy.RUNTIME)  
public @interface ErrorAnnotation {
	public String msg() default "";  
}