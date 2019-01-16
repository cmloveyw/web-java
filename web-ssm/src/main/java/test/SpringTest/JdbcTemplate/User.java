package test.SpringTest.JdbcTemplate;

/**
 * @version 1.0
 * @类名: User.java
 * @描述:
 * @创建人: CM
 * @创建时间: 2019-1-10
 */
public class User {
    private int id;
    private String userName;
    private String userPhone;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserPhone() {
        return userPhone;
    }

    public void setUserPhone(String userPhone) {
        this.userPhone = userPhone;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", userName='" + userName + '\'' +
                ", userPhone='" + userPhone + '\'' +
                '}';
    }
}
