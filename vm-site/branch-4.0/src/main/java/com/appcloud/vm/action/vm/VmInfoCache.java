package com.appcloud.vm.action.vm;

import org.apache.log4j.Logger;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by stone on 2016/6/7.
 */
public class VmInfoCache {
    public static final Long serialVersionId = 1L;
    private static Logger logger = Logger.getLogger(VmInfoCache.class);
    //单例模式
    private static VmInfoCache cacheInstance;

    private static Map<String, Object> cache = new HashMap<>();

    public static void refresh() {
        cache.clear();
    }


    public static VmInfoCache getCacheInstance() {
        if (cacheInstance == null) {
            logger.info("主机信息缓存启动");
            cacheInstance = new VmInfoCache();
            new refreshThread().run();
        }
        return cacheInstance;
    }

    public static void cache(String name, Object obj) {
        cache.put(name, obj);
    }

    public static Object get(String name) {
        return cache.get(name);
    }
}

class refreshThread extends Thread {
    @Override
    public void run() {
        while (true) {
            try {
                VmInfoCache.refresh();
                sleep(1000 * 3600);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
