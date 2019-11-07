/*
 * Copyright 2011 BUPT. All rights reserved.
 * BUPT PROPRIETARY/CONFIDENTIAL.
 */

package appcloud.common.util;

import java.security.MessageDigest;
import java.math.BigInteger;
/**
 *
 * @author yicou
 */
public class Md5Tool {

    private static MessageDigest md5 = null;
    static {
        try {
            md5 = MessageDigest.getInstance("MD5");
        } catch (Exception e) {
            System.out.println(e.toString());
            e.printStackTrace();
        }
    }
    
    /**
     * 用于获取一个String的md5值
     * @param string
     * @return
     */
    public static String getMd5(String str) {
        byte[] bs = md5.digest(str.getBytes());
        StringBuilder sb = new StringBuilder(40);
        for (int i = 0; i < bs.length; i++) {
            int x = bs[i];
            if (x < 0) {
                x += 256;
            }
            sb.append(Integer.toHexString(x));
        }
        return sb.toString();
    }

    /**
     * 0x0F
     */
    public static int MASK = 15;

    /**
     * 用于获取一个String的md5数值
     * @param BigInteger
     * @return
     */
    public static BigInteger getMd5Num(String str) {
        byte[] bs = md5.digest(str.getBytes());
        BigInteger num = BigInteger.ZERO;
        for (int i = 0; i < bs.length; i++) {
            int x = bs[i];
            if (x < 0) {
                x += 256;
            }
            num = num.shiftLeft(4);
            num = num.add(BigInteger.valueOf(x & MASK));
        }
        return num;
    }
}
