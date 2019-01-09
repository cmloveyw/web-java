package test.SpringTest.MyAop;

import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @version 1.0
 * @类名: UserDao.java
 * @描述: 目标对象
 * @创建人: CM
 * @创建时间: 2019-1-8
 */
@Component("userDao")
public class UserDao implements IUserDao {
    @Resource
    private MyAop myAop;

    @Override
    public void save() {
        //开始事务
        myAop.beginTrans();
        System.out.println("数据已保存。。");
        //提交事务
        myAop.commitTrans();
    }
}
