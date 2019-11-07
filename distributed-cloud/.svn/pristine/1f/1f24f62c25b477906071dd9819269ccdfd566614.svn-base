package com.distributed.common.store;

import com.distributed.common.utils.FileUtil;

import java.util.List;

/**
 * Created by Idan on 2018/1/17.
 * IO工具
 */
public class IoHelper {

    /**
     * //TODO 落盘的复杂实现
     * 如可规定每条日志的长度，不足补上，这样就可以用NIO来读取，然后根据position读取，应该更快速
     */

    /**
     * 下面是利用 BufferedWriter和BufferedReader进入文件读取
     */
    public static Boolean storeOneLine(String value,String folderPath, String fileName) {
        return FileUtil.storeOneLine(value, folderPath, fileName);
    }

    public static Boolean storeList(List<String> values, String folderPath, String fileName) {
        return FileUtil.storeList(values, folderPath, fileName);
    }

    public static String getLatestLine(String filePath) {
        return FileUtil.getLatestLine(filePath);
    }

    public static List<String> getLines(String filePath, Integer num) {
        return FileUtil.getLines(filePath, num);
    }

    public static List<String> getReverseFileLines(String filePath, Integer n) {
        return FileUtil.getReverseFileLines(filePath, n);
    }
}