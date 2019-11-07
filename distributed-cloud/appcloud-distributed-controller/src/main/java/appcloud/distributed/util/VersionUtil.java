package appcloud.distributed.util;

import java.text.ParseException;
import java.util.Date;

/**
 * Created by Idan on 2018/1/13.
 * 版本号由两部分组成，前8位是递增数字，后8位是时间
 */
public class VersionUtil {


    //1. 获得最新的，版本号从1开始
    public static Long getNewVersion(Long oldVersion) {
        return oldVersion==null?1:oldVersion+1;
    }

    //2. 比较版本号是否相同， 注意此处未考虑不同版本号之间的
    public static Integer compare(Long version1, Long version2) {
        Boolean rs = isEqual(version1, version2);
        if (rs) {
            return 0;
        } else {
            Long result = version2-version1;
            return Math.abs(result.intValue());
        }
    }
    // 是否相等
    public static Boolean isEqual(Long version1, Long version2) {
        if (NumUtil.isEmpty(version1) || NumUtil.isEmpty(version2)) {
            return false;
        }
        return version1.equals(version2);
    }

    //3. 得到版本号
    public static Integer getNum(String version) {
        String temp = StringUtil.sub8b(version);
        return temp == null?0:Integer.valueOf(temp);
    }

    //4. 得到时间
    public static Date getDate(String version) {
        String temp = StringUtil.sub8a(version);
        if (temp ==null) {
            return null;
        }
        try {
            return TimeUtil.ConvertToDate(temp, null);
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static void main (String args[]) {
        Boolean rs = VersionUtil.isEqual(0L, 0L);
        System.out.println(rs);
    }

}
