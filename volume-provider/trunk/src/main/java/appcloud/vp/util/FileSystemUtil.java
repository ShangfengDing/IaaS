package appcloud.vp.util;

import java.io.File;
import java.io.IOException;
import java.math.BigInteger;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;

import appcloud.common.util.Md5Tool;
import appcloud.common.util.io.ShellUtil;

public class FileSystemUtil {
	private static Logger logger = Logger.getLogger(FileSystemUtil.class);
	public static final BigInteger CHAR_NUM = BigInteger.valueOf(26);
	public static final String FILE_SEPARATOR = "/";
	public static final String SPLIT_STRING = "#@#";
	
	public static void main(String[] args) {
		System.out.println(getImgDir("f077dded7d2e4230a1aa66bf92051b2e"));
	}
	public static String getImgDir(String imgFile) {
        BigInteger md5Num = Md5Tool.getMd5Num(imgFile.trim());

        // md5 % 26
        int firstCharVal = md5Num.remainder(CHAR_NUM).intValue();
        // (md5 / 26 ) % 26
        int secondCharVal = md5Num.divide(CHAR_NUM).remainder(CHAR_NUM).intValue();

        char aChar = 'a';
        int aCharVal = (int) aChar;

        char firstChar = (char)(aCharVal + firstCharVal);
        char secondChar = (char)(aCharVal + secondCharVal);

        return firstChar + FILE_SEPARATOR
                + secondChar + FILE_SEPARATOR;
    }
	public static boolean createDirIfNotExist(String filePath) {
		if(filePath != null && !"".equals(filePath)) {
			if(new File(filePath).exists()) {
				return true;
			}
		} else {
			logger.warn("filePath is empty!");
			return true;
		}
		int lastIndex = filePath.lastIndexOf(FILE_SEPARATOR);
		if(lastIndex != -1) {
			filePath = filePath.substring(0,lastIndex);
		} else {
			logger.warn(filePath + " not valid");
			return true;
		}
		if(filePath != null && !"".equalsIgnoreCase(filePath)) {
			try {
				FileUtils.forceMkdir(new File(filePath));
			} catch (IOException e) {
				logger.error(e.getMessage());
				return false;
			}
//			boolean result = ShellUtil.run("mkdir -p " + filePath);
//			if(!result) {
//				logger.error("mkdir " + filePath + " failed!");
//				return false;
//			}
		}
		return true;		
	}
}
