package test.sixiang;

import java.io.*;

/**
 * @version 1.0
 * @类名: FileTest.java
 * @描述: 文件读取
 * @创建人: CM
 * @创建时间: 2018-6-11
 */
public class FileTest {
    public static void main(String[] args) throws IOException {
        String path = "F:/testProject/test.txt";
        File file = new File(path);
        if (!file.exists() || file.isDirectory()) {
            throw new FileNotFoundException();
        }
        StringBuffer sb = new StringBuffer();
        BufferedReader bufferedReader = new BufferedReader(new FileReader(file));
        String temp = null;
        temp = bufferedReader.readLine();
        while (temp != null) {
            sb.append(temp + "\n");
            if (temp.contains("红")) {
                System.out.println("找到啦！！");
            }
            temp = bufferedReader.readLine();
        }
        System.out.println(sb.toString());

        BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(file));
        int index = sb.indexOf("红");
        try {
            if (index > 0) {
                System.out.println("找到了！！！！");
                sb = sb.insert(index, "woshi");
                System.out.println(sb.toString());
                System.out.println(index);
                bufferedWriter.write(sb.toString());
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            bufferedWriter.flush();
            bufferedWriter.close();
        }


    }
}
