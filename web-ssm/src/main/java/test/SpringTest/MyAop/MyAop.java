package test.SpringTest.MyAop;

import org.springframework.stereotype.Component;

/**
 * @version 1.0
 * @类名: MyAop.java
 * @描述:
 * @创建人: CM
 * @创建时间: 2019-1-8
 */
@Component
public class MyAop {
    public void beginTrans() {
        System.out.println("开始事务。。。");
    }

    public void commitTrans() {
        System.out.println("提交事务。。。");
    }
}
