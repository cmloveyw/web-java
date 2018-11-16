package test.io;

import java.io.*;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

/**
 * @version 1.0
 * @类名: GZIPcompress.java
 * @描述: 用GZIP进行简单压缩
 * @创建人: CM
 * @创建时间: 2018-6-25
 */
public class GZIPcompress {
    public static void main(String[] args) {
        try {
            BufferedReader in = new BufferedReader(new FileReader("f:/data.txt"));
            BufferedOutputStream out = new BufferedOutputStream
                    (new GZIPOutputStream(new FileOutputStream("f:/data.gz")));
            System.out.println("Writing file");
            int c;
            while ((c = in.read()) != -1)
                out.write(c);
            in.close();
            out.close();
            System.out.println("Reading file");
            BufferedReader in2 = new BufferedReader(new InputStreamReader
                    (new GZIPInputStream(new FileInputStream("f:/data.gz"))));
            String s;
            while ((s = in2.readLine()) != null)
                System.out.println(s);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
