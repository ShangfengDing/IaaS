package appcloud.imageserver.util;

import java.math.BigInteger;

import appcloud.common.util.Md5Tool;
import appcloud.common.util.WarLocationTool;

public class NfsLocationTool extends WarLocationTool{

	/**
	 * 返回某个image在nfs上的存储相对目录，如images/a/b/
	 * @param imageName	image名，如 CentOS 6.3-uuid.img
	 * @return 			image在nfs上的存储目录
	 */
	public static String getNfsLocationDir(String imageName){
		BigInteger md5Num = Md5Tool.getMd5Num(imageName.trim());

        // md5 % 26
        int firstCharVal = md5Num.remainder(CHAR_NUM).intValue();
        // (md5 / 26 ) % 26
        int secondCharVal = md5Num.divide(CHAR_NUM).remainder(CHAR_NUM).intValue();

        char aChar = 'a';
        int aCharVal = (int) aChar;

        char firstChar = (char)(aCharVal + firstCharVal);
        char secondChar = (char)(aCharVal + secondCharVal);
        
        String fileSeparator = "/";
        return Config.IMAGE_ROOTDIR + fileSeparator
                + firstChar + fileSeparator
                + secondChar + fileSeparator;
//        return Config.IMAGE_ROOTDIR + FILE_SEPARATOR
//                + firstChar + FILE_SEPARATOR
//                + secondChar + FILE_SEPARATOR;
	}
	
	/**
	 * 返回某个image在nfs上的具体位置，如images/a/b/CentOS 6.3.img
	 * @param imageName	image名，如 CentOS 6.3.img
	 * @return 			image在nfs上的具体位置
	 */
	public static String getNfsLocation(String imageName){
		return getNfsLocationDir(imageName) + imageName.trim();
	}
}
