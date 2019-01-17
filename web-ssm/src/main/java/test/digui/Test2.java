package test.digui;

import java.io.File;

/**
 * @version 1.0
 * @类名: Test2.java
 * @描述: 删除文件(只能删除空文件夹 ， 有子文件删除不掉 ， 删除的文件不会进入回收站)
 * @创建人: CM
 * @创建时间: 2019-1-17
 */
public class Test2 {
    public static void main(String[] args) {
        //File file = new File("E://TestFile");
        File file = new File("E://TestFile");
        System.out.println(deleteFile(file));
    }

    public static boolean deleteFile(File file) {
        boolean isDelete = false;
        if (file.exists()) {
            File[] files = file.listFiles();
            if (files.length > 0) {
                for (File file1 : files) {
                    //如果是文件直接删除
                    if (file1.isFile()) {
                        file1.delete();
                    } else {
                        //如果是文件夹则递归删除
                        deleteFile(file1);
                    }
                }
            }
            isDelete = file.delete();
        }
        return isDelete;
    }
}
