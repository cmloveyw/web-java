package test.digui;

import java.io.*;

/**
 * @version 1.0
 * @类名: Test3.java
 * @描述: 文件拷贝
 * @创建人: CM
 * @创建时间: 2019-1-17
 */
public class Test3 {
    public static void main(String[] args) throws IOException {
        File src = new File("E://T1");
        File target = new File("E://T2");
        copyFile(src, target);
    }

    public static void copyFile(File src, File target) throws IOException {
        //在目标文件夹创建原文件
        /*File newDir = new File(target, src.getName());
        newDir.mkdir();*/
        File[] files = src.listFiles();
        if (files.length > 0) {
            for (File file : files) {
                if (file.isFile()) {
                    //如果是文件就用io流写文件
                    BufferedInputStream bis = new BufferedInputStream(new FileInputStream(file));
                    BufferedOutputStream bos = new BufferedOutputStream(
                            new FileOutputStream(
                                    new File(target, file.getName())));
                    int b = 0;
                    while ((b = bis.read()) != -1) {
                        bos.write(b);
                    }
                    bis.close();
                    bos.close();
                } else {
                    //如果是文件夹就递归调用
                    File newDir = new File(target, file.getName());
                    newDir.mkdir();
                    copyFile(file, newDir);
                }
            }
        }
    }
}
