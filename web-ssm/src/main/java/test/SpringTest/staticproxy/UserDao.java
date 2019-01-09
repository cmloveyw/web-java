package test.SpringTest.staticproxy;

/**
 * @version 1.0
 * @类名: UserDao.java
 * @描述: 目标对象
 * @创建人: CM
 * @创建时间: 2019-1-8
 */
public class UserDao implements IUserDao {
    @Override
    public void save() {
        System.out.println("数据已保存。。");
    }
}
