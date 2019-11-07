package appcloud.distributed.helper;

import appcloud.distributed.header.VersionHeader;
import com.distributed.common.utils.ClassUtil;

/**
 * Created by Idan on 2018/1/18.
 * 有顺序的加载类
 */
public class HelperLoader {

    public static void init() {
        Class<?>[] classList = {
                ClassHelper.class,
                BeanHelper.class,
                VersionHeader.class
        };
        for (Class<?> clazz: classList) {
            ClassUtil.loadClass(clazz.getName());
        }
    }

}
