package com.appcloud.vm.common;

import com.appcloud.vm.util.PropsUtil;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * 阿里云实例资源规格对照表
 *
 * @author 包鑫彤
 * @create 2016-07-26-16:55
 * @since 1.0.0
 */
public class AliYunInstanceSourceCompareTable {
    public static AliYunInstanceSourceCompareTable instanceSourceCompareTable;

    public static AliYunInstanceSourceCompareTable getInstance() {
        if (instanceSourceCompareTable == null) {
            synchronized (AliYunInstanceSourceCompareTable.class) {
                if (instanceSourceCompareTable == null) {
                    instanceSourceCompareTable = new AliYunInstanceSourceCompareTable();
                }
            }
        }
        return instanceSourceCompareTable;
    }

    public Properties getCompareTables() {
        Properties properties = PropsUtil.loadProps("AliYunInstanceSourceCompareTableFile.properties");
        return properties;
    }
}
