package com.appcloud.vm.util;/**
 * Created by dell on 2016/6/9.
 */

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.log4j.Logger;


import java.io.File;
import java.io.IOException;

/**
 * 文件操作工具类
 *
 * @author 包鑫彤
 * @create 2016-06-09-22:51
 * @since 1.0.0
 */
public final class FileUtil {

    private static final Logger LOGGER = Logger.getLogger(FileUtil.class);

    /**
     * 获取真实文件信息（自动去掉文件路径）
     * @param fileName
     * @return
     */
    public static String getRealFileName(String fileName) {
        return FilenameUtils.getName(fileName);
    }

    /**
     * 创建文件
     * @param filePath
     * @return
     */
    public static File createFile(String filePath){
        File file;
        try{
            file = new File(filePath);
            File parentDir = file.getParentFile();
            if(!parentDir.exists()){
                FileUtils.forceMkdir(parentDir);
            }
        } catch (IOException e) {
            LOGGER.error("create file failure",e);
            throw new RuntimeException(e);
        }
        return  file;
    }

}
