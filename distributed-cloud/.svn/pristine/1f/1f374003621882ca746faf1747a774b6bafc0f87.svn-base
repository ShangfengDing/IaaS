package appcloud.distributed.util;

/**
 * Created by lizhenhao on 2017/12/6.
 */
public class StringUtil {

    public static String address(String ip,String port) {
        return ip.concat(":").concat(port);
    }

    public static boolean isEmpty(String str) {
        if (str == null || str.length() == 0) return true;
        else return false;
    }

    public static String sub8b(String value) {
        if (isEmpty(value) || value.length()<9) {
            return null;
        }
        String temp = value.substring(0, value.length()-8);
        return temp;
    }

    public static String sub8a(String value) {
        if (isEmpty(value) || value.length()<9) {
            return null;
        }
        String temp = value.substring(value.length()-9, value.length());
        return temp;
    }

    public static String[] split(String value, String sign) {
        value = value.trim();
        return value.split(sign);
    }

//    public static void main (String args[]) {
//        System.out.println(StringUtil.sub8b("ab12345678"));
//    }

}
