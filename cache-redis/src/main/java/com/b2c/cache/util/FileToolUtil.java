package com.b2c.cache.util;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;

/**
 * 文件操作工具类
 */
public class FileToolUtil {

    /**
     * 判断文件是否存在
     * @param file
     */
    public static void judeFileExists(File file) throws IOException {

        if (file.exists ()) {
            System.out.println ("file exists");
        } else {
            System.out.println ("file not exists, create it ...");
            file.compareTo (file);
        }

    }

    /**
     * 判断文件夹是否存在
     * @param file
     */
    public static void judeDirExists(File file) throws Exception {
        if (file.exists ()) {
            if (file.isDirectory ()) {
                System.out.println ("已经存在");
            } else {
                System.out.println ("已经存在文件，不能创建");
            }
        } else {
            System.out.println ("不存在，需要创建");
            boolean mkdir = file.mkdir ();
            if (mkdir){
                System.out.println ("创建创建成功");
            }
        }

    }

    /**
     * 拷贝文件
     * @param source
     * @param dest
     * @throws IOException
     */
    public static void copyFile(File source, File dest) throws IOException {
        Files.copy(source.toPath(), dest.toPath());
    }

    /**
     * 将文件流转换并输出字节数组
     * @param inputStream
     * @return
     * @throws Exception
     */
    public static byte[] inputStreamToByte(FileInputStream inputStream) throws IOException {
        ByteArrayOutputStream output = new ByteArrayOutputStream ();
        byte[] buffer = new byte[1024 * 4];
        int n = 0;
        while (-1 != (n = inputStream.read (buffer))) {
            output.write (buffer, 0, n);
        }
        return output.toByteArray ();
    }
}
