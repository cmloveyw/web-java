package test.SpringTest.dynamic;

/**
 * @version 1.0
 * @类名: App.java
 * @描述:
 * @创建人: CM
 * @创建时间: 2019-1-8
 */
public class App {
    public static void main(String[] args) throws Exception {
        //目标对象
        UserDao target = new UserDao();
        //代理对象
        //class com.sun.proxy.$Proxy0 内存中动态生成的代理对象
        IUserDao proxy = (IUserDao) new ProxyFactory(target).getProxyInstance();
        //执行
        proxy.save();
        proxy.update();
        System.out.println(proxy.getClass());
    }
}
