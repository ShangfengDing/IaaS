package appcloud.vp.util;

import java.util.HashSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class VolumeFormatUtil {

	private final static HashSet<String> VALID_IMAGE_FORMAT = new HashSet<String>();
	
	static {
		VALID_IMAGE_FORMAT.add("qcow2");
		VALID_IMAGE_FORMAT.add("raw");
	}

	public static boolean ValidImageFS(String fmt) {
		if(VALID_IMAGE_FORMAT.contains(fmt)) {
			return true;
		}
		return false;
	}
	
	public static boolean ValidSnapShotTag(String snapshotTag) {
		Pattern p = Pattern.compile("^[\\w|\\-\\_]+$");
		Matcher m = p.matcher(snapshotTag);
		while (m.find()) {
			System.out.println(m.group(0));
			return true;
		}
		
		return false;
	}

}
