package test.SpringTest.JdbcTemplate;

import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.List;

/**
 * @version 1.0
 * @类名: App.java
 * @描述:
 * @创建人: CM
 * @创建时间: 2019-1-10
 */
public class App {
    ApplicationContext applicationContext = new ClassPathXmlApplicationContext("bean.xml");

    @Test
    public void testApp() {
        UserDao1 userDao = (UserDao1) applicationContext.getBean("userDao1");
        try {
            //userDao.save();
            //userDao.queryAll();
            List<User> list = userDao.query();
            System.out.println(list);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
