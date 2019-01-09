package test.SpringTest.MyAop1;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @version 1.0
 * @类名: App.java
 * @描述:
 * @创建人: CM
 * @创建时间: 2019-1-8
 */
public class App {
    public static void main(String[] args) {
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("spring-aop1.xml");
        IUserDao proxy = (IUserDao) applicationContext.getBean("userProxy");
        try {
            proxy.save();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
