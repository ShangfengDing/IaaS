/**
 * File: StringUtil.java
 * Author: weed
 * Create Time: 2012-11-13
 */
package appcloud.common.util;

import java.security.MessageDigest;

/**
 * @author weed
 *
 */
public class StringUtil {

	public static String md5Digest(String content) throws Exception {
        MessageDigest messageDigest = MessageDigest.getInstance("MD5");
        messageDigest.update(content.getBytes());
        byte[] buffer = messageDigest.digest();
 
        StringBuffer sb = new StringBuffer(buffer.length * 2);
        for (int i = 0; i < buffer.length; i++) {
            sb.append(Character.forDigit((buffer[i] & 240) >> 4, 16));
            sb.append(Character.forDigit(buffer[i] & 15, 16));
        }
        return sb.toString();
    }
}
