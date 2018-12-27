package test.serialTest;

import java.io.*;

/**
 * @version 1.0
 * @类名: SerialDemo.java
 * @描述:
 * @创建人: CM
 * @创建时间: 2018-12-13
 */
public class SerialDemo {
    public static void main(String[] args) throws IOException, ClassNotFoundException {
        //序列化
        FileOutputStream fos = new FileOutputStream("D:\\object1.out");
        ObjectOutputStream oos = new ObjectOutputStream(fos);
        User user = new User("cm", "123456", "男");
        oos.writeObject(user);
        oos.flush();
        oos.close();
        //反序列化
        FileInputStream fis = new FileInputStream("D:\\object1.out");
        ObjectInputStream ois = new ObjectInputStream(fis);
        User user1 = (User) ois.readObject();
        System.out.println(user1.getUserName() + "##" + user1.getPassword() + "#" + user1.getSex());
    }

}


