package appcloud.vmschduler.utils;



public class VSUtil {
    public static boolean isEmpty(Object obj) {
        if (obj == null) {
            return true;
        } else if ("".equalsIgnoreCase(obj.toString())) {
            return true;
        } else {
            return false;
        }
    }
}
