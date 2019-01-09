package test.SpringTest.staticproxy;

/**
 * @version 1.0
 * @类名: UserDaoProxy.java
 * @描述: 代理对象（静态代理）
 * @创建人: CM
 * @创建时间: 2019-1-8
 */
public class UserDaoProxy implements IUserDao {

    private IUserDao target;

    public UserDaoProxy(IUserDao target) {
        this.target = target;
    }

    @Override
    public void save() {
        System.out.println("开始事务。。。");
        target.save();
        System.out.println("提交事务。。。");
    }
}
