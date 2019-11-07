package appcloud.common.util;

/**
 * @ClassName: MACUtils
 * @Description: TODO
 * @author wenchaoz361
 * @date 2013-4-12 下午3:55:22
 */
public class MACUtils {

    public static String Format(String macAddress) {
        macAddress = macAddress.toUpperCase();
        macAddress = macAddress.replaceAll("^([0-9a-zA-Z])(?=:)", "0$1");
        macAddress = macAddress.replaceAll("(?<=:)([0-9a-zA-Z])(?=:)", "0$1");
        macAddress = macAddress.replaceAll("(?<=:)([0-9a-zA-Z])$", "0$1");
        return macAddress; 
    }
}
