package appcloud.api.checkutils;

/**
 * Created by lizhenhao on 2017/10/11.
 */
public class StringChecker {

    public static boolean empty(String s) {
        if (s == null || s.length() == 0 || s.equals("null")) return true;
        else return false;
    }
}
