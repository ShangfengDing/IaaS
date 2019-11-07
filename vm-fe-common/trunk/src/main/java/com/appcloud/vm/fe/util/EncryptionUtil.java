package com.appcloud.vm.fe.util;

import java.security.SecureRandom;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;

import org.apache.commons.lang3.StringUtils;

public class EncryptionUtil {
	
	public static String getKey(String email) {
		if (StringUtils.isEmpty(email)) {
			return "yunhai";
		} else {
			return email+"yunhai";
		}
	}
	
	/** 
     * 加密 
     *  
     * @param content 
     *            待加密内容 
     * @param key 
     *            加密的密钥 
     * @return 
     */  
    public static byte[] encrypt(String content, String key) {  
        try {  
            SecureRandom random = new SecureRandom();  
            DESKeySpec desKey = new DESKeySpec(key.getBytes("ISO-8859-1"));  
            SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");  
            SecretKey securekey = keyFactory.generateSecret(desKey);  
            Cipher cipher = Cipher.getInstance("DES");  
            cipher.init(Cipher.ENCRYPT_MODE, securekey, random);  
            byte[] result = cipher.doFinal(content.getBytes());  
            return result;  
        } catch (Throwable e) {  
            e.printStackTrace();  
        }  
        return null;  
    }  
  
    /** 
     * 解密 
     *  
     * @param content 
     *            待解密内容 
     * @param key 
     *            解密的密钥 
     * @return 
     */  
    public static String decrypt(byte[] content, String key) {  
        try {  
            SecureRandom random = new SecureRandom();  
            DESKeySpec desKey = new DESKeySpec(key.getBytes("ISO-8859-1"));  
            SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");  
            SecretKey securekey = keyFactory.generateSecret(desKey);  
            Cipher cipher = Cipher.getInstance("DES");  
            cipher.init(Cipher.DECRYPT_MODE, securekey, random);  
            byte[] result = cipher.doFinal(content);  
            return new String(result);  
        } catch (Throwable e) {  
            e.printStackTrace();  
        }  
        return null;  
    } 
}
