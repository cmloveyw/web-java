package test.SpringTest.JDKProxy;

/**
 * @version 1.0
 * @类名: UserServiceImpl.java
 * @描述:
 * @创建人: CM
 * @创建时间: 2019-2-13
 */
public class UserServiceImpl implements UserService {
    @Override
    public User getById(String id) {
        System.out.println("执行了getById方法");
        return null;
    }

    @Override
    public int add(User user) {
        System.out.println("执行了add方法");
        return 0;
    }

    @Override
    public boolean delete(String id) {
        System.out.println("执行了delete方法");
        return false;
    }
}
