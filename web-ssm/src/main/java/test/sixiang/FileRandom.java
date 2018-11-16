package test.sixiang;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;

/**
 * @version 1.0
 * @类名: FileRandom.java
 * @描述:
 * @创建人: CM
 * @创建时间: 2018-6-11
 */
public class FileRandom {
    public static void main(String[] args) throws IOException {
        String path = "F:/testProject/test.txt";
        File file = new File(path);
        if (!file.exists() || file.isDirectory()) {
            throw new FileNotFoundException();
        }
        StringBuffer sb = new StringBuffer();
        //BufferedReader bufferedReader = new BufferedReader(new FileReader(file));
        RandomAccessFile raf = new RandomAccessFile(path, "rw");
        byte[] buff = new byte[8];
        int temp = 0;
        String str = "红";
        String txt = "我来咯！！";
        while ((temp = raf.read(buff)) > 0) {
            raf.seek(temp);
            System.out.println(temp);
            raf.write(txt.getBytes());
        }
        System.out.println(sb.toString());
        raf.close();
    }
}
