package appcloud.core.sdk.utils;

import java.security.MessageDigest;

public class ParameterHelper {


	public ParameterHelper(){}

	public static String md5Sum(byte[] buff) {
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			byte[] messageDigest = md.digest(buff);
			return Base64Helper.encode(messageDigest);
		}catch (Exception e) {}
		
		return null;
	}
}
