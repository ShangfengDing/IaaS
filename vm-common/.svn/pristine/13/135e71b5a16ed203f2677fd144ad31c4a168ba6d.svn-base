/*
 * Copyright 2011 BUPT. All rights reserved.
 * BUPT PROPRIETARY/CONFIDENTIAL.
 */

package appcloud.common.util;

import java.math.BigInteger;

/**
 *
 * @author yicou
 */
public class WarLocationTool {

    /**
     * '/'
     */
    public static final String FILE_SEPARATOR =
            System.getProperties().getProperty("file.separator");

    public static final String DEFAULT_NFS_DIR = "/nfs";
    public static final String DEFAULT_WAR_DIR = "j2ee-wars";
    public static final BigInteger CHAR_NUM = BigInteger.valueOf(26);

    /**
     * 返回如　/nfs/j2ee-wars/a/b/appid.war
     * @param appid
     * @return
     */
    public static String getWarLocation(String appid) {
        return getWarLocation(DEFAULT_NFS_DIR,
                DEFAULT_WAR_DIR,
                appid.trim());
    }

    /**
     * 返回如　/nfs/j2ee-wars/a/b/
     * @param nfsDir
     * @param warDir
     * @param appid
     * @return
     */
    public static String getWarLocationDir(String nfsDir, String warDir, String appid) {
        BigInteger md5Num = Md5Tool.getMd5Num(appid.trim());

        // md5 % 26
        int firstCharVal = md5Num.remainder(CHAR_NUM).intValue();
        // (md5 / 26 ) % 26
        int secondCharVal = md5Num.divide(CHAR_NUM).remainder(CHAR_NUM).intValue();

        char aChar = 'a';
        int aCharVal = (int) aChar;

        char firstChar = (char)(aCharVal + firstCharVal);
        char secondChar = (char)(aCharVal + secondCharVal);

        return nfsDir + FILE_SEPARATOR
                + warDir + FILE_SEPARATOR
                + firstChar + FILE_SEPARATOR
                + secondChar + FILE_SEPARATOR;
    }

    /**
     * 返回如　/nfs/j2ee-wars/a/b/appid.war
     * @param nfsDir
     * @param warDir
     * @param appid
     * @return
     */
    public static String getWarLocation(String nfsDir, String warDir, String appid) {
        return getWarLocationDir(nfsDir, warDir, appid.trim()) + appid.trim() + ".war";
    }
}
