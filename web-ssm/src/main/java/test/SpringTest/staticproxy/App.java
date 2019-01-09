package test.SpringTest.staticproxy;

/**
 * @version 1.0
 * @类名: App.java
 * @描述:
 * @创建人: CM
 * @创建时间: 2019-1-8
 */
public class App {
    public static void main(String[] args) {
        //目标对象
        IUserDao target = new UserDao();
        //代理对象
        IUserDao proxy = new UserDaoProxy(target);
        proxy.save();
    }
}
