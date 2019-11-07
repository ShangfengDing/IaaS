package appcloud.common.util;

import java.util.UUID;

/**
 * @author lzc
 *
 */
public class UuidUtil {

	/**
	 * 随机生成一个32位的uuid
	 * @return
	 */
	public static String getRandomUuid() {
		return UUID.randomUUID().toString().replace("-", "");
	}
}
