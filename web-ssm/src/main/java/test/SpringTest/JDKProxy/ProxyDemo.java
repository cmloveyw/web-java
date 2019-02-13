package test.SpringTest.JDKProxy;

/**
 * @version 1.0
 * @类名: ProxyDemo.java
 * @描述:
 * @创建人: CM
 * @创建时间: 2019-2-13
 */
public class ProxyDemo {
    public static void main(String[] args) {
        UserService userService = new UserServiceImpl();
        UserService proxy = (UserService) new ProxyFactory(userService).getProxy();
        proxy.add(new User());
        proxy.delete("");
        proxy.getById("");
    }
}
