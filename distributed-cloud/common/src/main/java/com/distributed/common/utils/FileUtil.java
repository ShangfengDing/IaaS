package com.distributed.common.utils;

import org.apache.commons.io.input.ReversedLinesFileReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Idan on 2018/1/17.
 */
public class FileUtil {

    private static final Logger logger = LoggerFactory.getLogger(FileUtil.class);

    /**
     * 存储一行
     * @param value
     * @param folderPath
     * @param fileName
     * @return
     */
    public static Boolean storeOneLine(String value, String folderPath, String fileName) {
        BufferedWriter writer = getBufferWriter(folderPath, fileName);
        if (writer == null) {
            return false;
        }
        try {
            if (value != null) {
                writer.write(value + "\r\n");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        } finally {
            close(writer, null);
        }
        return true;
    }

    /**
     * 储存多行
     * @param values
     * @param folderPath
     * @param fileName
     * @return
     */
    public static Boolean storeList(List<String> values, String folderPath, String fileName) {
        BufferedWriter writer = getBufferWriter(folderPath, fileName);
        if (writer == null) {
            return false;
        }
        try {
            for (String value : values) {
                if (value != null) {
                    writer.write(value + "\r\n");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        } finally {
            close(writer, null);
        }
        return true;
    }

    /**
     * 读取最新的一行
     * @param filePath
     * @return
     */
    public static String getLatestLine(String filePath) {
        List<String> stringList = getLines(filePath, 1);
        if (CollectionUtil.isNotEmpty(stringList)) {
            return stringList.get(0);
        } else {
            return null;
        }
    }

    /**
     * 顺序读取读取多行数据
     * @param filePath
     * @param num
     * @return
     */
    public static List<String> getLines(String filePath, Integer num) {
        BufferedReader reader = getBufferReader(filePath);
        if (reader == null) {
            return null;
        }
        List<String> lineList = new ArrayList<>();
        try {
            for (int i = 0; i < num; i++) {
                String lineStr = reader.readLine();
                if (lineStr!=null) {
                    lineList.add(lineStr);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        } finally {
            close(null, reader);
        }
        return lineList;
    }


    /**
     * 倒序导读n行文件
     */
    public static List<String> getReverseFileLines(String filePath, Integer n) {
        File file = new File(filePath);
        List<String> lineList = new ArrayList<>();
        try {
            ReversedLinesFileReader object = new ReversedLinesFileReader(file, 4096, "UTF-8");
            int counter = 1;
            while (counter < n) {
                String line = object.readLine();
                if (line != null) {
                    lineList.add(line);
                } else {
                    break;
                }
                counter++;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return lineList;
    }


    /**
     * 关闭BufferedWriter和BufferedReader
     * @param writer
     * @param reader
     */
    public static void close(BufferedWriter writer, BufferedReader reader) {
        try {
            if (writer != null) {
                writer.close();
            }
            if (reader != null) {
                reader.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 获得BufferedReader
     * @param filePath
     * @return
     */
    public static BufferedReader getBufferReader(String filePath) {
        File file = new File(filePath);
        if (file == null) {
            return null;
        }
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new FileReader(file));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return reader;
    }

    /**
     * 获得BufferedWriter
     * @param folderPath
     * @param fileName
     * @return
     */
    public static BufferedWriter getBufferWriter(String folderPath, String fileName) {
        File folder = new File(folderPath);
        if (!folder.exists() && !folder.isDirectory()) {
            // 创建文件
            logger.warn("文件夹不存在!");
            folder.mkdirs();
        }
        // BufferedWriter 会自动创建文件
        File file = new File(folderPath+File.separator+fileName);
        BufferedWriter writer = null;
        try {
            writer = new BufferedWriter(new FileWriter(file, true));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return writer;
    }

}
