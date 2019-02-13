package test.SpringTest.JDKProxy;

/**
 * @version 1.0
 * @类名: UserService.java
 * @描述:
 * @创建人: CM
 * @创建时间: 2019-2-13
 */
public interface UserService {
    public User getById(String id);

    public int add(User user);

    public boolean delete(String id);
}
